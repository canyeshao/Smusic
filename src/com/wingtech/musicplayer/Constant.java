package com.wingtech.musicplayer;


public class Constant {
	//操作
	public final static int PLAY = 1;
	public final static int PAUSE = 2;
	public final static int UNPAUSE = 3;
	public final static int PREVIOUS = 4;
	public final static int NEXT = 5;

	//状态
	public final static int UNPAUSESTATUS = 6;
	public final static int PAUSESTATUS = 7;
	
	//两个Activity
	public final static int MAINACTIVITY=8;
	public final static int MUSICCONTROLACTIVITY=9;
	
	//构建intent的不同变量
	public final static String ACTION_MUSIC_CONTROL = "action_music_control";
	public final static String ACTION_MUSIC_ACTIVITY_UPDATE = "action_music_activity_update";
}
