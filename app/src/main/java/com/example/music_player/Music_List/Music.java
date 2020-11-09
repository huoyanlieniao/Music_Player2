package com.example.music_player.Music_List;

public class Music {
    private String Music_Title;
    private String Music_Author;
    private String Music_Time;

    public Music(String music_Title, String music_Author, String music_Time) {
        Music_Title = music_Title;
        Music_Author = music_Author;
        Music_Time = music_Time;
    }

    public Music(String music_Title) {
        Music_Title = music_Title;
    }

    public Music() {

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