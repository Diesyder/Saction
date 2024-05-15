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
    private int actid;
    private Long time;
    private boolean isAskcompletion;
    private boolean isCompleted;
}
