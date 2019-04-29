package com.instant.message.entity;

public class Result {

    private int id;
    private String describe;
    private String name;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        String[] images={"images/user1-default.jpg","images/user2-default.jpg",
                         "images/user3-default.jpg","images/user4-default.jpg",
                         "images/user5-default.jpg","images/user6-default.jpg",
                         "images/user7-default.jpg","images/user8-default.jpg",
                         "images/user9-default.jpg"};
        int random= (int) (Math.random()*(images.length-1));
        return images[random];
    }

    public void setImg(String img) {
        this.img = img;
    }
}
