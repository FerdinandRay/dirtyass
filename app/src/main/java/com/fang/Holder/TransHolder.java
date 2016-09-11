package com.fang.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;

/**
 * Created by bull on 16-6-12.
 */
public class TransHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_trans_money, tv_trans_date, tv_trans_payass_id, tv_trans_reason;
    ItemClickListener mItemClickListener;

    public TransHolder(View itemView) {
        super(itemView);
        tv_trans_payass_id = (TextView) itemView.findViewById(R.id.tv_trans_payass_id);
        tv_trans_money = (TextView) itemView.findViewById(R.id.tv_trans_money);
        tv_trans_reason = (TextView) itemView.findViewById(R.id.tv_trans_reason);
        tv_trans_date = (TextView) itemView.findViewById(R.id.tv_trans_date);

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
