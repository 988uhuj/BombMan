package com.dave.bombman;

import com.dave.bombman.app.SoundPlayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;

public class ChooseView extends Activity{
	private HorizontalScrollView scroll;
	public static String WORLD = "world1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.choose_view);
		
		Button view1 = (Button)findViewById(R.id.view01);
		Button view2 = (Button)findViewById(R.id.view02);
		Button view3 = (Button)findViewById(R.id.view03);
		Button view4 = (Button)findViewById(R.id.view04);
		Button view5 = (Button)findViewById(R.id.view05);
		Button view6 = (Button)findViewById(R.id.view06);
		Button back = (Button)findViewById(R.id.chooseBack1);
		Button left = (Button)findViewById(R.id.chooseLeft);
		Button right = (Button)findViewById(R.id.chooseRight);
		scroll = (HorizontalScrollView)findViewById(R.id.scrollView);
		
	//	Toast t = Toast.makeText(this, Integer.toString(MenuActivity.SIZE), Toast.LENGTH_SHORT);
	//	t.show();
		
		ClickListener clickListener  = new ClickListener();
		view1.setOnClickListener(clickListener);
		view2.setOnClickListener(clickListener);
		view3.setOnClickListener(clickListener);
		view4.setOnClickListener(clickListener);
		view5.setOnClickListener(clickListener);
		view6.setOnClickListener(clickListener);
		back.setOnClickListener(clickListener);
		left.setOnClickListener(clickListener);
		right.setOnClickListener(clickListener);
	}
	public void startgame(String world) {
		WORLD = world;
		Intent intent = new Intent();
		intent.setClass(ChooseView.this, ChooseActivity.class);
	//	intent.putExtra("GameWorld", world);
		startActivity(intent);
	//	SoundPlayer.pauseMusic();
		finish();
	//	overridePendingTransition(R.anim.fade, R.anim.fade);
	}
	public void startMenu() {
		finish();
	//	overridePendingTransition(R.anim.fade, R.anim.fade);
	}
	class ClickListener implements View.OnClickListener{

		public void onClick(View v) {
			SoundPlayer.playSound(R.raw.click);
			switch(v.getId()){
			case R.id.view01:
				startgame("WORLD1");
				break;
			case R.id.view02:
				startgame("WORLD2");
				break;
			case R.id.view03:
				startgame("WORLD3");
				break;
			case R.id.view04:
				startgame("WORLD4");
				break;
			case R.id.view05:
				startgame("WORLD5");
				break;
			case R.id.view06:
				startgame("WORLD6");
				break;
			case R.id.chooseBack1:
				startMenu();
				break;
			case R.id.chooseLeft:
				scroll.arrowScroll(View.FOCUS_RIGHT);
				break;
			case R.id.chooseRight:
				scroll.arrowScroll(View.FOCUS_LEFT);
				break;
			}
			
		}
		
	}
}
