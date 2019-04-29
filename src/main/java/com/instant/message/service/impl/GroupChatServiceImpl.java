package com.instant.message.service.impl;

import com.instant.message.dao.GroupChatDao;
import com.instant.message.entity.GroupChat;
import com.instant.message.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("groupChatService")
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private GroupChatDao groupChatDao;

    @Override
    public boolean insert(GroupChat object) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(GroupChat object) {
        return false;
    }

    @Override
    public List<GroupChat> toList(Map<String, Object> map) {
        return groupChatDao.toList(map);
    }

    @Override
    public GroupChat selectByPrimaryKey(Integer id) {
        return groupChatDao.selectByPrimaryKey(id);
    }

    @Override
    public GroupChat selectByOnly(Map<String, Object> map) {
        return groupChatDao.selectByOnly(map);
    }
}
