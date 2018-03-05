package com.yilei.graphics2dexample.view.activity;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseActivity;
import com.yilei.graphics2dexample.ui.dynamic.BallMoveView;

import butterknife.BindView;
import butterknife.OnClick;

public class InvalidateActivity extends BaseActivity {
    @BindView(R.id.ball_view)
    BallMoveView ball_view;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invalidate;
    }

    @Override
    protected void onInitView() {
        ball_view.startMove();
    }

    @OnClick(R.id.tv_left_text)
    public void tabBack(){
        finish();
    }

}
