package com.instant.message.service.impl;

import com.instant.message.dao.LoginTokenDao;
import com.instant.message.entity.LoginToken;
import com.instant.message.entity.User;
import com.instant.message.service.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginTokenService")
public class LoginTokenServiceImpl implements LoginTokenService {
    @Autowired
    private LoginTokenDao tokenDao;
    @Override
    public User selectUserByToken(String token) {
        return tokenDao.selectUserByToken(token);
    }

    @Override
    public boolean insert(LoginToken token) {
        return tokenDao.insert(token)>0;
    }
}
