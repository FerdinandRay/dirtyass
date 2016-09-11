package com.fang.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;

/**
 * Created by bull on 16-6-12.
 */
public class TradeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_trade_money, tv_trade_reason, tv_trade_date;
    ItemClickListener mItemClickListener;

    public TradeHolder(View itemView) {
        super(itemView);
        tv_trade_money = (TextView) itemView.findViewById(R.id.tv_trade_money);
        tv_trade_reason = (TextView) itemView.findViewById(R.id.tv_trade_reason);
        tv_trade_date = (TextView) itemView.findViewById(R.id.tv_trade_date);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        this.mItemClickListener.OnItemClick(v, getLayoutPosition());
    }

    public void setmItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
