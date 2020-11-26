package com.gdkjxy.controller.developer;

import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.service.developer.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("dev")
public class LoginDevController {

    @Autowired
    DevUserService devUserService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("login")
    public String login(){
        return "devlogin";
    }

    /**
     * 登陆处理
     * @param devUser
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("dologin")
    public String doLogin(DevUser devUser, Model model,HttpServletRequest request,HttpServletResponse response){
        System.out.println("devUser:"+devUser);
        //查询是否登陆成功
        int result = devUserService.doLogin(devUser);
        //获取用户
        DevUser user = devUserService.getLoginDevUser(devUser);
        HttpSession session = request.getSession();
        session.setAttribute("devUserSession",user);
        if (result == 0){
            model.addAttribute("error","账号或密码错误");
            return "devlogin";
        }else if (result == 2){
            model.addAttribute("error","账号或密码不为空");
            return "devlogin";
        }
        return "developer/main";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("devUserSession");
        response.sendRedirect("/index.jsp");
    }
}
