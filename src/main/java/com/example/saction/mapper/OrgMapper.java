package com.example.saction.mapper;

import com.example.saction.pojo.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgMapper {

    List<Organization> findAllOrg();
    Organization findOrgById(@Param("id") int id);
    int deleteOrg(@Param("id") int id);

}
