package com.example.saction.mapper;

import com.example.saction.pojo.JoinOrg;
import com.example.saction.pojo.JoinAct;
import com.example.saction.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceMapper {

    List<JoinOrg> findAllJO();
    List<JoinAct> findAllJA();
    List<JoinOrg> findAllMyJO(@Param("userid") int userid);
    List<JoinAct> findAllMyJA(@Param("userid") int userid);
    JoinOrg findJOById(@Param("id") int id);
    JoinAct findJAById(@Param("id") int id);
    int deleteJO(@Param("id") int id);
    int deleteJA(@Param("id") int id);
    int joinOrg(@Param("userid") int userid, @Param("orgid") int orgid, @Param("time") Long time);
    int joinAct(@Param("userid") int userid, @Param("actid") int actid, @Param("time") Long time);

}
