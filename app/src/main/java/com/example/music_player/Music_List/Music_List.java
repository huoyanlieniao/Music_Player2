package com.example.music_player.Music_List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.music_player.R;

import java.util.ArrayList;

public class Music_List implements AdapterView.OnItemClickListener {

   private ArrayList<Music> musicArrayList;
   private ListView listView;

   public Music_List(){

   }

   public Music_List(ListView listView,ArrayList<Music>music){
      this.listView=listView;
      this.musicArrayList=music;
   }

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      View inflate = inflater.inflate(R.layout.music_list_adaer, container, false);
      listView=inflate.findViewById(R.id.list_item);
      Word_List_Adapter word_list_adapter=new Word_List_Adapter(words,getActivity());
      listView.setAdapter(word_list_adapter);
      listView.setOnItemClickListener(this);
      return inflate;
   }
   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

   }
}