package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;


/**
 * Created by Think on 2016/10/19.
 */

public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {

    Context mContext;
    ArrayList<CartBean> mList;
   
    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartBean CartBean = mList.get(position);
//    ImageLoader.downloadImg(mContext,bh.ivBoutiqueImg,boutiqueBean.getImageurl());
//  bh.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
//   bh.tvBoutiqueName.setText(boutiqueBean.getName());
//    bh.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
//        bh.layoutBoutiqueItem.setTag(boutiqueBean);

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }



  class CartViewHolder extends ViewHolder {
        @BindView(R.id.cb_card_checked)
        CheckBox cbCardChecked;
        @BindView(R.id.iv_card_goods_image)
        ImageView ivCardGoodsImage;
        @BindView(R.id.iv_card_goodsName)
        TextView ivCardGoodsName;
        @BindView(R.id.iv_card_add)
        ImageView ivCardAdd;
        @BindView(R.id.tv_card_goods_count)
        TextView tvCardGoodsCount;
        @BindView(R.id.iv_card_del)
        ImageView ivCardDel;
        @BindView(R.id.tv_card_goods_price)
        TextView tvCardGoodsPrice;
        @BindView(R.id.rl_layout_card)
        RelativeLayout rlLayoutCard;
        CartViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}
