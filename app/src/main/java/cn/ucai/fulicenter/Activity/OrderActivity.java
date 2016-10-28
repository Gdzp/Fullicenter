package cn.ucai.fulicenter.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.fragment.CartFragment;
import cn.ucai.fulicenter.utils.L;

public class OrderActivity extends BaseActivity {
    private static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.ed_order_name)
    EditText edOrderName;
    @BindView(R.id.ed_order_phone)
    EditText edOrderPhone;
    @BindView(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @BindView(R.id.layout_order_street)
    RelativeLayout layoutOrderStreet;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_buy)
    TextView tvOrderBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String cartIds=getIntent().getStringExtra(I.Cart.ID);
        User user= FuLiCenterApplication.getUser();
        L.e(TAG,"cartIds="+cartIds);
        if (cartIds==null||cartIds.equals("") || user==null){

            finish();
        }

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void checkOrder() {
    }
}
