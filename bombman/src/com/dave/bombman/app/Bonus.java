package com.dave.bombman.app;

import java.io.InputStream;
import java.util.Collections;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;

import com.dave.bombman.ChooseView;
import com.dave.bombman.Effect;
import com.dave.bombman.LoadData;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.midp.Sprite;

public class Bonus {
	private Bitmap door = null;
	private Bitmap bonus1 = null;
	private Bitmap bonus2 = null;
	private Bitmap bonus3 = null;
	private Bitmap bonus4 = null;
	private Bitmap bonus5 = null;
	private Bitmap bonus6 = null;
	private Bitmap bonus7 = null;
	private Bitmap bonus8 = null;
	private Bitmap bonus9 = null;
	private Bitmap bonus10 = null;
	
	private Sprite doorSpr = null;
	private Sprite bonusSpr = null;
	public boolean isOpen = false;
	
	private Effect effect = null;
	
	public Vector<int[]> v = new Vector<int[]>();
	
	private int count = 0;
	
	private int[][] bonusArray = new int[LoadData.MAPROW][LoadData.MAPCOL];
	private Vector<int[]> bonusV;
	private int[] bonusNum = null;
	
	public static int doorX;
	public static int doorY;
	
	public Bonus(Context context){
		door = ReadBitMap(context, R.drawable.door);
		bonus1 = ReadBitMap(context, R.drawable.bonus50);
		bonus2 = ReadBitMap(context, R.drawable.bonus100);
		bonus3 = ReadBitMap(context, R.drawable.bonusstar);
		bonus4 = ReadBitMap(context, R.drawable.bonuspower);
		bonus5 = ReadBitMap(context, R.drawable.bonusnum);
		bonus6 = ReadBitMap(context, R.drawable.bonustime);
		bonus7 = ReadBitMap(context, R.drawable.bonusbox);
		bonus8 = ReadBitMap(context, R.drawable.bonusbomb);
		bonus9 = ReadBitMap(context, R.drawable.bonuswudi);
		bonus10 = ReadBitMap(context, R.drawable.bonusspeed);
		
		
		doorSpr = new Sprite(door, door.getWidth(), door.getHeight()/4);
		bonusSpr = new Sprite(bonus1, bonus1.getWidth()/5, bonus1.getHeight());
		bonusSpr.defineReferencePixel(bonusSpr.getWidth()/2, bonusSpr.getHeight()/2);
		
		bonusV = new Vector<int[]>();
		doorSpr.setVisible(false);
		
	}
	public void setEffect(Effect effect){
		this.effect = effect;
	}
	public void setBonus(LoadData ld){
		this.bonusNum = ld.getBonus();
	}
	public void initArray(int[][] tiles3){
		for(int i = 0; i < tiles3.length; i++){	//row
			for(int j = 0; j < tiles3[1].length; j++){	//col
				if(tiles3[i][j] != 0){
					int[] a = {i, j}; 
					bonusV.addElement(a);
				}
			}
		}
		Collections.shuffle(bonusV);		//打乱次序
		int index = 0;
		for(int i = 0; i < bonusNum.length; i++){
			for(int j = 0; j < bonusNum[i]; j++){
				int[] tmp = bonusV.elementAt(index);
				index ++;
				if(index >= bonusV.size()){
					return;
				}else{
					if(i == 0){
						bonusArray[tmp[0]][tmp[1]] = -1;
						
						doorX = tmp[1];		//门的位置
						doorY = tmp[0];
					}else{
						bonusArray[tmp[0]][tmp[1]] = i;
					}
					
				}
			}
		}
		
	}
	public int[][] getBonusArray(){
		return bonusArray;
	}
	
	public void putDoor(int x, int y){
		doorSpr.setPosition(x-MenuActivity.SIZE/2, y-MenuActivity.SIZE/2);
	}
	public void addBonus(int x, int y, int type){
		int bonusX = x ;
		int bonusY = y ;
		/**
		 * 1.x轴
		 * 2.y轴
		 * 3.类别
		 * 4.帧
		 */
		int a[] = {bonusX, bonusY, type, 0};	
		v.addElement(a);
		//Random r = new Random();
		//int index = r.nextInt(4);
		effect.addEffect(x, y, 102);
	}
	public void bonusDraw(Canvas canvas){
		int a[] = null;
		for(int i = 0; i < v.size(); i++){
			a = v.elementAt(i);
			
		//	bonusSpr.nextFrame();
			switch(a[2]){
			case 9:
				bonusSpr.setImage(bonus1, bonus1.getWidth()/5, bonus1.getHeight());
				a[3]++;
				if(a[3] > 4){
					a[3] = 0;
				}
				break;
			case 10:	
				bonusSpr.setImage(bonus2, bonus2.getWidth()/5, bonus2.getHeight());
				a[3]++;
				if(a[3] > 4){
					a[3] = 0;
				}
				break;
			case 1:
				bonusSpr.setImage(bonus3, bonus3.getWidth()/12, bonus3.getHeight());
				a[3]++;
				if(a[3] > 3){
					a[3] = 0;
				}
				break;
			case 2:
				bonusSpr.setImage(bonus4, bonus4.getWidth()/12, bonus4.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 3:
				bonusSpr.setImage(bonus5, bonus5.getWidth()/12, bonus5.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 4:
				bonusSpr.setImage(bonus6, bonus6.getWidth()/12, bonus6.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 5:
				bonusSpr.setImage(bonus7, bonus7.getWidth()/12, bonus7.getHeight());
				a[3]++; 
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 6:
				bonusSpr.setImage(bonus8, bonus8.getWidth()/12, bonus8.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 7:
				bonusSpr.setImage(bonus9, bonus9.getWidth()/12, bonus9.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			case 8:
				bonusSpr.setImage(bonus10, bonus10.getWidth()/12, bonus10.getHeight());
				a[3]++;
				if(a[3] > 11){
					a[3] = 0;
				}
				break;
			default :
				break;
			}
			bonusSpr.setRefPixelPosition(a[0], a[1]);
			bonusSpr.setFrame(a[3]);
			bonusSpr.paint(canvas);
			v.setElementAt(a, i);
		}
	}
	public int collision(Sprite spr){
		int a[] = null;
		for(int i = 0; i < v.size(); i++){
			a = v.elementAt(i);
			bonusSpr.setRefPixelPosition(a[0], a[1]);
			if(bonusSpr.collidesWith(spr, false)){
			//	Score.addScore(1000);
				v.removeElementAt(i);
				i--;
			//	System.out.println(a[2]);
				return a[2];	//返回碰撞的类别
			}
		}
		return 0;
	}
	public void setDoorVisible(boolean isVisible){
		if(isVisible && ChooseView.WORLD == "WORLD1"){
			Message msg = MenuActivity.myHandler.obtainMessage();
			msg.arg1 = 15;
			MenuActivity.myHandler.sendMessage(msg);
		}
		if(isVisible){
			SoundPlayer.playSound(R.raw.enemyzero);
		}
		doorSpr.setVisible(isVisible);
	}
	public boolean getDoorVisible(){
		return doorSpr.isVisible();
	}
	public Sprite getDoor(){
		return doorSpr;
	}
	public void bonusUpdate(){
		if(doorSpr.isVisible()){
			if(isOpen == true){
				if(doorSpr.getFrame() < 3){
					count++;
					if(count % 3 == 0){
						doorSpr.nextFrame();
					}
				}
			}else{
				if(doorSpr.getFrame() > 0){
					count ++;
					if(count % 3 == 0){
						doorSpr.prevFrame();
					}
				}
				
			}
		}
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
		door = null;
		bonus1 = null;
		bonus2 = null;
		bonus3 = null;
		bonus4 = null;
		bonus5 = null;
		bonus6 = null;
		bonus7 = null;
		bonus8 = null;
		bonus9 = null;
		bonus10 = null;
		//bonus = null;
		doorSpr = null;
		bonusSpr = null;
		v.removeAllElements();
		v = null;
		bonusArray = null;
		bonusV = null;
		bonusNum = null;
		
	}
}
