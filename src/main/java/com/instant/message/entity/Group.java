package com.instant.message.entity;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<Result> users;

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

    public List<Result> getUsers() {
        return users;
    }

    public void setUsers(List<Result> users) {
        this.users = users;
    }
}
