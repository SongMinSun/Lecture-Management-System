package com.example.myapplication;

public class UserAccount {
    private String idToken;
    private String emailId;
    private String password;
    private String name;

    public UserAccount(){ }

    public String getIdToken(){return idToken;}

    public void setIdToken(String idToken){this.idToken=idToken;}

    public String getEmailId(){ return emailId; }

    public void setEmailId(String emailId){this.emailId=emailId;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}

    public String getName(){return name;}

    public void setName(String name){this.name=name;}
}
