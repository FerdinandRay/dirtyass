package com.fang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.Holder.AssHolder;
import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;
import com.fang.model.Ass;
import com.fang.ui.DetailActivity;

import java.util.ArrayList;

/**
 * Created by bull on 16-6-4.
 */
public class AssAdapter extends RecyclerView.Adapter<AssHolder>{

    private ArrayList<Ass> data;
    private Context mContext;

    public AssAdapter(ArrayList<Ass> data, Context mContext){
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public AssHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_ass, parent, false);
        return new AssHolder(mView);
    }

    @Override
    public void onBindViewHolder(AssHolder holder, int position) {
        holder.tv_ass_name.setText(data.get(position).getAss_name());
        holder.tv_ass_trade.setText(String.valueOf(data.get(position).getAss_trade_count()));
        holder.tv_ass_trans.setText(String.valueOf(data.get(position).getAss_trans_count()));
        holder.tv_ass_money.setText(String.valueOf(data.get(position).getAss_total_money()));
        holder.setmItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("ass_id", data.get(position).getAss_id());
                intent.putExtra("ass_name", data.get(position).getAss_name());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}