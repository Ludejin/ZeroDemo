package com.zero.domvp.module.info;

import com.zero.domvp.common.http.LoadingSubscriber;
import com.zero.domvp.entity.BaseRes;
import com.zero.domvp.entity.bean.JokeInfo;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public class InfoPresenter extends InfoContract.Presenter {
    @Override
    public void getJokes(int page) {
        mRxManage.add(mModel.getJokes(page)
        .subscribe(new LoadingSubscriber<BaseRes<JokeInfo>>(mContext) {
            @Override
            public void _onNext(BaseRes<JokeInfo> entity) {
                mView.loadList(entity.showapi_res_body.getContentlist(),
                        entity.showapi_res_body.getAllPages());
            }
        }));
    }
}
