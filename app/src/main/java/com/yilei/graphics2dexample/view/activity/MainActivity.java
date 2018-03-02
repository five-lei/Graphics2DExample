package com.yilei.graphics2dexample.view.activity;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.ui.NavigateTabBar;
import com.yilei.graphics2dexample.view.fragment.AnyElseFragment;
import com.yilei.graphics2dexample.view.fragment.DrawFragment;
import com.yilei.graphics2dexample.view.fragment.ThreadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends FragmentActivity {
    private Unbinder unbinder;
    @BindView(R.id.mainTabBar)
    NavigateTabBar mainTabBar;

    private static final String TAG_CUSTOM_VIEW = "绘图";
    private static final String TAG_MULTI_THREAD = "线程";
    private static final String TAG_ANY_ELSE = "其他";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        mainTabBar.onRestoreInstanceState(savedInstanceState);
        mainTabBar.addTab(DrawFragment.class, new NavigateTabBar.TabParams(R.mipmap.market_default,
                R.mipmap.market_select, TAG_CUSTOM_VIEW));
        mainTabBar.addTab(ThreadFragment.class, new NavigateTabBar.TabParams(R.mipmap.trend_default,
                R.mipmap.trend_select, TAG_MULTI_THREAD));
        mainTabBar.addTab(AnyElseFragment.class, new NavigateTabBar.TabParams(R.mipmap.information_default,
                R.mipmap.information_select, TAG_ANY_ELSE));
        mainTabBar.setOnTabSelectedListener(new NavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(NavigateTabBar.ViewHolder holder) {
                if(holder.tag.toString().equals(TAG_CUSTOM_VIEW)){
                    mainTabBar.showFragment(holder);
                }else if(holder.tag.toString().equals(TAG_MULTI_THREAD)){
                    mainTabBar.showFragment(holder);
                }else if(holder.tag.toString().equals(TAG_ANY_ELSE)){
                    mainTabBar.showFragment(holder);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mainTabBar.onSavedInstanceState(outState);
    }
}
