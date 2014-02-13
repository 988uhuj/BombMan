package com.dave.bombman.view;

import java.io.InputStream;

import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MenuView extends SurfaceView implements Callback, Runnable{

	private boolean isEnd = false;
	private Thread th = null;
	private Canvas canvas = null;
	private SurfaceHolder holder = null;
	private MenuActivity activity = null;
	
	private Bitmap background = null;
//	private Bitmap begin = null;

	public MenuView(Context context) {
		super(context);
		
		
		this.activity = (MenuActivity)context;
		if(th == null){
			System.out.println("new th");
			th = new Thread(this);
		}
		background = ReadBitMap(context, R.drawable.mainbg);
//		begin = ReadBitMap(context, R.drawable.begin);
		
		holder = this.getHolder();
		holder.addCallback(this);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	public void menuTouch(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			int x = (int)event.getX();
			int y = (int)event.getY();
			if(x > 200 && y > 50 && y < 100){
				System.out.println("begin");
				activity.startChoose();
			}
		}
	}
	
	public void MyDraw(){
		try{
			canvas = holder.lockCanvas();
			if(canvas != null){
				canvas.drawBitmap(background, 0, 0, null);
				
				//canvas.drawBitmap(begin, 200, 50, null);
				//canvas.drawBitmap(begin, 200, 110, null);
				//canvas.drawBitmap(begin, 200, 170, null);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(canvas != null)
				holder.unlockCanvasAndPost(canvas);
		}
	}
	public void update(){
		
	}

	public void run() {
		while(isEnd  == false){
			long start = System.currentTimeMillis(); 
			MyDraw();
			update();
			long end = System.currentTimeMillis();
			try{
				if(end - start < 80)
				  Thread.sleep(80 - (end - start));
			}catch (InterruptedException e) {
				System.out.println("----->error");
			}
		}
		System.out.println("menu run out");
		
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}

	public void surfaceCreated(SurfaceHolder holder) {
		isEnd = false;
		if(th != null){
			th.start();
		}else{
			System.out.println("creat new th");
			th = new Thread(this);
			th.start();
		}
		
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

}
