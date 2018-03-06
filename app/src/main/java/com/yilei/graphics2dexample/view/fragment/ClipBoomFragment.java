package com.yilei.graphics2dexample.view.fragment;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseFragment;
import com.yilei.graphics2dexample.ui.dynamic.ClippingBoom;

import butterknife.BindView;

/**
 * Created by lei on 2018/3/1.
 */

public class ClipBoomFragment extends BaseFragment{
    @BindView(R.id.clip_boom)
    ClippingBoom clip_boom;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_clip_boom;
    }

    @Override
    protected void onInitView() {
        clip_boom.startPlay();

    }
}
