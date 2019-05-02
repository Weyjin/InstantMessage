package com.instant.message.controller;

import com.instant.message.entity.Group;
import com.instant.message.entity.GroupChat;
import com.instant.message.entity.User;
import com.instant.message.service.GroupChatService;
import com.instant.message.service.GroupService;
import com.instant.message.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api")
public class ApiController {

    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupChatService groupChatService;
    @Autowired
    private UserService userService;


    @RequestMapping("getGroups")
    @ResponseBody
    public List<Group> getGroups(){
        List<Group> groups=groupService.toList(new HashMap<>());
        List<String> list=new ArrayList<>();
        List<Object> objects=new ArrayList<>();
        for(Group g:groups){
            objects.add(g.getUsers());
            list.add(g.getName());
        }
        Map<String,Object> map=new HashMap<>();
        map.put("groups",list);
        map.put("users",objects);
        return groups;
    }

    @RequestMapping(value = "getGroupChats",method = RequestMethod.POST)
    @ResponseBody
    public List<GroupChat> getGroupChats(String userId){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        List<GroupChat> groupChats=groupChatService.toList(map);
        return groupChats;
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(String name,String password){
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("password",password);

        User user=userService.selectByOnly(map);

         map=new HashMap<>();
        if(user==null){
            map.put("msg","error");
            map.put("userName","");
            map.put("userId","");
            map.put("signature","");
        }else {
            map.put("msg","success");
            map.put("userName",user.getName());
            map.put("userId",user.getId());
            map.put("signature",user.getSignature());
        }
        return map;
    }
}
