package com.yilei.graphics2dexample.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.view.activity.ClippingActivity;
import com.yilei.graphics2dexample.view.activity.CoordinateActivity;
import com.yilei.graphics2dexample.view.activity.InvalidateActivity;
import com.yilei.graphics2dexample.view.activity.WatchActivity;

import java.util.ArrayList;

/**
 * Created by lei on 2018/3/5.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mDynamicList;
    public DynamicAdapter(Context context, ArrayList<String> dynamicList){
        this.mContext = context;
        this.mDynamicList = dynamicList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dynamic, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_dynamic.setText(mDynamicList.get(position));
        holder.tv_dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
                        intent.setClass(mContext, InvalidateActivity.class);
                        break;
                    case 1:
                        intent.setClass(mContext, CoordinateActivity.class);
                        break;
                    case 2:
                        intent.setClass(mContext, ClippingActivity.class);
                        break;
                    case 3:
                        intent.setClass(mContext, WatchActivity.class);
                        break;
                }
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDynamicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_dynamic;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_dynamic = (TextView) itemView.findViewById(R.id.tv_dynamic);
        }
    }
}
