package com.dave.bombman.ad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.dave.bombman.R;
import com.pkfg.k.MyKAM;

public class TestActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		
//		Intent intent = new Intent(this, com.pkfg.k.MyKAActivity.class);
//		startActivity(intent);
//		finish();
		MyKAM.getInstance().showKuguoSprite(this, MyKAM.STYLE_KUZAI);
	}
	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}



	@Override
	public void onBackPressed() {
//		return ;
		super.onBackPressed();
	}
	
	

}
