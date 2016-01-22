package com.wingtech.smusic;

import java.util.ArrayList;
import java.util.List;

import com.wingtech.musicplayer.Constant;
import com.wingtech.musicsource.MediaUtil;
import com.wingtech.musicsource.MusicInfo;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Music_ControlActivity extends Activity implements OnClickListener  {
	private ImageButton mibtn_control_back;
	private TextView mtextview_songname;
	private TextView mtextview_songlyric;
	private TextView mtextview_songartist;
	private ImageButton mibtn_control_love;
	private ImageButton mibtn_control_switch;
	private ImageButton mibtn_control_previous;
	private ImageButton mibtn_control_next;
	private ImageButton mibtn_control_list;
	private int mmusicplay_status=Constant.PAUSESTATUS;
	private boolean lovestatus=false;
	private UpdateControlActivityReceiver mupdateReceiver=new UpdateControlActivityReceiver();
	private List<MusicInfo> mmusicInfos = new ArrayList<MusicInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_control);
		init();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	    //注册更新页面的UpdateControlActivityReceiver
		IntentFilter intentfilter=new IntentFilter();
    	intentfilter.addAction(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
    	this.registerReceiver(mupdateReceiver,intentfilter);
    	//再次显示时update界面
    	Intent updateActivity_intent=new Intent(Constant.ACTION_MUSIC_CONTROL);
    	updateActivity_intent.putExtra("updateActivity", Constant.MUSICCONTROLACTIVITY);
    	sendBroadcast(updateActivity_intent);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.unregisterReceiver(mupdateReceiver);
	}
	
	private void init(){
		
		mmusicInfos=MediaUtil.getMusicInfos(Music_ControlActivity.this);
		
		mibtn_control_back=(ImageButton)findViewById(R.id.ibtn_control_back);
		mtextview_songname=(TextView)findViewById(R.id.textview_songname);
		mtextview_songlyric=(TextView)findViewById(R.id.textview_songlyric);
		mtextview_songartist=(TextView)findViewById(R.id.textview_songartist);
		mibtn_control_love=(ImageButton)findViewById(R.id.ibtn_control_love);
		mibtn_control_switch=(ImageButton)findViewById(R.id.ibtn_control_switch);
		mibtn_control_previous=(ImageButton)findViewById(R.id.ibtn_control_previous);
		mibtn_control_next=(ImageButton)findViewById(R.id.ibtn_control_next);
		mibtn_control_list=(ImageButton)findViewById(R.id.ibtn_control_list);
		
		mibtn_control_back.setOnClickListener(this);
		mibtn_control_list.setOnClickListener(this);
		mibtn_control_love.setOnClickListener(this);
		mibtn_control_switch.setOnClickListener(this);
		mibtn_control_previous.setOnClickListener(this);
		mibtn_control_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ibtn_control_back:
			Log.i("music_control","ibtn_control_back  onclick");
			Intent mskip_activity_intent=new Intent(Music_ControlActivity.this,MainActivity.class);
			startActivity(mskip_activity_intent);
			
			break;
		case R.id.ibtn_control_love:
			Log.i("mibtn_control_love","onclick");
			if(lovestatus==false){
				mibtn_control_love.setBackgroundResource(R.drawable.music_control_loved);
				lovestatus=true;
			}
			else {
				mibtn_control_love.setBackgroundResource(R.drawable.music_control_love);
				lovestatus=false;
			}
			break;
		case R.id.ibtn_control_switch:
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
			break;
		case R.id.ibtn_control_previous:
			Intent msendbroadcast_service_previous=new Intent(Constant.ACTION_MUSIC_CONTROL);
			msendbroadcast_service_previous.putExtra("message", Constant.PREVIOUS);
			sendBroadcast(msendbroadcast_service_previous);
			break;
		case R.id.ibtn_control_next:
			Intent msendbroadcast_service_next=new Intent(Constant.ACTION_MUSIC_CONTROL);
			msendbroadcast_service_next.putExtra("message", Constant.NEXT);
			sendBroadcast(msendbroadcast_service_next);
			break;
		case R.id.ibtn_control_list:
			Intent intent2=new Intent(Music_ControlActivity.this,MainActivity.class);
			startActivity(intent2);
			break;	
		default:
			break;
		}
		
	}
	
	
	
	public class UpdateControlActivityReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int position=intent.getIntExtra("position", -1);
			int status=intent.getIntExtra("status",-1);
			
			if(position!=-1){
				String mmusicname=mmusicInfos.get(position).getTitle();
				String mmusicartist=mmusicInfos.get(position).getArtist();
				mtextview_songname.setText(mmusicname);
				mtextview_songartist.setText(mmusicartist);	
			}
			
			if(status!=-1){
				mmusicplay_status=status;
				Log.i("status","mmusicplay_status change!"+mmusicplay_status);
				if(mmusicplay_status==Constant.PAUSESTATUS){
					Log.i("status","mmusicplay_status change!   I>"+mmusicplay_status);
					mibtn_control_switch.setBackgroundResource(R.drawable.music_control_playpause);	
				}
				else if(mmusicplay_status==Constant.UNPAUSESTATUS){
					Log.i("status","mmusicplay_status change!   II"+mmusicplay_status);
					mibtn_control_switch.setBackgroundResource(R.drawable.music_control_playon);	
				}
			}

		}
		
	}
	
	
}
