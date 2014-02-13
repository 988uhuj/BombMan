package com.dave.bombman.app;

import java.io.InputStream;

import com.dave.bombman.MenuActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Message;

public class Score {
	public static int score;
	private Paint paint;
	public static int getStarNum;
	public Score(Context context){
	//	score = 0;
		Typeface myTypeface = Typeface.createFromAsset(context.getResources().getAssets(),"f3.ttf");
				
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
		//paint.setStrokeWidth(1);
		paint.setTextSize(21);
		paint.setTypeface(myTypeface); 
		

		getStarNum = 0;
	}
	public Score(Context context, boolean isWin){
		//	score = 0;
			Typeface myTypeface = Typeface.createFromAsset(context.getResources().getAssets(),"f3.ttf");
					
			paint = new Paint();
			paint.setColor(Color.WHITE);
			//paint.setStrokeWidth(1);
			paint.setTextSize(21);
			paint.setTypeface(myTypeface); 
	
			getStarNum = 0;
		}
/*	public static void setBestScore(int score){
		Score.bestScore = score;
	}*/
	public static void setScore(int score){
		Score.score = score;
	}
	public static void addScore(int score){
		Score.score = Score.score + score;
		Message msg = MenuActivity.myHandler.obtainMessage();
		msg.arg1 = 5;
		MenuActivity.myHandler.sendMessage(msg);
	}
	public static void resetScore(){
		score = 0;
	}
	public static int getScore(){
		return score;
	}
	
	
	public Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	public void Release(){
		paint = null;
	}
}
