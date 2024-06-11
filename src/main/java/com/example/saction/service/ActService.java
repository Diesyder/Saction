package com.example.saction.service;

import com.example.saction.mapper.ActMapper;
import com.example.saction.pojo.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActService {

    @Autowired
    ActMapper actMapper;

    //查
    public List<Activity> findAllAct() {
        return actMapper.findAllAct();
    }

    public Activity findActById(int id) {
        return actMapper.findActById(id);
    }
}
