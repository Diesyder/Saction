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
    @Delete("delete from joinorg where orgid = #{orgid} and userid = #{userid}")
    int deleteJO(@Param("orgid") int actid, @Param("userid") int userid);

    // 根据id删除加入活动关联
    @Delete("delete from joinact where actid = #{actid} and userid = #{userid}")
    int deleteJA(@Param("actid") int actid, @Param("userid") int userid);

    // 新增申请加入组织关联
    @Insert("insert into joinorg (userid, orgid, applytime) values (#{userid}, #{orgid}, #{applytime})")
    int applyOrg(@Param("userid") int userid, @Param("orgid") int orgid, @Param("applytime") Long time);

    // 确认加入组织关联，更新处理状态和处理时间
    @Update("update joinorg set " +
            "ispass = 1, " +
            "handletime = #{handletime} " +
            "where id = #{joinorg.id}")
    int joinOrg(@Param("joinorg") JoinOrg joinorg, @Param("handletime") Long time);

    // 新增加入活动关联
    @Insert("insert into joinact (userid, actid, applytime) values (#{userid}, #{actid}, #{applytime})")
    int joinAct(@Param("userid") int userid, @Param("actid") int actid, @Param("applytime") Long time);

}
