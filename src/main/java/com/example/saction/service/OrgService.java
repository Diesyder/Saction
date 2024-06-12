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

    public List<Organization> findAllOrg() {
        return orgMapper.findAllOrg();
    }
    public Organization findOrg(int id) {
        return orgMapper.findOrgById(id);
    }
    public int deleteOrg(int id) {
        return orgMapper.deleteOrg(id);
    }
}
