package com.example.saction.mapper;

import com.example.saction.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /* 新增 */
    // 新增用户
    @Insert("insert into user (account, password) values (#{account}, #{password})")
    int addUser(@Param("account") String account, @Param("password") String password);

    /* 删除 */
    // 根据id删除用户
    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);

    /* 查找 */
    // 获取全部用户
    @Select("select * from user")
    List<User> findAllUser();
    // 根据id获取用户
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);
    // 根据账号获取用户
    @Select("select * from user where account = #{account}")
    User findUserByAccount(@Param("account") String account);
    // 根据账号获取密码获取用户
    @Select("select * from user where account = #{account} and password = #{password}")
    User findUserByAccountAndPassword(@Param("account") String account, @Param("password") String password);

    /* 修改 */
    // 根据id修改用户的账号与密码
    @Update("update user set account = #{user.account}, password = #{user.password} where id = #{user.id}")
    int updateUser(@Param("id") User user);
    // 根据id修改用户的个人信息
    @Update("update user set " +
            "account = #{user.account}, " +
            "nameNick = #{user.nameNick}, " +
            "nameReal = #{user.nameReal}, " +
            "schoolId = #{user.schoolId}, " +
            "schoolClass = #{user.schoolClass}, " +
            "schoolMajor = #{user.schoolMajor}, " +
            "schoolFaculty = #{user.schoolFaculty}, " +
            "phone = #{user.phone}, " +
            "email = #{user.email}, " +
            "description = #{user.description}, " +
            "gender = #{user.gender} " +
            "where id = #{user.id}")
    int updateUserInfo(@Param("user") User user);
    // 根据id修改用户的权限
    @Update("update user set priority = #{user.priority} where id = #{user.id}")
    int updateUserPriority(@Param("user") User user);
    // 根据账号修改ip地址与登录时间
    @Update("update user set timeLoginLast = #{time}, ipLoginLast = #{ip} where account = #{account}")
    int updateUserTrack(@Param("account") String account, @Param("ip") String ip, @Param("time") Long time);
    // 根据账号重置密码
    @Update("update user set password = #{password} where account = #{account}")
    int updateUserPassword(@Param("account") String account, @Param("password") String password);
    // 根据账号更改昵称
    @Update("update user set nameNick = #{nameNick} where account = #{account}")
    int updateUserNameNick(@Param("account") String account, @Param("nameNick") String nameNick);

}
