package com.example.myapplication;

//Database에 저장할 정보를 담은 class
public class UserActivity{
    private String name;
    private String email;
    private String phone;
    private String birth;

    public UserActivity() { } //빈 생성자가 필요 (firebase 관련)

    public UserActivity(String name, String email, String phone, String birth) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirth() {
        return birth;
    }
}