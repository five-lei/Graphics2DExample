package com.yilei.graphics2dexample.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.base.BaseFragment;
import com.yilei.graphics2dexample.view.adapter.DrawAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lei on 2018/3/1.
 */

public class DrawFragment extends BaseFragment{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<String> titles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_draw;
    }

    @Override
    protected void onInitView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        String[] arrays = getResources().getStringArray(R.array.DrawList);
        for(int i = 0; i < arrays.length; i++){
            titles.add(arrays[i]);
        }
        DrawAdapter adapter = new DrawAdapter(mContext, titles);

        recyclerView.setAdapter(adapter);
    }

}
