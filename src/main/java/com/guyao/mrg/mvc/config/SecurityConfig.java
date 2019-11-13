package com.guyao.mrg.mvc.config;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.guyao.mrg.mvc.role.entity.Role;
import com.guyao.mrg.mvc.security.KaptcherFilter;
import com.guyao.mrg.mvc.security.LoginAuthenticationProvider;
import com.guyao.mrg.mvc.security.LoginUserDetails;
import com.guyao.mrg.mvc.user.entity.User;
import com.guyao.mrg.mvc.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guyao
 * @date 2019-09-04 09:25
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint entryPoint;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 解决不允许显示在iFrame的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

        http.addFilterBefore(new KaptcherFilter("/login"), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.authorizeRequests().antMatchers(antMatchers()).permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failureHandler).permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .rememberMe();
        http.sessionManagement().invalidSessionUrl("/login")
                .sessionFixation().migrateSession()
                .maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry())
                .expiredUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new LoginAuthenticationProvider(userDetailsService()));
    }

    private String[] antMatchers() {
        return new String[]{"/static/**","/webjars/**","/kaptcha","/publicKey","/favicon.ico"};
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if(user == null) {
                log.info("=====用户不存在:{}=====",username);
                throw new UsernameNotFoundException("用户不存在");
            }
            ArrayList<Menu> menus = new ArrayList<>();
            for (Role r : user.getRoleList()) {
                menus.addAll(menuService.findByRoleId(r.getId()));
            }
            log.info("=====用户{}：具有菜单{}=====",user.getUserName(),menus);
            log.info("=====用户{}：具有角色{}=====",user.getUserName(),user.getRoleList());
            return LoginUserDetails.builder().username(user.getAccount()).password(user.getPassword()).menus(menus).build();
        };
    }
}
