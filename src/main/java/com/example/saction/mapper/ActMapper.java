package com.example.saction.mapper;

import com.example.saction.pojo.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActMapper {

    /* 删除 */
    // 根据id删除活动
    @Delete("delete from activity where id = #{id}")
    int deleteAct(@Param("id") int id);

    /* 查找 */
    // 查找所有活动
    @Select("select * from activity")
    List<Activity> findAllAct();
    // 根据id查找活动
    @Select("select * from activity where id = #{id}")
    Activity findActById(@Param("id") int id);

}
