package com.gdkjxy.controller.manager;

import com.gdkjxy.pojo.BackendUser;
import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.service.manager.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/26
 * @Version 1.0
 */
@Controller
@RequestMapping("manage")
public class LoginManagerController {

    @Autowired
    BackendUserService backendUserService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("login")
    public String login(){
        return "backendlogin";
    }

    /**
     * 登陆处理
     * @param backendUser
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("dologin")
    public String doLogin(BackendUser backendUser, Model model,HttpServletRequest request,HttpServletResponse response){
        // 禁用浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        System.out.println("devUser:"+backendUser);
        //查询是否登陆成功
        int result = backendUserService.doLogin(backendUser);
        //获取用户
        BackendUser user = backendUserService.getLoginBackendUser(backendUser);
        HttpSession session = request.getSession();
        session.setAttribute("userSession",user);
        if (result == 0){
            model.addAttribute("error","账号或密码错误");
            return "devlogin";
        }else if (result == 2){
            model.addAttribute("error","账号或密码不为空");
            return "devlogin";
        }
        return "backend/main";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 禁用浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        HttpSession session = request.getSession();
        session.removeAttribute("userSession");
        response.sendRedirect("/index.jsp");
    }
}
