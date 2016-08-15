package com.zero.domvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zero.domvp.R;
import com.zero.domvp.entity.bean.JokeInfo;

/**
 * Created by Jin_ on 2016/8/15
 * 邮箱：Jin_Zboy@163.com
 */
public class MyAdapter extends BaseQuickAdapter<JokeInfo.JokeContent> {

    public MyAdapter() {
        super(R.layout.item_list, null);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, JokeInfo.JokeContent data) {
        viewHolder.setText(R.id.item_title, data.getTitle())
                .setText(R.id.item_time, data.getCt().substring(0,10))
                .setText(R.id.item_content, data.getText());
    }
}
