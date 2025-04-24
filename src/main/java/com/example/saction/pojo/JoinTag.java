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
public class JoinTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 索引
    private int tagid; // 标签id
    private int targetid; // 目标id
    private String type; // 目标类型
}
