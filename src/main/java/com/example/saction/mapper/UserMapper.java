package com.example.saction.mapper;

import com.example.saction.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    //查
    List<User> findAllUser(); //查询全部用户信息,返回的类型应该是一个用户集合(所以我们要用list来接)
    User findUserByAccount(@Param("account") String account);
    User findUserByAccountAndPassword(@Param("account") String account, @Param("password") String password);
    User findUserById(@Param("id") int id); // 按照id找用户

    //改
    int updateUser(@Param("id") User user);
    int updateUserInfo(@Param("user") User user);
    int updateUserPriority(@Param("user") User user);

    //增
    int addUser(@Param("account") String account, @Param("password") String password);

    //删
    int deleteUser(@Param("id") int id);

    //重置密码
    int updateUserPassword(@Param("account") String account, @Param("password") String password);

    //记录踪迹
    int updateUserTrack(@Param("account") String account, @Param("ip") String ip, @Param("time") Long time);

    //更改昵称
    int updateUserNameNick(@Param("account") String account, @Param("nameNick") String nameNick);
}
