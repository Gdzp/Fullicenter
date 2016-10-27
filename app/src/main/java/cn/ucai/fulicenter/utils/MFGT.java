package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.Activity.BoutichildActivity;
import cn.ucai.fulicenter.Activity.CategoryChildActivity;
import cn.ucai.fulicenter.Activity.CollectsActivity;
import cn.ucai.fulicenter.Activity.GoodsDetailActivity;
import cn.ucai.fulicenter.Activity.LoginActivity;
import cn.ucai.fulicenter.Activity.MainActivity;
import cn.ucai.fulicenter.Activity.RegisterActivity;
import cn.ucai.fulicenter.Activity.UpdateNickActivity;
import cn.ucai.fulicenter.Activity.UserProfileActivity;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;

import static cn.ucai.fulicenter.I.REQUST_UPDATE_NICK;

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent, I.REQUEST_CODE_LOGIN);
    }
    public static void gotoGoodsDetailsActivity(Context context,int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
       startActivity(context,intent, I.REQUEST_CODE_LOGIN);
    }
    public static void gotoBoutichildActivity(Context context, BoutiqueBean tag){
        Intent intent = new Intent();
        intent.setClass(context, BoutichildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,tag);
        startActivity(context,intent, I.REQUEST_CODE_LOGIN);
    }
    public static void startActivity(Context context, Intent intent, int requestCodeLogin){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);

    }
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean tag){
        Intent intent = new Intent();
        intent.setClass(context, BoutichildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,tag);
        startActivity(context,intent, I.REQUEST_CODE_LOGIN);
    }
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent, I.REQUEST_CODE_LOGIN);
    }
public static void gotoLogin(Activity context){
    Intent intent=new Intent();
    intent.setClass(context,LoginActivity.class);
    startActivity(context,intent,I.REQUEST_CODE_LOGIN);
}
    public static void gotoLoginFromCart(Activity context){
        Intent intent=new Intent();
        intent.setClass(context,LoginActivity.class);
        startActivity(context,intent,I.REQUEST_CODE_LOGIN_FROM_CART);
    }





    public static void gotoRegister(Activity context){
        Intent intent=new Intent();
        intent.setClass(context,RegisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);

    }
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoSettings(Activity context){
        startActivity(context, UserProfileActivity.class);
    }
    public static void gotoUpdateNick(Activity context){
        startActivityForResult(context,new Intent(context, UpdateNickActivity.class),REQUST_UPDATE_NICK);
    }
    public static void gotoCollects(Activity context){
        startActivity(context, CollectsActivity.class);
    }

}
