package com.dave.bombman;

import java.io.InputStream;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dave.bombman.app.Enemy;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.view.GameView;

public class Effect {
	public Vector<int[]> v = new Vector<int[]>();
//	public static Vector<int[]> v2 = new Vector<int[]>();
	private Bitmap score50 = null;
	private Bitmap score100 = null;
	private Bitmap score1000 = null;
	private Bitmap enemyDeath1 = null;
	private Bitmap show1 = null;
	private Bitmap show2 = null;
	private Bitmap show3 = null;
	private Bitmap show4 = null;
	private Bitmap show5 = null;
	private Bitmap show6 = null;
	private Bitmap show7 = null;
	
	
//	private Bitmap effect1 = null;  //18
	private Bitmap effect2 = null;	//16
//	private Bitmap effect3 = null;	//15
//	private Bitmap effect4 = null;	//18
	private Bitmap effect5 = null;	//20
	private Bitmap effect6 = null;	//13
//	private Bitmap effect7 = null;	//13
	
	private Bitmap enemyEffect = null;	//17	
	
	private Sprite effectSpr = null;
	private Sprite player = null;;
	//private GameView gameView = null;
	
	public Effect(Context context, Sprite player, GameView gameView){
		score50 = ReadBitMap(context, R.drawable.score50);
		score100 = ReadBitMap(context, R.drawable.score100);
		score1000 = ReadBitMap(context, R.drawable.score1000);
		enemyDeath1 = ReadBitMap(context, R.drawable.enemydeath);
		show1 = ReadBitMap(context, R.drawable.show1);
		show2 = ReadBitMap(context, R.drawable.show2);
		show3 = ReadBitMap(context, R.drawable.show3);
		show4 = ReadBitMap(context, R.drawable.show4);
		show5 = ReadBitMap(context, R.drawable.show5);
		show6 = ReadBitMap(context, R.drawable.show6);
		show7 = ReadBitMap(context, R.drawable.show7);
		
	//	effect1 = ReadBitMap(context, R.drawable.effect1);
		effect2 = ReadBitMap(context, R.drawable.effect2);
	//	effect3 = ReadBitMap(context, R.drawable.effect3);
	//	effect4 = ReadBitMap(context, R.drawable.effect4);
		effect5 = ReadBitMap(context, R.drawable.mapeffect);
		effect6 = ReadBitMap(context, R.drawable.playereffect);
	//	effect7 = ReadBitMap(context, R.drawable.effect7);
		enemyEffect = ReadBitMap(context, R.drawable.enemyeffect);
		
		effectSpr = new Sprite(effect2, effect2.getWidth()/20, effect2.getHeight());
		effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
	//	effectSpr.defineCollisionRectangle(effectSpr.getWidth()*3/10, effectSpr.getHeight()*3/10, 32, 32);
		
		this.player = player;
	//	this.gameView = gameView;
	}
	
	public void addEffect(int x, int y, int style){
		switch(style){
		case 1:		//50分
			int[] a1 = {x, y-16, style, 50};
			v.addElement(a1);
			break;
		case 2:		//100分
			int[] a2 = {x, y-16, style, 50};
			v.addElement(a2);
			break;
		case 3:		//1000分
			int[] a3 = {x, y-16, style, 50};
			v.addElement(a3);
			break;
		case -1:	//敌人死亡
			int[] b = {x-MenuActivity.SIZE/2, y-MenuActivity.SIZE/2, style, 30};
			v.addElement(b);
			break;
		case 101:
			int[] a101= {x, y, style, 0};
			v.addElement(a101);
			break;
		case 102:
			int[] a102= {x, y, style, 0};
			v.addElement(a102);
			break;
		case 103:
			int[] a103= {x, y, style, 0};
			v.addElement(a103);
			break;
		case 104:
			int[] a104= {x, y, style, 0};
			v.addElement(a104);
			break;
		case 105:
			int[] a105= {x, y, style, 0};
			v.addElement(a105);
			break;
		case 106:
			int[] a106= {x, y, style, 0};
			v.addElement(a106);
			break;
		case 107:
			int[] a107= {x, y, style, 0};
			v.addElement(a107);
			break;
		case 201:
			int[] a201= {x, y, style, 0};
			v.addElement(a201);
			break;
		case 202:
			int[] a202= {x, y, style, 0};
			v.addElement(a202);
			break;
		case 203:
			int[] a203= {x, y, style, 0};
			v.addElement(a203);
			break;
		case 204:
			int[] a204= {x, y, style, 0};
			v.addElement(a204);
			break;
		case 205:
			int[] a205= {x, y, style, 0};
			v.addElement(a205);
			break;
		case 206:
			int[] a206= {x, y, style, 0};
			v.addElement(a206);
			break;
		case 207:
			int[] a207= {x, y, style, 0};
			v.addElement(a207);
			break;
		case 301:	//敌人光效
			int[] a301= {x, y, style, 0};
			v.addElement(a301);
			break;
		}
		
	}
	public void update(){
		for(int i = 0; i < v.size(); i++){
			int[] a = v.elementAt(i);
			switch(a[2]){
			case 1:
				a[1]--;
				a[3]--;
				if(a[3] == 0){
					v.removeElementAt(i);
					i--;
				}else{
					v.setElementAt(a, i);
				}
				break;
			case 2:
				a[1]--;
				a[3]--;
				if(a[3] == 0){
					v.removeElementAt(i);
					i--;
				}else{
					v.setElementAt(a, i);
				}
				break;
			case 3:
				a[1]--;
				a[3]--;
				if(a[3] == 0){
					v.removeElementAt(i);
					i--;
				}else{
					v.setElementAt(a, i);
				}
				break;
			case -1:
			//	a[1]--;
				a[3]--;
				if(a[3] == 0){
					v.removeElementAt(i);
					i--;
				}else{
					v.setElementAt(a, i);
				}
				break;
			
			case 101:
				effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
				effectSpr.defineCollisionRectangle(effectSpr.getWidth()*3/10, effectSpr.getHeight()*3/10, MenuActivity.SIZE, MenuActivity.SIZE);
				
				effectSpr.setRefPixelPosition(a[0], a[1]);
				if(effectSpr.collidesWith(player, false)){
					v.removeElementAt(i);
					i--;
				}else{
					a[3]++;
					
					if(a[3] > 100){
						v.removeElementAt(i);
						i--;
					}
					
				}
				break;
			case 102:
				effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
				effectSpr.defineCollisionRectangle(effectSpr.getWidth()*3/10, effectSpr.getHeight()*3/10, MenuActivity.SIZE, MenuActivity.SIZE);
				
				effectSpr.setRefPixelPosition(a[0], a[1]);
				if(effectSpr.collidesWith(player, false)){
					v.removeElementAt(i);
					i--;
				}else{
					a[3]++;
					if(a[3] > 100){

						v.removeElementAt(i);
						i--;
					}
				}
				break;
			case 103:
				effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
				effectSpr.defineCollisionRectangle(effectSpr.getWidth()*3/10, effectSpr.getHeight()*3/10, MenuActivity.SIZE, MenuActivity.SIZE);
				
				effectSpr.setRefPixelPosition(a[0], a[1]);
				if(effectSpr.collidesWith(player, false)){
					v.removeElementAt(i);
					i--;
				}else{
					a[3]++;
					if(a[3] > 100){
						v.removeElementAt(i);
						i--;
					}
				}
				break;
			case 104:
				effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
				effectSpr.defineCollisionRectangle(effectSpr.getWidth()*3/10, effectSpr.getHeight()*3/10, MenuActivity.SIZE, MenuActivity.SIZE);
				
				effectSpr.setRefPixelPosition(a[0], a[1]);
				if(effectSpr.collidesWith(player, false)){
					v.removeElementAt(i);
					i--;
				}else{
					a[3]++;
					if(a[3] > 100){
						v.removeElementAt(i);
						i--;
					}
				}
				break;
			case 105:
				effectSpr.setRefPixelPosition(a[0], a[1]);
			
				a[3]++;
				if(a[3] > 20){
					v.removeElementAt(i);
					i--;
				}
				break;
			case 106:
					effectSpr.setRefPixelPosition(a[0], a[1]);
				
					a[3]++;
					if(a[3] > 100){
						
						v.removeElementAt(i);
						i--;
					}
					break;
			case 107:
				effectSpr.defineReferencePixel(effectSpr.getWidth()/2, effectSpr.getHeight()/2);
				effectSpr.setRefPixelPosition(a[0], a[1]);
				a[3]++;
				if (a[3] > 60) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 201:		//宝物提示
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 202:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 203:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 204:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 205:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 206:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 207:
				a[3]++;
				if (a[3] > 40) {
					v.removeElementAt(i);
					i--;
				}
				break;
			case 301:
				if(a[3] % 17 == 8){
					SoundPlayer.playSound(R.raw.bosspower);
				}
				a[3]++;
				if(a[3] > 102){
					v.removeElementAt(i);
					i--;
					Enemy.bossIsPower = false;
				}
				break;
			}
		}
	}
	public void draw(Canvas canvas){
		for(int i = 0; i < v.size(); i++){
			int[] a = v.elementAt(i);
			switch(a[2]){
			case 1:
				canvas.drawBitmap(score50, a[0], a[1], null);
				break;
			case 2:
				canvas.drawBitmap(score100, a[0], a[1], null);
				break;
			case 3:
				canvas.drawBitmap(score1000, a[0], a[1], null);
				break;
			case -1:
				canvas.drawBitmap(enemyDeath1, a[0], a[1], null);
				break;
	/*		case 101:
				effectSpr.setImage(effect1, effect1.getWidth()/18, effect1.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				effectSpr.setFrame(a[3]%18);
				effectSpr.paint(canvas);
				
				break;*/
			case 102:
				effectSpr.setImage(effect2, effect2.getWidth()/16, effect2.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				effectSpr.setFrame(a[3]%16);
				effectSpr.paint(canvas);
				
				break;
		/*	case 103:
				effectSpr.setImage(effect3, effect3.getWidth()/15, effect3.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				effectSpr.setFrame(a[3]%15);
				effectSpr.paint(canvas);
				
				break;
			case 104:
				effectSpr.setImage(effect4, effect4.getWidth()/18, effect4.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				
				effectSpr.setFrame(a[3]%18);
				effectSpr.paint(canvas);
				break;*/
			case 105:
				effectSpr.setImage(effect5, effect5.getWidth()/20, effect5.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				
				effectSpr.setFrame(a[3]%20);
				effectSpr.paint(canvas);
				break;
			case 106:
				effectSpr.setImage(effect6, effect6.getWidth()/13, effect6.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				
				effectSpr.setFrame(a[3]%13);
				effectSpr.paint(canvas);
				break;
		/*	case 107:
				effectSpr.setImage(effect7, effect7.getWidth()/13, effect7.getHeight());
				effectSpr.setRefPixelPosition(a[0], a[1]);
				
				effectSpr.setFrame(a[3]%13);
				effectSpr.paint(canvas);
				break;*/
			case 201:
				canvas.drawBitmap(show1,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 202:
				canvas.drawBitmap(show2,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 203:
				canvas.drawBitmap(show3,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 204:
				canvas.drawBitmap(show4,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 205:
				canvas.drawBitmap(show5,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 206:
				canvas.drawBitmap(show6,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 207:
				canvas.drawBitmap(show7,GameView.viewPortX+MenuActivity.WIDTH/2, GameView.viewPortY+60, null);
				break;
			case 301:
				effectSpr.setImage(enemyEffect, enemyEffect.getWidth()/17, enemyEffect.getHeight());
				effectSpr.setRefPixelPosition(Enemy.bossX, Enemy.bossY);
				
				effectSpr.setFrame(a[3]%17);
				effectSpr.paint(canvas);
				break;
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
}
