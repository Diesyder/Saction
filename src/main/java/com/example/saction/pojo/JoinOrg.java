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
public class JoinOrg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 序号
    private int userid; // 用户id
    private int orgid; // 组织id
    private String type; // 事件类型
    private Long applytime; // 申请时间
    private int isPass; // 是否申请通过
    private Long handletime; // 申请受理时间
}
