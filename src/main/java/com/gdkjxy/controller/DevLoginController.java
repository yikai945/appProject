package com.gdkjxy.controller;

import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.service.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class DevLoginController {

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
     * 登录控制器不受session拦截器拦截
     * 登录控制器后页面登出后后退页面页面任然停留在拦截器不拦截的页面,并且没有已经session
     * 登录后重定向至此方法进入拦截
     * @return
     */
    @RequestMapping("redirect")
    public String loginRedirect(){
        return "developer/main";
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
        if (result == 0){
            model.addAttribute("error","账号或密码错误");
            return "devlogin";
        }else if (result == 2){
            model.addAttribute("error","账号或密码不为空");
            return "devlogin";
        }
        //获取用户
        DevUser user = devUserService.getLoginDevUser(devUser);
        HttpSession session = request.getSession();
        session.setAttribute("devUserSession",user);
        return "redirect:redirect";
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("devUserSession");
        return "redirect:/index.jsp";
    }

    /**
     * 返回主页面
     * @return
     */
    @RequestMapping("flatform/main")
    public String mian(){
        return "developer/main";
    }
}
