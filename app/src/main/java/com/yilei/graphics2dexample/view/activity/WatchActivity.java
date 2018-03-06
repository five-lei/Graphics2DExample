package com.yilei.graphics2dexample.view.activity;


import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseActivity;
import com.yilei.graphics2dexample.ui.dynamic.WatchView;

import butterknife.BindView;

public class WatchActivity extends BaseActivity {
    @BindView(R.id.watch_view)
    WatchView watch_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_watch;
    }

    @Override
    protected void onInitView() {
        watch_view.run();

    }

}
