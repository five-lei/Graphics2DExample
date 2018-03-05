package com.yilei.graphics2dexample.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseActivity;
import com.yilei.graphics2dexample.view.fragment.ClipBoomFragment;
import com.yilei.graphics2dexample.view.fragment.ClipViewFragment;

import butterknife.OnClick;

public class ClippingActivity extends BaseActivity {
    private Fragment[] fragments;
    private int mIndex;//当前fragment的下标

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clipping;
    }

    @Override
    protected void onInitView() {
        initFragment();
    }

    private void initFragment(){
        ClipViewFragment clipViewFragment = new ClipViewFragment();
        ClipBoomFragment clipBoomFragment = new ClipBoomFragment();
        fragments = new Fragment[]{clipViewFragment, clipBoomFragment};
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, clipViewFragment).commit();
        //默认设置为第一个
        setIndexSelected(0);
    }

    private void setIndexSelected(int index){
        if(mIndex == index){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[index]);
        //判断fragment是否已经添加
        if(!fragments[index].isAdded()){
            //添加进数组并显示
            transaction.add(R.id.fl_content, fragments[index]).show(fragments[index]);
        }else{
            //已经添加了直接显示
            transaction.show(fragments[index]);
        }
        transaction.commit();
        //重新赋值
        mIndex = index;
    }

    @OnClick({R.id.rb1, R.id.rb2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rb1:
                setIndexSelected(0);
                break;
            case R.id.rb2:
                setIndexSelected(1);
                break;

        }
    }

}
