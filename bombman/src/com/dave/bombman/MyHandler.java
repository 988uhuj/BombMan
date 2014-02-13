package com.dave.bombman;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dave.bombman.app.Score;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.app.TimeDown;
import com.dave.bombman.view.GameView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MyHandler extends Handler{
	private MainActivity activity = null;
	private Context context;
	
	public MyHandler(Context context){
		this.activity = (MainActivity)context;
		this.context = context;
	}

	@Override
	public void handleMessage(Message msg) {
		switch(msg.arg1){
		case 1://下一关
			activity.hideWin();
			activity.next.Release();
			MainActivity.level ++;
			initGameView();
		//	GameProgressDialog.hideProgressDialog();
			break;
		case 2:		//显示胜利
			GameView.isEnd = true;
			if(activity.gameView != null && activity.gameView.timeDown != null){
				activity.gameView.timeDown.Release();
			}
			activity.showWin();
			int id = MainActivity.level + 1;
			
			if(id > ChooseActivity.num || GameView.isWin == false){
				break;
			}
			String filename = android.os.Environment.getExternalStorageDirectory()+"/bombman/database.db";
	        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(filename, null);
	        String where = "_id" + " = ?";
	        
			String[] whereValue = { Integer.toString(id) };
			ContentValues cv = new ContentValues();
			cv.put("isLock", 0);
			database.update(ChooseView.WORLD, cv, where, whereValue);
			
			String[] whereValue2 = { Integer.toString(id-1) };
			cv.clear();
			cv.put("starNum", Score.getStarNum);
			database.update(ChooseView.WORLD, cv, where, whereValue2);
			
			String where3 = "_id" + " = ?";
			String[] whereValue3 = { "_id", "money" };
			Cursor cursor = database.query("PLAYER", whereValue3, null, null, null, null, null);
			cursor.moveToFirst();
			int money = cursor.getInt(1) + Score.getScore();
		//	System.out.println("__"+cursor.getInt(1));
			String[] whereValue4 = { Integer.toString(1) };
			cv.clear();
			cv.put("money", money);
			database.update("PLAYER", cv, where3, whereValue4);
			cursor.close();
			/*	String SQL = "UPDATE " + ChooseView.WORLD + " SET "
	        		+ "isLock = 0 WHERE _id = " + MainActivity.level++;
	        database.execSQL(SQL);
	        Cursor cursor = database.query(ChooseView.WORLD, null, null, null, null, null, null);
	        cursor.moveToFirst();
	        do{
	        	
	        	System.out.println(cursor.getInt(1));
	        }while(cursor.moveToNext());
	        cursor.close();*/
	        database.close();
			break;
		case 3:			//显示加载进度框
			GameProgressDialog.showProgressDialog(context);
		//	
			break;
		case 4:		//返回菜单
			Intent intent = new Intent();
			intent.setClass(activity, ChooseActivity.class);
			activity.startActivity(intent);
			
			activity.finish();
			SoundPlayer.pauseMusic();
			SoundPlayer.initMenuMusic();
			SoundPlayer.startMusic();
			GameProgressDialog.hideProgressDialog();
			break;
		case 5:		//分数框
			activity.scoreView.setText("money: " + String.valueOf(Score.score));
			break;
		case 6:		//时间框
			Date date = new Date(TimeDown.time*1000);
			SimpleDateFormat dateFormat = new SimpleDateFormat("mm-ss");
			activity.timeView.setText("time " + dateFormat.format(date));
			break;
		case 7:		//B键隐藏
			activity.buttonB.setVisibility(View.INVISIBLE);
			break;
		case 8:		//B键显示
			activity.buttonB.setVisibility(View.VISIBLE);
			break;
		case 9:		//replay
			activity.hideWin();
			activity.next.Release();
			initGameView();
			break;
		case 10:		//starNum++
			switch(Score.getStarNum){
			case 0:
				activity.starNum.setBackgroundResource(R.drawable.mainstarnum0);
				break;
			case 1:
				activity.starNum.setBackgroundResource(R.drawable.mainstarnum1);
				break;
			case 2:
				activity.starNum.setBackgroundResource(R.drawable.mainstarnum2);
				break;
			case 3:
				activity.starNum.setBackgroundResource(R.drawable.mainstarnum3);
				break;
			}
			
			break;
		case 11:	//显示BOSS出现
			SoundPlayer.playSound(R.raw.addboss);
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(R.drawable.addboss);
			
			Toast t = Toast.makeText(context, "ss", Toast.LENGTH_LONG);
			//t.setDuration(Toast.LENGTH_LONG);
			t.setView(imageView);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.show();
			break;
		case 12:	//显示时间耗尽
			ImageView imageView2 = new ImageView(context);
			imageView2.setBackgroundResource(R.drawable.fail);
			
			Toast t2 = Toast.makeText(context, "ss", Toast.LENGTH_LONG);
			//t.setDuration(Toast.LENGTH_LONG);
			t2.setView(imageView2);
			t2.setGravity(Gravity.CENTER, 0, 0);
			t2.show();
			break;
		case 13:	//显示提示1
			ImageView imageViewTeach1 = new ImageView(context);
			imageViewTeach1.setBackgroundResource(R.drawable.teach1);
			
			Toast teachT1 = Toast.makeText(context, "ss", Toast.LENGTH_LONG);
			//t.setDuration(Toast.LENGTH_LONG);
			teachT1.setView(imageViewTeach1);
			teachT1.setGravity(Gravity.CENTER, 0, 0);
			teachT1.show();
			break;
		case 14:	//显示提示2
			ImageView imageViewTeach2 = new ImageView(context);
			imageViewTeach2.setBackgroundResource(R.drawable.teach2);
			
			Toast teachT2 = Toast.makeText(context, "ss", Toast.LENGTH_LONG);
			//t.setDuration(Toast.LENGTH_LONG);
			teachT2.setView(imageViewTeach2);
			teachT2.setGravity(Gravity.CENTER, 0, 0);
			teachT2.show();
			break;
		case 15:	//显示提示3
			ImageView imageViewTeach3 = new ImageView(context);
			imageViewTeach3.setBackgroundResource(R.drawable.teach3);
			
			Toast teachT3 = Toast.makeText(context, "ss", Toast.LENGTH_LONG);
			//t.setDuration(Toast.LENGTH_LONG);
			teachT3.setView(imageViewTeach3);
			teachT3.setGravity(Gravity.CENTER, 0, 0);
			teachT3.show();
			break;
		default :
			break;
		}
		super.handleMessage(msg);
	}
	private void initGameView(){
		if(activity.gameView != null){
			
			activity.gameView.Release();
			activity.gameView = null;
			activity.next.Release();
			activity.next = null;
			activity.relativeLayout = null;
			
			activity.params = null;
	//		activity.gameView.initGameView(activity);
			System.out.println("gameView null");
		}
		activity.gameView = new GameView(activity);
		activity.setContentView(activity.gameView);
		
		activity.initSecondView();
	}
	
	public void Release(){
		activity = null;
	}
	
}
