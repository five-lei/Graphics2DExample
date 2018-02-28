package com.yilei.graphics2dexample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lei on 2018/2/28.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private Unbinder unbinder;

    protected abstract int getLayoutId();//获取布局Id
    protected abstract void onInitView();//初始化数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
            unbinder = ButterKnife.bind(this);
            onInitView();
        }
    }

    /**
     * 弹出短吐司
     * @param msg
     */
    protected void showShortToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出长吐司
     * @param msg
     */
    protected void showLongToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
