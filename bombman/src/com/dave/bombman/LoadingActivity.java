package com.dave.bombman;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import com.dave.bombman.app.Bomb;
import com.dave.bombman.app.Player;
import com.dave.bombman.app.SoundPlayer;

public class LoadingActivity extends Activity{
	private PowerManager.WakeLock mWakeLock;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//ÆÁÄ»³£ÁÁ
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Lock"); 
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.loading);
		
		SoundPlayer.pauseMusic();
		
		Timer timer = new Timer();
		TimerTask tk = new TimerTask() {
			
			@Override
			public void run() {
				initData();
				startGame();
			}
		};
		timer.schedule(tk, 2000);
	}
	@Override
	protected void onResume() {
		mWakeLock.acquire();
		super.onResume();
	}
	@Override
	protected void onPause() {
		mWakeLock.release();
		super.onPause();
	}
	private void startGame(){
		
		
		Intent intent = new Intent();
		intent.setClass(LoadingActivity.this, MainActivity.class);
		startActivity(intent);
		
		finish();
		
	}
	private void initData(){
		switch(Player.playerType){
		case 1:
			Player.SPEED1 = 5;
			Player.SPEED2 = 7;
			break;
		case 2:
			Player.SPEED1 = 5;
			Player.SPEED2 = 6;
			break;
		case 3:
			Player.SPEED1 = 5;
			Player.SPEED2 = 7;
			break;
		case 4:
			Player.SPEED1 = 6;
			Player.SPEED2 = 7;
			Player.isThroughBomb = true;
			break;
		}
		switch(Bomb.bombType){
		case 1:
			MyTimer.time = 6000;
			break;
		case 2:
			MyTimer.time = 6000;
			break;
		case 3:
			MyTimer.time = 5000;
			break;
		case 4:
			MyTimer.time = 7000;
			break;
		}
	}
}
