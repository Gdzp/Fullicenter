package cn.ucai.fulicenter.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CollectsAdapter;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.SpaceItemDecoration;

public class CollectsActivity extends BaseActivity {

    CollectsActivity mContext;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    CollectsAdapter mAdapter;
    GridLayoutManager glm ;
    int pageId = 1;
    ArrayList<CollectBean> mList;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new CollectsAdapter(mContext,mList);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.collect_title));
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadCollects(final int action) {
        NetDao.downloadCollects(mContext, user.getMuserName(), pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                srl.setRefreshing(false);

                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }


                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    } else {
                        mAdapter.setMore(false);
                    }
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showLongToast(error);
                L.e("error: " + error);

            }
        });

    }

    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastpositiion = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastpositiion == mAdapter.getItemCount() - 1 && mAdapter.isMore()) {
                    pageId++;
                    downloadCollects(I.ACTION_PULL_UP);
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                srl.setEnabled(firstPosition == 0);
            }
        });

    }

    @Override
    protected void initData() {
         user= FuLiCenterApplication.getUser();
        if (user==null){

        }
        downloadCollects(I.ACTION_DOWNLOAD);

    }
@Override
    protected void onResume(){
    super.onResume();
    initData();

}

}

