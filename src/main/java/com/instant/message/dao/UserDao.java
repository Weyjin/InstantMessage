package com.instant.message.dao;

import com.instant.message.entity.Result;
import com.instant.message.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends IBaseDao<User,Integer> {
   List<Result> selectByGroupId(int groupId);

   Result selectByPrimaryKeyToMessage(int id);

}
