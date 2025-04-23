package com.example.saction.controller;

import com.example.saction.pojo.User;
import com.example.saction.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class vueController {

    @Resource
    UserService userService;

    @GetMapping("/data")
    @ResponseBody
    public String getData() {
        return "Hello, Vue.js!";
    }

    @GetMapping("/testdata/{id}")
    @ResponseBody
    public String getTestData(@PathVariable int id) {
        User user = null;
        user = userService.findUserById(id);
        if (user == null) return "该用户不存在";
        String test = user.getEmail();
        System.out.println(test);
        return test;
//        return "Hello, Vue.js!";
    }
}
