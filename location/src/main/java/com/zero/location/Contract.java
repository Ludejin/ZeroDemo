package com.zero.location;

import com.zero.location.mvp.BaseModel;
import com.zero.location.mvp.BasePresenter;
import com.zero.location.mvp.BaseView;

import rx.Observable;

/**
 * Created by Jin_ on 2016/7/20
 * 邮箱：Jin_Zboy@163.com
 */
public interface Contract {
    interface Model extends BaseModel {
        Observable<CodeRes> uploadData(String phoneBrand, String phoneX,
                                       String phoneY, String baiDuX,
                                       String baiDuY, String collectTime);
    }

    interface View extends BaseView {
        void uploadSuc();
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void uploadData(String phoneBrand, String phoneX,
                                        String phoneY, String baiDuX,
                                        String baiDuY, String collectTime);

        @Override
        public void onStart() {

        }
    }
}
