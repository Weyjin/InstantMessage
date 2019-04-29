package com.instant.message.service.impl;

import com.instant.message.dao.UserDao;
import com.instant.message.entity.Result;
import com.instant.message.entity.User;
import com.instant.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public boolean insert(User object) {
        return userDao.insert(object)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(User object) {
        return false;
    }

    @Override
    public List<User> toList(Map<String, Object> map) {
        return userDao.toList(map);

    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public User selectByOnly(Map<String, Object> map) {
        return userDao.selectByOnly(map);
    }

    @Override
    public Result selectByPrimaryKeyToMessage(int id) {
        return userDao.selectByPrimaryKeyToMessage(id);
    }
}
