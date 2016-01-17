package com.wingtech.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.wingtech.musicsource.MediaUtil;
import com.wingtech.musicsource.MusicInfo;

public class MusicPlayer  extends Service{
	
	private MediaPlayer mmediaPlayer;   //播放器   
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
		Log.i("service", "service in create");
		mmusicInfos=MediaUtil.getMusicInfos(MusicPlayer.this);
		path=mmusicInfos.get(current).getUrl();
		Log.i("service", path.toString());
		mmediaPlayer=new MediaPlayer();
		Log.i("service", "MediaPlayer create  over");
		mmediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				current++;
				if(current > mmusicInfos.size()-1){
					current=0;
				}
				//Intent sendIntent = new Intent("更新");
				//sendIntent.putExtra("current", current);
				//sendBroadcast(sendIntent);
				path = mmusicInfos.get(current).getUrl();
				play(0);//播放
			}
		});
		Log.i("service", "service create over");
		play(0);
	}
	
	private void play(int currentTime) {
		try {
			mmediaPlayer.reset();// 把各项参数恢复到初始状态
			mmediaPlayer.setDataSource(path);
			mmediaPlayer.prepareAsync(); // 进行缓冲
			mmediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class PreparedListener implements OnPreparedListener {
		private int currentTime;

		public PreparedListener(int currentTime) {
			Log.i("PreparedListener","PreparedListener");
			this.currentTime = currentTime;
		}

		@Override
		public void onPrepared(MediaPlayer mp) {
			Log.i("media","music onPrepared");
			mmediaPlayer.start(); // 开始播放
			Log.i("media","music start");
			if (currentTime > 0) { // 如果音乐不是从头播放
				mmediaPlayer.seekTo(currentTime);
			}
		}
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int t=super.onStartCommand(intent, flags, startId);
		Log.i("service", "service onStartCommand");
		current=intent.getIntExtra("position", 5);
		path = mmusicInfos.get(current).getUrl();
		play(0);
		return t;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("destroy","onDestroy");
		mmediaPlayer.release();
		Log.i("destroy","mmediaPlayer release");
	}
}
