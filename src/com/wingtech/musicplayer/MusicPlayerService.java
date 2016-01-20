package com.wingtech.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;

import com.wingtech.musicsource.MediaUtil;
import com.wingtech.musicsource.MusicInfo;

public class MusicPlayerService extends Service{
	
	private MediaPlayer mmediaPlayer;   //播放器   
	private String path;         //音乐路径               
	private int mmusicplay_status=Constant.PAUSESTATUS;   //暂停状态
	private List<MusicInfo> mmusicInfos = new ArrayList<MusicInfo>();  //音乐列表
	private int position=0; 
	private ControlPlayerReceiver mPlayerReceiver=new ControlPlayerReceiver();
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		//注册ControlPlayerReceiver广播接收器
		IntentFilter intentfilter=new IntentFilter();
    	intentfilter.addAction(Constant.ACTION_MUSIC_CONTROL);
    	this.registerReceiver(mPlayerReceiver, intentfilter);
    	
    	//初始化MediaPlayer
		Log.i("service", "ControlPlayerReceiver registerReceiver");
		mmusicInfos=MediaUtil.getMusicInfos(MusicPlayerService.this);
		mmediaPlayer=new MediaPlayer();
		Log.i("service", "MediaPlayer create  over");
		mmediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				position++;
				if(position > mmusicInfos.size()-1){
					position=0;
				}
				
				Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
				updateMainActivityIntent.putExtra("position", position);
				updateMainActivityIntent.putExtra("status", mmusicplay_status);
				sendBroadcast(updateMainActivityIntent);
				path = mmusicInfos.get(position).getUrl();
				play(0);//播放
			}
		});
		Log.i("service", "service create over");
	}
	
	private final class PreparedListener implements OnPreparedListener {
		private int currentTime;

		public PreparedListener(int currentTime) {
			Log.i("PreparedListener","PreparedListener start");
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
		Log.i("service", "service onStartCommand play");
		int temp=intent.getIntExtra("message", -1);
		position=intent.getIntExtra("position", 0);
		path = mmusicInfos.get(position).getUrl();
		if(temp==1){
			play(0);
			Log.i("onStartCommand","onitemclick");
		}
		if(temp==0){
			play(0);
			pause();
			Log.i("onStartCommand","old_position stop");
		}
		return t;
	}

	private void play(int currentTime) {
		try {
			//播放即为非暂停状态，更新MainActivity
			mmusicplay_status=Constant.UNPAUSESTATUS;
			Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
			updateMainActivityIntent.putExtra("status", mmusicplay_status);
			updateMainActivityIntent.putExtra("position", position);
			sendBroadcast(updateMainActivityIntent);
			Log.i("status","UNPAUSESTATUS");
			mmediaPlayer.reset();// 把各项参数恢复到初始状态
			mmediaPlayer.setDataSource(path);
			Log.i("music control","paht"+path);
			mmediaPlayer.prepare(); // 进行缓冲
			mmediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));// 注册一个监听器
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pause(){
		if (mmediaPlayer != null && mmediaPlayer.isPlaying()) {
			mmediaPlayer.pause();
			mmusicplay_status = Constant.PAUSESTATUS;
			Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
			updateMainActivityIntent.putExtra("status", mmusicplay_status);
			updateMainActivityIntent.putExtra("position", position);
			sendBroadcast(updateMainActivityIntent);
		}
	}
	
	public void unpause(){
		if (mmusicplay_status==Constant.PAUSESTATUS) {
			mmediaPlayer.start();
			mmusicplay_status = Constant.UNPAUSESTATUS;
			Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
			updateMainActivityIntent.putExtra("status", mmusicplay_status);
			updateMainActivityIntent.putExtra("position", position);
			sendBroadcast(updateMainActivityIntent);
		}
	}
	
	public void previous(){
		position--;
		if(position<0){
			position=mmusicInfos.size()-1;
		}
		path = mmusicInfos.get(position).getUrl();
		Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
		updateMainActivityIntent.putExtra("position", position);
		updateMainActivityIntent.putExtra("status", mmusicplay_status);
		sendBroadcast(updateMainActivityIntent);
		play(0);
	}
	
	public void next(){
		position++;
		if(position > mmusicInfos.size()-1){
			position=0;
		}
		path = mmusicInfos.get(position).getUrl();
		Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
		updateMainActivityIntent.putExtra("position", position);
		updateMainActivityIntent.putExtra("status", mmusicplay_status);
		sendBroadcast(updateMainActivityIntent);
		play(0);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("destroy","onDestroy");
		mmediaPlayer.release();
		this.unregisterReceiver(mPlayerReceiver);
		Log.i("destroy","mmediaPlayer release");
		SharedPreferences.Editor editor=getSharedPreferences("old_position", MODE_PRIVATE).edit();
		editor.putInt("position", position);
		editor.commit();
		Log.i("destroy","old_position save");
	}
	
	
	public class ControlPlayerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int message=intent.getIntExtra("message", 0);
			int updateActivity=intent.getIntExtra("updateActivity",0);
			//音乐控制操作
			switch(message){
				/*case Constant.PLAY:
					Log.i("service", "service onStartCommand play");
					position=intent.getIntExtra("position", 5);
					path = mmusicInfos.get(position).getUrl();
					play(0);
					break;
				*/
				case Constant.PAUSE:
					Log.i("service", "onStartCommand pause");
					pause();
					break;
				case Constant.UNPAUSE:
					Log.i("service", "onStartCommand unpause");
					unpause();
					break;
				case Constant.PREVIOUS:
					Log.i("service", "onStartCommand previous");
					previous();
					break;
				case Constant.NEXT:
					Log.i("service", "onStartCommand next");
					next();
					break;
				default:
					break;
			}	
			
			//Activity更新
			if(updateActivity==Constant.MAINACTIVITY){
				Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
				updateMainActivityIntent.putExtra("position", position);
				updateMainActivityIntent.putExtra("status", mmusicplay_status);
				sendBroadcast(updateMainActivityIntent);	
			}
			if(updateActivity==Constant.MUSICCONTROLACTIVITY){
				Intent updateMainActivityIntent = new Intent(Constant.ACTION_MUSIC_ACTIVITY_UPDATE);
				updateMainActivityIntent.putExtra("position", position);
				updateMainActivityIntent.putExtra("status", mmusicplay_status);
				sendBroadcast(updateMainActivityIntent);	
				
			}
			
			
		}

	}
	
}
