package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    private int id; // 序号/组织码
    private String type; // 组织类型
    private String name; // 组织名称
    private String isJoinAllowed; // 是否可申请加入
    private String description; // 描述
}
