package com.guyao.mrg.mvc.utils;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.security.LoginUserDetails;
import com.guyao.mrg.mvc.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * @author guyao
 * @date 2019/11/6 4:54 下午
 */
public class SecurityUtils {
    public static LoginUserDetails getUserDetails() {
        return (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static List<Menu> getMenus() {
        return getUserDetails().getMenus();
    }
}
