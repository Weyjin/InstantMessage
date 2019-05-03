package com.instant.message.controller;

import com.alibaba.fastjson.JSON;
import com.instant.message.config.ApplicationHelper;
import com.instant.message.entity.*;
import com.instant.message.service.LoginTokenService;
import com.instant.message.service.UserService;
import org.apache.commons.codec.binary.Base64;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/ScanCode/{socketId}/{userId}")
public class WebSocketScanCode {

    private static int onlineCount;
    private static Map<String, WebSocketScanCode> connections = new ConcurrentHashMap<>();

    private Session session;
    private String id;
    private String socketId;

    @OnOpen
    public void onOpen(Session session, @PathParam("socketId") String socketId,@PathParam("userId") String userId) {
        this.session = session;
        this.socketId=socketId;
        this.id=userId;

        connections.put(socketId,this);     //添加到map中

        addOnlineCount();               // 在线数加
        System.out.println("有新连接加入！新用户："+",当前在线人数为" + getOnlineCount());
        if(userId.equals("0")){
            return;
        }
        try{
            System.out.println("信息: "+ userId);

            String json=decode(userId);
            System.out.println("信息: "+ json);

            ScanCode scanCode=JSON.parseObject(json,ScanCode.class);
            WebSocketScanCode socketScanCode=connections.get(scanCode.getCode());
            if(socketScanCode!=null){
                UserService userService= (UserService) ApplicationHelper.getBean("userService");
                User user=userService.selectByPrimaryKey(scanCode.getId());
                if(user!=null){
                    LoginTokenService loginTokenService= (LoginTokenService) ApplicationHelper.getBean("loginTokenService");
                    LoginToken token=new LoginToken();
                    token.setToken(scanCode.getCode());
                    token.setUserId(user.getId());
                    boolean success=loginTokenService.insert(token);
                    if(success){
                        socketScanCode.session.getBasicRemote().sendText(token.getToken());
                        System.out.println("发送消息给浏览器执行登录");
                        //发送完消息之后，删除
                        connections.remove(socketId);
                        connections.remove(scanCode);
                    }

                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }




    }
    private static String decode(String encodeStr){
        byte[] b=encodeStr.getBytes();
        Base64 base64=new Base64();
        b=base64.decode(b);
        String s=new String(b);
        return s;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        connections.remove(socketId);  // 从map中移除
        subOnlineCount();          // 在线数减
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     * @param session
     *            可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

    }



    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();

    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketScanCode.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketScanCode.onlineCount--;
    }


}
