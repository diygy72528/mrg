package com.guyao.mrg.mvc.manage.controller;

import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.base.MrG;
import com.guyao.mrg.mvc.manage.entity.Kaptcha;
import com.guyao.mrg.mvc.manage.service.AdminControllerService;
import com.guyao.mrg.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guyao
 * @date 2019/10/10 3:31 下午
 */
@Controller
public class AdminController extends BaseController {


    @Autowired
    private AdminControllerService adminService;



    @GetMapping("/login")
    public String login() {
        return "/home/login";
    }

    @GetMapping("/kaptcha")
    public void createKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        adminService.generateKaptcha(request,response);
    }

    @PostMapping("/publicKey")
    @ResponseBody
    public AjaxResult generateKeys() {
        return AjaxResult.ok(adminService.generateKeys());
    }
}
