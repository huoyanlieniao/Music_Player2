package com.example.music_player;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.music_player.Music_List.Music;
import com.example.music_player.Music_List.Music_Adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //播放器
    private MediaPlayer mediaPlayer = new MediaPlayer();
    //滑动条
    private SeekBar mSeekBar;
    private TextSwitcher mSwitcher;
    //播放列表
    private ArrayList<Music> musicList;
    //当前播放歌曲在列表Id
    private int NowMusicId = 0;
    //时间
    private Timer timer;
    //顺序or随机
    private boolean shunxu=true;
    //
    private Handler handler=new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // mediaPlayer=new MediaPlayer();
        // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.m1);
        //权限
        setRegist();
        //长按注册
        ListView listView = findViewById(R.id.Music_List);
        registerForContextMenu(listView);
        //时间
        final TextView Timeview=findViewById(R.id.Time);
        final TextView MusicTime=findViewById(R.id.Music_Time);


        //滑动条部分
        mSeekBar = (SeekBar) findViewById(R.id.music_seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int w=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    int time = (int) mediaPlayer.getCurrentPosition() / 1000;
                    Timeview.setText(transTime(time)+ "");
                    MusicTime.setText("/" + getMusicTime());
                    if (time == getMusciTime()) {
                        if (shunxu) {
                            try {
                                NextMusic();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Suiji();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(mSeekBar.getProgress());
                handler.postDelayed(run,1000);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Help:
                Toast.makeText(MainActivity.this, "这是帮助", Toast.LENGTH_LONG).show();
                break;
            case R.id.Get_Local:
                //获取本地音乐
                musicList = Music_Get.GetMusic(this);
                //调整界面
                ListView listView = findViewById(R.id.Music_List);
                Music_Adapter musicAdapter = new Music_Adapter(musicList, MainActivity.this);
                listView.setAdapter(musicAdapter);
                listView.setOnItemClickListener(this);
                // Toast.makeText(MainActivity.this,"aa",Toast.LENGTH_LONG).show();
                break;
            case R.id.Add_Web:
                //添加网路歌曲
                Add();
                musicAdapter = new Music_Adapter(musicList, MainActivity.this);
                listView = null;
                listView = findViewById(R.id.Music_List);
                listView.setAdapter(musicAdapter);
                listView.setOnItemClickListener(this);
                break;

        }
        return true;
    }

    //长按创建菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_content, menu);
    }

    //点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
       // Toast.makeText(MainActivity.this,item.getItemId()+"",Toast.LENGTH_LONG).show();
        switch (item.getItemId()) {
            case R.id.Delete:
                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int a=menuInfo.position;
                 Toast.makeText(this, "删除"+musicList.get(a).getMusic_Title(), Toast.LENGTH_SHORT).show();
                 musicList.remove(a);
                //调整界面
                ListView listView = findViewById(R.id.Music_List);
                Music_Adapter musicAdapter = new Music_Adapter(musicList, MainActivity.this);
                listView.setAdapter(musicAdapter);
                listView.setOnItemClickListener(this);

                if(NowMusicId>a){
                    NowMusicId=NowMusicId-1;
                }
                break;
        }
        return super.onContextItemSelected(item);

    }


    //播放
    public void Start(View view) {
        mediaPlayer.start();
    }

    //暂停或播放
    public void Pause_Start_Onclick(View view) throws IOException {
        Button button=findViewById(R.id.Music_Player_Pause_Start);
        if (mediaPlayer.isPlaying()) {
            //播放转暂停
            button.setText("播放");
            mediaPlayer.pause();

        } else {
            //暂停转播放
            mediaPlayer.start();
            button.setText("暂停");
        }
    }

    //停止播放
    public void Stop(View view) {
        mediaPlayer.stop();
    }



    //列表点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NowMusicId = position;
        try {
            StartMusic();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(MainActivity.this,musicList.get(NowMusicId).getMusic_Url()+"",Toast.LENGTH_LONG).show();
    }

    //上一曲
    public void PreviousMusic() throws IOException {
        //第一首则变成最后一首
        if (NowMusicId == 0) {
            NowMusicId = musicList.size();
        } else {
            NowMusicId = NowMusicId - 1;
        }
        StartMusic();
    }

    //下一曲or顺序
    public void NextMusic() throws IOException {
        //最后一首则变成第一首
        if (NowMusicId == musicList.size()) {
            NowMusicId = 0;
        } else {
            NowMusicId = NowMusicId + 1;
        }
        StartMusic();
    }

    //随机播放
    public void Suiji() throws IOException {
        Random random = new Random();
        int suiji = random.nextInt(musicList.size());
        NowMusicId = suiji;
        StartMusic();


    }


    //播放歌曲
    public void StartMusic() throws IOException {

        setRegist();
        String s=musicList.get(NowMusicId).getMusic_Url();
        System.out.println(s);
        mediaPlayer.stop();
        mediaPlayer=null;
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDataSource(s);
        mediaPlayer.prepare();
        mediaPlayer.start();
        //设置最大时间
        mSeekBar.setMax(getMusciTime()*1000);
        //设置等分方式
        mSeekBar.setKeyProgressIncrement(1);
        TextView textView=findViewById(R.id.Music_Message);
        textView.setText(musicList.get(NowMusicId).getMusic_Title());
        //AutoSeekBar();
        Button button=findViewById(R.id.Music_Player_Pause_Start);
        button.setText("暂停");

    }

    private void setRegist() {       //动态获取权限{
        //android 6.0新特性，一些保护权限，
        //如，文件读写除了要在AndroidManifest中申明权限，还需要进行动态获取
        if (Build.VERSION.SDK_INT >= 23) {
//大于23指android6.0以后的版本。
            int REQUEST_CODE_CONTACT = 101;
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"};
            //验证是否许可权限
            for (String str : PERMISSIONS_STORAGE) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(PERMISSIONS_STORAGE, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

    }

    //创建添加界面
    public void Add() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View ViewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_layout, null, false);
        builder.setTitle("登录对话框")
                .setView(ViewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText e1 = ViewDialog.findViewById(R.id.Web_Musci_Tile);
                        EditText e2 = ViewDialog.findViewById(R.id.Web_Music_Author);
                        EditText e3 = ViewDialog.findViewById(R.id.Web_Music_Time);
                        EditText e4 = ViewDialog.findViewById(R.id.Web_Music_Url);
                        Music music = new Music(e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString());
                        musicList.add(music);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

    /*//seekbar时间变动
    public void AutoSeekBar() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                mSeekBar.setProgress;

            }
        }, 0, 100);


    }*/

    //得到音乐时间
    public String getMusicTime(){
        return musicList.get(NowMusicId).getMusic_Time();
    }

    //得到音乐时间
    public int getMusciTime(){
        String[] s=musicList.get(NowMusicId).getMusic_Time().split(":");
        int i=0;
        try {
            int a = Integer.parseInt(s[0]);
            i=a*60;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {

            int a = Integer.parseInt(s[1]);
            i=i+a;

        } catch (NumberFormatException e) {

            e.printStackTrace();

        }
        return i;
    }

    //转换时间显示
    public String transTime(int Time){
        String s="";
        int c= Time / 60;
        s=s+c+":";
        c=Time%60;
        s=s+c;
        return s;
    }

    public void NextMusic(View view) throws IOException {
        NextMusic();
    }

    public void PreviousMusic(View view) throws IOException {
        PreviousMusic();
    }

    public void Shunxu(View view) {
        Button button =findViewById(R.id.shunxu);
        if(shunxu==true){
            button.setText("随机播放");
            shunxu=false;
        }else{
            button.setText("顺序播放");
            shunxu=true;
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            int a=mediaPlayer.getCurrentPosition();
            mSeekBar.setProgress(a);
            handler.postDelayed(run,100);
        }
    };
}




