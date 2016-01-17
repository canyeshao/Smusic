package com.wingtech.smusic;

import android.app.Activity;
import android.content.Intent;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_control);
		init();
	}
	
	private void init(){
		
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
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ibtn_control_back:
			Log.i("music_control","ibtn_control_back  onclick");
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
