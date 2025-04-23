package com.example.saction.mapper;

import com.example.saction.pojo.JoinOrg;
import com.example.saction.pojo.JoinAct;
import com.example.saction.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServiceMapper {

    // 查找所有加入组织事件
    @Select("select * from joinorg")
    List<JoinOrg> findAllJO();

    // 查找所有加入活动事件
    @Select("select * from joinact")
    List<JoinAct> findAllJA();

    // 根据userid查找加入组织情况
    @Select("select * from joinorg where userid = #{userid}")
    List<JoinOrg> findAllMyJO(@Param("userid") int userid);

    // 根据userid查找加入活动情况
    @Select("select * from joinact where userid = #{userid}")
    List<JoinAct> findAllMyJA(@Param("userid") int userid);

    // 根据id查找加入组织事件索引
    @Select("select * from joinorg where id = #{id}")
    JoinOrg findJOById(@Param("id") int id);

    // 根据id查找加入活动事件索引
    @Select("select * from joinact where id = #{id}")
    JoinAct findJAById(@Param("id") int id);

    // 根据id删除加入组织关联
    @Delete("delete from joinorg where id = #{id}")
    int deleteJO(@Param("id") int id);

    // 根据id删除加入活动关联
    @Delete("delete from joinact where id = #{id}")
    int deleteJA(@Param("id") int id);

    // 新增加入组织关联
    @Insert("insert into joinorg (userid, orgid, time) values (#{userid}, #{orgid}, #{time})")
    int joinOrg(@Param("userid") int userid, @Param("orgid") int orgid, @Param("time") Long time);

    // 新增加入活动关联
    @Insert("insert into joinact (userid, actid, time) values (#{userid}, #{actid}, #{time})")
    int joinAct(@Param("userid") int userid, @Param("actid") int actid, @Param("time") Long time);

}
