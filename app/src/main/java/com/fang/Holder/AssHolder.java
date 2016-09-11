package com.fang.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;

/**
 * Created by bull on 16-6-10.
 */
public class AssHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_ass_name, tv_ass_money, tv_ass_trade, tv_ass_trans;
    ItemClickListener mItemClickListener;

    public AssHolder(View itemView) {
        super(itemView);
        tv_ass_name = (TextView) itemView.findViewById(R.id.tv_ass_name);
        tv_ass_money = (TextView) itemView.findViewById(R.id.tv_ass_money);
        tv_ass_trade = (TextView) itemView.findViewById(R.id.tv_trade_count);
        tv_ass_trans = (TextView) itemView.findViewById(R.id.tv_trans_count);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.mItemClickListener.OnItemClick(v, getLayoutPosition());
    }

    public void setmItemClickListener(ItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

}
