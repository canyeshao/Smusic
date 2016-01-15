package com.wingtech.smusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wingtech.music.MediaUtil;
import com.wingtech.music.MusicInfo;

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
        Log.i("init","go init");
        Init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    protected void Init(){
    	
    	textview_username=(TextView)findViewById(R.id.textview_username);
    	textview_musicname=(TextView)findViewById(R.id.textview_musicname);
    	textview_musicnote=(TextView)findViewById(R.id.textview_musicnote);
    	btn_music=(ImageButton)findViewById(R.id.btn_music);
    	btn_previous=(ImageButton)findViewById(R.id.btn_previous);
    	btn_switch=(ImageButton)findViewById(R.id.btn_switch);
    	btn_next=(ImageButton)findViewById(R.id.btn_next);
    	img_user=(ImageView)findViewById(R.id.img_userhead);
    	listview_music=(ListView)findViewById(R.id.listview_music);
    	listInit();
    	buttoniInit();
    };
    
    
    private void buttoniInit() {
		
    	btn_music.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,Music_ControlActivity.class);
				startActivity(intent);
			}
		});
    		
    }

	public void listInit() {
    	 Log.i("list","in init");
    	 musicInfos=MediaUtil.getMusicInfos(MainActivity.this);
    	 Log.i("list","get musicInfos ");
    	 hashMapMusicInfos= MediaUtil.getMusicMaps(musicInfos);
         Log.i("list","get hashMapMusicInfos");
    	 simAdapter = new SimpleAdapter(this, hashMapMusicInfos,
    	 R.layout.music_list_item_layout, new String[] {"title",
    	 "Artist", "duration" }, new int[] {R.id.music_title,
    	 R.id.music_Artist, R.id.music_duration });
    	 Log.i("list","get simAdapter ");
    	 listview_music.setAdapter(simAdapter);
    	 Log.i("list","get listview_music"); 
    	 listview_music.setOnItemClickListener(new OnItemClickListener() {
    	 @Override
    	 	public void onItemClick(AdapterView<?> parent, View view, int position,
    			long id) {
    		// TODO Auto-generated method stub
    		 HashMap<String, Object> map=(HashMap<String, Object>)listview_music.getItemAtPosition(position);
    		 textview_musicname.setText(map.get("title").toString());
    		 textview_musicnote.setText(map.get("Artist").toString());
    	 	} 
		  });
    	  Log.i("list","get setOnItemClickListener()");
    	
    }
    
}
