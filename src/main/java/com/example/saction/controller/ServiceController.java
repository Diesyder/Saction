package com.example.saction.controller;

import com.example.saction.pojo.Activity;
import com.example.saction.pojo.JoinOrg;
import com.example.saction.pojo.JoinAct;
import com.example.saction.pojo.Organization;
import com.example.saction.pojo.User;
import com.example.saction.service.ActService;
import com.example.saction.service.OrgService;
import com.example.saction.service.ServiceService;
import com.example.saction.service.UserService;
import com.example.saction.utils.Result;
import com.example.saction.utils.Verify;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Resource
    UserService userService;
    @Resource
    ActService actService;
    @Resource
    OrgService orgService;
    @Resource
    ServiceService serviceService;

    // 加入组织
    @PostMapping("/joinOrg")
    @ResponseBody
    public Result joinOrg(@RequestParam int userid, @RequestParam int orgid) {
        // 查找是否存在对应用户和对应组织
        User user = userService.loginIn(userid);
        if (user == null) {
            return Result.error("001","该账号不存在！");
        }
        Organization org = orgService.findOrg(orgid);
        if (org == null) {
            return Result.error("002","该组织不存在！");
        }
        List<JoinOrg> joList = serviceService.findAllJO();
        for (JoinOrg jo : joList) {
            if (jo.getUserid() == userid && jo.getOrgid() == orgid) {
                return Result.error("003", "该用户已加入该组织！");
            }
        }
        serviceService.joinOrg(userid, orgid, System.currentTimeMillis());
        return Result.success(user,"加入成功！");
    }

    // 加入活动
    @PostMapping("/joinAct")
    @ResponseBody
    public Result joinAct(@RequestParam int userid, @RequestParam int actid) {
        // 查找是否存在对应用户和对应组织
        User user = userService.loginIn(userid);
        if (user == null) {
            return Result.error("001","该账号不存在！");
        }
        Activity act = actService.findAct(actid);
        if (act == null) {
            return Result.error("002","该活动不存在！");
        }
        List<JoinAct> jaList = serviceService.findAllJA();
        for (JoinAct ja : jaList) {
            if (ja.getUserid() == userid && ja.getActid() == actid) {
                return Result.error("003", "该用户已参与该活动！");
            }
        }
        serviceService.joinAct(userid, actid, System.currentTimeMillis());
        return Result.success(user,"加入成功！");
    }

}
