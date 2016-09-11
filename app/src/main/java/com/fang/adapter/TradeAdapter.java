package com.fang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.Holder.TradeHolder;
import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;
import com.fang.model.Trade;
import com.fang.ui.AddTradeActivity;
import com.fang.utils.Static;

import java.util.ArrayList;

/**
 * Created by bull on 16-6-12.
 */
public class TradeAdapter extends RecyclerView.Adapter<TradeHolder> {

    private ArrayList<Trade> data;
    private Context mContext;

    public TradeAdapter(ArrayList<Trade> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public TradeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_trade, parent, false);
        return new TradeHolder(mView);
    }

    @Override
    public void onBindViewHolder(TradeHolder holder, int position) {
        holder.tv_trade_money.setText(String.valueOf(data.get(position).getTrade_money()));
        holder.tv_trade_reason.setText(data.get(position).getTrade_reason());
        holder.tv_trade_date.setText(data.get(position).getTrade_date());

        holder.setmItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(mContext, AddTradeActivity.class);
                intent.putExtra("ass_id", data.get(position).getTrade_ass_id());
                intent.putExtra("trade_id", data.get(position).getTrade_id());
                intent.putExtra("state", Static.EDIT);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
