package com.dave.bombman.app;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;

import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.midp.TiledLayer;

public class Player {
	private Sprite player = null;
	private Sprite playerEffectSpr = null;
	
	private Bitmap playerBmp = null;
	private Bitmap failBmp = null;
	private Bitmap playerEffect = null;	//13
	
	public static int playerType = 1;
	public static int SPEED = 5;
	public static int SPEED1 = 5;
	public static int SPEED2 = 7;
	public static boolean isDead = false;
	public static boolean isPower = false;
	public static boolean isTiming = true;
	public static boolean isThroughBox = false;
	public static boolean isThroughBomb = false;
	
/*	public  int LEFT = 1;
	public  int RIGHT = 2;
	public  int UP = 3;
	public  int DOWN = 4;*/
	
	int LSequ[]={4,5,4,6,7};     
    int USequ[]={12,13,12,14,15};      
    int DSequ[]={0,1,0,2,3};
    int RSequ[]={8,9,8,10,11};
    
    public int direction = 4;
    
    
    private int index;
    
	public Player (Context context){
		index = 0;
		
		failBmp = ReadBitMap(context, R.drawable.playerdeath);
		
		switch(Player.playerType){
		case 1:
			playerBmp = ReadBitMap(context, R.drawable.player1);
			playerEffect = ReadBitMap(context, R.drawable.playereffect);
			break;
		case 2:
			playerBmp = ReadBitMap(context, R.drawable.player2);
			playerEffect = ReadBitMap(context, R.drawable.playereffect2);
			break;
		case 3:
			playerBmp = ReadBitMap(context, R.drawable.player3);
			playerEffect = ReadBitMap(context, R.drawable.playereffect5);
			break;
		case 4:
			playerBmp = ReadBitMap(context, R.drawable.player4);
			playerEffect = ReadBitMap(context, R.drawable.playereffect6);
			break;
		}
		
	//	playerBmp = Bitmap.createScaledBitmap(playerBmp, 200, 200, true);
		
		
		player = new Sprite(playerBmp, playerBmp.getWidth()/4, playerBmp.getHeight()/4);
		player.defineReferencePixel(player.getWidth()/2, player.getHeight()*3/4);
		player.setRefPixelPosition(MenuActivity.SIZE * 3 / 2, MenuActivity.SIZE * 3 /2);
		player.defineCollisionRectangle(player.getWidth()/3, player.getHeight()/2, player.getWidth()/3,player.getHeight()/2);
		player.setFrameSequence(DSequ);
		
		playerEffectSpr = new Sprite(playerEffect, playerEffect.getWidth()/13, playerEffect.getHeight());
		playerEffectSpr.defineReferencePixel(playerEffectSpr.getWidth()/2, playerEffectSpr.getHeight()/2+(int)(10 * (float)MenuActivity.SIZE / 32));
		
		init();
	}
	private void init(){
		switch(MenuActivity.SIZE){
		case 32:
			Player.SPEED1 = 5;
			Player.SPEED2 = 7;
			break;
		case 48:
			Player.SPEED1 = 8;
			Player.SPEED2 = 10;
			break;
		case 64:
			Player.SPEED1 = 10;
			Player.SPEED2 = 12;
			break;
		}
		Player.SPEED = Player.SPEED1;
		
		if(Player.isTiming == false){
			//设置B键不可见
			Message msg = MenuActivity.myHandler.obtainMessage();
    		msg.arg1 = 7;
    		MenuActivity.myHandler.sendMessage(msg);
		}
	}
	public void playerDirection(int direction){
		if(this.direction != direction){
			this.direction = direction;
		switch(direction){
		case 1:	
			player.setFrameSequence(LSequ);
			break;
		case 2:
			player.setFrameSequence(RSequ);
			break;
		case 3:
			player.setFrameSequence(USequ);
			break;
		case 4:
			player.setFrameSequence(DSequ);
			break;
		}
		}
	}
	public void playerDraw(Canvas canvas){
		
		if(Player.isDead && index < 50){
			canvas.drawBitmap(failBmp, player.getRefPixelX()-failBmp.getWidth()/2, player.getRefPixelY()-failBmp.getHeight()/2, null);
			index ++;
		}else{
			player.paint(canvas);
			if(Player.isPower == true){
				playerEffectSpr.setRefPixelPosition(player.getRefPixelX(), player.getRefPixelY());
				playerEffectSpr.nextFrame();
				playerEffectSpr.paint(canvas);
			}
		}
	}
	public boolean collidesWith(TiledLayer t){
		return player.collidesWith(t, false);
	}
	public boolean collidesWithBox(TiledLayer t3){
		if(Player.isThroughBox == false){
			return player.collidesWith(t3, false);
		}else{
			return false;
		}
	}
	public boolean collidesWithBomb(Sprite bomb){
		if(Player.isThroughBomb == false){
			return player.collidesWith(bomb, false);
		}else{
			return false;
		}
	}
	public static void setPlayerDeath(){
		Player.isDead = true;
		if(Player.playerType != 4){
			Player.isThroughBomb = false;
		}
		Player.isThroughBox = false;
	//	Player.isTiming = false;
		Bomb.bombNum = 3;
		Explorer.power = 1;
		Player.SPEED = Player.SPEED1;
	}
	
	public void Release(){
		playerBmp = null;
		player = null;
		failBmp = null;
		LSequ = null;
		USequ = null;
		DSequ = null;
		RSequ = null;
		playerEffectSpr = null;
		playerEffect = null;
	}
	public Sprite getPlayer(){
		return player;
	}
	public int getX(){
		return player.getRefPixelX();
	}
	public int getY(){
		return player.getRefPixelY();
	}
	public int getDirection(){
		return direction;
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
