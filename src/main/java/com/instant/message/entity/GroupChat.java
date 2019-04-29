package com.instant.message.entity;

public class GroupChat {
    private int id;
    private String name;
    private int count;
    private String img;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImg() {
        String[] imgs={"images/user1-default.jpg","images/user2-default.jpg",
                "images/user3-default.jpg","images/user4-default.jpg",
                "images/user5-default.jpg","images/user6-default.jpg",
                "images/user7-default.jpg","images/user8-default.jpg",
                "images/user9-default.jpg"};
        int random= (int) (Math.random()*(imgs.length-1));
        return imgs[random];
    }

    public void setImg(String img) {
        this.img = img;
    }
}
