package cn.ucai.fulicenter.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.view.DisplayUtils;

import static cn.ucai.fulicenter.R.id.username;


public class RegisterActivity extends BaseActivity {
    private static final String TAG=RegisterActivity.class.getSimpleName();
    @BindView(R.id.username)
    EditText musername;
    @BindView(R.id.nick)
    EditText nick;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirmPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    String Username;
    String nickname;
    String Password;
    RegisterActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账户注册");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {


    }

    @OnClick(R.id.btn_register)
    public void checkedInput() {
       Username = musername.getText().toString().trim();
      nickname = nick.getText().toString().trim();
       Password = password.getText().toString().trim();
        String confirmPwd = confirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(Username)) {
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            musername.requestFocus();
            return;

        } else if (!Username.matches("[a-zA-Z]\\w{5,15}")) {
            CommonUtils.showShortToast(R.string.illegal_user_name);
            musername.requestFocus();
            return;

        } else if (TextUtils.isEmpty(nickname)) {
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            nick.requestFocus();
            return;
        } else if (TextUtils.isEmpty(Password)) {
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            password.requestFocus();
            return;
        }else if (TextUtils.isEmpty(confirmPwd)) {
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
           confirmPassword.requestFocus();
            return;
        }else if (TextUtils.isEmpty(confirmPwd)) {
            CommonUtils.showShortToast(R.string.two_input_password);
           confirmPassword.requestFocus();
            return;
        }
        register();

    }

    private void register() {
        final ProgressDialog pd=new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, Username, nickname, Password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result==null){
                    CommonUtils.showShortToast(R.string.register_fail);
                }else {
                    if (result.isRetMsg()){
                        CommonUtils.showShortToast(R.string.register_success);
                        setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                        MFGT.finish(mContext);
                   }else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        musername.requestFocus();
                    }
                }

            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e(TAG,"register"+error);
            }
        });

    }

}







