package com.dave.bombman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
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
import android.widget.RelativeLayout;

import com.dave.bombman.ad.TestActivity;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.db.WriteToSD;
import com.dave.bombman.store.StoreActivity;
import com.dave.bombman.view.MenuView;

public class MenuActivity extends Activity{
	private MenuView menuView = null;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	
	public static int WIDTH;
	public static int HEIGHT;
	public static int SIZE;
	
	public static MyHandler myHandler = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	    getWindowSize();
	    
	    
	    
	    new GameProgressDialog(this);
	    menuView = new MenuView(this);
		setContentView(menuView);
		
		RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.menu, null);
		LayoutParams params = new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		
		
		
		
		addContentView(relativeLayout, params);
		
//		new SoundPlayer();
//		SoundPlayer.init(this);
//		SoundPlayer.startMusic();
		
		btn1 = (Button)relativeLayout.findViewById(R.id.button01);
		btn2 = (Button)relativeLayout.findViewById(R.id.button02);
		btn3 = (Button)relativeLayout.findViewById(R.id.button03);
		btn4 = (Button)relativeLayout.findViewById(R.id.button04);
		
		btn1.setOnTouchListener(new btnOnTouch());
		btn2.setOnTouchListener(new btnOnTouch());
//		btn3.setOnTouchListener(new btnOnTouch());
		btn4.setOnTouchListener(new btnOnTouch());
		
		new WriteToSD(this);
		
		btn3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent intent = new Intent(MenuActivity.this, TestActivity.class);
				startActivity(intent);
				
				finish();
			}
		});
		
		//ad
	/*	KuguoAdsManager.getInstance().receivePushMessage(this, false);
		KuguoAdsManager.getInstance().showKuguoSprite(this, 0);
		KuguoAdsManager.getInstance().setKuzaiPosition(true, 0);*/
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	//	menuView.menuTouch(event);
		return true;
		//return super.onTouchEvent(event);
	}

	public void startChoose() {
		Intent intent = new Intent();
		intent.setClass(MenuActivity.this, ChooseView.class);
		//intent.putExtra("GameDifficulty", diff);
		startActivity(intent);
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);     
		if(version  >= 5) {     
		     overridePendingTransition(R.anim.fade, R.anim.fade_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
		   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
		}    
	}
	public void startStore(){
		Intent intent = new Intent();
		intent.setClass(MenuActivity.this, StoreActivity.class);
		//intent.putExtra("GameDifficulty", diff);
		startActivity(intent);
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);     
		if(version  >= 5) {     
		     overridePendingTransition(R.anim.fade, R.anim.fade_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
		   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
		}    
	}
	public void startSet(){
		Intent intent = new Intent();
		intent.setClass(MenuActivity.this, SetActivity.class);
		//intent.putExtra("GameDifficulty", diff);
		startActivity(intent);
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);     
		if(version  >= 5) {     
		     overridePendingTransition(R.anim.fade, R.anim.fade_out);  //此为自定义的动画效果，下面两个为系统的动画效果  
		   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
		     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
		}    
	}
	

	@Override
	protected void onDestroy() {
//		KuguoAdsManager.getInstance().recycle(this);
		super.onDestroy();
	}
	
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_MENU){
			
//			finish();
		}
		return super.onKeyDown(keyCode, event);
	}


	class btnOnTouch implements OnTouchListener{

		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				switch (v.getId()) {
				case R.id.button01:
					btn1.setBackgroundResource(R.drawable.begindown);
					SoundPlayer.playSound(R.raw.click);
					break;
				case R.id.button02:
					btn2.setBackgroundResource(R.drawable.choosedown);
					SoundPlayer.playSound(R.raw.click);
					break;
				case R.id.button03:
					btn3.setBackgroundResource(R.drawable.setdown);
					SoundPlayer.playSound(R.raw.click);
					break;
				case R.id.button04:
					btn4.setBackgroundResource(R.drawable.exitdown);
					SoundPlayer.playSound(R.raw.click);
					break;
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				switch (v.getId()) {
				case R.id.button01:
					startChoose();		//剧情模式
					btn1.setBackgroundResource(R.drawable.begin);
					break;
				case R.id.button02:		//计时模式
					startStore();
					btn2.setBackgroundResource(R.drawable.choose);
					break;
				case R.id.button03:
					startSet();
					btn3.setBackgroundResource(R.drawable.set);
					break;
				case R.id.button04:
					finish();
					btn4.setBackgroundResource(R.drawable.exit);
					break;
				}
			}

			return false;
		}
	}
	public void Release(){
//		menuView = null;
//		btn1 = null;
//		btn2 = null;
//		btn3 = null;
//		btn4 = null;
	}
	public void getWindowSize(){
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay();
		//获取屏幕宽、高用
		
		WIDTH = d.getWidth();
		HEIGHT = d.getHeight();
		
		if(HEIGHT >= 480){
			SIZE = 64;
			return; 
		}else if(HEIGHT >= 320){
			SIZE = 48;
		}else{
			SIZE = 32;
		}
		
		System.out.println(SIZE);
	}
	
}

