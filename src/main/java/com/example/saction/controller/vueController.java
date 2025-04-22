package com.example.saction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class vueController {

    @GetMapping("/data")
    @ResponseBody
    public String getData() {
        return "Hello, Vue.js!";
    }
}
