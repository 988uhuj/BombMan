package com.dave.bombman.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class WriteToSD {
	private Context context;
	String filePath = android.os.Environment.getExternalStorageDirectory()+"/bombman";
	
	public WriteToSD(Context context){
		this.context = context;
		if(!isExist()){
			write();
		}
	//	System.out.println("finish");
	}
	private void write(){
		InputStream inputStream;
		
		try {
			inputStream = context.getResources().getAssets().open("bombman_db.db", 3);
			File file = new File(filePath);
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/database.db");
			byte[] buffer = new byte[512];
			int count = 0;
			while((count = inputStream.read(buffer)) > 0){
				fileOutputStream.write(buffer, 0 ,count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
			System.out.println("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean isExist(){
		File file = new File(filePath + "/database.db");
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
}
