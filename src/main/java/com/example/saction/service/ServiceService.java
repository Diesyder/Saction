package com.example.saction.service;

import com.example.saction.mapper.ServiceMapper;
import com.example.saction.pojo.Activity;
import com.example.saction.pojo.JoinAct;
import com.example.saction.pojo.JoinOrg;
import com.example.saction.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    ServiceMapper serviceMapper;

    public List<JoinOrg> findAllJO() {
        return serviceMapper.findAllJO();
    }
    public List<JoinAct> findAllJA() {
        return serviceMapper.findAllJA();
    }
    public List<JoinOrg> findAllMyJO(int userid) {
        return serviceMapper.findAllMyJO(userid);
    }
    public List<JoinAct> findAllMyJA(int userid) {
        return serviceMapper.findAllMyJA(userid);
    }
    public JoinOrg findJO(int id) {
        return serviceMapper.findJOById(id);
    }
    public JoinAct findJA(int id) {
        return serviceMapper.findJAById(id);
    }
    public int deleteJO(int orgid, int userid) {
        return serviceMapper.deleteJO(orgid, userid);
    }
    public int deleteJA(int actid, int userid) {
        return serviceMapper.deleteJA(actid, userid);
    }
    public int applyOrg(int userid, int orgid, long time) {
        return serviceMapper.applyOrg(userid, orgid, time);
    }
    public int joinOrg(JoinOrg joinOrg, long time) {
        return serviceMapper.joinOrg(joinOrg, time);
    }
    public int joinAct(int userid, int actid, long time) {
        return serviceMapper.joinAct(userid, actid, time);
    }
}
