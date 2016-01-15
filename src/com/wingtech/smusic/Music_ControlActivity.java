package com.wingtech.smusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Music_ControlActivity extends Activity implements OnClickListener  {
	private ImageButton ibtn_control_back;
	private TextView textview_songname;
	private TextView textview_songlyric;
	private TextView textview_songartist;
	private ImageButton ibtn_control_love;
	private ImageButton ibtn_control_switch;
	private ImageButton ibtn_control_previous;
	private ImageButton ibtn_control_next;
	private ImageButton ibtn_control_list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_control);
		init();
	}
	
	private void init(){
		
		ibtn_control_back=(ImageButton)findViewById(R.id.ibtn_control_back);
		textview_songname=(TextView)findViewById(R.id.textview_songname);
		textview_songlyric=(TextView)findViewById(R.id.textview_songlyric);
		textview_songartist=(TextView)findViewById(R.id.textview_songartist);
		ibtn_control_love=(ImageButton)findViewById(R.id.ibtn_control_love);
		ibtn_control_switch=(ImageButton)findViewById(R.id.ibtn_control_switch);
		ibtn_control_previous=(ImageButton)findViewById(R.id.ibtn_control_previous);
		ibtn_control_next=(ImageButton)findViewById(R.id.ibtn_control_next);
		ibtn_control_list=(ImageButton)findViewById(R.id.ibtn_control_list);
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ibtn_control_back:
			Intent intent1=new Intent(Music_ControlActivity.this,MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.ibtn_control_love:
			
			break;
		case R.id.ibtn_control_switch:
	
			break;
		case R.id.ibtn_control_previous:
	
			break;
		case R.id.ibtn_control_next:
	
			break;
		case R.id.ibtn_control_list:
			Intent intent2=new Intent(Music_ControlActivity.this,MainActivity.class);
			startActivity(intent2);
			break;	
		default:
			break;
		}
		
	}
	
	
	
	
}
