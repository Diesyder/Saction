package com.example.saction.controller;

import com.example.saction.pojo.*;
import com.example.saction.service.ServiceService;
import com.example.saction.service.UserService;
import com.example.saction.service.ActService;
import com.example.saction.service.OrgService;
import com.example.saction.utils.Result;
import org.apache.ibatis.ognl.Ognl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class pageController {

    @Resource
    UserService userService;
    @Resource
    ActService actService;
    @Resource
    OrgService orgService;
    @Resource
    ServiceService serviceService;

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
        List<JoinAct> jaList = serviceService.findAllJA();
        model.addAttribute("jaList", jaList);
        return "findActList";
    }

    // 前往查询某个活动的页面
    @GetMapping("act/findAct/{id}")
    public String findAct(@PathVariable("id") int id, Model model) {
        Activity act = actService.findAct(id);
        model.addAttribute("act", act);
        return "findAct";
    }

    @PostMapping("act/findActById")
    @ResponseBody
    public Result findActById(@RequestParam int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (id == -1) {
            if (session.getAttribute("actid") != null) {
                session.removeAttribute("actid");
            }
            return Result.success(id, "返回默认状态");
        }
        Activity act = actService.findAct(id);
        if(act == null) {
            return Result.error("101", "不存在此活动");
        }
        session.setAttribute("actid", id);
        return Result.success(act, "返还查找的活动信息");
    }

    // 前往查询所有组织的页面
    @GetMapping("org/findOrgList")
    public String findOrgList(Model model) {
        List<Organization> orgList = orgService.findAllOrg();
        model.addAttribute("orgList", orgList);
        List<JoinOrg> joList = serviceService.findAllJO();
        model.addAttribute("joList", joList);
        return "findOrgList";
    }

    // 前往查询某个组织的页面
    @GetMapping("org/findOrg/{id}")
    public String findOrgById(@PathVariable("id") int id, Model model) {
        Organization org = orgService.findOrg(id);
        model.addAttribute("org", org);
        return "findOrg";
    }

    @PostMapping("org/findOrgById")
    @ResponseBody
    public Result findOrgById(@RequestParam int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (id == -1) {
            if (session.getAttribute("orgid") != null) {
                session.removeAttribute("orgid");
            }
            return Result.success(id, "返回默认状态");
        }
        Organization org = orgService.findOrg(id);
        if(org == null) {
            return Result.error("101", "不存在此组织");
        }
        session.setAttribute("orgid", id);
        return Result.success(org, "返还查找的组织信息");
    }

    // 刷新推荐界面
    @GetMapping("act/refreshRecommend")
    public String refreshRecommend(Model model) {
        List<Activity> actListRandom = actService.findAllAct();
        Collections.shuffle(actListRandom);
        model.addAttribute("actListRandom", actListRandom);
        return "redirect:/main";
    }

    // 前往主界面
    @GetMapping("/main")
    public String jumpMain(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        // 按钮显示检查
        session.setAttribute("showBtn-1", "false");
        session.setAttribute("showBtn-2", "false");
        session.setAttribute("showBtn-4", "false");
        session.setAttribute("showBtn-5", "false");
        session.setAttribute("showBtn-6", "false");
        // 如果已登录则传入用户信息
        String userLast = (String) session.getAttribute("accountCurrent");
        User userTemp = userService.loginIn(userLast);
        if (userLast != null) {
            session.setAttribute("nameNick", userTemp.getNameNick());
            User user = userService.loginIn(userLast);
            // 管理员无法参加活动，因此不需要查看自身参加的活动和组织
            if (user.getPriority() == 1) {
                session.setAttribute("showBtn-1", "true");
                session.setAttribute("showBtn-4", "true");
                session.setAttribute("showBtn-6", "true");
            } else {
                session.setAttribute("showBtn-2", "true");
                session.setAttribute("showBtn-5", "true");
            }
        } else {
            session.setAttribute("nameNick", "未登录");
        }
        // 获取数据库内容
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        // 活动
        List<Activity> actList = actService.findAllAct();
        List<Activity> actListRandom = actService.findAllAct();
        if (session.getAttribute("actid") != null) {
            List<Activity> actListById = new ArrayList<>();
            for (Activity x : actList) {
                if (x.getId() == (int) session.getAttribute("actid")) {
                    actListById.add(x);
                    break;
                }
            }
            actList = actListById;
        }
        model.addAttribute("actList", actList);
        Collections.shuffle(actListRandom);
        model.addAttribute("actListRandom", actListRandom);
        // 组织
        List<Organization> orgList = orgService.findAllOrg();
        if (session.getAttribute("orgid") != null) {
            List<Organization> orgListById = new ArrayList<>();
            for (Organization x : orgList) {
                if (x.getId() == (int) session.getAttribute("orgid")) {
                    orgListById.add(x);
                    break;
                }
            }
            orgList = orgListById;
        }
        model.addAttribute("orgList", orgList);
        // 用户专属板块
        if (userTemp != null) {
            // 判断活动
            List<Activity> myjaList = new ArrayList<>();
            // 获取用户的所有加入活动事件
            List<JoinAct> myja = serviceService.findAllMyJA(userTemp.getId());
            // 对于每一个事件，获取对应的活动，并加入myjaList
            for (JoinAct ja : myja) {
                Activity act = actService.findAct(ja.getActid());
                if (act != null) {
                    myjaList.add(act);
                }
            }
            Collections.sort(myjaList, Comparator.comparingInt(Activity::getId)); // 排序
            model.addAttribute("myjaList", myjaList);
            // 判断组织
            List<Organization> myjoList = new ArrayList<>();
            List<JoinOrg> myjo = serviceService.findAllMyJO(userTemp.getId());
            for (JoinOrg jo : myjo) {
                Organization org = orgService.findOrg(jo.getOrgid());
                if (org != null) {
                    myjoList.add(org);
                }
            }
            Collections.sort(myjoList, Comparator.comparingInt(Organization::getId)); // 排序
            model.addAttribute("myjoList", myjoList);
        }
        // 将键值对传入
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

    // 删除组织
    @PostMapping("org/deleteOrg")
    @ResponseBody
    public Result deleteOrg(@RequestParam int id) {
        // 获取即将删除的用户对象
        Organization org = orgService.findOrg(id);
        // 删除用户对象
        orgService.deleteOrg(id);
        // 返还信息
        return Result.success(org, "返还删除的组织信息");
    }

    // 删除活动
    @PostMapping("act/deleteAct")
    @ResponseBody
    public Result deleteAct(@RequestParam int id) {
        // 获取即将删除的用户对象
        Activity act = actService.findAct(id);
        // 删除用户对象
        actService.deleteAct(id);
        // 返还信息
        return Result.success(act, "返还删除的活动信息");
    }

    // 离开组织
    @GetMapping("org/leaveOrg/{id}")
    public String leaveOrg(@PathVariable("id") int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userTemp = userService.loginIn((String) session.getAttribute("accountCurrent"));
        serviceService.deleteJO(id, userTemp.getId());
        return "redirect:/main";
    }

    // 离开活动
    @GetMapping("act/leaveAct/{id}")
    public String leaveAct(@PathVariable("id") int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userTemp = userService.loginIn((String) session.getAttribute("accountCurrent"));
        serviceService.deleteJA(id, userTemp.getId());
        return "redirect:/main";
    }

    // 申请加入组织
    @GetMapping("/org/apply/{id}")
    public String applyOrg(@PathVariable("id") int orgid, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // 获取用户
        User user = userService.loginIn((String) session.getAttribute("accountCurrent"));
        int userid = user.getId();
        // 判断用户是否已加入组织
        List<JoinOrg> joList = serviceService.findAllJO();
        for (JoinOrg jo : joList) {
            if (jo.getUserid() == userid && jo.getOrgid() == orgid) {
                return "redirect:/closePage";
            }
        }
        serviceService.applyOrg(userid, orgid, System.currentTimeMillis());
        return "redirect:/closePage";

    }@GetMapping("/closePage")
    public String closePage() {
        return "closePage";
    }

}
