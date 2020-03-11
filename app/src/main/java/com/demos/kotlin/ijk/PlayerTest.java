package com.demos.kotlin.ijk;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demos.kotlin.R;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class PlayerTest extends AppCompatActivity {

    //private String path = "rtsp://172.16.67.18/1001:admin:bonc123456@172.16.67.253:554:0:0";
    private String path =
        "http://172.16.17.111/group1/M00/00/31/rBARb13t7EmAA7JJABLmkIbBL8Q055.mp4";
    private List<VideoPlayerIJK> playerIJKList = new ArrayList<>();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer_test);
        initPlayer();
    }

    @Override protected void onResume() {
        super.onResume();
    }

    private void initPlayer() {
        //加载native库
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        playerIJKList.add((VideoPlayerIJK) findViewById(R.id.surface_view_player_test_01));
        playerIJKList.add((VideoPlayerIJK) findViewById(R.id.surface_view_player_test_02));
        playerIJKList.add((VideoPlayerIJK) findViewById(R.id.surface_view_player_test_03));
        playerIJKList.add((VideoPlayerIJK) findViewById(R.id.surface_view_player_test_04));
        initPlayer(0);
        initPlayer(1);
        initPlayer(2);
        initPlayer(3);
    }

    private void initPlayer(int index) {
        playerIJKList.get(index).setListener(new VideoPlayerListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            }

            @Override
            public void onCompletion(IMediaPlayer mp) {
            }

            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                return false;
            }

            @Override
            public void onPrepared(IMediaPlayer mp) {
                // 视频准备好播放了，但是他不会自动播放，需要手动让他开始。
                mp.start();
            }

            @Override
            public void onSeekComplete(IMediaPlayer mp) {

            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num,
                int sar_den) {
                //在此可以获取到视频的宽和高
            }
        });
        playerIJKList.get(index).setVideoPath(path);
    }

    @Override protected void onStop() {
        IjkMediaPlayer.native_profileEnd();
        super.onStop();
    }
}
