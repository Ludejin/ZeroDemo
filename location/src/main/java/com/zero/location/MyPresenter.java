package com.zero.location;

import com.zero.location.utils.Const;

import rx.functions.Action1;

/**
 * Created by Jin_ on 2016/7/20
 * 邮箱：Jin_Zboy@163.com
 */
public class MyPresenter extends Contract.Presenter {
    @Override
    public void uploadData(String phoneBrand, String phoneX, String phoneY, String baiDuX, String baiDuY, String collectTime) {
        mRxManage.add(mModel.uploadData(phoneBrand,phoneX,phoneY,baiDuX,baiDuY,collectTime)
        .subscribe(new Action1<CodeRes>() {
            @Override
            public void call(CodeRes codeRes) {
                if (Const.CODE_SUCCESS == codeRes.getCode()) {
                    mView.uploadSuc();
                } else {
                    mView.showMsg("上传数据错误");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }));
    }
}
