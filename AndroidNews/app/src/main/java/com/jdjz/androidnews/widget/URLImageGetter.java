package com.jdjz.androidnews.widget;

import android.app.Application;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.AndroidCharacter;
import android.text.Html;
import android.widget.TextView;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.api.Api;
import com.jdjz.androidnews.api.HostType;
import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.common.commonutils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tchl on 2016-12-16.
 */
public class URLImageGetter implements Html.ImageGetter{
    private TextView mTextView;
    private int mPicWidth;
    private String mNewsBody;
    private int mPicCount;
    private int mPicTotal;
    public Subscription mSubscription;
    private static final String mFilePath = AppApplication.getAppContext().getCacheDir().getAbsolutePath();
    public URLImageGetter(TextView textView,String newsBody,int picTotal){
        mTextView = textView;
        mNewsBody = newsBody;
        mPicTotal = picTotal;
        mPicWidth = mTextView.getWidth();
    }
    @Override
    public Drawable getDrawable(String source) {
        Drawable drawable;
        File file = new File(mFilePath,source.hashCode()+"");
        if(file.exists()){
            LogUtils.logd("mPicCount:"+mPicCount);
            mPicCount++;
            drawable = getDrawableFromDisk(file);
        }else{
            drawable = getDrawableFromNet(source);
        }
        return drawable;
    }

    private Drawable getDrawableFromNet(final String source) {
        mSubscription = Api.getDefault(HostType.NEWS_DETAIL_HTML_PHOTO)
                .getNewsBodyHtmlPhoto(Api.getCacheControl(),source)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody response) {
                        return WritePicToDisk(response, source);
                    }
                }).subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Boolean isLoadSuccess) {
                        LogUtils.logd("URLImageGetter mPicCount:"+mPicCount);
                        mPicCount++;
                        if (isLoadSuccess && (mPicCount == mPicTotal - 1)) {
                            mTextView.setText(Html.fromHtml(mNewsBody, URLImageGetter.this, null));
                        }
                    }
                });

        return createPicPlaceholder();
    }
    @NonNull
    private Boolean WritePicToDisk(ResponseBody response, String source) {
        File file = new File(mFilePath, source.hashCode() + "");
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = response.byteStream();
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
    }

    private Drawable getDrawableFromDisk(File file) {
        Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
        if(drawable != null){
            int picHeight = calculatePicHeight(drawable);
            drawable.setBounds(0,0,mPicWidth,picHeight);
        }
        return drawable;
    }

    private int calculatePicHeight(Drawable drawable) {
        float imgWidth = drawable.getIntrinsicHeight();
        float imgHeight = drawable.getIntrinsicWidth();
        float rate = imgHeight/imgWidth;
        return (int)(mPicWidth*rate);
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private Drawable createPicPlaceholder() {
        Drawable drawable;
        int color = R.color.white;
        drawable = new ColorDrawable(AppApplication.getAppContext().getResources().getColor(color));
        drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3);
        return drawable;
    }
}
