package com.qf.wenli.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qf.wenli.bindservicedemo.R;

/**
 * Created by Administrator on 2016/12/24.
 */

public class BindServicePlayMusic extends Service {
    private MediaPlayer mediaPlayer;
    private IBinder binder = null;

    @Override
    public void onCreate() {
        Log.d("mwl", "onCreate: 执行");
        super.onCreate();
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.mm);
            mediaPlayer.setLooping(true);
        }
      binder =new MyBindler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("mwl", "onBind: 执行");
        play();
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("mwl", "onDestroy:执行 ");
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void play(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    public void stop(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

   public  class MyBindler extends Binder{
        public BindServicePlayMusic getService(){
            return BindServicePlayMusic.this;
        }
    };
}
