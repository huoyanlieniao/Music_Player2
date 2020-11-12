package com.example.music_player.Music_List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.music_player.R;

import java.util.ArrayList;
import java.util.List;

public class Music_Adapter extends BaseAdapter {
    private ArrayList<Music> music;
    private Context context;


    public Music_Adapter(ArrayList<Music> music, Context context) {
        this.music = music;
        this.context = context;
    }

    @Override
    public int getCount() {
        return music.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=new ViewHolder();
        if(convertView == null){
            //视图为空
            //创建视图
            convertView = LayoutInflater.from(context).inflate(R.layout.music_list_layout,parent,false);
            viewHolder = new ViewHolder();
            //获取控件
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.Music_Picture);
            viewHolder.music_title= (TextView) convertView.findViewById(R.id.Music_Title);
            viewHolder.music_author= (TextView) convertView.findViewById(R.id.Music_Author);
            viewHolder.music_time= (TextView) convertView.findViewById(R.id.Music_Time);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
       // viewHolder.imageView.setImageURI();
        viewHolder.music_title.setText(music.get(position).getMusic_Title());
        viewHolder.music_author.setText(music.get(position).getMusic_Author());
        viewHolder.music_time.setText(music.get(position).getMusic_Time());
        return convertView;

    }

    private class ViewHolder{
        ImageView imageView;
        TextView music_title;
        TextView music_author;
        TextView music_time;
    }

}