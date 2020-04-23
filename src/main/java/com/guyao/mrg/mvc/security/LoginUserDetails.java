package com.guyao.mrg.mvc.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.guyao.mrg.mvc.menu.entity.Menu;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author guyao
 * @date 2019/9/30 3:11 下午
 */
@Data
@Builder
public class LoginUserDetails implements UserDetails {

    private String userId;

    private String username;

    private String password;

    private List<Menu> menus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return menus.stream().filter(m->StringUtils.isNotEmpty(m.getPermission())).map(m->new SimpleGrantedAuthority(m.getPermission())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUserDetails that = (LoginUserDetails) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
