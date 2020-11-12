package com.example.music_player.Music_List;

public class Music {
    private long Music_Id;
    private String Music_Url;
    private String Music_Title;
    private String Music_Author;
    private String Music_Time;

    public Music(){

    }

    public Music( String music_Title, String music_Author,String music_Time,String music_Url) {

        Music_Url = music_Url;
        Music_Title = music_Title;
        Music_Author = music_Author;
        Music_Time = music_Time;
    }
    public Music(long music_Id, String music_Url, String music_Title, String music_Author, String music_Time) {
        Music_Id = music_Id;
        Music_Url = music_Url;
        Music_Title = music_Title;
        Music_Author = music_Author;
        Music_Time = music_Time;
    }
    public Music(long music_Id, String music_Url, String music_Title, String music_Author) {
        Music_Id = music_Id;
        Music_Url = music_Url;
        Music_Title = music_Title;
        Music_Author = music_Author;
    }

    public Music(long id, String url, String name) {
        this.Music_Id=id;
        this.Music_Title=name;
        this.Music_Url=url;
    }

    public long getMusic_Id() {
        return Music_Id;
    }

    public void setMusic_Id(long music_Id) {
        Music_Id = music_Id;
    }

    public String getMusic_Url() {
        return Music_Url;
    }

    public void setMusic_Url(String music_Url) {
        Music_Url = music_Url;
    }

    public String getMusic_Title() {
        return Music_Title;
    }

    public void setMusic_Title(String music_Title) {
        Music_Title = music_Title;
    }

    public String getMusic_Author() {
        return Music_Author;
    }

    public void setMusic_Author(String music_Author) {
        Music_Author = music_Author;
    }

    public String getMusic_Time() {
        return Music_Time;
    }

    public void setMusic_Time(String music_Time) {
        Music_Time = music_Time;
    }
}