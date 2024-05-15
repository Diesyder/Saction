package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinAct {
    private int id; // 序号
    private int userid; // 用户id
    private int actid; // 活动id
    private Long time; // 报名时间
    private boolean isSignin; // 是否签到
    private boolean isSignout; // 是否签退
}
