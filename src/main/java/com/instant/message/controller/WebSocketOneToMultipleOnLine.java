package com.instant.message.controller;

import com.alibaba.fastjson.JSON;
import com.instant.message.config.ApplicationHelper;
import com.instant.message.entity.OneToOneMessage;
import com.instant.message.entity.Result;
import com.instant.message.service.UserService;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/OneToMultiple/{socketId}/{userId}")
public class WebSocketOneToMultipleOnLine {

    private static int onlineCount;
    private static Map<String, Set<WebSocketOneToMultipleOnLine>> connections = new ConcurrentHashMap<>();

    private Session session;
    private String id;
    private String socketId;

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("socketId") String socketId,@PathParam("userId") String userId) {
        this.session = session;
        this.socketId=socketId;
        this.id=userId;
         if(connections.size()==0){
             Set<WebSocketOneToMultipleOnLine> list=new HashSet<>();
             list.add(this);
             connections.put(socketId,list);     //添加到map中
         }
         if(connections.containsKey(socketId)){
             connections.get(socketId).add(this);
         }else {
             Set<WebSocketOneToMultipleOnLine> list=new HashSet<>();
             list.add(this);
             connections.put(socketId,list);     //添加到map中
         }



        addOnlineCount();               // 在线数加
        System.out.println("有新连接加入！新用户："+",当前在线人数为" + getOnlineCount());



    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        connections.get(socketId).remove(id);

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
            sendMessage(socketId,m.getCurrentUserId(),m);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private  static void sendMessage(String groupId,String currentUserId,OneToOneMessage message) throws IOException {


        Set<WebSocketOneToMultipleOnLine> toGroups=connections.get(groupId);
        if(toGroups!=null){

            //TODO:有时会发生同一个组里别人收到，而自己收不到的情况
            for(WebSocketOneToMultipleOnLine w:toGroups){
                if(w.id.equals(currentUserId)){
                    message.setCurrent(true);
                }else{
                    message.setCurrent(false);
                }
                String json=JSON.toJSONString(message);
                if(w.session.isOpen()){
                    w.session.getBasicRemote().sendText(json);
                    System.out.println("当前连接已打开:" + json);

                }else {
                    connections.get(groupId).remove(w.id);
                    System.out.println("当前连接已关闭:"+json);

                }

            }


        }

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
        WebSocketOneToMultipleOnLine.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketOneToMultipleOnLine.onlineCount--;
    }


    @Override
    public int hashCode() {
        return id.hashCode() * socketId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof WebSocketOneToMultipleOnLine) {
            WebSocketOneToMultipleOnLine socket = (WebSocketOneToMultipleOnLine) obj;

            // 比较每个属性的值 一致时才返回true
            if (socket.id.equals(this.id))
                return true;
        }
        return false;

    }
}
