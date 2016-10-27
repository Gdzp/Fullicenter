package cn.ucai.fulicenter.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.fragment.CartFragment;
import cn.ucai.fulicenter.fragment.CategoryFragment;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.fragment.PresonalCenterFragment;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;
    int index;
    int currentIndex;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFrament;
    PresonalCenterFragment mPresonalCenterFragment;


    RadioButton[] rbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity");
        super.onCreate(savedInstanceState);



    }

    private void initFragment() {
        mFragments=new Fragment[5];
        mNewGoodsFragment=new NewGoodsFragment();
        mBoutiqueFragment=new BoutiqueFragment();
        mCategoryFragment=new CategoryFragment();
        mCartFrament=new CartFragment();
        mPresonalCenterFragment = new PresonalCenterFragment();
        mFragments[0]=mNewGoodsFragment;
        mFragments[1]=mBoutiqueFragment;
        mFragments[2]=mCategoryFragment;
        mFragments[3]=mCartFrament;
        mFragments[4]=mPresonalCenterFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
//                .add(R.id.fragment_container,mBoutiqueFragment)
//                .add(R.id.fragment_container,mCategoryFragment)
//                .hide(mBoutiqueFragment)
//                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    @Override
    protected  void setListener() {

    }

    @Override
    protected  void initView() {
        rbs=new RadioButton[5];
        rbs[0]=layoutNewGood;
        rbs[1]=layoutBoutique;
        rbs[2]=layoutCategory;
        rbs[3]=layoutCart;
        rbs[4]=layoutPersonalCenter;

    }

    @Override
    protected void initData() {
        initFragment();


    }

    public void onCheckedChange(View v) {
    switch (v.getId()){
    case R.id.layout_new_good:
        index=0;
        break;
    case R.id.layout_boutique:
        index=1;
        break;
    case R.id.layout_category:
        index=2;
        break;
    case R.id.layout_cart:
        index=3;
        break;
    case R.id.layout_personal_center:
        if (FuLiCenterApplication.getUser()==null){
            MFGT.gotoLogin(this);
        }else {
            index=4;
        }
        break;
}
        setmFragments();
    }
    private void setmFragments(){
        if (index!=currentIndex){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.hide(mFragments[currentIndex]);
        if (!mFragments[index].isAdded()){
            ft.add(R.id.fragment_container,mFragments[index]);
        }
        ft.show(mFragments[index]).commit();

        }
      setRadioButtonStatus();
    currentIndex=index;
    }


    private void setRadioButtonStatus() {
        L.e("index="+index);
        for (int i=0;i<rbs.length;i++){
            if (i==index){
                rbs[i].setChecked(true);
            }else {
                rbs[i].setChecked(false);
            }
        }
    }
    public void onBackPressed(){
       finish();

    }
    protected void onResume(){
        super.onResume();
        L.e("onResult...");
        setmFragments();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        L.e("onActivityResult,requestCode="+requestCode);
        if (requestCode== I.REQUEST_CODE_LOGIN && FuLiCenterApplication.getUser()!=null){
            index=4;
        }
    }
}
