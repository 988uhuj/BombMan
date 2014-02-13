package com.dave.bombman.view;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;

import com.dave.bombman.GameProgressDialog;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.app.Bomb;
import com.dave.bombman.app.Explorer;
import com.dave.bombman.app.Player;
import com.dave.bombman.app.SoundPlayer;
import com.dave.bombman.midp.Sprite;

public class StoreView extends SurfaceView implements Callback,Runnable{
	private SurfaceHolder holder;
	private Canvas canvas;
	private boolean isEnd;
	private Thread th;
	
	private Bitmap bg;
	private Bitmap checkBmp;
	public Bitmap storeBmp;
	private Bitmap lockBmp;
	
	private Bitmap player1;
	private Bitmap player2;
	private Bitmap player3;
	private Bitmap player4;
	
	private Sprite[] playerSpr;
	
	private Bitmap effect1;
	private Bitmap effect2;
	private Bitmap effect3;
	private Bitmap effect4;
	
	private Bitmap[] bomb;
	private Bitmap[] bonus;
	private Bitmap[] bombEff;
	
	
	private Sprite[] effectSpr;
	private Sprite[] bombSpr;
	private Sprite[] bombSprEff;
	private Sprite[] bonusSpr;
	
/*	private Bitmap intro1;
	private Bitmap intro2;
	private Bitmap intro3;
	private Bitmap intro4;*/
	
	public int[] playerPrice = {0, 0, 0 ,0};
	public int[] playerLock = {0, 0, 0, 1};
	public int[] bombPrice = {0, 0, 0, 0};
	public int[] bombLock = {0, 0, 0, 1};
	

	int DSequ[]={0,1,0,2,3};

	public int check = 1;
	public int playerType = 1;
	public int bombFlag = 1;
	public int bonusFlag = 1;
	
	private Context context;
	
	public StoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		isEnd = false;
		th = new Thread(this);
		
		this.context = context;
		init(context);
		
		holder = this.getHolder();
		holder.addCallback(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	private void init(Context context){
		bg = ReadBitMap(context, R.drawable.background);
		checkBmp = ReadBitMap(context, R.drawable.check);
		storeBmp = ReadBitMap(context, R.drawable.storebg1);
		lockBmp = ReadBitMap(context, R.drawable.lock);
		
		player1 = ReadBitMap(context, R.drawable.player1);
		player2 = ReadBitMap(context, R.drawable.player2);
		player3 = ReadBitMap(context, R.drawable.player3);
		player4 = ReadBitMap(context, R.drawable.player4);
		
		effect1 = ReadBitMap(context, R.drawable.playereffect);
		effect2 = ReadBitMap(context, R.drawable.playereffect2);
		effect3 = ReadBitMap(context, R.drawable.playereffect5);
		effect4 = ReadBitMap(context, R.drawable.playereffect6);
		
		bomb = new Bitmap[4];
		bomb[0] = ReadBitMap(context, R.drawable.bomb1);
		bomb[1] = ReadBitMap(context, R.drawable.bomb2);
		bomb[2] = ReadBitMap(context, R.drawable.bomb3);
		bomb[3] = ReadBitMap(context, R.drawable.bomb4);
		
		bonus = new Bitmap[4];
		bonus[0] = ReadBitMap(context, R.drawable.bonuspower);
		bonus[1] = ReadBitMap(context, R.drawable.bonusnum);
		bonus[2] = ReadBitMap(context, R.drawable.bonusbomb);
		bonus[3] = ReadBitMap(context, R.drawable.bonusbox);
		
		bombEff = new Bitmap[4];
		bombEff[0] = ReadBitMap(context, R.drawable.bombeff1);
		bombEff[1] = ReadBitMap(context, R.drawable.bombeff2);
		bombEff[2] = ReadBitMap(context, R.drawable.bombeff3);
		bombEff[3] = ReadBitMap(context, R.drawable.bombeff4);
		
		playerSpr = new Sprite[4];
		playerSpr[0] = new Sprite(player1, player1.getWidth() / 4, player1.getHeight() / 4);
		playerSpr[1] = new Sprite(player2, player2.getWidth() / 4, player2.getHeight() / 4);
		playerSpr[2] = new Sprite(player3, player3.getWidth() / 4, player3.getHeight() / 4);
		playerSpr[3] = new Sprite(player4, player4.getWidth() / 4, player4.getHeight() / 4);
		
	
		
		effectSpr = new Sprite[4]; 
		effectSpr[0] = new Sprite(effect1, effect1.getWidth() / 13, effect1.getHeight());
		effectSpr[1] = new Sprite(effect2, effect2.getWidth() / 13, effect2.getHeight());
		effectSpr[2] = new Sprite(effect3, effect3.getWidth() / 13, effect3.getHeight());
		effectSpr[3] = new Sprite(effect4, effect4.getWidth() / 13, effect4.getHeight());
		effectSpr[0].setVisible(true);
		effectSpr[1].setVisible(false);
		effectSpr[2].setVisible(false);
		effectSpr[3].setVisible(false);
		
		bombSpr = new Sprite[4];
		bonusSpr = new Sprite[4];
		bombSprEff = new Sprite[4];
		
		for(int i = 0; i < 4; i++){
			playerSpr[i].defineReferencePixel(playerSpr[i].getWidth()/2, playerSpr[i].getHeight()*3/4);
			playerSpr[i].setFrameSequence(DSequ);
			playerSpr[i].setRefPixelPosition(MenuActivity.WIDTH/2 + (int)((i-1.5f) * MenuActivity.SIZE * 2), 
					MenuActivity.HEIGHT/2);
			
			effectSpr[i].defineReferencePixel(effectSpr[i].getWidth()/2, effectSpr[i].getHeight()/2+(int)(10 * (float)MenuActivity.SIZE / 32));
			effectSpr[i].setRefPixelPosition(playerSpr[i].getRefPixelX(), playerSpr[i].getRefPixelY());
			
			bombSpr[i] = new Sprite(bomb[i], bomb[i].getWidth()/12, bomb[i].getHeight());
			bombSpr[i].defineReferencePixel(bombSpr[i].getWidth()/2, bombSpr[i].getHeight()/2);
			bombSpr[i].setRefPixelPosition(MenuActivity.WIDTH/2 + (int)((i-1.5f) * MenuActivity.SIZE * 2), 
					MenuActivity.HEIGHT/2);
			
			bonusSpr[i] = new Sprite(bonus[i], bonus[i].getWidth()/12, bonus[i].getHeight());
			bonusSpr[i].defineReferencePixel(bonusSpr[i].getWidth()/2, bonusSpr[i].getHeight()/2);
			bonusSpr[i].setRefPixelPosition(MenuActivity.WIDTH/2 + (int)((i-1.5f) * MenuActivity.SIZE * 2), 
					MenuActivity.HEIGHT/2);
			
			bombSprEff[i] = new Sprite(bombEff[i], MenuActivity.SIZE/4*10, MenuActivity.SIZE/4*10);
			bombSprEff[i].defineReferencePixel(bombSprEff[i].getWidth()/2, bombSprEff[i].getHeight()/2);
			bombSprEff[i].setRefPixelPosition(MenuActivity.WIDTH/2 + (int)((i-1.5f) * MenuActivity.SIZE * 2), 
					MenuActivity.HEIGHT/2-MenuActivity.SIZE);
			if(i != 0){
				bombSprEff[i].setVisible(false);
			}
		}
	}
	public void storeTouch(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_UP){
			int x = (int)event.getX();
			int y = (int)event.getY();
			switch(check){
			case 1:
				
				if(x > playerSpr[0].getX() && x < playerSpr[0].getX()+playerSpr[0].getWidth()
						&& y > playerSpr[0].getY() && y < playerSpr[0].getY()+playerSpr[0].getHeight()){
					playerType = 1;
					effectSpr[0].setVisible(true);
					effectSpr[1].setVisible(false);
					effectSpr[2].setVisible(false);
					effectSpr[3].setVisible(false);
					Toast t1 = Toast.makeText(context, "速度:5, 稳定性:2, 加速:7", Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[1].getX() && x < playerSpr[1].getX()+playerSpr[1].getWidth()
						&& y > playerSpr[1].getY() && y < playerSpr[1].getY()+playerSpr[1].getHeight()){
					playerType = 2;
					effectSpr[1].setVisible(true);
					effectSpr[0].setVisible(false);
					effectSpr[2].setVisible(false);
					effectSpr[3].setVisible(false);
					Toast t1 = Toast.makeText(context, "速度:5, 稳定性:3, 加速:6", Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[2].getX() && x < playerSpr[2].getX()+playerSpr[2].getWidth()
						&& y > playerSpr[2].getY() && y < playerSpr[2].getY()+playerSpr[2].getHeight()){
					playerType = 3;
					effectSpr[2].setVisible(true);
					effectSpr[0].setVisible(false);
					effectSpr[1].setVisible(false);
					effectSpr[3].setVisible(false);
					
					String tmp = "";
					if(playerLock[2] == 1){
						tmp = ", 售价：" + Integer.toString(playerPrice[2]);
					}
					Toast t1 = Toast.makeText(context, "速度:5, 稳定性:4, 加速:7" + tmp, Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[3].getX() && x < playerSpr[3].getX()+playerSpr[3].getWidth()
						&& y > playerSpr[3].getY() && y < playerSpr[3].getY()+playerSpr[3].getHeight()){
					playerType = 4;
					effectSpr[3].setVisible(true);
					effectSpr[0].setVisible(false);
					effectSpr[1].setVisible(false);
					effectSpr[2].setVisible(false);
					String tmp = "";
					if(playerLock[3] == 1){
						tmp = ", 售价：" + Integer.toString(playerPrice[3]);
					}
					Toast t1 = Toast.makeText(context, "速度:6, 稳定性:4, 加速:7, 属性：穿箱" + tmp, Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				break;
			case 2:
				if(x > playerSpr[0].getX() && x < playerSpr[0].getX()+playerSpr[0].getWidth()
						&& y > playerSpr[0].getY() && y < playerSpr[0].getY()+playerSpr[0].getHeight()){
					bombFlag = 1;
					bombSprEff[0].setVisible(true);
					bombSprEff[1].setVisible(false);
					bombSprEff[2].setVisible(false);
					bombSprEff[3].setVisible(false);
					Toast t1 = Toast.makeText(context, "爆破定时:6 S, 爆破维持度：低", Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[1].getX() && x < playerSpr[1].getX()+playerSpr[1].getWidth()
						&& y > playerSpr[1].getY() && y < playerSpr[1].getY()+playerSpr[1].getHeight()){
					bombFlag = 2;
					bombSprEff[0].setVisible(false);
					bombSprEff[1].setVisible(true);
					bombSprEff[2].setVisible(false);
					bombSprEff[3].setVisible(false);
					Toast t1 = Toast.makeText(context, "爆破定时:5 S, 爆破维持度：中", Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[2].getX() && x < playerSpr[2].getX()+playerSpr[2].getWidth()
						&& y > playerSpr[2].getY() && y < playerSpr[2].getY()+playerSpr[2].getHeight()){
					bombFlag = 3;
					bombSprEff[0].setVisible(false);
					bombSprEff[1].setVisible(false);
					bombSprEff[2].setVisible(true);
					bombSprEff[3].setVisible(false);
					String tmp = "";
					if(bombLock[2] == 1){
						tmp = ", 售价：" + Integer.toString(bombPrice[2]);
					}
					Toast t1 = Toast.makeText(context, "爆破定时:6 S, 爆破维持度：高" + tmp, Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				if(x > playerSpr[3].getX() && x < playerSpr[3].getX()+playerSpr[3].getWidth()
						&& y > playerSpr[3].getY() && y < playerSpr[3].getY()+playerSpr[3].getHeight()){
					bombFlag = 4;
					bombSprEff[0].setVisible(false);
					bombSprEff[1].setVisible(false);
					bombSprEff[2].setVisible(false);
					bombSprEff[3].setVisible(true);
					String tmp = "";
					if(bombLock[3] == 1){
						tmp = ", 售价：" + Integer.toString(bombPrice[3]);
					}
					Toast t1 = Toast.makeText(context, "爆破定时:5 S, 爆破维持度：高" + tmp, Toast.LENGTH_SHORT);
					t1.show();
					SoundPlayer.playSound(R.raw.click);
				}
				break;
			case 3:
				if(x > playerSpr[0].getX() && x < playerSpr[0].getX()+playerSpr[0].getWidth()
						&& y > playerSpr[0].getY() && y < playerSpr[0].getY()+playerSpr[0].getHeight()){
					bonusFlag = 1;
					Toast t1 = Toast.makeText(context, "当前炸弹威力为：" + Integer.toString(Explorer.power), Toast.LENGTH_SHORT);
					t1.show();
				}
				if(x > playerSpr[1].getX() && x < playerSpr[1].getX()+playerSpr[1].getWidth()
						&& y > playerSpr[1].getY() && y < playerSpr[1].getY()+playerSpr[1].getHeight()){
					bonusFlag = 2;
					Toast t2 = Toast.makeText(context, "当前炸弹个数为：" + Integer.toString(Bomb.bombNum), Toast.LENGTH_SHORT);
					t2.show();
				}
				if(x > playerSpr[2].getX() && x < playerSpr[2].getX()+playerSpr[2].getWidth()
						&& y > playerSpr[2].getY() && y < playerSpr[2].getY()+playerSpr[2].getHeight()){
					bonusFlag = 3;
					
					String throughtBomb = "";
					if(Player.isThroughBomb){
						throughtBomb = "已装备";
					}else{
						throughtBomb = "无";
					}
					Toast t3 = Toast.makeText(context, "当前穿弹属性：" + throughtBomb, Toast.LENGTH_SHORT);
					t3.show();
				}
				if(x > playerSpr[3].getX() && x < playerSpr[3].getX()+playerSpr[3].getWidth()
						&& y > playerSpr[3].getY() && y < playerSpr[3].getY()+playerSpr[3].getHeight()){
					bonusFlag = 4;
					
					String throughtBox = "";
					if(Player.isThroughBox){
						throughtBox = "已装备";
					}else{
						throughtBox = "无";
					}
					Toast t4 = Toast.makeText(context, "当前穿箱属性：" + throughtBox, Toast.LENGTH_SHORT);
					t4.show();
				}
				break;
			}
			
		}
	}
	public void MyDraw(){
		try{
			canvas = holder.lockCanvas();
			if(canvas != null){
				canvas.drawRGB(0, 0, 0);
				canvas.drawBitmap(bg, 0, 0, null);
				canvas.drawBitmap(storeBmp, MenuActivity.WIDTH/2 - storeBmp.getWidth()/2,
						MenuActivity.HEIGHT/2 - storeBmp.getHeight()/2, null);
				switch(check){
				case 1:
					for(int i = 0; i < 4; i++){
						playerSpr[i].paint(canvas);
						if(effectSpr[i].isVisible()){
							playerSpr[i].nextFrame();
							
							effectSpr[i].nextFrame();
							effectSpr[i].paint(canvas);
							
							canvas.drawBitmap(checkBmp, playerSpr[i].getRefPixelX() - checkBmp.getWidth()/2, 
									playerSpr[i].getRefPixelY() - checkBmp.getHeight()/2, null);
						}else{
							playerSpr[i].setFrame(0);
						}
						if(playerLock[i] == 1){
							canvas.drawBitmap(lockBmp, playerSpr[i].getRefPixelX()-lockBmp.getWidth()/2, 
									playerSpr[i].getRefPixelY(), null);
						}
					}
					
					break;
				case 2:
					for(int i = 0; i < 4; i++){
						bombSpr[i].paint(canvas);
						if(bombSprEff[i].isVisible()){
							bombSpr[i].nextFrame();
							
							bombSprEff[i].nextFrame();
							bombSprEff[i].paint(canvas);
							
							canvas.drawBitmap(checkBmp, bombSpr[i].getRefPixelX() - checkBmp.getWidth()/2, 
									bombSpr[i].getRefPixelY() - checkBmp.getHeight()/2, null);
						}else{
							bombSpr[i].setFrame(0);
						}
						if(bombLock[i] == 1){
							canvas.drawBitmap(lockBmp, bombSpr[i].getRefPixelX()-lockBmp.getWidth()/2, 
									bombSpr[i].getRefPixelY(), null);
						}
					}
					
					break;
				case 3:
					switch (bonusFlag) {
					case 1:
						bonusSpr[0].nextFrame();
						bonusSpr[1].setFrame(0);
						bonusSpr[2].setFrame(0);
						bonusSpr[3].setFrame(0);
						
						
						break;
					case 2:
						bonusSpr[0].setFrame(0);
						bonusSpr[1].nextFrame();
						bonusSpr[2].setFrame(0);
						bonusSpr[3].setFrame(0);
						
						break;
					case 3:
						bonusSpr[0].setFrame(0);
						bonusSpr[1].setFrame(0);
						bonusSpr[2].nextFrame();
						bonusSpr[3].setFrame(0);
						
						break;
					case 4:
						bonusSpr[0].setFrame(0);
						bonusSpr[1].setFrame(0);
						bonusSpr[2].setFrame(0);
						bonusSpr[3].nextFrame();
						
						break;

					}
					canvas.drawBitmap(checkBmp, bonusSpr[bonusFlag-1].getRefPixelX() - checkBmp.getWidth()/2, 
							bonusSpr[bonusFlag-1].getRefPixelY() - checkBmp.getHeight()/2, null);
					for (int i = 0; i < 4; i++) {
						bonusSpr[i].paint(canvas);
					}
					
					break;
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
		// TODO Auto-generated method stub
		while(isEnd == false){
			long start = System.currentTimeMillis(); 
			MyDraw();
			long end = System.currentTimeMillis();
			try{
				if(end - start <100)
				  Thread.sleep(100 - (end - start));
			}catch (InterruptedException e) {
				// TODO: handle exception
				System.out.println("----->error");
			}
		}
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isEnd = false;
		th.start();
		GameProgressDialog.hideProgressDialog();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isEnd = true;
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
