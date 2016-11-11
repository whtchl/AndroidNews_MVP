package com.jdjz.common.baserx;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by tchl on 2016-11-11.
 */
public class RxSchedulers {
    public static <T> Observable.Transformer<T,T>io_main(){
        return new Observable.Transformer<T,T>(){
            @Override
            public Observable<T> call(Observable<T> observable){
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
