package com.instant.message.controller;

import com.alibaba.fastjson.JSON;
import com.instant.message.config.ApplicationHelper;
import com.instant.message.entity.OneToOneMessage;
import com.instant.message.entity.Result;
import com.instant.message.service.UserService;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/OneToOne/{socketId}/{userId}")
public class WebSocketOneToOneOnLine {

    private static int onlineCount;
    private static Map<String, WebSocketOneToOneOnLine> connections = new ConcurrentHashMap<>();

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

        OneToOneMessage m=JSON.parseObject(message,OneToOneMessage.class);

        try {
            int userId=Integer.parseInt(id);
            UserService userService= (UserService) ApplicationHelper.getBean("userService");

            Result user=userService.selectByPrimaryKeyToMessage(userId);
             m.setUser(user);
            sendMessage(socketId,m.getToUserId(),m);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private  static void sendMessage(String currentUserSocketId,String toUserSocketId,OneToOneMessage message) throws IOException {
        System.out.println("开始发消息...");

        WebSocketOneToOneOnLine currentUser=connections.get(currentUserSocketId);
        if(currentUser!=null){
            message.setCurrent(true);
            String json=JSON.toJSONString(message);
            if(currentUser.session.isOpen()) {
                currentUser.session.getBasicRemote().sendText(json);
                System.out.println("发送消息给自己:" + json);

            }else {
                connections.remove(currentUserSocketId);
            }
        }

        WebSocketOneToOneOnLine toUser=connections.get(toUserSocketId);
        if(toUser!=null){
            message.setCurrent(false);
            String json=JSON.toJSONString(message);
            if(toUser.session.isOpen()){
                toUser.session.getBasicRemote().sendText(json);
                System.out.println("发送消息给对方:" + json);

            }else {
                connections.remove(toUserSocketId);
            }


        }
        System.out.println("发完消息...");

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
        WebSocketOneToOneOnLine.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketOneToOneOnLine.onlineCount--;
    }


}
