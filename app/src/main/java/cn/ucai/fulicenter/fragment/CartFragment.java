package cn.ucai.fulicenter.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.ResultUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;


/**
 * Created by Administrator on 2016/10/19.
 */
public class CartFragment extends BaseFragment {
    private static final String TAG = CartFragment.class.getSimpleName();

    LinearLayoutManager mLinearLayoutManager;
    Context mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    int pageId = 1;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.tv_cart_sum_price)
    TextView tvCartSumPrice;
    @BindView(R.id.tv_cart_Save_price)
    TextView tvCartSavePrice;
    @BindView(R.id.tv_cart_buy)
    TextView tvCartBuy;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.rv)
    RecyclerView rlv;
    @BindView(R.id.srl)
    SwipeRefreshLayout sfl;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    updateCartReceive mReceiver;

    public CartFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        mContext = getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    /**
     * 创建一个监听器
     */
    @Override
    protected void setListener() {

        setPullDownListener();
        IntentFilter filter=new IntentFilter(I.BROADCAST_UPDATA_CART);
        mReceiver=new updateCartReceive();
        mContext.registerReceiver(mReceiver,filter);
    }

    /**
     * 下拉刷新
     */
    private void setPullDownListener() {
        sfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                sfl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downLoadCart();
            }
        });
    }

    @Override
    protected void initData() {
        downLoadCart();
    }

    private void downLoadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                    ArrayList<CartBean> list = ResultUtils.getCartFromJson(s);
                    L.e(TAG, "result=" + list);
                    sfl.setRefreshing(false);//设置是否刷新
                    tvRefresh.setVisibility(View.GONE);//隐藏刷新提示
                    if (list != null && list.size() > 0) {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.initData(list);
                        setCartLayout(true);
                    } else {
                        setCartLayout(false);
                    }
                }

                @Override
                public void onError(String error) {
                    sfl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    CommonUtils.showLongToast(error);
                    L.e("error: " + error);
                }
            });

        }

    }

    @Override
    protected void initView() {
        sfl.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rlv.setLayoutManager(mLinearLayoutManager);
        rlv.setHasFixedSize(true);
        rlv.setAdapter(mAdapter);
        rlv.addItemDecoration(new SpaceItemDecoration(12));
        setCartLayout(false);

    }


    public void setCartLayout(boolean hasCart) {
        layoutCart.setVisibility(hasCart ? View.VISIBLE : View.GONE);
        tvNothing.setVisibility(hasCart ? View.GONE : View.VISIBLE);
        rlv.setVisibility(hasCart ? View.VISIBLE : View.GONE);

    }

    @OnClick(R.id.tv_cart_buy)
    public void onClick() {
    }

    private void sumPrice() {
        int sumPrice = 0;
        int rankPrice = 0;
        if (mList != null && mList.size() > 0) {
            for (CartBean c : mList) {
                if (c.isChecked()) {
                    sumPrice += getPrice(c.getGoods().getCurrencyPrice()) * c.getCount();
                    rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                }
            }
            tvCartSumPrice.setText("合计：￥" + Double.valueOf(sumPrice));
            tvCartSavePrice.setText("节省：￥" + Double.valueOf(sumPrice - rankPrice));
        } else {
            setCartLayout(false);
            tvCartSumPrice.setText("合计：￥0");
            tvCartSavePrice.setText("节省：￥0");
        }
    }

    private int getPrice(String price) {
        price = price.substring(price.indexOf("￥") + 1);
        return Integer.valueOf(price);
    }

    class updateCartReceive extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            L.e(TAG,"updateCartReceiver....");
           sumPrice();
            setCartLayout(mList!=null&&mList.size()>0);
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if (mReceiver!=null){
            mContext.unregisterReceiver(mReceiver);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        L.e(TAG,"onResume.....");
        initData();
    }
}