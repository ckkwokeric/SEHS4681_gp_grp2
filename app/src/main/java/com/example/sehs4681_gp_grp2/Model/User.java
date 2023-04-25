package com.example.sehs4681_gp_grp2.Model;

import java.io.Serializable;

public class User implements Serializable {
    private static int UID;
    private static String userName;
    private static int score;

    public User(int UID, String userName, int score) {
        User.UID = UID;
        User.userName = userName;
        User.score = score;
    }

    public static int getUID() {
        return UID;
    }

    public static void setUID(int UID) {
        User.UID = UID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        User.score = score;
    }
}
