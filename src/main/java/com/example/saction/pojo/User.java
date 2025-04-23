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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 序号
    private String account; // 账号
    private String password; // 密码
    private int priority; // 权限等级
    private String nameNick; // 昵称
    private String nameReal; // 真实姓名
    private String schoolId; // 学号/工号
    private String schoolClass; // 班级/教学班级
    private String schoolMajor; // 专业
    private String schoolFaculty; // 院系
    private String description; // 个人描述
    private String email; // 邮箱
    private String phone; // 电话
    private String gender; // 性别
    private Long timeLoginLast; // 最后登录时间戳
    private String ipLoginLast; // 最后登录的ip地址// Getters and Setters
}
