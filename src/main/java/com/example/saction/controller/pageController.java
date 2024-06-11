package com.example.saction.controller;

import com.example.saction.pojo.User;
import com.example.saction.pojo.Organization;
import com.example.saction.pojo.Activity;
import com.example.saction.service.UserService;
import com.example.saction.service.ActService;
import com.example.saction.service.OrgService;
import com.example.saction.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class pageController {

    @Resource
    UserService userService;
    @Resource
    ActService actService;
    @Resource
    OrgService orgService;

    // 前往查询所有用户的页面
    @GetMapping("user/findUserList")
    public String findUserList(Model model) {
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "findUserList";
    }

    // 前往查询所有活动的页面
    @GetMapping("act/findActList")
    public String findActList(Model model) {
        List<Activity> actList = actService.findAllAct();
        model.addAttribute("actList", actList);
        return "findActList";
    }

    // 前往查询所有活动的页面
    @GetMapping("org/findOrgList")
    public String findOrgList(Model model) {
        List<Organization> orgList = orgService.findAllOrg();
        model.addAttribute("orgList", orgList);
        return "findOrgList";
    }

//    // 返还当前页面的selectionCurrent并选中
//    @GetMapping("page/getSelection")
//    @ResponseBody
//    public Result getSelection(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        String selection = (String) session.getAttribute("selectionCurrent");
//        if (selection == null) {
//            return Result.success(0, "获取默认标号");
//        }
//        return Result.success(selection, "获取当前选中标号");
//    }

    // 前往主界面
    @GetMapping("/main")
    public String jumpMain(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        // 按钮显示检查
        session.setAttribute("showBtn-1", "false");
        session.setAttribute("showBtn-2", "false");
        session.setAttribute("showBtn-4", "false");
        session.setAttribute("showBtn-5", "false");
        // 如果已登录则传入用户信息
        String userLast = (String) session.getAttribute("accountCurrent");
        User userTemp = userService.loginIn(userLast);
        if (userLast != null) {
            session.setAttribute("nameNick", userTemp.getNameNick());
            session.setAttribute("showBtn-2", "true");
            session.setAttribute("showBtn-5", "true");
            User user = userService.loginIn(userLast);
            if (user.getPriority() >= 9) {
                session.setAttribute("showBtn-1", "true");
                session.setAttribute("showBtn-4", "true");
                session.setAttribute("showBtn-6", "true");
            }
        } else {
            session.setAttribute("nameNick", "未登录");
        }
        // 获取数据库内容
        List<User> userList = userService.findAllUser();
        List<Activity> actList = actService.findAllAct();
        List<Organization> orgList = orgService.findAllOrg();
        // 将键值对传入
        model.addAttribute("userList", userList);
        model.addAttribute("actList", actList);
        model.addAttribute("orgList", orgList);
        return "main";
    }

    // 前往登录界面
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

    // 前往用户界面
    @GetMapping("/user")
    public String jumpUser() {
        return "user";
    }

}
