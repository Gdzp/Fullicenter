package cn.ucai.fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;

public class GoodsDetailActivity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    cn.ucai.fulicenter.view.SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    cn.ucai.fulicenter.view.FlowIndicator indicator;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;
int goodsId;
    GoodsDetailActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid" + goodsId);
        if (goodsId==0){
            finish();
        }
        mContext=this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
    }

    private void initData() {
        NetDao.downloadGoodsDetall(mContext,
                goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
                    @Override
                    public void onSuccess(GoodsDetailsBean result) {
                        L.i("details="+result);
                        if (result!=null){
                            showGoodsDetails(result);

                        }else{
                            finish();
                        }
                    }



                    @Override
                    public void onError(String error) {
                        finish();
                        L.e("details,error="+error);
                        CommonUtils.showShortToast(error);

                    }
                });

    }
    private void showGoodsDetails(GoodsDetailsBean detalis) {
        tvGoodNameEnglish.setText(detalis.getGoodsEnglishName());
        tvGoodName.setText(detalis.getGoodsName());
        tvGoodPriceCurrent.setText(detalis.getCurrencyPrice());
        tvGoodPriceShop.setText(detalis.getShopPrice());

    }
    private void initView() {
    }
}
