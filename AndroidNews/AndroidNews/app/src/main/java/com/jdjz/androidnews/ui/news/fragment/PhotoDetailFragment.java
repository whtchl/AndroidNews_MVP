package com.jdjz.androidnews.ui.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppConstant;
import com.jdjz.common.base.BaseFragment;
import com.jdjz.common.baserx.RxSchedulers;
import com.jdjz.common.commonutils.ImageLoaderUtils;
import com.jdjz.common.commonutils.LogUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by tchl on 2016-12-20.
 */
public class PhotoDetailFragment extends BaseFragment {
    @Bind(R.id.photo_view)
    PhotoView photoView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private String mImgSrc;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_news_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mImgSrc = getArguments().getString(AppConstant.PHOTO_DETAIL_IMGSRC);
            LogUtils.logd("PhotoDetailFragment ImgSrc:"+mImgSrc);
        }
        initPhotoView();
        setPhotoViewClickEvent();
    }

    private void setPhotoViewClickEvent() {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener(){

            @Override
            public void onPhotoTap(View view, float x, float y) {
                mRxManager.post(AppConstant.PHOTO_TAB_CLICK,"");
            }
        });
    }

    private void initPhotoView() {
        mRxManager.add(Observable.timer(100, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.<Long>io_main())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        ImageLoaderUtils.displayBigPhoto(getContext(),photoView,mImgSrc);
                    }
                }));
    }


}
