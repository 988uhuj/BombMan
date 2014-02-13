package com.dave.bombman;

import com.dave.bombman.app.SoundPlayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class SetActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set);
		
		
		final Button musicBtn = (Button)findViewById(R.id.musicBtn);
		final Button soundBtn = (Button)findViewById(R.id.soundBtn);
		final Button back = (Button)findViewById(R.id.back);
		if(SoundPlayer.isMusicSt()){
			musicBtn.setBackgroundResource(R.drawable.close);
		}else{
			musicBtn.setBackgroundResource(R.drawable.open);
		}
		if(SoundPlayer.isSoundSt()){
			soundBtn.setBackgroundResource(R.drawable.close2);
		}else{
			soundBtn.setBackgroundResource(R.drawable.open2);
		}
		
		musicBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(SoundPlayer.isMusicSt()){
					musicBtn.setBackgroundResource(R.drawable.open);
					SoundPlayer.pauseMusic();
					SoundPlayer.setMusicSt(false);
				}else{
					SoundPlayer.setMusicSt(true);
					SoundPlayer.initMenuMusic();
					SoundPlayer.startMusic();
					musicBtn.setBackgroundResource(R.drawable.close);
				}
				SoundPlayer.playSound(R.raw.click);
			
				
			}
		});
		soundBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(SoundPlayer.isSoundSt()){
					soundBtn.setBackgroundResource(R.drawable.open2);
					SoundPlayer.setSoundSt(false);
				}else{
					SoundPlayer.setSoundSt(true);
					soundBtn.setBackgroundResource(R.drawable.close2);
				}
				SoundPlayer.playSound(R.raw.click);
				
			}
		});
		
		
		back.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					SoundPlayer.playSound(R.raw.click);
					back.setBackgroundResource(R.drawable.backdown);
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					back.setBackgroundResource(R.drawable.back);
					finish();
				}
				
				return false;
			}
		});
		
	}
	
}
