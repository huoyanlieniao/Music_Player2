package com.example.music_player;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.music_player.Music_List.Music;
import com.example.music_player.Music_List.Music_Adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //播放器
    private MediaPlayer mediaPlayer;
    //滑动条
    private SeekBar mSeekBar;
    private TextSwitcher mSwitcher;
    //播放列表
    private ArrayList<Music> musicList;
    //当前播放歌曲在列表Id
    private int NowMusicId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mediaPlayer=new MediaPlayer();
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.m1);
        //长按注册
        ListView listView=findViewById(R.id.Music_List);
        registerForContextMenu(listView);
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
                Toast.makeText(MainActivity.this,"这是帮助",Toast.LENGTH_LONG).show();
                break;
            case R.id.Get_Local:
                //获取本地音乐
                musicList=Music_Get.GetMusic(this);
                //调整界面
                ListView listView=findViewById(R.id.Music_List);
                Music_Adapter musicAdapter=new Music_Adapter(musicList,MainActivity.this);
                listView.setAdapter(musicAdapter);
                listView.setOnItemClickListener(this);
               // Toast.makeText(MainActivity.this,"aa",Toast.LENGTH_LONG).show();
                break;
            case R.id.Add_Web:
                //添加网路歌曲
                Add();
                musicAdapter=new Music_Adapter(musicList,MainActivity.this);
                listView=null;
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
        switch (item.getItemId()) {
            case R.id.Delete:
                //这里需要看一下id后续再修改
               // Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                if(NowMusicId>){
                    NowMusicId=NowMusicId-1;
                }
                musicList.remove();
                break;
        }
        return super.onContextItemSelected(item);

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

    //列表点击
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         NowMusicId=position;
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
        if(NowMusicId==0){
            NowMusicId=musicList.size();
        }else{
            NowMusicId=NowMusicId-1;
        }
        StartMusic();
    }

    //下一曲or顺序
    public void NextMusic() throws IOException {
        //最后一首则变成第一首
        if(NowMusicId==musicList.size()){
            NowMusicId=0;
        }else{
            NowMusicId=NowMusicId+1;
        }
        StartMusic();
    }

    //随机播放
    public void Suiji() throws IOException {
        Random random=new Random();
        int suiji=random.nextInt(musicList.size());
        NowMusicId=suiji;
        StartMusic();

    }


    //播放歌曲
    public void StartMusic() throws IOException {
        mediaPlayer.setDataSource(musicList.get(NowMusicId).getMusic_Url());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    //创建添加界面
    public void Add(){
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        final View ViewDialog= LayoutInflater.from(MainActivity.this).inflate(R.layout.add_layout,null,false);
        builder.setTitle("登录对话框")
                .setView(ViewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText e1=ViewDialog.findViewById(R.id.Web_Musci_Tile);
                        EditText e2=ViewDialog.findViewById(R.id.Web_Music_Author);
                        EditText e3=ViewDialog.findViewById(R.id.Web_Music_Time);
                        EditText e4=ViewDialog.findViewById(R.id.Web_Music_Url);

                        Music music=new Music(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),e4.getText().toString());
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
}
