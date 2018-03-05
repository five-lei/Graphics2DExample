package com.yilei.graphics2dexample.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseActivity;
import com.yilei.graphics2dexample.view.adapter.DynamicAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class DynamicActivity extends BaseActivity {
    @BindView(R.id.rl_dynamic)
    RecyclerView rl_dynamic;
    private ArrayList<String> mDynamicList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dynamic;
    }

    @Override
    protected void onInitView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rl_dynamic.setLayoutManager(manager);

        String[] dynamicArray = getResources().getStringArray(R.array.DynamicList);
        for(int i = 0; i < dynamicArray.length; i++){
            mDynamicList.add(dynamicArray[i]);
        }

        DynamicAdapter dynamicAdapter = new DynamicAdapter(this, mDynamicList);
        rl_dynamic.setAdapter(dynamicAdapter);

    }

    @OnClick(R.id.tv_left_text)
    public void tabBack(){
        finish();
    }

}
