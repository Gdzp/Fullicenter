package cn.ucai.fulicenter;

import android.app.Application;

import cn.ucai.fulicenter.bean.User;

/**
 * Created by Think on 2016/10/17.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;
    private static String username;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        instance=this;
    }

    public FuLiCenterApplication() {
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
    if (instance==null){
    instance=new FuLiCenterApplication();
    }
        return instance;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }
}
