package com.example.saction.service;

import com.example.saction.mapper.UserMapper;
import com.example.saction.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //查
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    //改
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }
    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }
    public int updateUserPriority(User user) {
        return userMapper.updateUserPriority(user);
    }

    //增
    public int addUser(String account, String password) {
        return userMapper.addUser(account, password);
    }

    //删
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    //登录
    public User loginIn(String account) {
        return userMapper.findUserByAccount(account);
    }
    public User loginIn(String account, String password) {
        return userMapper.findUserByAccountAndPassword(account, password);
    }
    public User loginIn(int id) {
        return userMapper.findUserById(id);
    }

    //注册
    public int register(String account, String password) {
        return userMapper.addUser(account, password);
    }

    //重置密码
    public int reset(String account, String password) {
        return userMapper.updateUserPassword(account, password);
    }

    //更新登录时间和地点
    public int updateUserTrack(String account, String ip, Long time) {
        return userMapper.updateUserTrack(account, ip, time);
    }

    //更改昵称
    public int updateUserNameNick(String account, String nameNick) {
        return userMapper.updateUserNameNick(account, nameNick);
    }
}
