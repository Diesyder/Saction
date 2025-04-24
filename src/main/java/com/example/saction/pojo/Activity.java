package com.example.saction.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 序号/活动码
    private String type; // 活动类型
    private String name; // 活动名称
    private String actvenue; // 活动地点
    private Long signuptimeStart; // 活动报名开始时间
    private Long signuptimeEnd; // 活动报名结束时间
    private Long acttimeStart; // 活动开始时间
    private Long acttimeEnd; // 活动结束时间
    private String description; // 描述
    private int memberMax; // 最大报名人数
}
