package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.Activity.MainActivity;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.adapter.CategoryAdapter;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;

/**
 * Created by Think on 2016/10/20.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv_categorp)
    ExpandableListView elvCategorp;
    CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mchidList;
    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();
        mGroupList=new ArrayList<>();
        mchidList=new ArrayList<>();
        mAdapter = new CategoryAdapter(mContext,mGroupList,mchidList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        elvCategorp.setGroupIndicator(null);
        elvCategorp.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        downloadGroup();

    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("downloadGroup,result="+result);
                if (result!=null&&result.length>0){
                    ArrayList<CategoryGroupBean> groupList=ConvertUtils.array2List(result);
                    L.e("groupList="+groupList.size());
                    mGroupList.addAll(groupList);
                    for (int i=0;i<groupList.size();i++){
                        mchidList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g=groupList.get(i);
                        downloadChild(g.getId(),i);
                    }

                }
            }
            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

            private void downloadChild(int id,final int index) {
                NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
                    @Override
                    public void onSuccess(CategoryChildBean[] result) {
                        groupCount++;
                        L.e("downloadChild,result=" + result);
                        if (result != null && result.length > 0) {
                            ArrayList<CategoryChildBean> ChildList=ConvertUtils.array2List(result);
                            L.e("groupList="+ChildList.size());
                           mchidList.set(index,ChildList);
                        }
                        if (groupCount==mGroupList.size()){
                            mAdapter.initDate(mGroupList,mchidList);
                        }
                    }
                    @Override
                    public void onError(String error) {
                        L.e("error="+error);

                    }
                });
            }



    @Override
    protected void setListener() {


    }
}
