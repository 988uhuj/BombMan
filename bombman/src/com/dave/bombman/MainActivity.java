package com.dave.bombman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dave.bombman.app.Player;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.view.GameView;

public class MainActivity extends Activity {
	public GameView gameView = null;
	
	public NextDialog next = null;
	public Button buttonA = null;
	public Button buttonB = null;
	public Button play = null;
	public TextView scoreView = null;
	public TextView timeView = null;
	public ImageView starNum = null;

	public RelativeLayout relativeLayout;

	public LayoutParams params;
	
	private int menuState = 2;	//状态为2未弹出
	private View layout;
	private PopupWindow pop;

	private PowerManager.WakeLock mWakeLock;

	public static int level = 1;
	
	public static Context context;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = this;
        //屏幕常亮
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Lock"); 
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    //    GameProgressDialog.showProgressDialog(this);
        gameView = new GameView(this);
        setContentView(gameView);
        
        MenuActivity.myHandler = new MyHandler(this);
        
        initSecondView();
        
        
    }
	public void init(){
		gameView = new GameView(context);
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

	
	
	public void initSecondView(){
		next = new NextDialog(this);
		
        relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.main, null);
		params = new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		
		addContentView(relativeLayout, params);
		 
		
		
		buttonA = (Button)relativeLayout.findViewById(R.id.buttonA);
		buttonB = (Button)relativeLayout.findViewById(R.id.buttonB);
		play = (Button)relativeLayout.findViewById(R.id.play);
		scoreView = (TextView)relativeLayout.findViewById(R.id.scoreView);
		timeView = (TextView)relativeLayout.findViewById(R.id.timeView);
		starNum = (ImageView)relativeLayout.findViewById(R.id.mainStarNum);
		
		Typeface myTypeface = Typeface.createFromAsset(getResources().getAssets(),"f3.ttf");
		scoreView.setTypeface(myTypeface);
		timeView.setTypeface(myTypeface);
		scoreView.setTextSize(20f);
		timeView.setTextSize(20f);
		
		gameView.setClick(buttonA, buttonB, play);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(gameView != null && Player.isDead == false){
			gameView.touchEvent(event, buttonA, buttonB);
		}
		return true;
		//return super.onTouchEvent(event);
	}
	@Override
	protected void onDestroy() {
	
		System.out.println("ondestroy");
	//	SoundPlayer.pauseMusic();
		super.onDestroy();
	}
	public void showWin(){
		if(next != null){
			next.show();
		}
		
	}
	public void hideWin(){
		//next.hide();
		next.dismiss();
	}
	public void Release(){
		
		if(gameView != null){
			gameView.Release();
			gameView = null;
		}
		
		if(next != null){
			next.Release();
			next.cancel();
			next = null;
		}
		
	}
	@Override
	public void finish() {
		System.out.println("finish");
		
		Release();
	//	SoundPlayer.pauseMusic();
		
		super.finish();
				
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
		//	backMenuDialog();
			menuCreate();
			return false;
		}else if(keyCode == KeyEvent.KEYCODE_MENU){
			menuCreate();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void menuCreate(){
		if(menuState == 1 ){
			pop.dismiss();
			menuState = 2;
			gameView.isStop = false;
			gameView.timeDown.restartTime();
		///	return false;
		}else{
			if(Player.isDead == false && GameView.isWin == false){
			gameView.timeDown.stopTime();
			
			layout = getLayoutInflater().inflate(R.layout.gamemenu, null);
			pop = new PopupWindow(layout, MenuActivity.WIDTH, MenuActivity.HEIGHT);
			pop.showAtLocation(layout, Gravity.LEFT, 0, 0);
			
			Button pause = (Button)layout.findViewById(R.id.pause);
			final Button replay = (Button)layout.findViewById(R.id.popReplay);
			final Button sound = (Button)layout.findViewById(R.id.sound);
			final Button back = (Button)layout.findViewById(R.id.delete);
			
			if(SoundPlayer.isMusicSt()){
				sound.setBackgroundResource(R.drawable.sound);
			}else{
				sound.setBackgroundResource(R.drawable.soundclose);
			}
			
			pause.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					SoundPlayer.playSound(R.raw.click);
					gameView.isStop = false;
					pop.dismiss();
					menuState = 2;
				}
			});
			replay.setOnTouchListener(new OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						SoundPlayer.playSound(R.raw.click);
						replay.setBackgroundResource(R.drawable.replaydown);
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						replay.setBackgroundResource(R.drawable.replay);
						
						pop.dismiss();
						menuState = 2;
						
						Message msg1 = MenuActivity.myHandler.obtainMessage();
						msg1.arg1 = 3;
						MenuActivity.myHandler.sendMessage(msg1);
						
						
						Message msg2 = MenuActivity.myHandler.obtainMessage();
						msg2.arg1 = 9;
						MenuActivity.myHandler.sendMessageDelayed(msg2, 1000);
					}
					
					return false;
				}
			});
			sound.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					if(SoundPlayer.isMusicSt()){
						sound.setBackgroundResource(R.drawable.soundclose);
						SoundPlayer.pauseMusic();
						SoundPlayer.setMusicSt(false);
						SoundPlayer.setSoundSt(false);
					}else{
						SoundPlayer.setMusicSt(true);
						SoundPlayer.setSoundSt(true);
						SoundPlayer.changeGameMusic();
						SoundPlayer.startMusic();
						sound.setBackgroundResource(R.drawable.sound);
					}
					SoundPlayer.playSound(R.raw.click);
					
				}
			});
			back.setOnTouchListener(new OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN){
						
						SoundPlayer.playSound(R.raw.click);
						back.setBackgroundResource(R.drawable.menudown);
					}else if(event.getAction() == MotionEvent.ACTION_UP){
						back.setBackgroundResource(R.drawable.menu);
						pop.dismiss();
						
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ChooseActivity.class);
					
						startActivity(intent);
						SoundPlayer.pauseMusic();
						SoundPlayer.initMenuMusic();
						SoundPlayer.startMusic();
						finish();
					}
					return false;
				}
			});
			
			
			menuState = 1;
			gameView.isStop = true;
			}
		}
		//return false;
	}
	public void backMenuDialog(){
		if(Player.isDead == false){
			gameView.timeDown.stopTime();
			gameView.isStop = true;
			
			new AlertDialog.Builder(this).setMessage("返回主菜单？").setTitle("")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					GameView.isEnd = true;
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, ChooseActivity.class);
				
					startActivity(intent);
					SoundPlayer.pauseMusic();
					SoundPlayer.initMenuMusic();
					SoundPlayer.startMusic();
					finish();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					gameView.isStop = false;
					gameView.timeDown.restartTime();
				}
			}).show();
			
		}else{
			
		}
	}
	
	
}