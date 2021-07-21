package com.example.chatzup.Models;

public class Users {

    String profilepic, username, email, password, userid, lastmessage, status;

    public Users(String profilepic, String username, String email, String password, String userid, String lastmessage) {
        this.profilepic = profilepic;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
    }

    public Users() {

    }
    // SignUp Constructor

    public Users(String username, String email, String password) {
        this.profilepic = profilepic;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public String getUserid() {
        return userid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
