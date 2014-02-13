package com.dave.bombman.app;

import java.io.InputStream;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
import com.dave.bombman.midp.TiledLayer;
import com.dave.bombman.view.GameView;

public class Enemy {
	private Bitmap enemy1 = null;
	private Bitmap enemy2 = null;
	private Bitmap enemy3 = null;
	private Bitmap enemy4 = null;
	private Bitmap enemy5 = null;
	private Bitmap enemy6 = null;
	private Bitmap enemy7 = null;
	private Bitmap enemy8 = null;
	private Bitmap enemy9 = null;
	private Bitmap enemy10 = null;

	
	private Sprite enemySpr = null;
	//private Sprite enemyBoss = null;
	
    private int r1 = 5;
  //  private int optimizing;
    
    public Vector<int[]> v = new Vector<int[]>(20, 20);
    private Vector<int[]> enemyDataV = new Vector<int[]>(10,10);
    
    private TiledLayer t1 = null;		//t3
    private TiledLayer t2 = null;		//t2
    
    private int[][] tilesArray1;
    private int[][] tilesArray2;
    
    private Bomb bomb = null;
    private Sprite player = null;
    private Effect effect = null;
    
    int flag[][] = new int[LoadData.MAPROW][LoadData.MAPCOL]; 
    Random r = new Random();
    
    private Explorer explorer = null;
    private GameView gameView;
    private Bullet bullet;
    
    public static boolean bossIsPower = false;
    public static int bossX;
    public static int bossY;
    public static int bossDic;
    
    private int[] enemyData = null;
	private int speed1;
	private int speed2;
	private int speed3;
	public int enemyNum = 0;
    
	public Enemy(Context context, TiledLayer t1, TiledLayer t2, Sprite player, Bomb bomb, Explorer explore){
		enemy1 = ReadBitMap(context, R.drawable.enemy1);
		enemy2 = ReadBitMap(context, R.drawable.enemy2);
		enemy3 = ReadBitMap(context, R.drawable.enemy3);
		enemy4 = ReadBitMap(context, R.drawable.enemy4);
		enemy5 = ReadBitMap(context, R.drawable.enemy5);
		enemy6 = ReadBitMap(context, R.drawable.enemy6);
		enemy7 = ReadBitMap(context, R.drawable.enemy7);
		enemy8 = ReadBitMap(context, R.drawable.enemy8);
		enemy9 = ReadBitMap(context, R.drawable.enemy9);
		enemy10 = ReadBitMap(context, R.drawable.enemy11);
		
		enemySpr = new Sprite(enemy1, enemy1.getWidth()/4, enemy1.getHeight()/4);
		enemySpr.defineReferencePixel(enemySpr.getWidth()/2, enemySpr.getHeight()/2);
		enemySpr.defineCollisionRectangle(enemySpr.getWidth()*2/32, enemySpr.getHeight()*2/32, enemySpr.getWidth()*28/32, enemySpr.getHeight()*28/32);
	//	enemySpr.setPosition(0, 0);
		
	//	enemyBoss = new Sprite(enemy10, enemy10.getWidth()/4, enemy10.getHeight()/4);
	//	enemyBoss.defineReferencePixel(enemyBoss.getWidth()/2, enemyBoss.getHeight()/2);
	//	enemyBoss.defineCollisionRectangle(MenuActivity.SIZE/4, MenuActivity.SIZE/4, MenuActivity.SIZE, MenuActivity.SIZE);
		
		this.t1 = t1;
		this.t2 = t2;
		this.bomb = bomb;
		this.explorer = explore;
		this.player = player;
		enemyNum = 0;
		
		init();
	}
	private void init(){
		switch(MenuActivity.SIZE){
		case 32:
			speed1 = 1;
			speed2 = 2;
			speed3 = 3;
			break;
		case 48:
			speed1 = 2;
			speed2 = 3;
			speed3 = 4;
			break;
		case 64:
			speed1 = 2;
			speed2 = 4;
			speed3 = 6;
			break;
		}
	}
	
	
	public void getLoadData(LoadData ld){
		tilesArray1 = ld.getMap2();
		tilesArray2 = ld.getMap3();
		enemyData = ld.getEnemy();
		addEnemy();
	}
	public void setEffect(Effect effect){
		this.effect = effect;
	}
	public void setGameView(GameView gameView){
		this.gameView = gameView;
	}
	public void setBullet(Bullet bullet){
		this.bullet = bullet;
	}
	public void addEnemy(int x, int y, int direction){
		//ը�Ż�ը����������
		/**
		 * 1.������
		 * 2.������
		 * 3.����	��������
		 * 4.ͼƬ֡
		 * 5.����ֵ
		 * 6.�ٶ�
		 * 7.�������
		 * 8.����Χ
		 */
		switch(direction){
		case 1:		//��
			int a1[] = {0, 0, 1, 4, 1, 2 ,8, 48, 0 ,0, 1, 0, 2};;
			a1[0] = x;
			a1[1] = y;
			a1[4] = 3;
			a1[5] = speed2;
			a1[7] = MenuActivity.SIZE * 2;
			int b1[] = {0, 0, 2, 8, 1, 2 ,8, 48, 0 ,0, 1, 0, 2};
			b1[0] = x;
			b1[1] = y;
			b1[4] = 3;
			b1[5] = speed2;
			b1[7] = MenuActivity.SIZE * 2;
			v.addElement(a1);
			v.addElement(b1);
			break;
		case 2:		//��
			int a2[] = {0, 0, 1, 4, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			a2[0] = x;
			a2[1] = y;
			a2[4] = 3;
			a2[5] = speed2;
			a2[7] = MenuActivity.SIZE * 2;
			int b2[] = {0, 0, 2, 8, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			b2[0] = x;
			b2[1] = y;
			b2[4] = 3;
			b2[5] = speed2;
			b2[7] = MenuActivity.SIZE * 2;
			v.addElement(a2);
			v.addElement(b2);
			break;
		case 3:		//��
			int a3[] = {0, 0, 4, 0, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			a3[0] = x;
			a3[1] = y;
			a3[4] = 3;
			a3[5] = speed2;
			a3[7] = MenuActivity.SIZE * 2;
			int b3[] = {0, 0, 3, 12, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			b3[0] = x;
			b3[1] = y;
			b3[4] = 3;
			b3[5] = speed2;
			b3[7] = MenuActivity.SIZE * 2;
			v.addElement(a3);
			v.addElement(b3);
			break;
		case 4:		//��
			int a4[] = {0, 0, 4, 0, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			a4[0] = x;
			a4[1] = y;
			a4[4] = 3;
			a4[5] = speed2;
			a4[7] = MenuActivity.SIZE * 2;
			int b4[] = {0, 0, 3, 12, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
			b4[0] = x;
			b4[1] = y;
			b4[4] = 3;
			b4[5] = speed2;
			b4[7] = MenuActivity.SIZE * 2;
			v.addElement(a4);
			v.addElement(b4);
			break;
		}
		enemyNum = enemyNum + 2;
		
	}
	public void addBoss(){		//����BOSS
		t1.setCell(Bonus.doorY, Bonus.doorX, 0);
		explorer.bonus.putDoor(Bonus.doorX*MenuActivity.SIZE+MenuActivity.SIZE/2, Bonus.doorY*MenuActivity.SIZE+MenuActivity.SIZE/2);
		explorer.bonus.setDoorVisible(true);
		explorer.bonus.isOpen = false;
		
		int a[] = {0, 0, 4, 0, 1, 2 ,10, 48, 0 ,0, 0, 1, 3};
		a[0] = Bonus.doorX * MenuActivity.SIZE + MenuActivity.SIZE/2;
		a[1] = Bonus.doorY * MenuActivity.SIZE + MenuActivity.SIZE/2;
		a[4] = 15;
		a[5] = speed1;
		a[7] = MenuActivity.SIZE * 3 / 2;
		v.addElement(a);
		
		enemyNum = enemyNum + 1;
	}
	public void addEnemy(){
		for(int i = 0; i < tilesArray1.length; i++){	//row
			for(int j = 0; j < tilesArray1[1].length; j++){	//col
				if(tilesArray1[i][j] == 0 && tilesArray2[i][j] == 0){
					if(i == 1 && j == 1){
						continue;
					}else if(i == 1 && j == 2){
						continue;
					}else if(i == 2 && j == 1){
						continue;
					}else if(i == 2 && j == 2){
						continue;
					}else{
						int[] a = {i, j}; 
						enemyDataV.addElement(a);
					}
				}
			}
		}
		Collections.shuffle(enemyDataV);		//���Ҵ���
		int size = MenuActivity.SIZE;
		
		for(int i = 0; i < enemyData.length; i++){
			for(int j = 0; j < enemyData[i]; j++){
				
				int temp[] = enemyDataV.elementAt(0);
				int x = temp[1]*size+size/2;
				int y = temp[0]*size+size/2;
				/**
				 * 1.������
				 * 2.������
				 * 3.����	��������
				 * 4.ͼƬ֡
				 * 5.����ֵ
				 * 6.�ٶ�
				 * 7.�������
				 * 8.����Χ
				 * 9.��������
				 * 10.����ը��
				 * 11.��ը��ͬ���ھ�
				 * 12.boss
				 * 13.����
				 */
				switch(i){
				case 0:
					int a0[] = {0, 0, 2, 16, 1, 2, 1, 32, 0 ,0, 0, 0, 1};
					a0[0] = x;
					a0[1] = y;
					a0[4] = 3; 
					a0[5] = speed2;
					a0[7] = size;
					v.addElement(a0);
					
					break;
				case 1:
					int a1[] = {0, 0, 4, 0, 1, 4 ,2, 64, 0 ,0, 0, 0, 1};
					
					a1[0] = x;
					a1[1] = y;
					a1[4] = 3; 
					a1[5] = speed2;
					a1[7] = size;
					v.addElement(a1);
					break;
				case 2:
					int a2[] = {0, 0, 4, 0, 1, 2 ,3, 48, 0 ,0, 0, 0, 1};
					
					a2[0] = x;
					a2[1] = y;
					a2[4] = 3; 
					a2[5] = speed1;
					a2[7] = size * 3 / 2;
					v.addElement(a2);
					break;
				case 3:
					int a3[] = {0, 0, 4, 0, 1, 2 ,4, 48, 0 ,0, 0, 0, 1};
					
					a3[0] = x;
					a3[1] = y;
					a3[4] = 3; 
					a3[5] = speed2;
					a3[7] = size * 2;
					v.addElement(a3);
					break;
				case 4:
					int a4[] = {0, 0, 4, 0, 1, 2 ,5, 48, 0 ,0, 0, 0, 1};
					
					a4[0] = x;
					a4[1] = y;
					a4[4] = 3; 
					a4[5] = speed2;
					a4[7] = size * 2;
					v.addElement(a4);
					break;
				case 5:
					int a5[] = {0, 0, 4, 0, 1, 2 ,6, 48, 1 ,0, 0, 0, 2};
					
					a5[0] = x;
					a5[1] = y;
					a5[4] = 3; 
					a5[5] = speed2;
					a5[7] = size;
					v.addElement(a5);
					break;
				case 6:
					int a6[] = {0, 0, 4, 0, 1, 2 ,7, 48, 0 ,0, 0, 0, 2};
					
					a6[0] = x;
					a6[1] = y;
					a6[4] = 3; 
					a6[5] = speed3;
					a6[7] = size * 3 / 2;
					v.addElement(a6);
					break;
				
				case 7:
					int a7[] = {0, 0, 4, 0, 1, 2 ,8, 48, 0 ,1, 0, 0, 2};
					
					a7[0] = x;
					a7[1] = y;
					a7[4] = 3; 
					a7[5] = speed2;
					a7[7] = size * 3 / 2;
					v.addElement(a7);
					break;
				case 8:
					int a8[] = {0, 0, 4, 0, 1, 2 ,9, 48, 0 ,0, 1, 0, 2};
					
					a8[0] = x;
					a8[1] = y;
					a8[4] = 3; 
					a8[5] = speed2;
					a8[7] = size * 2;
					v.addElement(a8);
					break;
				case 9:		//BOSS
					int a9[] = {0, 0, 4, 0, 1, 2 ,10, 48, 0 ,0, 0, 1, 3};
					a9[0] = x;
					a9[1] = y;
					a9[4] = 3;
					a9[5] = speed1;
					a9[7] = size * 3 / 2;
					v.addElement(a9);
					break;
				}
				enemyDataV.removeElementAt(0);
			}
		}
		
		
		enemyNum = v.size();
		enemyDataV.removeAllElements();
	}
	
	public void enemyDraw(Canvas canvas){
		int a[] = null;
		
		for(int i = 0; i < v.size(); i++){
			a = v.elementAt(i);
			this.flag = bomb.getFlag();
		switch(a[6]){
		case 1:
			enemySpr.setImage(enemy1, enemy1.getWidth()/4, enemy1.getHeight()/4);
		//	enemySpr.defineReferencePixel(enemySpr.getWidth()/2, enemySpr.getHeight()/2);
			break;
		case 2:
			enemySpr.setImage(enemy2, enemy2.getWidth()/4, enemy2.getHeight()/4);
			break;
		case 3:
			enemySpr.setImage(enemy3, enemy3.getWidth()/4, enemy3.getHeight()/4);
			break;
		case 4:
			enemySpr.setImage(enemy4, enemy4.getWidth()/4, enemy4.getHeight()/4);
			break;
		case 5:
			enemySpr.setImage(enemy5, enemy5.getWidth()/4, enemy5.getHeight()/4);
			break;
		case 6:
			enemySpr.setImage(enemy6, enemy6.getWidth()/4, enemy6.getHeight()/4);
			break;
		case 7:
			enemySpr.setImage(enemy7, enemy7.getWidth()/4, enemy7.getHeight()/4);
			break;
		case 8:
			enemySpr.setImage(enemy8, enemy8.getWidth()/4, enemy8.getHeight()/4);
			break;
		case 9:
			enemySpr.setImage(enemy9, enemy9.getWidth()/4, enemy9.getHeight()/4);
			break;
		case 10:
			enemySpr.setImage(enemy10, enemy10.getWidth()/4, enemy10.getHeight()/4);
			enemySpr.defineCollisionRectangle(MenuActivity.SIZE/4, MenuActivity.SIZE/4, MenuActivity.SIZE, MenuActivity.SIZE);
			break;
		}
		enemySpr.defineReferencePixel(enemySpr.getWidth()/2, enemySpr.getHeight()/2);
		if(a[11] == 0){		//��boss
			enemySpr.defineCollisionRectangle(enemySpr.getWidth()*2/32, enemySpr.getHeight()*2/32, enemySpr.getWidth()*28/32, enemySpr.getHeight()*28/32);
		}
		
		int x = a[1]/MenuActivity.SIZE;
		int y = a[0]/MenuActivity.SIZE;
		int half = MenuActivity.SIZE / 2;
		switch(a[2]){
		case 1:			//��
			//��ֹ��ס
			
			 if(tilesArray1[x][y-1] != 0 ||( tilesArray2[x][y-1] != 0 && a[8] == 0)){
				if(tilesArray1[x][y+1] != 0 || (tilesArray2[x][y+1] != 0 && a[8] == 0)){
					a[2] = 4;	//��xia
					a[3] = 0;
			//		enemySpr.setRefPixelPosition(a[0], a[1]);
					break;
				}
			}
			
			a[0] = a[0] - a[5];
			if(a[3] > 15){	//֡ѭ��
				a[3] = 8;
			}
			enemySpr.setRefPixelPosition(a[0], a[1]);
			
			
			y = a[0] / MenuActivity.SIZE;		//�µ�����
		
			//����ײ��ı䷽��
			if((enemySpr.collidesWith(t1, false) && a[8] == 0) || enemySpr.collidesWith(t2, false) 
				 || (flag[x][y] != 0 && a[9] == 0)){
				a[2] = 2;		//��ײ��������
				a[3] = 16;
		//		a[0] = a[0] + a[5];
				break;
			}
			//������ڵ����ҷ���С����С��Χ����ı䷽�����ң���������仯���·���
			if(player.getRefPixelX() - a[0] < a[7] && player.getRefPixelX() - a[0] > 0 && 
					Math.abs(player.getRefPixelY() - a[1]) < half){	//��֤��ͬһ��
				a[2] = 2;		//�ı䷽��Ϊ����
				a[3] = 16;
			//	a[0] = a[0] + a[5];
			}else{
			//�漴�ı䷽��
			if(player.getRefPixelY() <= a[1]){
				if((t1.getCell(x-1, y) == 0 || a[8] != 0) && t2.getCell(x-1, y) == 0){
					//������ڵ����Ϸ���С����С��Χ��ı䷽�����ϣ��������
					if(a[1] - player.getRefPixelY() < a[7] && Math.abs(a[0] - player.getRefPixelX()) < half){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 3;
						a[3] = 24;
					}else if(r.nextInt(r1) == 0 && (a[0]-half) % MenuActivity.SIZE == 0){//��֤�仯������·��
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 3;
						a[3] = 24;
					}
				}
			}else if(player.getRefPixelY() > a[1]){//��ֹt1��t2����Խ��
				if((t1.getCell(x+1, y) == 0 || a[8] != 0) && t2.getCell(x+1, y) == 0){
					//������ڵ����·���С����С��Χ��ı䷽�����£��������
					if(player.getRefPixelY() - a[1] < a[7] && Math.abs(a[0] - player.getRefPixelX()) < half){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 4;
						a[3] = 0;
					}else if(r.nextInt(r1) == 0 && (a[0]-half) % MenuActivity.SIZE == 0){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 4;
						a[3] = 0;
					}
				}
			}
			}
			break;
		case 2:	//��
			//��ֹ��ס
			if(tilesArray1[x][y-1] != 0 || (tilesArray2[x][y-1] != 0 && a[8] == 0)){
				if(tilesArray1[x][y+1] != 0 ||( tilesArray2[x][y+1] != 0 && a[8] == 0)){
					a[2] = 4;	//����
					a[3] = 0;
				//	enemySpr.setRefPixelPosition(a[0], a[1]);
					break;
				}
			}
			
			a[0] = a[0] + a[5];
			if(a[3] > 23){
				a[3] = 16;
			}
			enemySpr.setRefPixelPosition(a[0], a[1]);
			
			y = a[0] / MenuActivity.SIZE;		//�µ�����
			
			//��ײ�ı䷽��
			if((enemySpr.collidesWith(t1, false) && a[8] == 0) || enemySpr.collidesWith(t2, false)
					|| (flag[x][y] != 0 && a[9] == 0)){
				a[2] = 1;	//�ı䷽��Ϊ����
				a[3] = 8;
		//		a[0] = a[0] - a[5];
				break;
			}
			//������ڵ�������С����С��Χ����ı䷽�����󣬷�������仯���·���
			if(a[0] - player.getRefPixelX() < a[7] && a[0] - player.getRefPixelX() > 0 && 
					Math.abs(player.getRefPixelY() - a[1]) < half){
				a[2] = 1;
				a[3] = 8;
		//		a[0] = a[0] - a[5];
			}else{
				if(player.getRefPixelY() < a[1]){
					if((t1.getCell(x-1, y) == 0 || a[8] != 0) && t2.getCell(x-1, y) == 0){
						//������ڵ����Ϸ���С����С��Χ��ı䷽�����ϣ��������
						if(a[1] - player.getRefPixelY() < a[7] && Math.abs(a[0] - player.getRefPixelX()) < half){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 3;
							a[3] = 24;
						}else if(r.nextInt(r1) == 0 && (a[0]-half) % MenuActivity.SIZE == 0){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 3;
							a[3] = 24;
						}
						
					}
				}else if(player.getRefPixelY() > a[1]){//��ֹt1��t2����Խ��
					if((t1.getCell(x+1, y) == 0 || a[8] != 0) && t2.getCell(x+1, y) == 0){
						//������ڵ����·���С����С��Χ��ı䷽�����£��������
						if(player.getRefPixelY() - a[1] < a[7] && Math.abs(a[0] - player.getRefPixelX()) < half){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 4;
							a[3] = 0;
						}else if(r.nextInt(r1) == 0 && (a[0]-half) % MenuActivity.SIZE == 0){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 4;
							a[3] = 0;
						}
					}
				}
			}
			break;
		case 3:	//��
			//��ֹ��ס
			if(tilesArray1[x-1][y] != 0 || (tilesArray2[x-1][y] != 0 && a[8] == 0)){
				if(tilesArray1[x+1][y] != 0 ||( tilesArray2[x+1][y] != 0 && a[8] == 0)){
					a[2] = 1;	//����
					a[3] = 8;
				//	enemySpr.setRefPixelPosition(a[0], a[1]);
					break;
				}
			}
			
			a[1] = a[1] - a[5];
			if(a[3] > 31){
				a[3] = 24;
			}
			
			enemySpr.setRefPixelPosition(a[0], a[1]);
			
			x = a[1] / MenuActivity.SIZE;		//�µ�����
			
			//��ײ�ı䷽��
			if((enemySpr.collidesWith(t1, false) && a[8] == 0) || enemySpr.collidesWith(t2, false)
					|| (flag[x][y] != 0 && a[9] == 0)){
				a[2] = 4;	//�ı䷽��Ϊ����
				a[3] = 0;
	//			a[1] = a[1] + a[5];
				break;
			}
			//������ڵ����·���С����С��Χ����ı䷽�����£���������仯���ҷ���
			if(player.getRefPixelY() - a[1] < a[7] && player.getRefPixelY() - a[1] > 0 && 
					Math.abs(player.getRefPixelX() - a[0]) < half){
				a[2] = 4;
				a[3] = 0;
	//			a[1] = a[1] + a[5];
			}else{
			if(player.getRefPixelX() <= a[0]){
				if((t1.getCell(x, y-1) == 0 || a[8] != 0) && t2.getCell(x, y-1) == 0){
					//������ڵ�������С����С��Χ��ı䷽�����󣬷������
					if(a[0] - player.getRefPixelX() < a[7] && Math.abs(a[1] - player.getRefPixelY()) < half){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 1;
						a[3] = 8;
					}else
					if(r.nextInt(r1) == 0 && (a[1]-half) % MenuActivity.SIZE == 0){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 1;
						a[3] = 8;
					}
				}
			}else if(player.getRefPixelX() > a[0]){
				if((t1.getCell(x, y+1) == 0 || a[8] != 0) && t2.getCell(x, y+1) == 0){
					//������ڵ����ҷ���С����С��Χ��ı䷽�����ң��������
					if(player.getRefPixelX() - a[0] < a[7] && Math.abs(a[1] - player.getRefPixelY()) < half){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 2;
						a[3] = 16;
					}else
					if(r.nextInt(r1) == 0 && (a[1]-half) % MenuActivity.SIZE == 0){
						a[0] = y*MenuActivity.SIZE+half;
						a[1] = x*MenuActivity.SIZE+half;
						a[2] = 2;
						a[3] = 16;
					}
				}
			}
			}
			break;
		case 4:	//��
			//��ֹ��ס
			if(tilesArray1[x-1][y] != 0 || (tilesArray2[x-1][y] != 0  && a[8] == 0)){
				if(tilesArray1[x+1][y] != 0 || (tilesArray2[x+1][y] != 0  && a[8] == 0)){
					a[2] = 1;	//����
					a[3] = 8;
			//		enemySpr.setRefPixelPosition(a[0], a[1]);
					break;
				}
			}
			a[1] = a[1] + a[5];
			if(a[3] > 7){
				a[3] = 0;
			}
			
			enemySpr.setRefPixelPosition(a[0], a[1]);
			
			x = a[1] / MenuActivity.SIZE;		//�µ�����
			
			
			//��ײ�ı䷽��
			if((enemySpr.collidesWith(t1, false) && a[8] == 0) || enemySpr.collidesWith(t2, false)
					|| (flag[x][y] != 0 && a[9] == 0)){
				a[2] = 3;	//�ı䷽��Ϊ����
				a[3] = 24;
	//			a[1] = a[1] - a[5];
				break;
			}
			//������ڵ����Ϸ���С����С��Χ����ı䷽�����ϣ���������仯���ҷ���
			if(a[1] - player.getRefPixelY() < a[7] && a[1] - player.getRefPixelY() > 0 && 
					Math.abs(player.getRefPixelX() - a[0]) < half){
				a[2] = 3;	//�ı䷽��Ϊ����
				a[3] = 24;
	//			a[1] = a[1] - a[5];
			}else{
				if(player.getRefPixelX() < a[0]){
					if((t1.getCell(x, y-1) == 0 || a[8] != 0) && t2.getCell(x, y-1) == 0){
						//������ڵ�������С����С��Χ��ı䷽�����󣬷������
						if(a[0] - player.getRefPixelX() < a[7] && Math.abs(a[1] - player.getRefPixelY()) < half){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 1;
							a[3] = 8;
						}else
						if(r.nextInt(r1) == 0 && (a[1]-half) % MenuActivity.SIZE == 0){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 1;
							a[3] = 8;
						}
					}
				}else if(player.getRefPixelX() > a[0]){
					if((t1.getCell(x, y+1) == 0 || a[8] != 0) && t2.getCell(x, y+1) == 0){
						//������ڵ����ҷ���С����С��Χ��ı䷽�����ң��������
						if(player.getRefPixelX() - a[0] < a[7] && Math.abs(a[1] - player.getRefPixelY()) < half){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 2;
							a[3] = 16;
						}else
						if(r.nextInt(r1) == 0 && (a[1]-half) % MenuActivity.SIZE == 0){
							a[0] = y*MenuActivity.SIZE+half;
							a[1] = x*MenuActivity.SIZE+half;
							a[2] = 2;
							a[3] = 16;
						}
					}
				}
			}
			break;
		}
		if(a[10] != 0){
			for(int index = 0; index < bomb.v.size(); index++){
				if(enemySpr.collidesWith(bomb.v.elementAt(index), false)){
					a[4] = 0;
					gameView.deleteBomb(index);
					break;
				}
			}
		}
		if(a[11] != 0){		//�����BOSS��¼λ�÷���
			bossX = a[0];
			bossY = a[1];
			bossDic = a[2];
			if(bossIsPower){
				a[5] = speed3;	//�����������
				a[7] = MenuActivity.SIZE * 3;
			}else{
				a[5] = speed1;
				a[7] = MenuActivity.SIZE * 3;
			}
		}
		a[4] = a[4] - bullet.collide(enemySpr);	//�е���
		if(explorer.collision(enemySpr)){	//��������
			if(a[11] != 0){	//BOSS
				if(bossIsPower == false){	//�Ǳ���ģʽ
					a[4] -= 3;		//����ֵ��һ
					if(a[4] >= 0){
						bossIsPower = true;		//����ģʽ
						effect.addEffect(a[0], a[1], 301);	//��ӱ�����Ч�������걣��״̬��ʧ
						Timer timer = new Timer();		//������ʱ��������
						TimerTask tk = new TimerTask() {
							
							@Override
							public void run() {
								if(v != null && GameView.isWin == false){
									addEnemy(bossX, bossY, bossDic);
								}
							//	timer.cancel();
							}
						};
						timer.schedule(tk, 6000);
					}
				}
				enemySpr.setRefPixelPosition(a[0], a[1]);	//����BOSS
				enemySpr.setFrame(a[3]/2);
				a[3]++;
				enemySpr.paint(canvas);
				if(enemySpr.collidesWith(player, false)){
					if(Player.isPower == false){
						SoundPlayer.pauseMusic();
						SoundPlayer.playSound(R.raw.fail);
						Player.setPlayerDeath();		//�������
					}
				}
				
			}else{
				a[4] = 0;		//����BOSS����
			}
			
			
		}else{
			enemySpr.setRefPixelPosition(a[0], a[1]);
			enemySpr.setFrame(a[3]/2);
			a[3]++;
			enemySpr.paint(canvas);		//���Ƶ���
			if(enemySpr.collidesWith(player, false)){
				if(Player.isPower == false && Player.isDead == false){
					SoundPlayer.pauseMusic();
					SoundPlayer.playSound(R.raw.fail);
					Player.setPlayerDeath();		//�������
				}
			}
		}
		
		
		if(a[4] <= 0){
		
			effect.addEffect(a[0], a[1], a[12]);	//��ʾ����
			effect.addEffect(a[0], a[1], -1);		//��ʾ��������ͼ��
			SoundPlayer.playSound(R.raw.enemydeath);
			switch(a[12]){
			case 1:
				Score.addScore(50);
				break;
			case 2:
				Score.addScore(100);
				break;
			case 3:
				Score.addScore(1000);
				break;
			}
			
			v.removeElementAt(i);
			i--;
			enemyNum--;
			if(enemyNum == 0){
				if(explorer.bonus.isOpen == false){
					
					if(ChooseView.WORLD == "WORLD1"){
						//�Ŵ���ʾ
						Message msg = MenuActivity.myHandler.obtainMessage();
						msg.arg1 = 14;
						MenuActivity.myHandler.sendMessage(msg);
					}
					
					explorer.bonus.isOpen = true;	//�Ŵ�
				}
			}else{
				if(explorer.bonus.isOpen == true){
					explorer.bonus.isOpen = false;	//�Źر�
				}
			}
		}else{
			
			v.setElementAt(a, i);
			
		}
		}
	}
	

	
	
	public Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// ��ȡ��ԴͼƬ
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	public void Release(){
		enemy1 = null;
		enemy2 = null;
		enemy3 = null;
		enemy4 = null;
		enemy5 = null;
		enemy6 = null;
		enemy7 = null;
		enemy8 = null;
		enemy9 = null;
		enemy10 = null;
		
		enemySpr = null;
		v.removeAllElements();
		v = null;
		enemyDataV = null;
		t1 = null;
		t2 = null;
		tilesArray1 = null;
		tilesArray2 = null;
		bomb = null;
		player = null;
		flag = null;
		r = null;
		explorer = null;
		enemyData = null;
		gameView = null;
		
	}
	
}

