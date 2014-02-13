package com.dave.bombman.view;

import java.io.InputStream;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.dave.bombman.ChooseView;
import com.dave.bombman.Effect;
import com.dave.bombman.GameProgressDialog;
import com.dave.bombman.LoadData;
import com.dave.bombman.MainActivity;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.MyTimer;
import com.dave.bombman.R;
import com.dave.bombman.app.Bomb;
import com.dave.bombman.app.Bonus;
import com.dave.bombman.app.Bullet;
import com.dave.bombman.app.Enemy;
import com.dave.bombman.app.Explorer;
import com.dave.bombman.app.Player;
import com.dave.bombman.app.Score;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.app.TileBg;
import com.dave.bombman.app.TimeDown;
import com.dave.bombman.midp.LayerManager;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.midp.TiledLayer;

public class GameView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder holder = null;
	private Canvas canvas = null;
	private MainActivity activity = null;
	public static boolean isEnd = false;
	public boolean isStop = false;
	private boolean isOver = false;
	private Thread th = null;
	private boolean isShowEnemy = false;
	
	TiledLayer tiledLayer1 = null;
	TiledLayer tiledLayer2 = null;
	TiledLayer tiledLayer3 = null;
	//TiledLayer tiledLayer4 = null;
	Sprite sprPlayer = null;
	LayerManager lm = null;
	
	private TileBg tileBg = null;
	private Player player = null;
	public Bomb bomb = null;
	private Explorer explorer = null;
	private Enemy enemy = null;
	private MyTimer timer = null;
	public Score score = null;
	public TimeDown timeDown;
	private Bonus bonus = null;
	private Effect effect = null;
	private Bullet bullet = null;

	public boolean isTouch = false;

	public static int touchX = 0;
	public static int touchY = 0;
	
	public static int viewPortX = 0;
	public static int viewPortY = 0;
	
	private static int LENGTHX = MenuActivity.WIDTH/2;
	private static int LENGTHY = MenuActivity.HEIGHT/2;

	public static int playerLife = 3;

	public static boolean isWin = false;
	
	private int ctrlX ;
	private int ctrlY ;
	private int ctrlX2 ;
	private int ctrlY2 ;
	
	private Bitmap ctrlBmp1 = null;
	private Bitmap ctrlBmp2 = null;
	
	private Bitmap showEnemy = null;
	private Bitmap bg = null;
	
	private LoadData ld = null;
	
	private Bitmap bmpSave;
	private boolean first = true;
	private Canvas canvasSave;
	public int[][] tiles2;
	public int[][] tiles3;
	private Random r = null;
	
	private int optimizingX;
	private int optimizingY;
	private int optimizingLength;
	private int optimizingLength2;
	private int optimizingX2;
	private int optimizingY2;
	//private Context context;
	
	public GameView(Context context) {
		super(context);
		this.activity = (MainActivity) context;
		//this.context = context;
		
		ld = new LoadData(context);
		ld.loadLevel(ChooseView.WORLD, MainActivity.level);
		tiles2 = ld.getMap2();
		tiles3 = ld.getMap3();
		
		score = new Score(context);
		Score.resetScore();
	//	Score.setBestScore(ld.getScore());
		
		timeDown = new TimeDown(context);
	///	TimeDown.setTime(140);		//游戏时间
		initTime();	//初始化时间
		
		r = new Random();
	
		
		lm = new LayerManager();
		tileBg = new TileBg(context);
		tileBg.getLoadData(ld);
		
		tiledLayer1 = tileBg.getTiledbg1();
		tiledLayer2 = tileBg.getTiledbg2();
		tiledLayer3 = tileBg.getTiledbg3();
	//	tiledLayer4 = tileBg.getTiledbg4();
		
		player = new Player(context);
		sprPlayer = player.getPlayer();
		
		effect = new Effect(context, sprPlayer, GameView.this);
		
		bomb = new Bomb(context,sprPlayer, GameView.this);
		timer = new MyTimer(GameView.this);
		
		bonus = new Bonus(context);
		bonus.setBonus(ld);
		bonus.initArray(tiles3);
		bonus.setEffect(effect);
		
		bullet = new Bullet(context);
		
		explorer = new Explorer(this, context, tiledLayer3, tiledLayer2, bomb, sprPlayer, bonus);
		explorer.setEffect(effect);
		
		enemy = new Enemy(context, tiledLayer3, tiledLayer2, sprPlayer, bomb, explorer);
		enemy.getLoadData(ld);
		enemy.setEffect(effect);
		enemy.setGameView(this);
		enemy.setBullet(bullet);
		
		timeDown.setEnemy(enemy);
		
		explorer.getEnemy(enemy);
		explorer.getMyTimer(timer);
	
		viewPortX = 0;
		viewPortY = 0;
		lm.append(explorer.getDoor());
		lm.append(tiledLayer3);
		lm.append(tiledLayer2);
		lm.append(tiledLayer1);
		lm.setViewWindow(viewPortX, viewPortY, MenuActivity.WIDTH, MenuActivity.HEIGHT);
		
		ctrlBmp1 = ReadBitMap(context, R.drawable.ctrl11);
		ctrlBmp2 = ReadBitMap(context, R.drawable.ctrl1);
	//	scoreBg = ReadBitMap(context, R.drawable.scorebg);
	//	timeBg = ReadBitMap(context, R.drawable.timebg);
	
		bg = ReadBitMap(context, R.drawable.bg);
	//	timeUp = ReadBitMap(context, R.drawable.fail);
		
		initEnemyShow(context);
		init();		//初始数据
		
		
		holder = this.getHolder();
		holder.addCallback(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	//初始化时间
	private void initTime(){
		if(ChooseView.WORLD == "WORLD1"){
			TimeDown.setTime(180);
		}else if(ChooseView.WORLD == "WORLD2"){
			TimeDown.setTime(160);
		}else if(ChooseView.WORLD == "WORLD3"){
			TimeDown.setTime(140);
		}else if(ChooseView.WORLD == "WORLD4"){
			TimeDown.setTime(130);
		}else if(ChooseView.WORLD == "WORLD5"){
			TimeDown.setTime(120);
		}else if(ChooseView.WORLD == "WORLD6"){
			TimeDown.setTime(100);
		}
	}
	private void initEnemyShow(Context context){
		if(ChooseView.WORLD == "WORLD1"){
		switch(MainActivity.level){
		case 1:
			showEnemy = ReadBitMap(context, R.drawable.ess1);
			isShowEnemy = true;
			break;
		case 2:
			showEnemy = ReadBitMap(context, R.drawable.ess2);
			isShowEnemy = true;
			break;
		case 3:
			showEnemy = ReadBitMap(context, R.drawable.ess3);
			isShowEnemy = true;
			break;
		case 4:
			showEnemy = ReadBitMap(context, R.drawable.ess4);
			isShowEnemy = true;
			break;
		case 5:
			showEnemy = ReadBitMap(context, R.drawable.ess5);
			isShowEnemy = true;
			break;
		case 6:
			showEnemy = ReadBitMap(context, R.drawable.ess6);
			isShowEnemy = true;
			break;
		case 7:
			showEnemy = ReadBitMap(context, R.drawable.ess7);
			isShowEnemy = true;
			break;
		case 8:
			showEnemy = ReadBitMap(context, R.drawable.ess8);
			isShowEnemy = true;
			break;
		case 9:
			showEnemy = ReadBitMap(context, R.drawable.ess9);
			isShowEnemy = true;
			break;
		case 10:
			showEnemy = ReadBitMap(context, R.drawable.ess10);
			isShowEnemy = true;
			break;
		default:
			isShowEnemy = false;
			break;
		}
		}else{
			isShowEnemy = false;
		}
		
	}
	private void init(){
		Player.isDead = false;
		Player.isPower = false;
		isWin = false;
		isEnd = false;
		isOver = false;
		Enemy.bossIsPower = false;
		
		ctrlX = ctrlBmp1.getWidth()/2;
		ctrlY = MenuActivity.HEIGHT - ctrlX;
		ctrlX2 = ctrlX;
		ctrlY2 = ctrlY;
		
		switch(MenuActivity.SIZE){
		case 32:
			optimizingX = 18;
			optimizingY = 25;
			optimizingX2 = 16;
			optimizingY2 = 27;
			optimizingLength = 17;
			optimizingLength2 = 15;
			break;
		case 48:
			optimizingX = 27;
			optimizingY = 37;
			optimizingX2 = 24;
			optimizingY2 = 40;
			optimizingLength = 25;
			optimizingLength2 = 22;
			break;
		case 64:
			optimizingX = 36;
			optimizingY = 50;
			optimizingX2 = 32;
			optimizingY2 = 54;
			optimizingLength = 34;
			optimizingLength2 = 30;
			break;
		}
		
	}
	public void control(){
		if(isTouch == true){
		//	if(touchX < 2*ctrlX+20 && touchY > ctrlY-ctrlX-20){		//控制区域
				ctrlX2 = touchX;
				ctrlY2 = touchY;
		//	}
		}
	}
	
	private void MoveViewPort(int direction) {
		switch (direction) {
		case 1:			//left
			if (sprPlayer.getRefPixelX() - viewPortX < LENGTHX) {
				viewPortX -= Player.SPEED;
			}
			if (viewPortX <= 0) {
				viewPortX = 0;
			}
			break;
		case 2:			//right
			if (sprPlayer.getRefPixelX() >= MenuActivity.WIDTH - LENGTHX) {
				viewPortX += Player.SPEED;
			}
			if (viewPortX >= tiledLayer1.getWidth() - MenuActivity.WIDTH) {
				viewPortX = tiledLayer1.getWidth() - MenuActivity.WIDTH;
			}
			break;
		case 3:			//up
			if (sprPlayer.getRefPixelY() - viewPortY < LENGTHY) {
				viewPortY -= Player.SPEED;
			}
			if (viewPortY <= 0) {
				viewPortY = 0;
			}

			break;
		case 4:			//down
			if (sprPlayer.getRefPixelY() >= MenuActivity.HEIGHT - LENGTHY) {
				viewPortY += Player.SPEED;
			}
			if (viewPortY >= tiledLayer1.getHeight() - MenuActivity.HEIGHT) {
				viewPortY = tiledLayer1.getHeight() - MenuActivity.HEIGHT;
			}
			break;

		}
        lm.setViewWindow(viewPortX, viewPortY, MenuActivity.WIDTH, MenuActivity.HEIGHT);

    }

	
	public void touchEvent(MotionEvent event, Button buttonA, Button buttonB) {
		int num = event.getPointerCount();
		if(num == 1){		//单点触控
			int tempX = (int)event.getX();
			int tempY = (int)event.getY();
			if(isShowEnemy == true && event.getAction() == MotionEvent.ACTION_DOWN){
				isShowEnemy = false;
				//按键提示
				Message msg = MenuActivity.myHandler.obtainMessage();
				msg.arg1 = 13;
				MenuActivity.myHandler.sendMessage(msg);
			}else{
				if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN){
					touchX = tempX;
					touchY = tempY;
					isTouch = true;
				}	
				if(event.getAction() == MotionEvent.ACTION_UP){
					isTouch = false;
					sprPlayer.setFrame(0);
					ctrlX2 = ctrlX;
					ctrlY2 = ctrlY;
					
				}
			}

		}else{			//双点触控
			for(int i = 0; i < num; i++){
				int tempX = (int)event.getX(i);
				int tempY = (int)event.getY(i);
				if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN ){
					
						if(tempX < 2*ctrlX+20 && tempY > ctrlY-ctrlX-20){		//控制区域
							touchX = tempX;
							touchY = tempY;
							isTouch = true;
						} 
						
				}	
				if(event.getAction() == MotionEvent.ACTION_POINTER_1_UP || event.getAction() == MotionEvent.ACTION_POINTER_2_UP){
					
					if(tempX > buttonA.getLeft() && tempY > buttonA.getTop()
							&& tempX < buttonA.getRight() && tempY < buttonA.getBottom()){
						buttonAClick();
					}else if(tempX > buttonB.getLeft() && tempY > buttonB.getTop()
							&& tempX < buttonB.getRight() && tempY < buttonB.getBottom()){
						buttonBClick();
					}
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					isTouch = false;
					sprPlayer.setFrame(0);
					ctrlX2 = ctrlX;
					ctrlY2 = ctrlY;

				}

					//return false;
				}
			
		}
	}
	private void buttonAClick(){
		if(isShowEnemy == false && Player.isDead == false){
			if(bomb.addBomb(sprPlayer.getRefPixelX(), sprPlayer.getRefPixelY())){
				//	activity.showWin();
					//timeDown.stopTime();
					//isEnd = true;
				SoundPlayer.playSound(R.raw.click);
				timer.addTimer();
				
				
			}
		}
	}
		
	private void buttonBClick(){
		if(Player.isTiming == true && bomb.v.size() != 0 && Player.isDead == false){
			int bombX = bomb.v.elementAt(0).getX();
			int bombY = bomb.v.elementAt(0).getY();
			bomb.delete(0);
			timer.delete(0);
			//lm.remove(3);
			explorer.add(bombX, bombY);
			
		}
		//bullet.addBullet(player.getX(), player.getY(), player.getDirection());
	}
	public void setClick(Button A, Button B, Button play){
		A.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 buttonAClick();
			}
		});
		B.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttonBClick();
			}
		});
		play.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(Player.isDead == false && GameView.isWin == false){
					activity.menuCreate();
					SoundPlayer.playSound(R.raw.click);
				}
			}
		});
	}
	public void deleteBomb(){
		if(bomb.v.size() != 0){
			int bombX = bomb.v.elementAt(0).getX();
			int bombY = bomb.v.elementAt(0).getY();
			bomb.delete(0);
			timer.delete(0);
			//lm.remove(4);
			explorer.add(bombX, bombY);
			
		}
	}
	public void deleteBomb(int index){
		if(index < bomb.v.size()){
			int bombX = bomb.v.elementAt(index).getX();
			int bombY = bomb.v.elementAt(index).getY();
			bomb.delete(index);
			timer.delete(index);
			//lm.remove(4);
			explorer.add(bombX, bombY);
		}
	}
	public void playerMove(int x, int y){
		if(isTouch == true && !Player.isDead && touchX < 2*ctrlX + 20 && touchY > ctrlY-ctrlX - 20){
			int temp1 = MenuActivity.HEIGHT - 2 * ctrlX;
			int temp2 = MenuActivity.HEIGHT;
			if (x + temp1 > y && y > -x + temp2) { // right
				SoundPlayer.playSound(R.raw.move);
				playerMoveRight();
			} else if (x + temp1 < y && y < -x + temp2) { // left
				SoundPlayer.playSound(R.raw.move);
				playerMoveLeft();
			} else if (y - temp1 > x && x > -y + temp2) { // right
				SoundPlayer.playSound(R.raw.move);
				playerMoveDown();
			} else if (y - temp1 < x && x < -y + temp2) { // left
				SoundPlayer.playSound(R.raw.move);
				playerMoveUp();
			}
		}
	}
	private void playerMoveLeft(){
		player.playerDirection(1);
		sprPlayer.move(-Player.SPEED, 0);
		sprPlayer.nextFrame();
		if (player.collidesWith(tiledLayer2) || 
				player.collidesWithBox(tiledLayer3) || 
				bomb.collision() || 
				explorer.collision()) {
			sprPlayer.move(Player.SPEED , 0);
			
			int x = (sprPlayer.getY()+optimizingY)/MenuActivity.SIZE;
			int y = (sprPlayer.getX()+optimizingX)/MenuActivity.SIZE;
			System.out.println(tiles3[x][y-1]);
			System.out.println(tiles3[x-1][y-1]);
			if(tiles2[x][y-1] != 0 || tiles3[x][y-1] != 0){		//移动优化
				System.out.println(tiles2[x][y-1]);
				if(tiles2[x+1][y-1] == 0 && tiles3[x+1][y-1] == 0){
					if((sprPlayer.getY()+optimizingY)%MenuActivity.SIZE > optimizingLength2){
						playerMoveDown();
					}
				}
			}else if(tiles2[x][y-1] == 0 && tiles3[x][y-1] == 0){		//移动优化
				if(tiles2[x+1][y-1] != 0 || tiles3[x+1][y-1] != 0){
					if((sprPlayer.getY()+sprPlayer.getHeight())%MenuActivity.SIZE <= optimizingLength){
						playerMoveUp();
					}
				}
			}
		}else{
			MoveViewPort(1);
		}
		if(bomb.isIn == true){
	
			if(bomb.getBomb() != null && !bomb.getBomb().collidesWith(sprPlayer, false)){
				bomb.isIn = false;
			}else if(Bomb.bombNum == Bomb.maxBombNum){
				bomb.isIn = false;
			}
		}
	}
	private void playerMoveRight(){
		player.playerDirection(2);	
		sprPlayer.move(Player.SPEED, 0);
		sprPlayer.nextFrame();
		if (player.collidesWith(tiledLayer2) || 
				player.collidesWithBox(tiledLayer3) ||  
				bomb.collision() || 
				explorer.collision()) {
			sprPlayer.move(-Player.SPEED , 0);
			
			int x = (sprPlayer.getY()+optimizingY)/MenuActivity.SIZE;
			int y = (sprPlayer.getX()+optimizingX)/MenuActivity.SIZE;
			if(tiles2[x][y+1] != 0 || tiles3[x][y+1] != 0){		//移动优化
				if(tiles2[x+1][y+1] == 0 && tiles3[x+1][y+1] == 0){
					if((sprPlayer.getY()+optimizingY)%MenuActivity.SIZE > optimizingLength2){
						playerMoveDown();
					}
				}
			}else if(tiles2[x][y+1] == 0 && tiles3[x][y+1] == 0){		//移动优化
				if(tiles2[x+1][y+1] != 0 || tiles3[x+1][y+1] != 0){
					if((sprPlayer.getY()+sprPlayer.getHeight())%MenuActivity.SIZE <= optimizingLength){
						playerMoveUp();
					}
				}
			}
			
		}else{
			MoveViewPort(2);
		}
		if(bomb.isIn == true){
			
			if(bomb.getBomb() != null && !bomb.getBomb().collidesWith(sprPlayer, false)){
				bomb.isIn = false;
			}else if(Bomb.bombNum == Bomb.maxBombNum){
				bomb.isIn = false;
			}
		}
	}
	private void playerMoveUp(){
		player.playerDirection(3);
		sprPlayer.move(0, -Player.SPEED);
		sprPlayer.nextFrame();
		
		if (player.collidesWith(tiledLayer2) || 
				player.collidesWithBox(tiledLayer3) || 
				bomb.collision() || 
				explorer.collision()) {
			sprPlayer.move(0, Player.SPEED );
			
			int x = (sprPlayer.getY()+optimizingY2)/MenuActivity.SIZE;
			int y = (sprPlayer.getX()+optimizingX2)/MenuActivity.SIZE;
			if(tiles2[x-1][y] != 0 || tiles3[x-1][y] != 0){		//移动优化
				if(tiles2[x-1][y+1] == 0 && tiles3[x-1][y+1] == 0){
					if((sprPlayer.getX()+optimizingX2)%MenuActivity.SIZE > optimizingLength2){
						playerMoveRight();
					}
				}
			}else if(tiles2[x-1][y] == 0 && tiles3[x-1][y] == 0){		//移动优化
				if(tiles2[x-1][y+1] != 0 || tiles3[x-1][y+1] != 0){
					if((sprPlayer.getX()+sprPlayer.getWidth()*2/3)%32 <= optimizingLength){
						playerMoveLeft();
					}
				}
			}
		}else{
			MoveViewPort(3);
		}
		if(bomb.isIn == true){
			if(bomb.getBomb() != null && !bomb.getBomb().collidesWith(sprPlayer, false)){
				bomb.isIn = false;
			}else if(Bomb.bombNum == Bomb.maxBombNum){
				bomb.isIn = false;
			}
		}
	}
	private void playerMoveDown(){
		player.playerDirection(4);
		sprPlayer.move(0, Player.SPEED);
		sprPlayer.nextFrame();
		if (player.collidesWith(tiledLayer2)
				|| player.collidesWithBox(tiledLayer3) || bomb.collision()
				|| explorer.collision()) {

			sprPlayer.move(0, -Player.SPEED);

			int x = (sprPlayer.getY() + optimizingY2) / MenuActivity.SIZE;
			int y = (sprPlayer.getX() + optimizingX2) / MenuActivity.SIZE;
			if (tiles2[x + 1][y] != 0 || tiles3[x + 1][y] != 0) { // 移动优化
				if (tiles2[x + 1][y + 1] == 0 && tiles3[x + 1][y + 1] == 0) {
					if ((sprPlayer.getX() + optimizingX2) % MenuActivity.SIZE > optimizingLength2) {
						playerMoveRight();
					}
				}
			} else if (tiles2[x + 1][y] == 0 && tiles3[x + 1][y] == 0) { // 移动优化
				if (tiles2[x + 1][y + 1] != 0 || tiles3[x + 1][y + 1] != 0) {
					if (((sprPlayer.getX() + sprPlayer
							.getWidth() * 2 / 3) % MenuActivity.SIZE) <= optimizingLength2) {
						playerMoveLeft();
					}
				}
			}
		} else {
			MoveViewPort(4);
		}
		if (bomb.isIn == true) {
			if (bomb.getBomb() != null
					&& !bomb.getBomb().collidesWith(sprPlayer, false)) {
				bomb.isIn = false;
			}else if(Bomb.bombNum == Bomb.maxBombNum){
				bomb.isIn = false;
			}
		}
	}
	
	public void update(){
		playerMove(touchX, touchY);
		control();
		effect.update();
		if(r.nextInt(50) == 0){
			effect.addEffect(viewPortX+r.nextInt(MenuActivity.WIDTH), viewPortY+r.nextInt(MenuActivity.HEIGHT), 105);
		}
		explorer.bonus.bonusUpdate();
		bullet.collide(tiles2, tiles3);
		
	}
	
	public void MyDraw(){
		try{
			canvas = holder.lockCanvas();
			if(canvas != null){
				//刷新画布
				canvas.drawRGB(0, 0, 0);
				canvas.clipRect(0, 0, MenuActivity.WIDTH, MenuActivity.HEIGHT);
				
				if(first == true){
					first = false;
					bmpSave = Bitmap.createBitmap( LoadData.MAPCOL*MenuActivity.SIZE, LoadData.MAPROW*MenuActivity.SIZE, Config.ARGB_8888 );//创建一个新的和SRC长度宽度一样的位图
					canvasSave = new Canvas( bmpSave );
					tiledLayer1.paint(canvasSave);
					tiledLayer2.paint(canvasSave);
					
					tiledLayer1.paint(canvas);
					tiledLayer2.paint(canvas);
					tiledLayer3.paint(canvas);
					
			
					canvasSave.save( Canvas.ALL_SAVE_FLAG );//保存
					canvasSave.restore();//存储
				}else{
					canvas.translate(-viewPortX, -viewPortY);
					canvas.drawBitmap(bmpSave, 0, 0, null);
					tiledLayer3.paint(canvas);
					explorer.getDoor().paint(canvas);
					canvas.translate(viewPortX, viewPortY);
				}
				
				
				
				canvas.translate(-viewPortX, -viewPortY);
				bomb.bombDraw(canvas);
				
				
				player.playerDraw(canvas);
				
				
				if(isShowEnemy == false){
					enemy.enemyDraw(canvas);
				}
				bonus.bonusDraw(canvas);
				bullet.draw(canvas);
				
				effect.draw(canvas);
				explorer.drawExp(canvas);
				
				canvas.translate(viewPortX, viewPortY);
				
				canvas.drawBitmap(ctrlBmp1, ctrlX-ctrlBmp1.getWidth()/2, ctrlY-ctrlBmp1.getHeight()/2, null);
				canvas.drawBitmap(ctrlBmp2, ctrlX2-ctrlBmp2.getWidth()/2, ctrlY2-ctrlBmp2.getHeight()/2, null);
				
				
				
				if(isShowEnemy == true){
					canvas.drawBitmap(bg, MenuActivity.WIDTH/2 - bg.getWidth()/2, MenuActivity.HEIGHT/2 - bg.getHeight()/2, null);
					canvas.drawBitmap(showEnemy, MenuActivity.WIDTH/2 - showEnemy.getWidth()/2, MenuActivity.HEIGHT/2 - showEnemy.getHeight()/2, null);
				}
				
				
				if(Player.isDead == true){
						
				//		if(TimeDown.time == 0){		//时间耗尽
				//			canvas.drawBitmap(timeUp, MenuActivity.WIDTH/2 - timeUp.getWidth()/2, MenuActivity.HEIGHT/2 - timeUp.getHeight()/2, null);
				//		}
						if(isOver == false){
							isOver = true;
							
							SoundPlayer.pauseMusic();
	                		SoundPlayer.playSound(R.raw.fail);
							Message msg = MenuActivity.myHandler.obtainMessage();
							msg.arg1 = 2;
							MenuActivity.myHandler.sendMessageDelayed(msg, 3000);
						}
					}else if(GameView.isWin == true){
						isEnd = true;
						SoundPlayer.changeWinMusic();
						SoundPlayer.startMusic();
						//下一关
						Message msg = MenuActivity.myHandler.obtainMessage();
						msg.arg1 = 2;
						MenuActivity.myHandler.sendMessageDelayed(msg, 1000);
					}
				
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(canvas != null)
				holder.unlockCanvasAndPost(canvas);
		}
	}
	
	public void run() {
		while(isEnd == false){
			if(isStop == false){
				long start = System.currentTimeMillis(); 
				MyDraw();
				update();
				
				long end = System.currentTimeMillis();
				try{
				//	System.out.println(end-start);
					if(end - start < 80){
						//System.out.println(end-start);
						Thread.sleep(80 - (end - start));
					}
				}catch (InterruptedException e) {
					System.out.println("----->error");
				}
			}
		}
		System.out.println("run out");
		
	}


	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		timeDown.startTime(1000);
		isEnd = false;
		
		th = new Thread(this);
		th.start();
		
		GameProgressDialog.hideProgressDialog();
		SoundPlayer.changeGameMusic();
		SoundPlayer.startMusic();
	
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		isEnd = true;
		th = null;
	
		System.out.println("destroy");
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
		isEnd = true;
		holder = null;
		canvas = null;
	//	activity = null;
		th = null;
		tiledLayer1 = null;
		tiledLayer2 = null;
		tiledLayer3 = null;
		sprPlayer = null;
		lm = null;
		tileBg = null;
		player.Release();
		player = null;
		bomb.Release();
		bomb = null;
		explorer.Release();
		explorer = null;
		enemy.Release();
		enemy = null;
		timer.Release();
		timer = null;
		score.Release();
		score = null;
		timeDown.Release();
		timeDown = null;
		bonus.Release();
		bonus = null;
		ctrlBmp1 = null;
		ctrlBmp2 = null;
		showEnemy = null;
		bg = null;
	//	timeUp = null;
		ld.Release();
		ld = null;
		bmpSave = null;
		canvasSave = null;
		tiles2 = null;
		tiles3 = null;
		
		
		
	}
	

}
