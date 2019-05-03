package com.instant.message.dao;

import com.instant.message.entity.LoginToken;
import com.instant.message.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginTokenDao extends IBaseDao<LoginToken,Integer> {

    User selectUserByToken(String token);

}
