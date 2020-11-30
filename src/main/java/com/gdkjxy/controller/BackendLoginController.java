package com.gdkjxy.controller;

import com.gdkjxy.pojo.BackendUser;
import com.gdkjxy.service.BackendUserService;
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
public class BackendLoginController {

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
     * 登录控制器不受session拦截器拦截
     * 登录控制器后页面登出后后退页面页面任然停留在拦截器不拦截的页面,并且没有已经session
     * 登录后重定向至此方法进入拦截
     * @return
     */
    @RequestMapping("redirect")
    public String loginRedirect(){
        return "backend/main";
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
        System.out.println("devUser:"+backendUser);
        //查询是否登陆成功
        int result = backendUserService.doLogin(backendUser);
        if (result == 0){
            model.addAttribute("error","账号或密码错误");
            return "backendlogin";
        }else if (result == 2){
            model.addAttribute("error","账号或密码不为空");
            return "backendlogin";
        }
        //获取用户
        BackendUser user = backendUserService.getLoginBackendUser(backendUser);
        HttpSession session = request.getSession();
        session.setAttribute("userSession",user);
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
        session.removeAttribute("userSession");
        return "redirect:/index.jsp";
    }

    /**
     * 返回主页面
     * @return
     */
    @RequestMapping("flatform/main")
    public String mian(){
        return "backend/main";
    }
}
