package com.wingtech.musicplayer;


public class Constant {
	//操作
	public final static int play = 1;
	public final static int pause = 2;
	public final static int stop = 3;
	public final static int next = 4;
	public final static int back = 5;

	//状态
	public final static int playstatus = 6;
	public final static int pausestatus = 7;
	
	//构建intent的不同变量
	public final static String ACTION_MUSIC_CONTROL = "action_music_control";
	public final static String ACTION_MUSIC_ACTIVITY_UPDATE = "action_music_activity_update";
}
