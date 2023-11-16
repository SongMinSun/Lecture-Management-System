package com.example.myapplication;
public class AttendanceAccount {
    private String attend;
    private String attend_date;
    private String attend_time;
    private String classnum;
    private String studentnum;
    private String studentname;

    public AttendanceAccount() { }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getAttend_date() {
        return attend_date;
    }

    public void setAttend_date(String attend_date) {
        this.attend_date = attend_date;
    }

    public String getAttend_time() {
        return attend_time;
    }

    public void setAttend_time(String attend_time) {
        this.attend_time = attend_time;
    }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
    }

    public String getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(String studentnum) {
        this.studentnum = studentnum;
    }


}
