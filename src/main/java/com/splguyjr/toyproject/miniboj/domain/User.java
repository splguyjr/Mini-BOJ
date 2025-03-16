package com.splguyjr.toyproject.miniboj.domain;

import java.io.Serializable;

//회원 정보를 관리하는 클래스
public class User implements Serializable {
    private String id;
    private String pwd;

    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
