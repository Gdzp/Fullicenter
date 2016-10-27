package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ImageLoader;


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
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        final CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, holder.ivCardGoodsImage, goods.getGoodsThumb());
            holder.ivCardGoodsName.setText(goods.getGoodsName());
            holder.tvCardGoodsPrice.setText(goods.getCurrencyPrice());
        }
        holder.tvCardGoodsCount.setText("(" + cartBean.getCount() + ")");
        holder.cbCardChecked.setChecked(false);
        holder.cbCardChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setChecked(b);
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        });
        holder.ivCardAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {

        mList = list;
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


        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.iv_card_add)
        public void addCart() {
            final int position = (int) ivCardAdd.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.get(position).setCount(mList.get(position).getCount() + 1);
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                        tvCardGoodsCount.setText("(" + (mList.get(position).getCount()) + ")");
                    }
                }

                @Override
                public void onError(String error) {

                }
            });


        }

        @OnClick(R.id.iv_card_del)
        public void delCart() {
            final int position = (int) ivCardAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount() > 1) {
                NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            mList.get(position).setCount(mList.get(position).getCount() - 1);
                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                            tvCardGoodsCount.setText("(" + (mList.get(position).getCount()) + ")");
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });


            } else {
              NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                  @Override
                  public void onSuccess(MessageBean result) {
                      if (result!=null&&result.isSuccess()){
                          mList.remove(position);
                          mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                          notifyDataSetChanged();
                      }
                  }

                  @Override
                  public void onError(String error) {

                  }
              });
            }
        }
    }
}
