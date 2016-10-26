package cn.ucai.fulicenter.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG=SplashActivity.class.getSimpleName();
    private static final long sleepTime=3000;
    SplashActivity mContext;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.splash2);
        mContext=this;
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                User user= FuLiCenterApplication.getUser();
                L.e(TAG,"fulicenter,user="+user);
                String username= SharePrefrenceUtils.getInstence(mContext).getUser();
                L.e(TAG,"fulicenter,username");
                if (user==null && username!=null){
                    UserDao dao=new UserDao(mContext);
                    user=dao.getUser(username);
                    L.e(TAG,"database,user="+user);
                    if (user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },sleepTime);
    }


}
