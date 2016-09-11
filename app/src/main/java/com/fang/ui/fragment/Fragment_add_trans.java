package com.fang.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.adapter.TransAdapter;
import com.fang.database.D;
import com.fang.database.TransDbManager;
import com.fang.dirtyass.R;
import com.fang.model.Trans;
import com.fang.ui.AddTransActivity;
import com.fang.utils.Static;

import java.util.ArrayList;

/**
 * Created by bull on 16-6-11.
 */
public class Fragment_add_trans extends Fragment {

    private View mView;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private TransAdapter mTransAdapter;
    private TransDbManager mTransDbManager;
    private Context mContext;
    private ArrayList<Trans> data = new ArrayList<>();

    private int ass_id;

    public void setmContext(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_detail_viewpager, container, false);
        init(mView);
        return mView;
    }

    private void init(View mView){
        final Bundle bundle = getArguments();
        ass_id = bundle.getInt("ass_id");

        mTransDbManager = new TransDbManager(mContext);
        mTransAdapter = new TransAdapter(data, mContext);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTransAdapter);

        fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        if(fab != null){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddTransActivity.class);
                    intent.putExtra("ass_id", ass_id);
                    intent.putExtra("state", Static.ADD);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void refresh(){
        Trans trans;
        mTransDbManager.open();
        data.clear();
        Cursor cursor = mTransDbManager.selectAllTransByAssId(ass_id);
        mTransDbManager.close();
        if(cursor.moveToFirst()){
            do {
                trans = new Trans();
                trans.setTrans_id(cursor.getInt(cursor.getColumnIndex(D.Tbl_Trans.TRANS_ID)));
                trans.setTrans_date(cursor.getString(cursor.getColumnIndex(D.Tbl_Trans.TRANS_DATE)));
                trans.setTrans_money(cursor.getDouble(cursor.getColumnIndex(D.Tbl_Trans.TRANS_MONEY)));
                trans.setTrans_payass_id(cursor.getInt(cursor.getColumnIndex(D.Tbl_Trans.TRANS_PAYASS_ID)));
                trans.setTrans_otherass_id(cursor.getInt(cursor.getColumnIndex(D.Tbl_Trans.TRANS_OTHERASS_ID)));
                trans.setTrans_reason(cursor.getString(cursor.getColumnIndex(D.Tbl_Trans.TRANS_REASON)));
                data.add(trans);
            } while (cursor.moveToNext());
        }

        if(!(data.size() < 1)){
            mRecyclerView.setAdapter(mTransAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }
}
