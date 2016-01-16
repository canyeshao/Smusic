package com.wingtech.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.util.Log;

import com.wingtech.musicsource.MediaUtil;
import com.wingtech.musicsource.MusicInfo;

public class MusicPlayer  extends Service{
	
	private MediaPlayer mediaPlayer =  new MediaPlayer();   //播放器   
	private String path;         //音乐路径               
	private boolean isPause;     //暂停状态
	private List<MusicInfo> mmusicInfos = new ArrayList<MusicInfo>();  //音乐列表
	private int current=0;  
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("service", "service created");
		mediaPlayer = new MediaPlayer();
		mmusicInfos=MediaUtil.getMusicInfos(MusicPlayer.this);
		
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				current++;
				if(current > mmusicInfos.size()-1){
					current=0;
				}
				Intent sendIntent = new Intent("更新");
				sendIntent.putExtra("current", current);
				sendBroadcast(sendIntent);
				path = mmusicInfos.get(current).getUrl();
				//播放
			}
		});
		
		
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

}
