package com.wingtech.smusic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.wingtech.musicplayer.MusicPlayerService;
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
	//private Bundle mBundle=new Bundle();
	private int mmusicplay_status=Constant.PAUSESTATUS;
	private int mold_position=-2;		//-２代表程序安装后第一次打开
	private UpdateMainActivityReceiver mupdateReceiver=new UpdateMainActivityReceiver();
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
    	Log.i("onstart","onstart ");
    	IntentFilter intentfilter=new IntentFilter();
    	intentfilter.addAction(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
    	this.registerReceiver(mupdateReceiver,intentfilter);
    	//再次显示时update界面
    	
    	Intent updateActivity_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
    	updateActivity_intent.putExtra("updateActivity", Constant.MAINACTIVITY);
    	sendBroadcast(updateActivity_intent);
    }
    
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	this.unregisterReceiver(mupdateReceiver);
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
    	//old_position_init();
    	Log.i("init","init over");
    };
     
    
    private void buttonInit() {
		
    	mbtn_music_photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mskip_activity_intent=new Intent(MainActivity.this,Music_ControlActivity.class);
				//mskip_activity_intent.putExtras(mBundle);
				startActivity(mskip_activity_intent);
			}		
		});
    	
    	mbtn_switch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mmusicplay_status==Constant.UNPAUSESTATUS){
					Log.i("mmusicplay_status","UNPAUSESTATUS to PAUSESTATUS");
					Intent msendbroadcast_service_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
					msendbroadcast_service_intent.putExtra("message", Constant.PAUSE);
					sendBroadcast(msendbroadcast_service_intent);
				}
				else if(mmusicplay_status==Constant.PAUSESTATUS){
					Log.i("mmusicplay_status","PAUSESTATUS to UNPAUSESTATUS");
					Intent msendbroadcast_service_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
					msendbroadcast_service_intent.putExtra("message", Constant.UNPAUSE);
					sendBroadcast(msendbroadcast_service_intent);
			   }
			}
		});
    	
    	
    	mbtn_previous.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent msendbroadcast_service_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
				msendbroadcast_service_intent.putExtra("message", Constant.PREVIOUS);
				sendBroadcast(msendbroadcast_service_intent);
			}	
    	});
    	
    	mbtn_next.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent msendbroadcast_service_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
				msendbroadcast_service_intent.putExtra("message", Constant.NEXT);
				sendBroadcast(msendbroadcast_service_intent);
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
    		//mBundle.putSerializable("map", map);
    		 Intent mstart_service_intent=new Intent(MainActivity.this,MusicPlayerService.class);
    		// mstart_service_intent.putExtras(mBundle);
    		 //mstart_service_intent.putExtra("message",Constant.PLAY);
    		 mstart_service_intent.putExtra("position", position);
    		 mstart_service_intent.putExtra("message", 1);
    		 Log.i("list position",position+"");
    		 Log.i("list","setOnItemClickListener()");
    		 startService(mstart_service_intent);
    	 	} 
		  });
    }
	
	public void old_position_init() {
		if (mold_position == -2) {
			mold_position = 0;
		} else {
			SharedPreferences pref = getSharedPreferences("old_position",
					MODE_PRIVATE);
			mold_position = pref.getInt("position", -1);
			
		}
		if (mold_position != -1) {

			Log.i("onCreate", "mold_position=" + mold_position);
			mmusicInfos = MediaUtil.getMusicInfos(MainActivity.this);
			String mmusicname = mmusicInfos.get(mold_position).getTitle();
			String mmusicartist = mmusicInfos.get(mold_position).getArtist();
			mtextview_musicname.setText(mmusicname);
			mtextview_musicnote.setText(mmusicartist);
			// 开启服务，负责第二个页面无法更新到old_position
			Intent mstart_service_intent = new Intent(MainActivity.this,
					MusicPlayerService.class);
			mstart_service_intent.putExtra("position", mold_position);
			mstart_service_intent.putExtra("message", 0);
			mold_position = -1;
			startService(mstart_service_intent);
			
		} else {
			Log.e("SharedPreferences","SharedPreferences read error!");
		}

		// MainActivity刚显示出来，歌曲不应播放，所以暂停
		Log.i("mmusicplay_status", "UNPAUSESTATUS to PAUSESTATUS");
		Intent msendbroadcast_service_intent = new Intent(
				Constant.ACTION_MUSIC_CONTROL);
		msendbroadcast_service_intent.putExtra("message", Constant.PAUSE);
		sendBroadcast(msendbroadcast_service_intent);

	}
	
	public class UpdateMainActivityReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int position=intent.getIntExtra("position", -1);
			int status=intent.getIntExtra("status",-1);

			if(position!=-1){
				String mmusicname=mmusicInfos.get(position).getTitle();
				String mmusicartist=mmusicInfos.get(position).getArtist();
				mtextview_musicname.setText(mmusicname);
				mtextview_musicnote.setText(mmusicartist);	
			}
			
			if(status!=-1){
				mmusicplay_status=status;
				Log.i("status","mmusicplay_status change!"+mmusicplay_status);
				if(mmusicplay_status==Constant.PAUSESTATUS){
					mbtn_switch.setBackgroundResource(R.drawable.switch_off);	
				}
				else if(mmusicplay_status==Constant.UNPAUSESTATUS){
					mbtn_switch.setBackgroundResource(R.drawable.switch_on);	
				}
			}

		}
		
	}
	
    
}
