package com.example.music_player;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextSwitcher;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //播放器
    private MediaPlayer mediaPlayer;
    //滑动条
    private SeekBar mSeekBar;
    private TextSwitcher mSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mediaPlayer=new MediaPlayer();
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.m1);

        //滑动条部分
        mSeekBar = (SeekBar) findViewById(R.id.music_seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

            }

    //播放
    public void Start(View view){
        mediaPlayer.start();
    }

    //暂停或播放
    public void Pause_Start_Onclick(View view) throws IOException {

        if(mediaPlayer.isPlaying()){
            //播放转暂停
            mediaPlayer.pause();
        }else{
            //暂停转播放
            mediaPlayer.start();
        }
    }

    //停止播放
    public void Stop(View view){
        mediaPlayer.stop();
    }

    //循环播放,目前未实现
    public void Looping(View view){
        boolean loop=mediaPlayer.isLooping();
        //if(!loop)
    }

}
