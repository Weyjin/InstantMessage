package com.instant.message.service;

import com.instant.message.entity.LoginToken;
import com.instant.message.entity.User;

public interface LoginTokenService {

    User selectUserByToken(String token);
    boolean insert(LoginToken token);

}
