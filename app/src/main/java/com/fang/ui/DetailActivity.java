package com.fang.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fang.adapter.FragmentAdapter;
import com.fang.database.AssDbManager;
import com.fang.database.TradeDbManager;
import com.fang.database.TransDbManager;
import com.fang.dirtyass.R;
import com.fang.ui.fragment.Fragment_add_trade;
import com.fang.ui.fragment.Fragment_add_trans;

/**
 * Created by bull on 16-6-6.
 */
public class DetailActivity extends AppCompatActivity {

    private int ass_id;
    private String ass_name;

    private ViewPager viewPager;
    private Context mContext;

    private AssDbManager mAssDbManager;
    private TradeDbManager mTradeDbManager;
    private TransDbManager mTransDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 初始化数据库链接
        mAssDbManager = new AssDbManager(mContext);
        mTradeDbManager = new TradeDbManager(mContext);
        mTransDbManager = new TransDbManager(mContext);

        // 获得页面传送参数
        ass_id = getIntent().getIntExtra("ass_id", 0);
        ass_name = getIntent().getStringExtra("ass_name");

        mContext = DetailActivity.this;

        // 设置返回按钮
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ass_name + "的羞羞小账本");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putInt("ass_id", ass_id);

        // viewPager设置
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        Fragment_add_trade mFragment_add_trade = new Fragment_add_trade();
        mFragment_add_trade.setArguments(bundle);
        mFragment_add_trade.setmContext(mContext);
        Fragment_add_trans mFragment_add_trans = new Fragment_add_trans();
        mFragment_add_trans.setArguments(bundle);
        mFragment_add_trans.setmContext(mContext);
        adapter.addFragment(mFragment_add_trade, "添加欠账");
        adapter.addFragment(mFragment_add_trans, "转移欠账");
        viewPager.setAdapter(adapter);

        // 获得tablayout，这个和Viewpager会有联系
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // 这个函数让viewpager和tablayout联系在一起
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_modify_ass :
                edit();
                break;
            case R.id.item_del_ass :
                del();
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void edit(){
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.dialog_add_ass, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final EditText et_assName = (EditText) view.findViewById(R.id.dialog_ass_name);
        builder.setTitle("修改欠账人姓名");
        builder.setView(view);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAssDbManager.open();
                int result = mAssDbManager.updateAssName(et_assName.getText().toString(), ass_id);
                mAssDbManager.close();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }

    private void del(){
        mAssDbManager.open();
        mAssDbManager.deleteAss(ass_id);
        mAssDbManager.close();

        mTradeDbManager.open();
        mTradeDbManager.deleteTradeByAssId(ass_id);
        mTradeDbManager.close();

        mTransDbManager.open();
        mTransDbManager.deleteTransByPayAssId(ass_id);
        mTransDbManager.deleteTransByOtherAssId(ass_id);
        mTransDbManager.close();
    }
}
