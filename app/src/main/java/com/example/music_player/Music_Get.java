package com.example.music_player;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import com.example.music_player.Music_List.Music;

import java.util.ArrayList;

public class Music_Get {
    private static ArrayList<Music> musicArrayList;

    public static ArrayList<Music> GetMusic(Context context) {
        musicArrayList=new ArrayList<Music>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                Music music = new Music();
                music.setMusic_Title(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)));
                music.setMusic_Author(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                music.setMusic_Url(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                music.setMusic_Time(formatTime(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))));
                musicArrayList.add(music);

            }

        }
        cursor.close();
        return musicArrayList;
    }


        //    转换歌曲时间的格式
        public static String formatTime(int time) {
            if (time / 1000 % 60 < 10) {
                String tt = time / 1000 / 60 + ":0" + time / 1000 % 60;
                return tt;
            } else {
                String tt = time / 1000 / 60 + ":" + time / 1000 % 60;
                return tt;
            }
        }

}