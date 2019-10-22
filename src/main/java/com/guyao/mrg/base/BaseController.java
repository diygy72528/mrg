package com.guyao.mrg.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author guyao
 * @date 2019/10/15 3:49 下午
 */
public abstract class BaseController {

    @Autowired
    private MrG mrG;

    /**
     * 登陆页面标志
     */
    @ModelAttribute
    protected void addModel(Model model) {
        model.addAttribute("logo",mrG.getProject().getName());
    }
}
