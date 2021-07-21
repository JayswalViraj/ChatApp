package com.example.chatzup.Models;

public class MessageModel {

    String uid, mesaage,messageID;
    Long timestamp;


    public MessageModel(String uid, String mesaage, Long timestamp) {
        this.uid = uid;
        this.mesaage = mesaage;
        this.timestamp = timestamp;
    }

    public MessageModel(String uid, String mesaage) {
        this.uid = uid;
        this.mesaage = mesaage;
        this.timestamp = timestamp;

    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public MessageModel(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMesaage() {
        return mesaage;
    }

    public void setMesaage(String mesaage) {
        this.mesaage = mesaage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
