package com.zero.domvp.module.info;

import com.zero.domvp.base.BaseModel;
import com.zero.domvp.base.BasePresenter;
import com.zero.domvp.base.BaseView;
import com.zero.domvp.entity.BaseRes;
import com.zero.domvp.entity.bean.JokeInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by Jin_ on 2016/7/8
 * 邮箱：Jin_Zboy@163.com
 */
public interface InfoContract {
    interface Model extends BaseModel {
        Observable<BaseRes<JokeInfo>> getJokes(int page);
    }

    interface View extends BaseView {
        void loadList(List<JokeInfo.JokeContent> jokeContents, int pageCount);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void getJokes(int page);

        @Override
        public void onStart() {

        }
    }
}
