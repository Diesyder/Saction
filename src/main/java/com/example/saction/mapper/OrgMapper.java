package com.example.saction.mapper;

import com.example.saction.pojo.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgMapper {
    //查
    List<Organization> findAllOrg();
}
