package com.qf.wenli.bindservicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qf.wenli.service.BindServicePlayMusic;

public class MainActivity extends AppCompatActivity {

    private Intent intent ;
    private ServiceConnection conn = null;
    private BindServicePlayMusic musicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,BindServicePlayMusic.class);
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicService = ((BindServicePlayMusic.MyBindler) service).getService();
                if (musicService != null){
                    musicService.play();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                if (musicService == null){
                    bindService(intent,conn, Context.BIND_AUTO_CREATE);
                }else {
                    musicService.play();
                }
                break;
            case R.id.btn_pause:
                if (musicService != null){
                    musicService.pause();
                }
                break;
            case R.id.btn_stop:
                if (musicService != null){
                    musicService.stop();
                }
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.btn_stopService:
                //BindService中stopService(Intent)不起作用，要通过unbindService来停止服务
                //stopService(intent);
                //musicService = null;的目的是如果停止服务后，再次“播放”，可以以正常执行
                //如果不讲musicService设置为null,再次播放是，将执行musicService.play(),而不执行bind Service（），会导致异常
                musicService = null;
                unbindService(conn);
                break;
        }
    }
}
