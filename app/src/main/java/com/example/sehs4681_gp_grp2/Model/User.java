package com.example.sehs4681_gp_grp2.Model;

public class User {
    private int UID;
    private String userName;
    private int score;

    public User(int UID, String userName, int score) {
        this.UID = UID;
        this.userName = userName;
        this.score = score;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
