package com.jdjz.androidnews.db;

import com.jdjz.androidnews.R;
import com.jdjz.androidnews.app.AppApplication;
import com.jdjz.androidnews.bean.VideoChannelTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tchl on 2017-01-16.
 */
public class VideoChannelTableManager {
    public static List<VideoChannelTable> loadVideosChannelsMine(){
        List<String> channelName = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.video_channel_name));
        List<String> channelId = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.video_channel_id));
        List<VideoChannelTable> newsChannelTables = new ArrayList<>();
        for(int i=0;i<channelName.size();i++){
            VideoChannelTable entity = new VideoChannelTable(channelId.get(i),channelName.get(i));
            newsChannelTables.add(entity);
        }
        return  newsChannelTables;
    }
}
