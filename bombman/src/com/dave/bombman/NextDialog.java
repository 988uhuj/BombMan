package com.dave.bombman;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dave.bombman.app.Player;
import com.dave.bombman.app.Score;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.app.TimeDown;

public class NextDialog extends Dialog{
//	private NextView nextView = null;
//	private Context context;
	private Button replay;
	private Button next;
	private Button menu;
	private TextView scoreView;
	private TextView timeView;
	private ImageView star;
	private Timer timer;
	private TimerTask tk;
	private TimerTask tk2;
	
	private int starNum = 0;
	private int starIndex = 0;
	
	private int score;
	
	/**
	* 按下这个按钮进行的颜色过滤
	*/
	public final static float[] BT_SELECTED = new float[] { 2, 0, 0, 0, 2, 0,
	    2, 0, 0, 2, 0, 0, 2, 0, 2, 0, 0, 0, 1, 0 };

	/**
	* 按钮恢复原状的颜色过滤
	*/
	public final static float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
	    0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	public NextDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	//	this.context = context;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if(!Player.isDead){
			setContentView(R.layout.dialog);
		}else{
			setContentView(R.layout.dialogdeath);
		}
		
		
		
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		
	//	lp.alpha = 0.7f; // 透明度
		lp.dimAmount = 0.7f;
		lp.height = (int) (MenuActivity.HEIGHT * 0.8);
		lp.width = (int) (MenuActivity.WIDTH * 0.8);
		
		dialogWindow.setAttributes(lp);
		
		replay = (Button)findViewById(R.id.replayButton);
		next = (Button)findViewById(R.id.nextButton);
		menu = (Button)findViewById(R.id.menuButton);
		
		if(Player.isDead){
			next.setVisibility(View.GONE);
		}
		
		
		replay.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println("replay");
					SoundPlayer.playSound(R.raw.click);
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
					Message msg = MenuActivity.myHandler.obtainMessage();
					msg.arg1 = 3;
					MenuActivity.myHandler.sendMessage(msg);
					Message msg2 = MenuActivity.myHandler.obtainMessage();
					msg2.arg1 = 9;
					MenuActivity.myHandler.sendMessageDelayed(msg2, 1000);
				}
				return false;
			}
		}); 
		if(MainActivity.level >= ChooseActivity.num){
			next.setVisibility(View.GONE);
		}else{
			next.setOnTouchListener(new OnTouchListener(){
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						v.getBackground().setColorFilter(
								new ColorMatrixColorFilter(BT_SELECTED));
						v.setBackgroundDrawable(v.getBackground());
						System.out.println("next");
						SoundPlayer.playSound(R.raw.click);
						
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.getBackground().setColorFilter(
								new ColorMatrixColorFilter(BT_NOT_SELECTED));
						v.setBackgroundDrawable(v.getBackground());
						Message msg = MenuActivity.myHandler.obtainMessage();
						msg.arg1 = 3;
						MenuActivity.myHandler.sendMessage(msg);
						Message msg2 = MenuActivity.myHandler.obtainMessage();
						msg2.arg1 = 1;
						MenuActivity.myHandler.sendMessageDelayed(msg2, 1000);
						
					}
					return false;
				}
			}); 	
		}
		menu.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
					SoundPlayer.playSound(R.raw.click);
					
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.getBackground().setColorFilter(
							new ColorMatrixColorFilter(BT_NOT_SELECTED));
					v.setBackgroundDrawable(v.getBackground());
					Message msg = MenuActivity.myHandler.obtainMessage();
					msg.arg1 = 3;
					MenuActivity.myHandler.sendMessage(msg);
					Message msg2 = MenuActivity.myHandler.obtainMessage();
					msg2.arg1 = 4;
					MenuActivity.myHandler.sendMessageDelayed(msg2, 1000);
					
				}
				return false;
			}
		}); 
		
		
		if(Player.isDead == false){
			scoreView = (TextView)findViewById(R.id.scoreViewDlg);
			timeView = (TextView)findViewById(R.id.timeViewDlg);
			scoreView.setTextSize(20f);
			timeView.setTextSize(20f);			
			scoreView.setText("money: " + String.valueOf(Score.score));
			Date date = new Date(TimeDown.time*1000);
			SimpleDateFormat dateFormat = new SimpleDateFormat("mm-ss");
        	timeView.setText("time " + dateFormat.format(date));
			
        	score = Score.score;
        	Score.score = Score.score + TimeDown.time*10;		//总分
        	
        	starIndex = 0;
        	
        	starNum = Score.getStarNum;
        	
			star = (ImageView)findViewById(R.id.star);
			
			timer = new Timer();
			tk = new TimerTask() {
				
				@Override
				public void run() {
					Message msg = handler.obtainMessage();
					msg.arg1 = 1;
					handler.sendMessage(msg);
				}
			};
			tk2 = new TimerTask() {
				
				@Override
				public void run() {
					Message msg = handler.obtainMessage();
					msg.arg1 = 2;
					handler.sendMessage(msg);
					
				}
			};
			timer.schedule(tk, 1000, 70);	//0.07秒倒计时
			timer.schedule(tk2, 1000, 1000);
		}
		
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	

	@Override
	protected void onStop() {
		System.out.println("onStop");
		super.onStop();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(timer != null && tk != null){
				timer.cancel();
				tk.cancel();
			}
		
			Message msg = MenuActivity.myHandler.obtainMessage();
			msg.arg1 = 3;
			MenuActivity.myHandler.sendMessage(msg);
			Message msg2 = MenuActivity.myHandler.obtainMessage();
			msg2.arg1 = 4;
			MenuActivity.myHandler.sendMessageDelayed(msg2, 1000);
			return true;
		}
		return super.onKeyDown(keyCode, event);
		
	}
	
	public void Release(){
		
		if(timer != null){
			timer.cancel();
			timer = null;
		}
		if(tk != null){
			tk.cancel();
			tk = null;
		}
		if(tk2 != null){
			tk2.cancel();
			tk = null;
		}
	//	context = null;
		replay = null;
		next = null;
		menu = null;
		
	}
	Handler handler = new Handler() { 
		 
        @Override 
        public void handleMessage(Message msg) { 
            super.handleMessage(msg); 
            switch(msg.arg1){
            case 1:
            	if(TimeDown.time >= 0){
                	score = score + 10;
                	scoreView.setText("money: " + String.valueOf(score));
                	Date date = new Date(TimeDown.time*1000);
        			SimpleDateFormat dateFormat = new SimpleDateFormat("mm-ss");
                	timeView.setText("time: " + dateFormat.format(date));
                	
                	TimeDown.time--;
                }else{
                //	timer.cancel();
                	if(tk != null){
                		tk.cancel();
                		tk = null;
                	}
                }
            	break;
            case 2:
            	
            	switch(starIndex){
            	case 0:
            		star.setBackgroundResource(R.drawable.star0);
            		break;
            	case 1:
            		star.setBackgroundResource(R.drawable.star1);
            		SoundPlayer.playSound(R.raw.bonus2);
            		break;
            	case 2:
            		star.setBackgroundResource(R.drawable.star2);
            		SoundPlayer.playSound(R.raw.bonus2);
            		break;
            	case 3:
            		star.setBackgroundResource(R.drawable.star3);
            		SoundPlayer.playSound(R.raw.bonus2);
            		break;
            	}
            	starIndex++;
            	if(starIndex > starNum){
            	//	timer.cancel();
            		tk2.cancel();
            		tk2 = null;
            	}
            	break;
            }
            
            
        } 
    }; 
	
}
