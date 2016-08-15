package com.zero.domvp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zero.domvp.adapter.MyAdapter;
import com.zero.domvp.common.DividerItemDecoration;
import com.zero.domvp.common.utils.ToastUtil;
import com.zero.domvp.entity.bean.JokeInfo;
import com.zero.domvp.module.info.InfoContract;
import com.zero.domvp.module.info.InfoModel;
import com.zero.domvp.module.info.InfoPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements InfoContract.View,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private InfoPresenter mPresenter;
    private InfoModel mModel;

    private RecyclerView mRcyList;
    private SwipeRefreshLayout mRefLayout;
    private MyAdapter mAdapter;

    private int page = 1;
    private boolean isRefresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRcyList = (RecyclerView) findViewById(R.id.rcy_list);
        mRefLayout = (SwipeRefreshLayout) findViewById(R.id.ref_layout);

        mPresenter = new InfoPresenter();
        mModel = new InfoModel();
        mPresenter.setVM(mModel, this, this);

        init();
        mRefLayout.setRefreshing(true);
        onRefresh();
    }

    private void init() {
        mAdapter = new MyAdapter();
        mRefLayout.setProgressViewOffset(true, -20, 60);
        mRefLayout.setColorSchemeResources(R.color.green,
                R.color.red, R.color.black);

        DividerItemDecoration dividerLine = new DividerItemDecoration(DividerItemDecoration.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(getResources().getColor(R.color.light_grey));
        mRcyList.addItemDecoration(dividerLine);
        mRcyList.setLayoutManager(new LinearLayoutManager(this));

        mRcyList.setNestedScrollingEnabled(false);
        mRcyList.setHasFixedSize(false);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.openLoadMore(20, true);
        mRcyList.setAdapter(mAdapter);

        // 设置事件监听
        mAdapter.setOnLoadMoreListener(this);
        mRefLayout.setOnRefreshListener(this);

        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ToastUtil.showShort(MainActivity.this, mAdapter.getItem(i).getText());
            }
        });
    }


    @Override
    public void showMsg(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void loadList(List<JokeInfo.JokeContent> infos, int pageCount) {
        if (isRefresh) {
            mRefLayout.setRefreshing(false);
            if (null != infos && 0 < infos.size()) {
                mAdapter.setNewData(infos);
            } else {
                // 设置空界面
                //mAdapter.setEmptyView(emptyView);
            }
        } else {
            if (null != infos) {
                mAdapter.notifyDataChangedAfterLoadMore(infos, true);
            }
        }
        if (page >= pageCount) {
            mAdapter.notifyDataChangedAfterLoadMore(false);
            mAdapter.removeAllFooterView();
            // 没有更多
            //mAdapter.addFooterView(noMoreView);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefresh = true;
        mPresenter.getJokes(page);
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        mPresenter.getJokes(++page);
    }
}
