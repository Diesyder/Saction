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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // 确认加入组织
    @PostMapping("/joinOrg/{id}")
    @ResponseBody
    public Result joinOrg(@PathVariable("id") int id) {
        // 查找对应关联
        JoinOrg joinorg = serviceService.findJO(id);
        serviceService.joinOrg(joinorg, System.currentTimeMillis());
        return Result.success(joinorg,"加入成功！");
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
//// 生成推荐列表
//public List<Integer> generateRecommendations(int targetUserId, int topN, User userItemRatings, User userSimilarities) {
//    Map<Integer, Double> itemScores = new HashMap<>();
//    Map<Integer, Double> targetRatings = userItemRatings.get(targetUserId);
//
//    // 遍历相似用户
//    for (Map.Entry<Integer, Double> entry : userSimilarities.get(targetUserId).entrySet()) {
//        int similarUser = entry.getKey();
//        double similarity = entry.getValue();
//
//        // 遍历相似用户评分过的物品
//        for (Map.Entry<Integer, Double> ratingEntry : userItemRatings.get(similarUser).entrySet()) {
//            int item = ratingEntry.getKey();
//            double rating = ratingEntry.getValue();
//
//            // 排除目标用户已评分的物品
//            if (!targetRatings.containsKey(item)) {
//                itemScores.put(item, itemScores.getOrDefault(item, 0.0) + similarity * rating);
//            }
//        }
//    }
//    // 按得分排序并返回TopN
//    return itemScores.entrySet().stream()
//            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//            .limit(topN)
//            .map(Map.Entry::getKey)
//            .collect(Collectors.toList());
//}
