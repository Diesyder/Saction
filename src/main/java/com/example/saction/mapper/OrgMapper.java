package com.example.saction.mapper;

import com.example.saction.pojo.Organization;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrgMapper {

    /* 删除 */
    // 根据id删除组织
    @Delete("delete from organization where id = #{id}")
    int deleteOrg(@Param("id") int id);

    /* 查找 */
    // 查找所有组织
    @Select("select * from organization")
    List<Organization> findAllOrg();
    // 根据id查找组织
    @Select("select * from organization where id = #{id}")
    Organization findOrgById(@Param("id") int id);

}
