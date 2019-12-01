package com.lmh.blog.web.admin;

import com.lmh.blog.po.User;
import com.lmh.blog.service.UserService;
import com.lmh.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by lvmen on 2019/11/18
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping
    public String loginPage(){

        return "admin/login";
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model){

        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user", user);
        } else {
            attributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }

        return "admin/index";
    }

    /**
     * 用户登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }


}
