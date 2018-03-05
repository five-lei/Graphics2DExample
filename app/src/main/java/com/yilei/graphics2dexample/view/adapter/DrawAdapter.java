package com.yilei.graphics2dexample.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilei.graphics2dexample.R;
import com.yilei.graphics2dexample.view.activity.DynamicActivity;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_title.setText(mTitles.get(position));
//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        holder.item_recycler.setLayoutManager(manager);
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
                        intent.setClass(mContext, DynamicActivity.class);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    default:
                        break;
                }
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public RecyclerView item_recycler;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_draw);
            item_recycler = (RecyclerView) itemView.findViewById(R.id.item_recycler);
        }
    }
}
