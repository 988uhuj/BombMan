package com.dave.bombman.app;

import java.io.InputStream;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dave.bombman.LoadData;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.view.GameView;

public class Bomb {
	private Sprite spr = null;
	private Bitmap bombBmp = null;
	public Vector<Sprite> v;
	private Sprite player = null;
	public boolean isIn = false;
	public static int bombNum = 3;
	public static int maxBombNum = 3;
	public static int bombType = 1;
	
	private GameView gameView = null;
	
	public int[][] flag = new int[LoadData.MAPROW][LoadData.MAPCOL];
	public Bomb(Context context, Sprite player, GameView gameView){
		switch(bombType){
		case 1:
			bombBmp = ReadBitMap(context, R.drawable.bomb1);
			break;
		case 2:
			bombBmp = ReadBitMap(context, R.drawable.bomb2);
			break;
		case 3:
			bombBmp = ReadBitMap(context, R.drawable.bomb3);
			break;
		case 4:
			bombBmp = ReadBitMap(context, R.drawable.bomb4);
			break;
		}
		
		this.player = player;
		v = new Vector<Sprite>();
	
		this.gameView = gameView;
		bombNum = maxBombNum;
	}
	public boolean addBomb(int x, int y){
		
		if(bombNum > 0 && isIn == false){
		int bombX = x/MenuActivity.SIZE;
		int bombY = y/MenuActivity.SIZE;
			if(flag[bombY][bombX] == 0 && gameView.tiles3[bombY][bombX] == 0){
				flag[bombY][bombX] = 1;
				isIn = true;
				if(spr != null){
					spr = null;
					spr = new Sprite(bombBmp, bombBmp.getWidth()/12, bombBmp.getHeight());
				}else{
					spr = new Sprite(bombBmp, bombBmp.getWidth()/12, bombBmp.getHeight());
				}
				spr.defineReferencePixel(spr.getWidth()/2, spr.getHeight()/2);
				spr.setRefPixelPosition(bombX*MenuActivity.SIZE+MenuActivity.SIZE/2, bombY*MenuActivity.SIZE+MenuActivity.SIZE/2);
				v.addElement(spr);
				bombNum--;
				return true;
			}
		}
		
		return false;
	}
	public void delete(int i){
		
		if(i < v.size()){
			spr = v.elementAt(i);
			flag[spr.getY()/MenuActivity.SIZE][spr.getX()/MenuActivity.SIZE] = 0;
			v.removeElementAt(i);
			i--;
		}
		bombNum++;
		
		if(bombNum > maxBombNum){
			bombNum = maxBombNum;
		}
	}
	public boolean collision(){
		int count = 0;
		boolean isCollision = false;
		for(int i = 0; i < v.size(); i++){
			spr = v.elementAt(i);
			if(spr.collidesWith(player, false)){
				count++;
			}
		}
		if(isIn == false){
			if(count != 0 && Player.isThroughBomb == false){
				isCollision = true;
			}else{
				isCollision = false;
			}
		}else{
			if(count > 1 && Player.isThroughBomb == false){
				isCollision = true;
			}else{
				isCollision = false;
			}
		}
		return isCollision;
	}
	public void bombDraw(Canvas canvas){
		
			for(int i = 0; i < v.size(); i++){
				spr = v.elementAt(i);	
				spr.nextFrame();
				spr.paint(canvas);
			}
		
	
	}
	public Sprite getBomb(){
		if(v.size() == 0){
			return null;
		}
		return v.elementAt(v.size()-1);
	}
	public int[][] getFlag(){
		return flag;
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
		spr = null;
		bombBmp = null;
		v.removeAllElements();
		v = null;
		player = null;
		flag = null;
		gameView = null;
	}
}
