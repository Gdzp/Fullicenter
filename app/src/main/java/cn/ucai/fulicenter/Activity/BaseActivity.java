package cn.ucai.fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Think on 2016/10/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }
    abstract protected void initView();
    abstract protected void initData();
    abstract protected void setListener();
    public void onBackPressed(){
        MFGT.finish(this);

    }
}
