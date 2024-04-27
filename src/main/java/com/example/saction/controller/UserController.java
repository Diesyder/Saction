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
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    //查询所有页面
    @GetMapping("/findUserList")
    public String findStudentList(Model model) {
        List<User> userList=userService.findAllUser();
        //传进去的是一个键值对
        model.addAttribute("userList",userList);//传进前端的东西
        //返回值==html文件名
        return "findUserList";
    }

    // 登录验证
    @PostMapping("/loginCheck")
    @ResponseBody
    public Result loginController(@RequestParam String account, @RequestParam String password, @RequestParam String captcha, HttpSession session, HttpServletRequest request) {
        // 如果已经登录那么禁止再次登录
        String userLast = (String) session.getAttribute("accountCurrent");
        if (userLast != null) {
            return Result.error("101","用户“" + userLast + "”已登录！");
        }
        // 查找account对应的数据行
        User userExist = userService.loginIn(account);
        User userRight = userService.loginIn(account, password);
        if (userExist != null && userRight != null) {
            // 判断验证码
            String imageCode = (String) session.getAttribute("imageCode");
            if(!imageCode.equals(captcha)){
                return Result.error("000","验证码错误！");
            }
            // 记录该账户最后的登录时间戳和地点
            String ipAddress = "127.0.0.1";
            try {
                InetAddress localHost = InetAddress.getLocalHost();
                ipAddress = localHost.getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            userService.updateUserTrack(account, ipAddress, System.currentTimeMillis());
            // 将该账户存入session
            // HttpSession session = request.getSession();
            session.setAttribute("accountCurrent", account);
            return Result.success(userRight,"登录成功！");
        }
        if (userExist == null) {
            return Result.error("001","该账号不存在！");
        }
        if (userRight == null) {
            return Result.error("002","密码错误！");
        }
        return Result.error("003","未知错误！");
    }

    @GetMapping("/getCode")
    @ResponseBody
    public void getCode(HttpServletResponse response, HttpSession session) throws Exception {
        //利用图片工具生成图片，第一个参数是生成的验证码，第二个参数是生成的验证码图片
        Object[] objs = Verify.createImage();
        //将验证码存入Session
        session.setAttribute("imageCode",objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    // 登出操作
    @PostMapping("/loginOut")
    @ResponseBody
    public Result loginOut(HttpServletRequest request) {
        // 如果未登录那么无法登出
        HttpSession session = request.getSession();
        String userLast = (String) session.getAttribute("accountCurrent");
        if (userLast == null) {
            return Result.error("102","您尚未登录！");
        }
        session.removeAttribute("accountCurrent");
        return Result.success(userLast,"登出成功！");
    }

    // 注册验证
    @PostMapping("/registerCheck")
    @ResponseBody
    public Result registerController(@RequestParam String account, @RequestParam String password) {
        // 查找id对应的数据行
        User userTemp = userService.loginIn(account);
        if (userTemp != null) {
            return Result.error("004","该账号已存在！");
        }
        userService.register(account, password);
        User user = userService.loginIn(account);
        // 将昵称暂时设置为账号
        userService.updateUserNameNick(account, account);
        return Result.success(user,"注册成功！");
    }

    // 重置密码验证
    @PostMapping("/resetCheck")
    @ResponseBody
    public Result resetController(@RequestParam String account, @RequestParam String password) {
        // 查找id对应的数据行
        User user = userService.loginIn(account);
        if (user == null) {
            return Result.error("005","该账号不存在！");
        }
        if (user.getPriority() >= 9) {
            return Result.error("100","禁止修改管理员密码！");
        }
        if (user.getPassword().equals(password)) {
            return Result.error("006","该密码与原密码相同！");
        }
        userService.reset(account, password);
        return Result.success(user,"重置密码成功！");
    }

}
