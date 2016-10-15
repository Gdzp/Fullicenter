package cn.ucai.fulicenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final long sleepTime=3000;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_splash);
    }
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();

            }
        },sleepTime);

    }


}
