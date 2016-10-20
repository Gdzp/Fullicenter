package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

;


/**
 * Created by Think on 2016/10/19.
 */

public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;


    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
        mContext=context;
        mList =new ArrayList<>();
        mList.addAll(list);
    }



    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BoutiqueViewHolder
                holder = new BoutiqueViewHolder(View.inflate(mContext, R.layout.item_boutique, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          BoutiqueViewHolder bh=(BoutiqueViewHolder)holder;
    BoutiqueBean boutiqueBean=mList.get(position);
    ImageLoader.downloadImg(mContext,bh.ivBoutiqueImg,boutiqueBean.getImageurl());
  bh.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
   bh.tvBoutiqueName.setText(boutiqueBean.getName());
    bh.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
        bh.layoutBoutiqueItem.setTag(boutiqueBean);

}

    @Override
    public int getItemCount() {
        return mList != null?mList.size():0;
    }
public void initData(ArrayList<BoutiqueBean>list){
    if (mList!=null){
        mList.clear();
    }
    mList.addAll(list);
    notifyDataSetChanged();
}

    class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_boutique_item)
        public void onBoutiqueClick(){
        BoutiqueBean bean = (BoutiqueBean)layoutBoutiqueItem.getTag();
         MFGT.gotoBoutichildActivity(mContext,bean);
        }

    }
}
