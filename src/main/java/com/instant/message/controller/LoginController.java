package com.instant.message.controller;

import com.instant.message.entity.User;
import com.instant.message.service.LoginTokenService;
import com.instant.message.service.QRCodeService;
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
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private LoginTokenService tokenService;

    @RequestMapping("login")
    public ModelAndView index() throws IOException {
        ModelAndView view=new ModelAndView();
        view.setViewName("login/login");
        String uuid= UUID.randomUUID().toString();
        String code=qrCodeService.createQRCode(uuid,200,200);
        view.addObject("code",code);
        view.addObject("uuid",uuid);

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


    @RequestMapping(value = "login/token",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> loginToken(String code, HttpSession session){

        User user=tokenService.selectUserByToken(code);
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
        view.setViewName("redirect:/login");
        session.setAttribute("user",null);
        SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
        return view;
    }


}
