package com.dave.bombman.app;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;

import com.dave.bombman.ChooseView;
import com.dave.bombman.MainActivity;
import com.dave.bombman.MenuActivity;

public class TimeDown {
	private Timer timer; 
    private TimerTask timerTask;
	public static int time = 100;
	private Paint paint;
	private long period = 1000;
	private Enemy enemy;
	
	public TimeDown(Context context){
		Typeface myTypeface = Typeface.createFromAsset(context.getResources().getAssets(),"f3.ttf");
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(21);
		paint.setTypeface(myTypeface); 
		
	}
	public void setEnemy(Enemy enemy){
		this.enemy = enemy;
	}
	public static void setTime(int time){
		TimeDown.time = time;
	}

	public void startTime(long period){
		if(timer == null){
			timer = new Timer();
		}
		if(timerTask == null){
			setTimerTask();
		}
		this.period = period;
		timer.schedule(timerTask, 1000, period);
	}
	public void restartTime(){
		if(time > 0){
			if(timer == null){
				timer = new Timer();
			}
			if(timerTask == null){
				setTimerTask();
			}
			timer.schedule(timerTask, 1000, period);
		}
	}
	

	public void stopTime(){
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		if(timerTask != null){
			timerTask.cancel();
			timerTask = null;
		}
	}
	
    
    
  

    private void setTimerTask(){

		timerTask = new TimerTask() {
			public void run() {
				time--;

				if (time < 0 && timer != null) {
					time = 0;
					timer.cancel();
					timer = null;

					if (MainActivity.level < 20 && ChooseView.WORLD != "WORLD1") {
						enemy.addBoss();
						//添加Boss提示
						Message msg = MenuActivity.myHandler.obtainMessage();
						msg.arg1 = 11;
						MenuActivity.myHandler.sendMessage(msg);
					} else {
						Player.setPlayerDeath();
						//时间耗尽提示
						Message msg = MenuActivity.myHandler.obtainMessage();
						msg.arg1 = 12;
						MenuActivity.myHandler.sendMessage(msg);
					}

				} else {
					//时间控件
					Message msg = MenuActivity.myHandler.obtainMessage();
					msg.arg1 = 6;
					MenuActivity.myHandler.sendMessage(msg);
				}
			}
		};
 
    }
	public void Release(){
		if(timer != null){
			timer.cancel();
		}
		if(timerTask != null){
			timerTask.cancel();
		}
		timer = null;
		timerTask = null;
		paint = null;
		
	}
}
