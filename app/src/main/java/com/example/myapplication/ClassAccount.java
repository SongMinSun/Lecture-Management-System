package com.example.myapplication;

public class ClassAccount {
    private String classname;
    private String classnum;
    private String classday;
    private String classtime;
    private String idToken;

    public ClassAccount(){ }

    public String getIdToken(){return idToken;}

    public void setIdToken(String idToken){this.idToken=idToken;}

    public String getClassname(){ return classname; }

    public void setClassname(String classname){this.classname = classname;}

    public String getClassnum(){return classnum;}

    public void setClassnum(String classnum){this.classnum=classnum;}

    public String getClassday(){return classday;}

    public void setClassday(String classaday){this.classday=classaday;}

    public String getClasstime(){return classtime;}

    public void setClasstime(String classtime){this.classtime=classtime;}
}