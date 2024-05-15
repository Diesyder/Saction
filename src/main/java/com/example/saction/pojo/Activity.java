package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private int id; // 序号
    private String code; // 活动码
    private String type; // 活动类型
    private String name; // 活动名称
    private String actvenue; // 活动地点
    private Date signuptimeStart; // 活动报名开始时间
    private Date signuptimeEnd; // 活动报名结束时间
    private Date acttimeStart; // 活动开始时间
    private Date acttimeEnd; // 活动结束时间
    private String description; // 描述
    private int memberCurrent; // 当前报名人数
    private int memberMax; // 最大报名人数
    private Boolean isSigninNeed; // 是否需要签到
    private Boolean isSignoutNeed; // 是否需要签退
}
