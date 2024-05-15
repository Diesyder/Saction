package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAct {
    private int id; // 序号
    private int userid; // 用户id
    private int actid; // 活动id
    private Long time; // 创建时间
    private boolean isAskcompletion; // 是否申请完结
    private boolean isCompleted; // 是否已经完结
}
