package com.instant.message.entity;

public class User {
    private int id;
    private String name;
    private String password;
    private String signature;
    public User(){}
    public User(String name,String password,String signature){
        this.name=name;
        this.password=password;
        this.signature=signature;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
