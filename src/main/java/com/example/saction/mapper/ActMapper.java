package com.example.saction.mapper;

import com.example.saction.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActMapper {
    //查
    List<Activity> findAllAct();
    Activity findActById(@Param("id") int id); // 按照id找活动
}
