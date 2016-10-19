package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;


/**
 * Created by Think on 2016/10/19.
 */

public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;



    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(ArrayList<BoutiqueBean> mList, Context context) {
        mContext=context;
        mList =new ArrayList<>();
        mList.addAll(mList);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        if (viewType == I.TYPE_FOOTER) {

            holder = new FooterViewHolder
                    (LayoutInflater.from(mContext).inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutqueViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof FooterViewHolder){
                ((FooterViewHolder) holder).tvFooter.setText(getFooterString());
            }
if (holder instanceof BoutqueViewHolder){
BoutiqueBean boutiqueBean=mList.get(position);
    ImageLoader.downloadImg(mContext,((BoutqueViewHolder)holder).ivBoutiqueImg,boutiqueBean.getImageurl());
    ((BoutqueViewHolder) holder).tvBoutiqueTitle.setText(boutiqueBean.getTitle());
    ((BoutqueViewHolder) holder).tvBoutiqueName.setText(boutiqueBean.getName());
    ((BoutqueViewHolder) holder).tvBoutiqueDescription.setText(boutiqueBean.getDescription());

}
    }

    private int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }


     class BoutqueViewHolder extends ViewHolder {
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

        BoutqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
