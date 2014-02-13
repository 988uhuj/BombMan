package com.dave.bombman.store;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dave.bombman.GameProgressDialog;
import com.dave.bombman.R;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.view.StoreView;

public class StoreActivity extends Activity{
	private StoreView storeView;
	private Button buyPlayer;
	private Button buyBomb;
	private Button buyPower;
	private Button purchase;
	private Button back;
	private TextView moneyShow;
	private int money;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    GameProgressDialog.showProgressDialog(this);
	    
	    storeView = new StoreView(this);
		setContentView(storeView);
		
		RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.store, null);
		LayoutParams params = new FrameLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		
		addContentView(relativeLayout, params);
		
		 	String filename = android.os.Environment.getExternalStorageDirectory()+"/bombman/database.db";
	        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(filename, null);
			String[] whereValue = { "money"};
			Cursor cursor = database.query("PLAYER", whereValue, null, null, null, null, null);
			cursor.moveToFirst();
			money = cursor.getInt(0);
			
			
			String[] whereValue2 = { "money", "isLock"};
			cursor = database.query("STORE1", whereValue2, null, null, null, null, null);
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++){
				storeView.playerPrice[i] = cursor.getInt(0);
				storeView.playerLock[i] = cursor.getInt(1);
				cursor.moveToNext();
			}
			cursor = database.query("STORE2", whereValue2, null, null, null, null, null);
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++){
				storeView.bombPrice[i] = cursor.getInt(0);
				storeView.bombLock[i] = cursor.getInt(1);
				cursor.moveToNext();
			}
			cursor.close();
			database.close();
		
		buyPlayer = (Button)findViewById(R.id.buyPlayer);
		buyBomb = (Button)findViewById(R.id.buyBomb);
		buyPower = (Button)findViewById(R.id.buyPower);
		purchase = (Button)findViewById(R.id.purchase);
		moneyShow = (TextView)findViewById(R.id.showMoney);
		back = (Button)findViewById(R.id.storeback);
		
		BtnListener btnListener = new BtnListener();
		buyPlayer.setOnTouchListener(btnListener);
		buyBomb.setOnTouchListener(btnListener);
		buyPower.setOnTouchListener(btnListener);
		purchase.setOnTouchListener(btnListener);
		back.setOnTouchListener(btnListener);
		
		buyPlayer.setBackgroundResource(R.drawable.checkdown1);
		moneyShow.setText("money:" + Integer.toString(money));
		
		
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		storeView.storeTouch(event);
		return true;
	//	return super.onTouchEvent(event);
	}
	
	@Override
	public void finish() {
		String filename = android.os.Environment.getExternalStorageDirectory()+"/bombman/database.db";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(filename, null);
        String where = "_id" + " = ?";
        String[] whereValue = { "1" };
		ContentValues cv = new ContentValues();
		if(storeView.playerLock[storeView.playerType-1] == 0){
			cv.put("playerType", storeView.playerType);
		}
		if(storeView.bombLock[storeView.bombFlag-1] == 0){
			cv.put("bomb", storeView.bombFlag);
		}
		
		cv.put("bonus1", 0);
		cv.put("bonus2", 0);
		cv.put("bonus3", 0);
		cv.put("bonus4", 0);
		
		cv.put("money", money);
		
		database.update("PLAYER", cv, where, whereValue);
		
		database.close();
		super.finish();
	}

	class BtnListener implements OnTouchListener{

		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				SoundPlayer.playSound(R.raw.click);
				switch(v.getId()){
				case R.id.buyPlayer:
					buyPlayer.setBackgroundResource(R.drawable.checkdown1);
					buyBomb.setBackgroundResource(R.drawable.check2);
					buyPower.setBackgroundResource(R.drawable.check3);
					break;
				case R.id.buyBomb:
					buyPlayer.setBackgroundResource(R.drawable.check1);
					buyBomb.setBackgroundResource(R.drawable.checkdown2);
					buyPower.setBackgroundResource(R.drawable.check3);
					break;
				case R.id.buyPower:
					buyPlayer.setBackgroundResource(R.drawable.check1);
					buyPower.setBackgroundResource(R.drawable.checkdown3);
					buyBomb.setBackgroundResource(R.drawable.check2);
					break;
				case R.id.purchase:
					purchase.setBackgroundResource(R.drawable.purchasedown);
					break;
				}
			}else if(event.getAction() == MotionEvent.ACTION_UP){
				switch(v.getId()){
				case R.id.storeback:
					finish();
					break;
				case R.id.buyPlayer:
					storeView.check = 1;
					storeView.storeBmp = storeView.ReadBitMap(StoreActivity.this, R.drawable.storebg1);
					break;
				case R.id.buyBomb:
					storeView.check = 2;
					storeView.storeBmp = storeView.ReadBitMap(StoreActivity.this, R.drawable.storebg2);
					break;
				case R.id.buyPower:
					storeView.check = 3;
					storeView.storeBmp = storeView.ReadBitMap(StoreActivity.this, R.drawable.storebg3);
					break;
				case R.id.purchase:
					purchase.setBackgroundResource(R.drawable.purchase);
					switch(storeView.check){
					case 1:
						if(storeView.playerLock[storeView.playerType-1] == 1){
							new AlertDialog.Builder(StoreActivity.this).setMessage("确定用金币换取？").setTitle("")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									Message msg = handler.obtainMessage();
									msg.arg1 = 1;
									handler.sendMessage(msg);
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
								}
							}).show();
							return true;
						}else{
							Toast t = Toast.makeText(StoreActivity.this, "已解封", Toast.LENGTH_SHORT);
							t.show();
						}
						break;
					case 2:
						if(storeView.bombLock[storeView.bombFlag-1] == 1){
							new AlertDialog.Builder(StoreActivity.this).setMessage("确定用金币换取？").setTitle("")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									Message msg = handler.obtainMessage();
									msg.arg1 = 2;
									handler.sendMessage(msg);
								}
							})
							.setNegativeButton("取消", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
								}
							}).show();
							return true;
						}else{
							Toast t = Toast.makeText(StoreActivity.this, "已解封", Toast.LENGTH_SHORT);
							t.show();
						}
						break;
					}
					break;
				}
			}
			return false;
		}
		
	}
	Handler handler = new Handler() { 
		 
	    @Override 
	    public void handleMessage(Message msg) { 
	        super.handleMessage(msg); 
	        switch(msg.arg1){
	        case 1:
	        	int moneyTemp = 0;
	        	if((moneyTemp = money - storeView.playerPrice[storeView.playerType-1]) >= 0){
	        		Toast t = Toast.makeText(StoreActivity.this, "成功解封", Toast.LENGTH_SHORT);
					t.show();
					saveLock("STORE1", storeView.playerType);
					storeView.playerLock[storeView.playerType-1] = 0;
					money = moneyTemp;
					moneyShow.setText("money:" + Integer.toString(money));
	        	}else{
	        		Toast t = Toast.makeText(StoreActivity.this, "金币不足，可在游戏中赚取", Toast.LENGTH_SHORT);
					t.show();
	        	}
	        	break;
	        case 2:
	        	moneyTemp = 0;
	        	if((moneyTemp = money - storeView.bombPrice[storeView.bombFlag-1]) >= 0){
	        		Toast t = Toast.makeText(StoreActivity.this, "成功解封", Toast.LENGTH_SHORT);
					t.show();
					saveLock("STORE2", storeView.bombFlag);
					storeView.bombLock[storeView.bombFlag-1] = 0;
					money = moneyTemp;
					moneyShow.setText("money:" + Integer.toString(money));
	        	}else{
	        		Toast t = Toast.makeText(StoreActivity.this, "金币不足，可在游戏中赚取", Toast.LENGTH_SHORT);
					t.show();
	        	}
	        	break;
	        }
	        
	        
	    } 
	}; 
	public void saveLock(String table, int id){
		String filename = android.os.Environment.getExternalStorageDirectory()+"/bombman/database.db";
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(filename, null);
        String where = "_id" + " = ?";
        String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put("isLock", 0);
		database.update(table, cv, where, whereValue);
		
		database.close();
	}

}

