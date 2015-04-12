package com.oztz.hackinglabmobile.businessclasses;

/**
 * Created by Tobi on 09.04.2015.
 */
public class User {
    String deviceID;
    String name;
    String regID;
    int userID;

    public User(String deviceID, String name, String regID, int userID){
        this.deviceID = deviceID;
        this.name = name;
        this.regID = regID;
        this.userID = userID;
    }
}
