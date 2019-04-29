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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupChatService groupChatService;

    @RequestMapping("index")
    public ModelAndView index(HttpSession session){
        ModelAndView view=new ModelAndView();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        if(user==null){
            view.setViewName("login/login");
            return view;
        }
        view.setViewName("home/index");
        view.addObject("user",user);
        return view;
    }

    @RequestMapping("message/{id}")
    public ModelAndView message(@PathVariable("id") int id, HttpSession session){
        ModelAndView view=new ModelAndView();
        view.setViewName("home/message");
        User toUser=userService.selectByPrimaryKey(id);
        view.addObject("toUser",toUser);
        User user= (User) session.getAttribute("user");
        view.addObject("currentUser",user);
        return view;
    }
    @RequestMapping("toMessageGroupChat/{id}")
    public ModelAndView toMessageGroupChat(@PathVariable("id") int id, HttpSession session){
        ModelAndView view=new ModelAndView();
        view.setViewName("home/messageGroupChat");
        view.addObject("toGroup",id);
        User user= (User) session.getAttribute("user");
        view.addObject("currentUser",user);
        return view;
    }


    @RequestMapping("getGroups")
    @ResponseBody
    public Map<String,Object> getGroups(){
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
        return map;
    }

    @RequestMapping("getGroupChats")
    @ResponseBody
    public List<GroupChat> getGroupChats(){
        Map<String,Object> map=new HashMap<>();
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        map.put("userId",user.getId());
        List<GroupChat> groupChats=groupChatService.toList(map);
        return groupChats;
    }
}
