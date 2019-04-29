package com.instant.message.service;

import com.instant.message.entity.Result;
import com.instant.message.entity.User;

import java.util.List;

public interface UserService extends IBaseService<User,Integer> {

    Result selectByPrimaryKeyToMessage(int id);

}
