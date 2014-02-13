package com.dave.bombman;

import android.app.ProgressDialog;
import android.content.Context;

public class GameProgressDialog {
	
	private static ProgressDialog progressDialog;

	public GameProgressDialog(Context context){
		
	}
	
	public static void showProgressDialog(Context context){
		if(progressDialog != null){
			progressDialog = null;
		}
		progressDialog = new ProgressDialog(context);
	//	progressDialog.setContentView(R.layout.progress);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage("‘ÿ»Î÷–...");
		progressDialog.show();
	}
	
	public static void hideProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
		
	}
	
}
