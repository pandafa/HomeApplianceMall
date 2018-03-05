package com.chenexample.myshop16173.util;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.Map;

public class MyApplication extends Application {

    private String s_loginUserKind,s_loginUserId,s_loginUserName;
    private int numberShoppingCar,numberOrder;
    private boolean b_login;
    private String webURL,sessionId;
    private Map<String,String> kindMap;
    private ViewPager viewPager;
    private int currentKind ;

    @Override
    public void onCreate() {
        super.onCreate();
        webURL = "http://10.0.3.2:8080/myshop";
//        webURL = "http://172.19.64.91:8080/myshop";
        b_login = false;
        sessionId = "";
        kindMap = null;
        viewPager = null;
        currentKind = 0;
        numberShoppingCar = 0;
        numberOrder = 0;
        s_loginUserKind = "wu";
        s_loginUserId = "5";
        s_loginUserName = "哈哈";

    }

    public int getCurrentKind() {
        return currentKind;
    }

    public void setCurrentKind(int currentKind) {
        this.currentKind = currentKind;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public Map<String, String> getKindMap() {
        return kindMap;
    }

    public void setKindMap(Map<String, String> kindMap) {
        this.kindMap = kindMap;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getWebURL() {
        return webURL;
    }

    public int getNumberShoppingCar() {
        return numberShoppingCar;
    }

    public void setNumberShoppingCar(int numberShoppingCar) {
        this.numberShoppingCar = numberShoppingCar;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getS_loginUserKind() {
        return s_loginUserKind;
    }

    public void setS_loginUserKind(String s_loginUserKind) {
        this.s_loginUserKind = s_loginUserKind;
    }

    public String getS_loginUserId() {
        return s_loginUserId;
    }

    public void setS_loginUserId(String s_loginUserId) {
        this.s_loginUserId = s_loginUserId;
    }

    public String getS_loginUserName() {
        return s_loginUserName;
    }

    public void setS_loginUserName(String s_loginUserName) {
        this.s_loginUserName = s_loginUserName;
    }

    public boolean isB_login() {
        return b_login;
    }

    public void setB_login(boolean b_login) {
        this.b_login = b_login;
    }
}
