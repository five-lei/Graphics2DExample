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

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<String> mTitles;
    public DrawAdapter(Context context, ArrayList<String> titles){
        this.mContext = context;
        this.mTitles = titles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_draw, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.tv_title.setText(mTitles.get(position));

    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_draw);
        }
    }
}
