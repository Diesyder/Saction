package com.example.saction.controller;

import com.example.saction.pojo.User;
import com.example.saction.service.UserService;
import com.example.saction.utils.Result;
import com.example.saction.utils.Verify;
import com.fasterxml.jackson.databind.DatabindException;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
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
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.io.OutputStream;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

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
        // 密码账号是否匹配
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

    // 获取验证码
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

    // 设置权限等级
    @PostMapping("/updatePriority")
    @ResponseBody
    public Result resetController(@RequestParam int id, @RequestParam int priority) {
        // 查找id对应的数据行
        User user = userService.loginIn(id);
        if (user == null) {
            return Result.error("005","该账号不存在！");
        }
        user.setPriority(priority);
        userService.updateUserPriority(user);
        return Result.success(user,"设置权限等级成功！");
    }

    // 获取用户数据库
    @PostMapping("/getUser")
    @ResponseBody
    public Result getUserController(HttpSession session, HttpServletRequest request) {
        // 如果已登录则获取用户信息，如果未登录则返回消息
        String accountLast = (String) session.getAttribute("accountCurrent");
        if (accountLast != null) {
            User userExist = userService.loginIn(accountLast);
            return Result.success(userExist, "获取用户信息");
        } else {
            return Result.error("103", "当前无用户登录！");
        }
    }

    // 更新用户个人信息
    @PostMapping("/updateInfo")
    @ResponseBody
    public Result updateInfoController(
            @RequestParam int id,
            @RequestParam String account,
            @RequestParam String nameNick,
            @RequestParam String nameReal,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String gender,
            @RequestParam String address,
            @RequestParam String description
    ) {
        // 查找id对应的数据行
        User user = userService.loginIn(id);
        if (user == null) {
            return Result.error("005","该账号不存在！");
        }
        // 查找账号是否已经存在(不与其他账号同名但可与原账号同名)
        User userTemp = userService.loginIn(account);
        if (userTemp != null && !Objects.equals(account, user.getAccount())) {
            return Result.error("006","该账号已存在！");
        }
        // 对user类进行修改后,传入数据库
        user.setAccount(account); // 账号
        user.setNameNick(nameNick); // 昵称
        user.setNameReal(nameReal); // 真实姓名
        user.setEmail(email); // 邮箱
        user.setPhone(phone); // 电话
        user.setGender(gender); // 性别
        user.setAddress(address); // 地址
        user.setDescription(description); // 个人描述
        userService.updateUserInfo(user);
        return Result.success(user,"设置用户信息成功！");
    }

    // 删除用户
    @PostMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(@RequestParam int id) {
        // 获取即将删除的用户对象
        User user = userService.loginIn(id);
        // 删除用户对象
        userService.deleteUser(id);
        // 返还信息
        return Result.success(user, "返还删除的用户信息");
    }

}
