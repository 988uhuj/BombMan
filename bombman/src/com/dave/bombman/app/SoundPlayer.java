package com.dave.bombman.app;

import java.util.Map;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.dave.bombman.R;

public class SoundPlayer {

	    private static MediaPlayer music;
	    private static SoundPool soundPool;
	    
	    private static boolean musicSt = true; //���ֿ���
	    private static boolean soundSt = true; //��Ч����
	    private static Context context;
	    
	    private static final int[] musicId = {R.raw.game2,R.raw.game3,R.raw.game4};
	    private static Map<Integer,Integer> soundMap; //��Ч��Դid����ع������Դid��ӳ���ϵ��
	    
	    /**
	     * ��ʼ������
	     * @param c
	     */
	    public static void init(Context c)
	    {
	        context = c;

	      //  initMusic();
//	        initMenuMusic();
//	        
//	        initSound();

	    }
	    
	    //��ʼ����Ч������
	    private static void initSound()
	    {
//	        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,5);
//	        
//	        soundMap = new HashMap<Integer,Integer>();
//	   //     soundMap.put(R.raw.cancel, soundPool.load(context, R.raw.cancel, 1));
//	    //    soundMap.put(R.raw.prompt, soundPool.load(context, R.raw.prompt, 1));
//	        soundMap.put(R.raw.fail, soundPool.load(context, R.raw.fail, 1));
//	        soundMap.put(R.raw.bonus, soundPool.load(context, R.raw.bonus, 1));
//	        soundMap.put(R.raw.bonus2, soundPool.load(context, R.raw.bonus2, 1));
//	        soundMap.put(R.raw.begingame, soundPool.load(context, R.raw.begingame, 1));
//	        soundMap.put(R.raw.move, soundPool.load(context, R.raw.move, 1));
//	        soundMap.put(R.raw.move2, soundPool.load(context, R.raw.move2, 1));
//	        soundMap.put(R.raw.click, soundPool.load(context, R.raw.click, 1));
//	        soundMap.put(R.raw.explore, soundPool.load(context, R.raw.explore3, 1));
//	        soundMap.put(R.raw.addboss, soundPool.load(context, R.raw.addboss, 1));
//	        soundMap.put(R.raw.enemyzero, soundPool.load(context, R.raw.enemyzero, 1));
//	        soundMap.put(R.raw.enemydeath, soundPool.load(context, R.raw.enemydeath, 1));
//	        soundMap.put(R.raw.bosspower, soundPool.load(context, R.raw.bosspower, 1));
//	        soundMap.put(R.raw.playerpower, soundPool.load(context, R.raw.playerpower, 1));
	    }
	    
	    //��ʼ�����ֲ�����
	    private static void initGameMusic(){
//	        int r = new Random().nextInt(musicId.length);
//	        music = MediaPlayer.create(context,musicId[r]);
//	    //	music = MediaPlayer.create(context, R.raw.game2);
//	        music.setLooping(true);

	 //       am.setStreamVolume(AudioManager.STREAM_MUSIC, 15,
//					AudioManager.FLAG_PLAY_SOUND);
	    }
	    public static void initMenuMusic(){
//	    	music = MediaPlayer.create(context, R.raw.begin);
//	    	music.setLooping(true);
	    }
	    
	    /**
	     * ������Ч
	     * @param resId ��Ч��Դid
	     */
	    public static void playSound(int resId)
	    {
//	        if(soundSt == false)
//	            return;
//	        
//	        Integer soundId = soundMap.get(resId);
//	        if(soundId != null){
//	            soundPool.play(soundId, 1.0F, 1.0F, 1, 0, 1);
//	        }
	    }
	    /**
	     * ������Ч      ���ȼ�
	     * @param resId
	     * @param priority
	     */
	    
	    
	    public static void playSound(int resId,int priority)
	    {
//	        if(soundSt == false)
//	            return;
//	        
//	        Integer soundId = soundMap.get(resId);
//	        if(soundId != null){
//	            soundPool.play(soundId, 0.2F, 0.2F, priority, 0, 1);
//	        }
	    }

	    /**
	     * ��ͣ����
	     */
	    public static void pauseMusic()
	    {	
//	    	if(musicSt == true)
//		        if(music.isPlaying() == true)
//		            music.pause();
	    }

	    
	    /**
	     * ��������
	     */
	    public static void startMusic()
	    {
//	        if(musicSt){
//	            music.start();
//	            music.setVolume(1.0F, 1.0F);
//	        }
	    }
	    
	  
	    /**
	     * �л�һ�����ֲ�����
	     */
	    public static void changeWinMusic()
	    {
//	        if(music != null){
//	            music.release();
//	            music = null;
//	        }
//	        music = MediaPlayer.create(context,R.raw.win);
	    }
	    public static void changeFailMusic(){
//	    	 if(music != null){
//	    		 music.release();
//	    		 music = null;
//		     }
//	    	 music = MediaPlayer.create(context,R.raw.fail);
	    }
	    public static void changeGameMusic(){
//	    	if(music != null){
//	            music.release();
//	            music = null;
//	        }
//	        initGameMusic();
	    }
	    
	    /**
	     * ������ֿ���״̬
	     * @return
	     */
	    public static boolean isMusicSt() {
	        return musicSt;
	    }
	    
	    /**
	     * �������ֿ���
	     * @param musicSt
	     */
	    public static void setMusicSt(boolean musicSt) {
//	        SoundPlayer.musicSt = musicSt;
	/*      if(musicSt){
	            music.start();   
	        }
	        else{
	        	if(music.isPlaying())
	        		music.pause();
	        }*/
	        
	    }

	    /**
	     * �����Ч����״̬
	     * @return
	     */
	    public static boolean isSoundSt() {
	        return soundSt;
	    }

	    /**
	     * ������Ч����
	     * @param soundSt
	     */
	    public static void setSoundSt(boolean soundSt) {
//	        SoundPlayer.soundSt = soundSt;
	    }
	    
	    /**
	     * �ͷű���������Դ
	     */
	    public static void Release(){
//	    	if(music != null){
//	    		music.release();
//	    		System.out.println("Release");
//	    	}
	    }
	    public static void stopSound(int resId){
//	    	Integer soundId = soundMap.get(resId);
//	        if(soundId != null)
//	            soundPool.stop(resId);
	    }
}
