package com.wingtech.smusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView textview_username;
	private TextView textview_musicname;
	private TextView textview_musicnote;
	private ImageButton btn_music;
	private ImageButton btn_previous;
	private ImageButton btn_switch;
	private ImageButton btn_next;
	private List<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
	private List<HashMap<String, Object>> hashMapMusicInfos= new ArrayList<HashMap<String, Object>>(); ;
	private SimpleAdapter simAdapter;
	private ImageView img_user;
	private ListView listview_music;
	//private String[] data={"1","2","3","4","5","6","7","8","9","10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idInit();
        listInit();
 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    protected void idInit(){
    	
    	textview_username=(TextView)findViewById(R.id.textview_username);
    	textview_musicname=(TextView)findViewById(R.id.textview_musicname);
    	textview_musicnote=(TextView)findViewById(R.id.textview_musicnote);
    	btn_music=(ImageButton)findViewById(R.id.btn_music);
    	btn_previous=(ImageButton)findViewById(R.id.btn_previous);
    	btn_switch=(ImageButton)findViewById(R.id.btn_switch);
    	btn_next=(ImageButton)findViewById(R.id.btn_next);
    	img_user=(ImageView)findViewById(R.id.img_userhead);
    	listview_music=(ListView)findViewById(R.id.listview_music);
    	
    };
    public void listInit() {
    	 Log.e("Tag","aaaa");
    	 musicInfos=MediaUtil.getMusicInfos(MainActivity.this);
    	 Log.e("Tag","bbbb");
         MediaUtil.getMusicMaps(musicInfos);
         Log.e("Tag","cccc");
    	 simAdapter = new SimpleAdapter(this, hashMapMusicInfos,
    	 R.layout.music_list_item_layout, new String[] {"title",
    	 "Artist", "duration" }, new int[] {R.id.music_title,
    	 R.id.music_Artist, R.id.music_duration });
    	 listview_music.setAdapter(simAdapter);
    }
    
}
