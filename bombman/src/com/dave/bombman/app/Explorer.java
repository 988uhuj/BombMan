package com.dave.bombman.app;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;


import com.dave.bombman.Effect;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.MyTimer;
import com.dave.bombman.R;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.midp.TiledLayer;
import com.dave.bombman.view.GameView;

public class Explorer{
	private Bitmap expBmp;	//16
//	private Bitmap expBmp2;	//13
	private Sprite spr1;
//	private Sprite spr2;
	private Vector<int[]> v = null;
	private int[][] bonusArray = null;
	
	private TiledLayer t1 = null; 
	private TiledLayer t2 = null;
//	private TiledLayer t3 = null;
	
	private Sprite player = null;
	
	private Bomb bomb = null;
	public Bonus bonus = null;
	private Enemy enemy = null;
	private Effect effect = null;
	
	private MyTimer timer = null;
	public int x = 0;
	public int y = 0;
	
	public static int power = 1;
	
	private GameView gameView;
	
	public final static int MAXPOWER = 5;	//炸弹最大威力 
	
	public Explorer(GameView gameView, Context context, TiledLayer t2, TiledLayer t1, Bomb bomb, Sprite player, Bonus bonus){
		switch(Bomb.bombType){
		case 1:
			expBmp = ReadBitMap(context, R.drawable.bombeff1);
			break;
		case 2:
			expBmp = ReadBitMap(context, R.drawable.bombeff2);
			break;
		case 3:
			expBmp = ReadBitMap(context, R.drawable.bombeff3);
			break;
		case 4:
			expBmp = ReadBitMap(context, R.drawable.bombeff4);
			break;
		}
		
	//	expBmp2 = ReadBitMap(context, R.drawable.effect7);
		
		spr1 = new Sprite(expBmp, MenuActivity.SIZE/4*10, MenuActivity.SIZE/4*10);
		spr1.defineReferencePixel(spr1.getWidth()/2, spr1.getHeight()/2);
		spr1.defineCollisionRectangle(spr1.getWidth()*3/10, spr1.getHeight()*3/10, MenuActivity.SIZE, MenuActivity.SIZE);
		
	//	spr2 = new Sprite(expBmp2, expBmp2.getWidth()/13, expBmp2.getHeight());
	//	spr2.defineReferencePixel(spr2.getWidth()/2, spr2.getHeight()/2);
		
		v = new Vector<int[]>();
		this.bonus = bonus;
		this.bonusArray = this.bonus.getBonusArray();
		this.t1 = t1;
		this.t2 = t2;
	//	this.t3 = t3;
		this.bomb = bomb;
		this.player = player;
		
		this.gameView = gameView;
	}
	public void getEnemy(Enemy enemy){
		this.enemy = enemy;
	}
	public void getMyTimer(MyTimer timer){
		this.timer = timer;
	}
	public void setEffect(Effect effect){
		this.effect = effect;
	}
	public void add(int x, int y){
		this.x = x;
		this.y = y;
		SoundPlayer.playSound(R.raw.explore);
		addExp(x, y, Explorer.power, 0);
	}

	public void addExp(int x, int y, int power, int n){
		int size = MenuActivity.SIZE;
		int expX = x + size/2;
		int expY = y + size/2;
		int e[] = {expX, expY, 0, 0};//center
		
		/**
		 * 上
		 */
		if( n != 3){
		for (int i = 1; i <= power; i++) {	
			final int a[] = { expX, expY - size * i, 0, 0};	//上一行
			
			int row = a[1] / size;
			int col = a[0] / size;
			
			spr1.setRefPixelPosition(a[0], a[1]);
			if (!spr1.collidesWith(t1, false)) {	//没碰到墙
				
				//如果是砖块则停止，否则判断是否是炸弹
				if (!spr1.collidesWith(t2, false)) {

					//炸到炸弹
					for (int j = 0; j < bomb.v.size(); j++) {
						if (spr1.collidesWith(bomb.v.elementAt(j), false)) {
							int bombX = bomb.v.elementAt(j).getX();
							int bombY = bomb.v.elementAt(j).getY();
							bomb.delete(j);
							timer.delete(j);
							// lm.remove(3+j);
							addExp(bombX, bombY, power, 4);
							i = power;
							break;
						}
					}		
					v.addElement(a);
					
				} else {
					//炸到砖块
					t2.setCell(row, col, 0);
			//		t3.setCell(row-1, col, 0);
					gameView.tiles3[row][col] = 0;
					//砖块是门
					if(bonusArray[row][col] == -1){//门出现
						bonus.putDoor(a[0],a[1]);
						bonus.setDoorVisible(true);
					}else if(bonusArray[row][col] != 0){
						bonus.addBonus(a[0], a[1], bonusArray[row][col]);
					}
					v.addElement(a);
					i = power;
					break;
				}
				//炸到门
				if (bonus.getDoorVisible() && spr1.collidesWith(bonus.getDoor(), false)){
					
					addEnemy(a[0], a[1], 3);
					//v.addElement(a);
					i = power;
					break;
				}
				//炸到宝物
	/*			if(bonus.collision(spr1) != 0){
					final Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						
						public void run() {
							enemy.addEnemy(a[0], a[1], 3);
							bonus.isOpen = false;
							timer.cancel();
						}
					};
					timer.schedule(timerTask, 2000);
					
					//v.addElement(a);
					i = power;
					break;
				}*/
			}else{
				i = power;
			}
		}
		}
		/**
		 * 下
		 */
		if(n != 4){
		for (int i = 1; i <= power; i++) {
			final int b[] = { expX, expY + size * i, 0 , 0};	//下一行
			
			int row = b[1] / size;
			int col = b[0] / size;
			
			spr1.setRefPixelPosition(b[0], b[1]);
			if (!spr1.collidesWith(t1, false)) {
				
					
				if (!spr1.collidesWith(t2, false)) {
					//炸到炸弹
					for(int j = 0; j < bomb.v.size(); j++){
						if(spr1.collidesWith(bomb.v.elementAt(j), false)){
							int bombX = bomb.v.elementAt(j).getX();
							int bombY = bomb.v.elementAt(j).getY();
							bomb.delete(j);
							timer.delete(j);
							//lm.remove(3+j);
							addExp(bombX, bombY, power, 3);
							i = power;
							break;
						}
					}
					
					v.addElement(b);
				} else {
					t2.setCell(row, col, 0);
			//		t3.setCell(row-1, col, 0);
					gameView.tiles3[row][col] = 0;
					
					
					if(bonusArray[row][col] == -1){//门出现
						bonus.putDoor(b[0],b[1]);
						bonus.setDoorVisible(true);
					}else if(bonusArray[row][col] != 0){//宝物
						bonus.addBonus(b[0], b[1], bonusArray[row][col]);
					}
					v.addElement(b);
					i = power;
					break;
				}
				//炸到门
				if (bonus.getDoorVisible() && spr1.collidesWith(bonus.getDoor(), false)){
					
					addEnemy(b[0], b[1], 4);
					//v.addElement(b);
					i = power;
					break;
				}
				//炸到宝物
		/*		if(bonus.collision(spr1) != 0){
					final Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						
						public void run() {
							enemy.addEnemy(b[0], b[1], 4);
							bonus.isOpen = false;
							timer.cancel();
						}
					};
					timer.schedule(timerTask, 2000);
					
					//v.addElement(b);
					i = power;
					break;
				}*/
			}
			else{
				i = power;
			}
		}
		}
		/**
		 * 左
		 */
		if(n != 1){
		for (int i = 1; i <= power; i++) {
			final int c[] = { expX - size * i, expY, 0, 0 };
			
			int row = c[1] / size;
			int col = c[0] / size;
			
			spr1.setRefPixelPosition(c[0], c[1]);
			if (!spr1.collidesWith(t1, false)) {
					
				
				if (!spr1.collidesWith(t2, false)) {
					//炸到炸弹
					for(int j = 0; j < bomb.v.size(); j++){
						if(spr1.collidesWith(bomb.v.elementAt(j), false)){
							int bombX = bomb.v.elementAt(j).getX();
							int bombY = bomb.v.elementAt(j).getY();
							bomb.delete(j);
							timer.delete(j);
							//lm.remove(3+j);
							addExp(bombX, bombY, power, 2);
							i = power;
							break;
						}
					}
					
					v.addElement(c);
				} else {
					t2.setCell(row, col, 0);
				//	t3.setCell(row-1, col, 0);
					gameView.tiles3[row][col] = 0;
					
					if(bonusArray[row][col] == -1){//门出现
						bonus.putDoor(c[0],c[1]);
						bonus.setDoorVisible(true);
					}else if(bonusArray[row][col] != 0){//宝物
						bonus.addBonus(c[0], c[1], bonusArray[row][col]);
					}
					v.addElement(c);
					i = power;
					break;
				}
				//炸到门
				if (bonus.getDoorVisible() && spr1.collidesWith(bonus.getDoor(), false)){
					
					addEnemy(c[0], c[1], 1);
					//v.addElement(c);
					i = power;
					break;
				}
				//炸到宝物
		/*		if(bonus.collision(spr1) != 0){
					final Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						
						public void run() {
							enemy.addEnemy(c[0], c[1], 1);
							bonus.isOpen = false;
							timer.cancel();
						}
					};
					timer.schedule(timerTask, 2000);
					
					//v.addElement(c);
					i = power;
					break;
				}*/
			}
			else{
				i = power;
			}
		}
		}
		/**
		 * 右
		 */
		if(n != 2){
		for (int i = 1; i <= power; i++) {
			final int d[] = { expX + size * i, expY, 0, 0 };
			
			int row = d[1] / size;
			int col = d[0] / size;
			
			spr1.setRefPixelPosition(d[0], d[1]);
			if (!spr1.collidesWith(t1, false)) {
					
				
				if (!spr1.collidesWith(t2, false)) {
					//炸到炸弹
					for(int j = 0; j < bomb.v.size(); j++){
						if(spr1.collidesWith(bomb.v.elementAt(j), false)){
							int bombX = bomb.v.elementAt(j).getX();
							int bombY = bomb.v.elementAt(j).getY();
							bomb.delete(j);
							timer.delete(j);
							//lm.remove(3+j);
							addExp(bombX, bombY, power, 1);
							i = power;
							break;
						}
					}
					v.addElement(d);
					
				} else {
					t2.setCell(row, col, 0);
				//	t3.setCell(row-1, col, 0);
					gameView.tiles3[row][col] = 0;
					
					if(bonusArray[row][col] == -1){//门出现
						bonus.putDoor(d[0],d[1]);		
						bonus.setDoorVisible(true);
					}else if(bonusArray[row][col] != 0){//宝物
						bonus.addBonus(d[0], d[1], bonusArray[row][col]);
					}
					v.addElement(d);
					i = power;
					break;
				}
				//炸到门
				if (bonus.getDoorVisible() && spr1.collidesWith(bonus.getDoor(), false)){
					
					addEnemy(d[0], d[1], 2);
					//v.addElement(d);
					i = power;
					break;
				}
				//炸到宝物
	/*			if(bonus.collision(spr1) != 0){
					final Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						
						public void run() {
							enemy.addEnemy(d[0], d[1], 2);
							timer.cancel();
						}
					};
					timer.schedule(timerTask, 2000);
					bonus.isOpen = false;
					
					//v.addElement(d);
					i = power;
					break;
				}*/
			}
			else{
				i = power;
			}
		}
		}
		v.addElement(e);
	}
	public void addEnemy(int temp0, int temp1, int temp2){
		final Timer timer = new Timer();
		final int x = temp0;
		final int y = temp1;
	//	final int style = temp2;
		
		TimerTask timerTask = new TimerTask() {
			
			public void run() {
				if(enemy != null){
					enemy.addEnemy(x, y, 1);
				}
				timer.cancel();
			}
		};
		bonus.isOpen = false;
		timer.schedule(timerTask,5000);
	}
	public void delete(int i){
		if(i < v.size()){
			v.removeElementAt(i);
		}
	}
	public void update(){
	
	}
	public void drawExp(Canvas canvas){
		
		int a[] = null;

		for(int i = 0; i < v.size(); i++){
		 	a = v.elementAt(i);
       /*     if(a[2] > 15){	//15
            	a[3] = 1;
            	if(a[2] >= spr1.getFrameSequenceLength()){
            		v.removeElementAt(i);
            		i--;
            	}else{
	            	spr2.setRefPixelPosition(a[0], a[1]);
	            	spr2.setFrame(a[2]-16);
	            	spr2.paint(canvas);
	            	a[2]++;
	            	v.setElementAt(a, i);
            	}
            }else{*/
            	if(a[2] > 1){
            		a[3] = 1;
            	}
            	if(a[2] >= spr1.getFrameSequenceLength()){
            		v.removeElementAt(i);
            		i--;
            	}else{
	    			spr1.setRefPixelPosition(a[0], a[1]);
	    			
	    			spr1.setFrame(a[2]);
	    			spr1.paint(canvas);
	                
	                if(spr1.collidesWith(player, false) && a[3] == 0){
	                	if(Player.isPower == false && Player.isDead == false){
	                		
	                		Player.setPlayerDeath();	//玩家死亡
	                	}
	                }
	            	a[2] = a[2]+ 1;
	            	v.setElementAt(a, i);
            	}
           // }
		}
		//bonus.bonusDraw(canvas);
	}
	public boolean collision(){
		if(bonus.getDoorVisible()){
			if(player.collidesWith(bonus.getDoor(), false)){
				if(bonus.isOpen == true){
					System.out.println("win");
					
					
					GameView.isWin = true;
				//	return false;
				}
				return false;
			}
		}
		
		switch(bonus.collision(player)){	//获得宝物
		
		case 9:		//50分
			SoundPlayer.playSound(R.raw.bonus);
			Score.addScore(50);
			effect.addEffect(player.getRefPixelX(), player.getRefPixelY(), 1);
			break;
		case 10:		//100分
			SoundPlayer.playSound(R.raw.bonus);
			Score.addScore(100);
			effect.addEffect(player.getRefPixelX(), player.getRefPixelY(), 2);
			break;
		case 1:		//1000分
			SoundPlayer.playSound(R.raw.bonus2);
			Score.addScore(1000);
			Score.getStarNum++;		//星数增加
			effect.addEffect(player.getRefPixelX(), player.getRefPixelY(), 3);
			
			Message msg2 = MenuActivity.myHandler.obtainMessage();
			msg2.arg1 = 10;
			MenuActivity.myHandler.sendMessage(msg2);
			break;
		case 2:		//增加炸弹威力
			SoundPlayer.playSound(R.raw.bonus);
			Explorer.power++;
			if(Explorer.power > Explorer.MAXPOWER){		//不超过最大威力
				Explorer.power = Explorer.MAXPOWER;
			}
			effect.addEffect(0, 0, 202);	//宝物提示
			break;
		case 3:		//增加炸弹个数
			SoundPlayer.playSound(R.raw.bonus);
			Bomb.maxBombNum++;
			Bomb.bombNum++;
			
			effect.addEffect(0, 0, 201);
			break;
		case 4:		//炸弹定时
			SoundPlayer.playSound(R.raw.bonus);
			effect.addEffect(0, 0, 203);
			Player.isTiming = true;
			//设置B键可见
			Message msg = MenuActivity.myHandler.obtainMessage();
			msg.arg1 = 8;
			MenuActivity.myHandler.sendMessage(msg);
			break;
		case 5:		//穿箱子
			SoundPlayer.playSound(R.raw.bonus);
			effect.addEffect(0, 0, 205);
			Player.isThroughBox = true;
			break;
		case 6:		//穿炸弹
			SoundPlayer.playSound(R.raw.bonus);
			effect.addEffect(0, 0, 204);
			Player.isThroughBomb = true;
			break;
		case 7:		//玩家无敌
			SoundPlayer.playSound(R.raw.playerpower);
			effect.addEffect(0, 0, 206);
			Timer timer9 = new Timer();
			TimerTask tk9 = new TimerTask() {
				@Override
				public void run() {
					Player.isPower = false;
				}
			};
			Player.isPower = true;
			timer9.schedule(tk9, 20000);	//无敌时间20秒
			break;
		case 8:	//增加速度
			SoundPlayer.playSound(R.raw.bonus);
			effect.addEffect(0, 0, 207);
			Timer timer10 = new Timer();
			TimerTask tk10 = new TimerTask() {
				@Override
				public void run() {
					Player.SPEED = Player.SPEED1;
				}
			};
			Player.SPEED = Player.SPEED2;
			timer10.schedule(tk10, 20000);	//凌波微步20秒
			break;
		}
		return false;
	}
	public boolean collision(Sprite spr){
		int a[] = null;
		for(int i = 0; i < v.size(); i++){
			a = v.elementAt(i);
			spr1.setRefPixelPosition(a[0], a[1]);
			if(spr1.collidesWith(spr, false)){
				return true;
			}
		}
		return false;
	}
	public Sprite getDoor(){
		return bonus.getDoor();
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
		expBmp = null;
	//	expBmp2 = null;
		spr1 = null;
	//	spr2 = null;
		v = null;
		bonusArray = null;
		t1 = null;
		t2 = null;
		player = null;
		bomb = null;
		bonus = null;
		enemy = null;
	//	ld = null;
		timer = null;
		gameView = null;
		effect = null;
		
				
	}
}
