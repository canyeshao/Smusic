package com.wingtech.smusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.wingtech.musicplayer.Constant;
import com.wingtech.musicplayer.ControlPlayerReceiver;
import com.wingtech.musicplayer.MusicPlayer;
import com.wingtech.musicsource.MediaUtil;
import com.wingtech.musicsource.MusicInfo;

public class MainActivity extends Activity {
	private TextView mtextview_username;
	private TextView mtextview_musicname;
	private TextView mtextview_musicnote;
	private ImageButton mbtn_music_photo;
	private ImageButton mbtn_previous;
	private ImageButton mbtn_switch;
	private ImageButton mbtn_next;
	private ImageView mimg_user;
	private List<MusicInfo> mmusicInfos = new ArrayList<MusicInfo>();
	private List<HashMap<String, Object>> mhashMapMusicInfos= new ArrayList<HashMap<String, Object>>(); ;
	private SimpleAdapter msimAdapter;
	private ListView mlistview_music;
	private Bundle mBundle=new Bundle();
	private ControlPlayerReceiver mPlayerReceiver=new ControlPlayerReceiver();
	//private String[] data={"1","2","3","4","5","6","7","8","9","10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("init","go init");
        Init();
    }
  
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	IntentFilter intentfilter=new IntentFilter();
    	intentfilter.addAction(Constant.ACTION_MUSIC_CONTROL);
    	this.registerReceiver(mPlayerReceiver, intentfilter);
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	this.unregisterReceiver(mPlayerReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    protected void Init(){
    	
    	mtextview_username=(TextView)findViewById(R.id.textview_username);
    	mtextview_musicname=(TextView)findViewById(R.id.textview_musicname);
    	mtextview_musicnote=(TextView)findViewById(R.id.textview_musicnote);
    	mbtn_music_photo=(ImageButton)findViewById(R.id.btn_music_photo);
    	mbtn_previous=(ImageButton)findViewById(R.id.btn_previous);
    	mbtn_switch=(ImageButton)findViewById(R.id.btn_switch);
    	mbtn_next=(ImageButton)findViewById(R.id.btn_next);
    	mimg_user=(ImageView)findViewById(R.id.img_userhead);
    	mlistview_music=(ListView)findViewById(R.id.listview_music);
    	listInit();
    	buttonInit();
    	Log.i("init","init over");
    };
     
    
    private void buttonInit() {
		
    	mbtn_music_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mmusic_control_intent=new Intent(MainActivity.this,Music_ControlActivity.class);
				mmusic_control_intent.putExtras(mBundle);
				startActivity(mmusic_control_intent);
			}
		});
    	
    	
    		
    }

	public void listInit() {
    	 Log.i("list","in init");
    	 mmusicInfos=MediaUtil.getMusicInfos(MainActivity.this);
    	 mhashMapMusicInfos= MediaUtil.getMusicMaps(mmusicInfos);
         Log.i("list","get hashMapMusicInfos");
         
    	 msimAdapter = new SimpleAdapter(this, mhashMapMusicInfos,
    	 R.layout.music_list_item_layout, new String[] {"title",
    	 "Artist", "duration" }, new int[] {R.id.music_title,
    	 R.id.music_Artist, R.id.music_duration });
    	 
    	 Log.i("list","get simAdapter ");
    	 mlistview_music.setAdapter(msimAdapter);
    	 Log.i("list","get listview_music"); 
    	 mlistview_music.setOnItemClickListener(new OnItemClickListener() {
    	 @Override
    	 	public void onItemClick(AdapterView<?> parent, View view, int position,
    			long id) {
    		// TODO Auto-generated method stub
    		 HashMap<String, Object> map=(HashMap<String, Object>)mlistview_music.getItemAtPosition(position);
    		 mtextview_musicname.setText(map.get("title").toString());
    		 mtextview_musicnote.setText(map.get("Artist").toString());
    		//mBundle.putSerializable("map", map);
    		 Intent mstart_service_intent=new Intent(MainActivity.this,MusicPlayer.class);
    		// mstart_service_intent.putExtras(mBundle);
    		 mstart_service_intent.putExtra("position", position);
    		 Log.i("list position",position+"");
    		 startService(mstart_service_intent);
    		 Log.i("list","setOnItemClickListener()");
    	 	} 
		  });
    }
    
}
