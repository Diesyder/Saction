package com.example.saction.service;

import com.example.saction.mapper.OrgMapper;
import com.example.saction.pojo.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgService {

    @Autowired
    OrgMapper orgMapper;

    //查
    public List<Organization> findAllOrg() {
        return orgMapper.findAllOrg();
    }
}
