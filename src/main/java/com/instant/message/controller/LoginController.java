package com.instant.message.controller;

import com.instant.message.entity.User;
import com.instant.message.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public ModelAndView index(){
        ModelAndView view=new ModelAndView();
        view.setViewName("login/login");
        return view;
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> login(String name, String password, HttpSession session){
        Map<String,Object> map=new HashMap<>();

        map.put("name",name);
        map.put("password",password);

        User user=userService.selectByOnly(map);
        Map<String,Object> m=new HashMap<>();

        if(user!=null){
            UsernamePasswordToken token=new UsernamePasswordToken(user.getName(),user.getPassword());
            Subject subject=SecurityUtils.getSubject();
            subject.login(token);

            session.setAttribute("user",user);
            m.put("msg","成功");
            return new ResponseEntity<>(m,HttpStatus.OK);
        }else {
            m.put("msg","失败");
            return new ResponseEntity<>(m,HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session){
        ModelAndView view=new ModelAndView();
        view.setViewName("login/login");
        session.setAttribute("user",null);
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return view;
    }


}
