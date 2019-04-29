package com.instant.message.entity;

public class OneToOneMessage {
    private String currentUserId;
    private String toUserId;
    private String message;
    private boolean isCurrent;
    private Result user;

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public Result getUser() {
        return user;
    }

    public void setUser(Result user) {
        this.user = user;
    }
}
