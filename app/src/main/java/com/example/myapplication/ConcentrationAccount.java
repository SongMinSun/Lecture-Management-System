package com.example.myapplication;

public class ConcentrationAccount {
    private String classnum;
    private String con_time;
    private String con_date;
    private String high_count;
    private String low_count;
    private String studentnum;
    private String studentname;
    private String percentage;

    public String getStudnetname() {
        return studentname;
    }

    public void setStudnetname(String studentname) {
        this.studentname = studentname;
    }

    public ConcentrationAccount() { }

    public String getClassnum() {
        return classnum;
    }

    public void setClassnum(String classnum) {
        this.classnum = classnum;
    }

    public String getCon_time() {
        return con_time;
    }

    public void setCon_time(String con_time) {
        this.con_time = con_time;
    }

    public String getCon_date() {
        return con_date;
    }

    public void setCon_date(String con_date) {
        this.con_date = con_date;
    }

    public String getHigh_count() {
        return high_count;
    }

    public void setHigh_count(String high_count) {
        this.high_count = high_count;
    }

    public String getLow_count() {
        return low_count;
    }

    public void setLow_count(String low_count) {
        this.low_count = low_count;
    }

    public String getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(String studentnum) {
        this.studentnum = studentnum;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

}
