package com.fang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.Holder.TransHolder;
import com.fang.database.TransDbManager;
import com.fang.dirtyass.R;
import com.fang.listener.ItemClickListener;
import com.fang.model.Trans;
import com.fang.ui.AddTransActivity;
import com.fang.utils.Static;

import java.util.ArrayList;

/**
 * Created by bull on 16-6-12.
 */
public class TransAdapter extends RecyclerView.Adapter<TransHolder> {

    private ArrayList<Trans> data;
    private Context mContext;
    private String trans_otherass_name;

    private TransDbManager mTransDbManager;

    public TransAdapter(ArrayList<Trans> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public TransHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_trans, parent, false);
        mTransDbManager = new TransDbManager(mContext);
        return new TransHolder(mView);
    }

    @Override
    public void onBindViewHolder(TransHolder holder, int position) {
        mTransDbManager.open();
        trans_otherass_name = mTransDbManager.getAssNameByTransId(data.get(position).getTrans_otherass_id());
        mTransDbManager.close();

        holder.tv_trans_money.setText(String.valueOf(data.get(position).getTrans_money()));
        holder.tv_trans_payass_id.setText(trans_otherass_name);
        holder.tv_trans_reason.setText(data.get(position).getTrans_reason());
        holder.tv_trans_date.setText(data.get(position).getTrans_date());

        holder.setmItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(mContext, AddTransActivity.class);
                intent.putExtra("state", Static.EDIT);
                intent.putExtra("ass_id", data.get(position).getTrans_payass_id());
                intent.putExtra("trans_id", data.get(position).getTrans_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
