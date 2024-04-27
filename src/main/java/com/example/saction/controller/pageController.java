package com.example.saction.controller;

import com.example.saction.pojo.User;
import com.example.saction.service.UserService;
import com.example.saction.utils.Result;
import com.example.saction.utils.Verify;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.io.OutputStream;

@Controller
public class pageController {

//    @Resource
//    PageService pageService;

    //前往主界面
    @GetMapping("/main")
    public String jumpMain(HttpServletRequest request, Model model) {
        // 如果已登录则传入用户信息
        HttpSession session = request.getSession();
        String userLast = (String) session.getAttribute("accountCurrent");
        if (userLast != null) {
            session.setAttribute("accountShow", userLast);
        } else {
            session.setAttribute("accountShow", "未登录");
        }
        return "main";
    }

    //前往登录界面
    @GetMapping("/login")
    public String jumpLogin(HttpServletRequest request) {
        // 如果已登录则前往用户界面而不是登录界面
        HttpSession session = request.getSession();
        String userLast = (String) session.getAttribute("accountCurrent");
        if (userLast != null) {
            return "user";
        }
        return "login";
    }

    //前往用户界面
    @GetMapping("/user")
    public String jumpUser() {
        return "user";
    }

}
