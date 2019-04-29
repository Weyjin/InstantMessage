package com.instant.message.service.impl;

import com.instant.message.dao.GroupDao;
import com.instant.message.entity.Group;
import com.instant.message.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;


    @Override
    public boolean insert(Group object) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Group object) {
        return false;
    }

    @Override
    public List<Group> toList(Map<String, Object> map) {
        return groupDao.toList(map);
    }

    @Override
    public Group selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public Group selectByOnly(Map<String, Object> map) {
        return groupDao.selectByOnly(map);
    }
}
