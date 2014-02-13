package com.dave.bombman;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.dave.bombman.view.GameView;

public class MyTimer{
	public Vector<Timer>  timerV = new Vector<Timer>();
	public Vector<TimerTask>  timerTaskV = new Vector<TimerTask>();
	public GameView gameView = null;
	public static int time = 6000;
	
	public MyTimer(GameView gameView){
		super();
		this.gameView = gameView;
		
	}
	public void addTimer(){
		Timer timer = new Timer();
		timerV.addElement(timer);
		TimerTask tk = new TimerTask() {
			@Override
			public void run() {
				if(gameView != null){
					System.out.println(timerV.size());
					gameView.deleteBomb();
					System.out.println(timerV.size());
				}
			}
		};
		timerTaskV.addElement(tk);
		timer.schedule(tk, time);
	}
	
	public void delete(int i){
		if(i < timerV.size()){
			Timer timer = timerV.elementAt(i);
			TimerTask tk = timerTaskV.elementAt(i);
			if(timer != null){
			//	timer.purge();
			
				timer.cancel();
				tk.cancel();
			}
			timerV.removeElementAt(i);
			timerTaskV.removeElementAt(i);
		}
	}
	public void Release(){
	
		timerV = null;
		timerTaskV.removeAllElements();
		timerTaskV = null;
		
		gameView = null;
		
	}
}
