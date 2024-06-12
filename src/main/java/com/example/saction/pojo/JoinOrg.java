package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinOrg {
    private int id; // 序号
    private int userid; // 用户id
    private int orgid; // 组织id
    private Long time; // 加入时间
}
