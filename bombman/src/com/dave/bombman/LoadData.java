package com.dave.bombman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class LoadData {
	private Context context = null;
	private int a[][] = null;
	private int b[][] = null;
	private int c[][] = null;
//	private int d[][] = null;
	
	private int enemy[] = new int[10];
	private int bonus[] = new int[11];
	private int time = 0;
	private int score = 0;
	
	public static int MAPROW = 19;
	public static int MAPCOL = 31;

	public LoadData(Context context) {
		this.context = context;
	}

	public void loadLevel(String world, int level) {
		try {
			loadMap(world + "/" + level + "/" + "map.json");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadGameData(world + "/" + level + "/" + "gameData.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int[][] getMap1() {
		return a;
	}
	public int[][] getMap2() {
		return b;
	}
	public int[][] getMap3() {
		return c;
	}
/*	public int[][] getMap4() {
		return d;
	}*/

	public int[] getEnemy() {
		return enemy;
	}
	public int[] getBonus(){
		return bonus;
	}
	public int getScore(){
		return score;
	}
	public int getTime(){
		return time;
	}

	private void loadGameData(String filename) throws IOException {
		InputStream inputReader = null;
		try {
			inputReader = context.getResources().getAssets().open(filename, 3);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputReader, "GB2312"));
			String line; // 一行数据
			int row = 1;
			// 逐行读取，并将每个数组放入到数组中
			while ((line = reader.readLine()) != null) {
				String[] temp;
				temp = line.split(","+"\\p{Blank}");
	/*			if (temp.length == 1) {
					enemy = new int[Integer.parseInt(temp[0])][8];
					continue;
				}*/
				if(row == 1){
					for (int j = 0; j < temp.length; j++) {
						enemy[j] = Integer.parseInt(temp[j]);
					}
				}else if(row ==  2){
					for (int j = 0; j < temp.length; j++) {
						bonus[j] = Integer.parseInt(temp[j]);
					}
				}else if(row == 3){
					score = Integer.parseInt(line);
				}else if(row == 4){
					time = Integer.parseInt(line);
				}
				row++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inputReader.close();
		}
	}
	
	
	public void loadMap(String filename) throws IOException, JSONException {
		InputStream inputReader = null;
		inputReader = context.getResources().getAssets().open(filename, 3);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputReader, "GB2312"));

		String laststr = "";
		String tempString = null;
		while ((tempString = reader.readLine()) != null) {
			laststr = laststr + tempString;
		}
		inputReader.close();
		reader.close();
	     
		parseJson(laststr);
	}

	private void parseJson(String strResult) throws JSONException {
		JSONObject jo = new JSONObject(strResult);
		JSONArray jsonObjs = jo.getJSONArray("layers");
		
		int height = jo.getInt("height");
		int width = jo.getInt("width");
		LoadData.MAPROW = height;		//row
		LoadData.MAPCOL = width + 3;			//col
		
		for (int i = 0; i < jsonObjs.length(); i++) {
			JSONObject jsonObj = (JSONObject) jsonObjs.opt(i);
					//.getJSONObject("singer");
			String name = jsonObj.getString("name");
			if(name.equals("map1")){
				a = new int[height][width + 3];
				String tmp = jsonObj.getString("data");
				String map = tmp.substring(1, tmp.length()-1);
				String[] data = map.split(",");
				for(int j = 0; j < data.length; j++){
					if(j%width == 7){
						a[j/width][j%width] = Integer.parseInt(data[j]);
						a[j/width][j%width + 1] = Integer.parseInt(data[j]);
						a[j/width][j%width + 2] = Integer.parseInt(data[j]);
						a[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else if(j%width > 7){
						a[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else{
						a[j/width][j%width] = Integer.parseInt(data[j]);
					}
					
				}
			}else if(name.equals("map2")){
				b = new int[height][width + 3];
				String tmp = jsonObj.getString("data");
				String map = tmp.substring(1, tmp.length()-1);
				String[] data = map.split(",");
				for(int j = 0; j < data.length; j++){
					if(j%width == 7){
						b[j/width][j%width] = Integer.parseInt(data[j]);
						b[j/width][j%width + 1] = Integer.parseInt(data[j]);
						b[j/width][j%width + 2] = Integer.parseInt(data[j]);
						b[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else if(j%width > 7){
						b[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else{
						b[j/width][j%width] = Integer.parseInt(data[j]);
					}
				}
				
			}else if(name.equals("map3")){
				c = new int[height][width + 3];
				String tmp = jsonObj.getString("data");
				String map = tmp.substring(1, tmp.length()-1);
				String[] data = map.split(",");
				for(int j = 0; j < data.length; j++){
					if(j%width == 7){
						c[j/width][j%width] = Integer.parseInt(data[j]);
						c[j/width][j%width + 1] = Integer.parseInt(data[j]);
						c[j/width][j%width + 2] = Integer.parseInt(data[j]);
						c[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else if(j%width > 7){
						c[j/width][j%width + 3] = Integer.parseInt(data[j]);
					}else{
						c[j/width][j%width] = Integer.parseInt(data[j]);
					}
				}
				
			}
	/*		else if(name.equals("map4")){
				d = new int[height][width];
				String tmp = jsonObj.getString("data");
				String map = tmp.substring(1, tmp.length()-1);
				String[] data = map.split(",");
				for(int j = 0; j < data.length; j++){
					d[j/width][j%width] = Integer.parseInt(data[j]);
				}
				
			}*/
			
		}
		
	}
	public void Release(){
		context = null;
		a = null;
		b = null;
		c = null;
	//	d = null;
		
	}
	

	
}
