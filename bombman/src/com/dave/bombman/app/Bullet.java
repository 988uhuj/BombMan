package com.dave.bombman.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.midp.Sprite;
import com.dave.bombman.view.GameView;

public class Bullet {
	private Bitmap bullet;
	private Bitmap bulletEff;
	private Sprite sprBullet;
	private Vector<BulletEntry> v;
	
	public static final int SHOT_COUNT = 5;
	
	public Bullet(Context context){
		bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.buttle2);
		bulletEff = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_eff);
		sprBullet = new Sprite(bullet);
		sprBullet.defineReferencePixel(sprBullet.getWidth()/2, sprBullet.getHeight()/2);
		v = new Vector<BulletEntry>(20, 10);
	}
	public void addBullet(int x, int y, int direction){
		if(v.size() < 10){
			BulletEntry entry = new BulletEntry();
			entry.setX(x);
			entry.setY(y);
			entry.setFrame(0);
			entry.setPower(1);
			entry.setSpeed(8);
			entry.setDirection(direction);
			entry.setDead(false);
			entry.setCount(SHOT_COUNT);
			v.addElement(entry);
		}
	}
	public void deleteBullet(int i){
		v.removeElementAt(i);
	}
	public void deleteAll(){
		v.removeAllElements();
	}
	public int collide(Sprite s){
		int count = 0;
		for(int i = 0; i < v.size(); i++){
			BulletEntry entry = v.elementAt(i);
			sprBullet.setRefPixelPosition(entry.getX(), entry.getY());
			if(sprBullet.collidesWith(s, false)){
				entry.setDead(true);
				v.setElementAt(entry, i);
				count ++;
			}
		}
		return count;
	}
	public List<int[]> collide(int[][] a, int [][] b){
		int[] position = new int[2];
		List<int[]> lists = new ArrayList<int[]>(); 
		for(int i = 0; i < v.size(); i++){
			BulletEntry entry = v.elementAt(i);
			if(entry.getX() > MenuActivity.WIDTH + GameView.viewPortX + 20
					|| entry.getY() > MenuActivity.HEIGHT + GameView.viewPortY + 20
					|| entry.getX() < 0 - 20 + GameView.viewPortX 
					|| entry.getY() < 0 - 20 + GameView.viewPortY ){
				entry.setDead(true);
			}
			
			int col = entry.getX() / MenuActivity.SIZE;
			int row = entry.getY() / MenuActivity.SIZE;
			if(a[row][col] != 0 || b[row][col] != 0){
				entry.setDead(true);
				position[0] = row;
				position[1] = col;
				lists.add(position);
			}
			v.setElementAt(entry, i);
		}
		return lists;
	}
	public void update(){
		
	}
	public void draw(Canvas canvas){
		int x = 0;
		int y = 0;
		for(int i = 0; i < v.size(); i++){
			BulletEntry entry = v.elementAt(i);
			if(entry.isDead()){
				canvas.drawBitmap(bulletEff, entry.getX() - bulletEff.getWidth()/2, entry.getY() - bulletEff.getHeight()/2, null);
				deleteBullet(i);
				if(i != 0){
					i --;
				}
				continue;
			}
			
			switch(entry.getDirection()){
			case 1:		//Left
				x = entry.getX() - entry.getSpeed();
				y = entry.getY();
				entry.setX(x);
				break;
			case 2:		//Right
				x = entry.getX() + entry.getSpeed();
				y = entry.getY(); 
				entry.setX(x);
				break;
			case 3:		//Up
				x = entry.getX();
				y = entry.getY() - entry.getSpeed();
				entry.setY(y);
				break;
			case 4:		//Down
				x = entry.getX();
				y = entry.getY() + entry.getSpeed();
				entry.setY(y);
				break;
			default:
				break;
			}
			sprBullet.setRefPixelPosition(x, y);
			sprBullet.paint(canvas);
			
			if(entry.getCount() > 0){		//∑¢µØπ‚–ß
				if(entry.getCount() == SHOT_COUNT){
					canvas.drawBitmap(bulletEff, entry.getX() - bulletEff.getWidth()/2, entry.getY() - bulletEff.getHeight()/2, null);
				}
				entry.setCount(entry.getCount() - 1);
			}
		}
	}
	public void release(){
		
	}
}
