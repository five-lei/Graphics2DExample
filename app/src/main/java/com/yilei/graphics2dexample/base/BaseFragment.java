package com.yilei.graphics2dexample.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lei on 2018/2/28.
 */

public abstract class BaseFragment extends Fragment{
    protected Context mContext;
    private View rootView;
    private Unbinder unbinder;

    protected abstract int getLayoutId();//获取布局Id
    protected abstract void onInitView();//初始化数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView != null){
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
            return rootView;
        }
        if(getLayoutId() != 0){
            rootView = inflater.inflate(getLayoutId(), container, false);
        }else{
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, rootView);
        onInitView();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mContext = getActivity();
    }

    /**
     * 弹出短吐司
     * @param msg
     */
    protected void showShortToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出长吐司
     * @param msg
     */
    protected void showLongToast(String msg){
        Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
