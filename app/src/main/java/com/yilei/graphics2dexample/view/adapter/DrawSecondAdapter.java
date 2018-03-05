package com.yilei.graphics2dexample.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilei.graphics2dexample.R;

import java.util.ArrayList;

/**
 * Created by lei on 2018/3/2.
 */

public class DrawSecondAdapter extends RecyclerView.Adapter<DrawSecondAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> itemList;
    public DrawSecondAdapter(Context context, ArrayList<String> item){
        this.mContext = context;
        this.itemList = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_draw_second, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_draw_second;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_draw_second = (TextView) itemView.findViewById(R.id.tv_draw_second);
        }
    }


}
