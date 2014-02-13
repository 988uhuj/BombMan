package com.dave.bombman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dave.bombman.app.SoundPlayer;

public class ChooseActivity extends Activity {
private SQLiteDatabase database;
private Cursor cursor;
private List<Integer> lockList;
public static int num = 20;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    
		setContentView(R.layout.chooselevel);
		
		String filename = android.os.Environment.getExternalStorageDirectory()+"/bombman/database.db";
	
	        database = SQLiteDatabase.openOrCreateDatabase(filename, null);
	        cursor = database.query(ChooseView.WORLD, null, null, null, null, null, null);
	        cursor.moveToFirst();
	        num = cursor.getCount();	//关数
	        lockList = new ArrayList<Integer>();
	        do{
	        	switch(cursor.getInt(1)){
	        	case 0:
	        		lockList.add(cursor.getInt(3));//星数
	        		break;
	        	case 1:
	        		lockList.add(4);//锁
	        		break;
	        	}
	        	
	        	
	        }while(cursor.moveToNext());
	        cursor.close();
	        database.close();
		GridView gridview = (GridView) findViewById(R.id.gridview);
		Button back = (Button)findViewById(R.id.chooseBack2);

		// 生成动态数组，并且转入数据
		List<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < num; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", R.drawable.num);// 添加图像资源的ID
			map.put("textView", String.valueOf(i+1));// 按序号做ItemText
			map.put("lockImage", R.drawable.lock);
			lstImageItem.add(map);
		}
		PictureAdapter pictureAdapter = new PictureAdapter(lstImageItem, this);
		// 添加并且显示
		gridview.setAdapter(pictureAdapter);
		// 添加消息处理
		gridview.setOnItemClickListener(new ItemClickListener());
		
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				SoundPlayer.playSound(R.raw.click);
				startChooseWorld();
				
			}
		});
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			//HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
		//	GameProgressDialog.showProgressDialog(ChooseActivity.this);
			SoundPlayer.playSound(R.raw.begingame);
			if(lockList.get(arg2) != 4){
				MainActivity.level = arg2 + 1;
				startgame();
			}else{
				Toast t = Toast.makeText(ChooseActivity.this, "未解锁", Toast.LENGTH_SHORT);
				t.show();
			}
		}

	}
	
	public void startgame() {
	//	GameProgressDialog.showProgressDialog(this);
		Intent intent = new Intent();
		intent.setClass(ChooseActivity.this, LoadingActivity.class);
		startActivity(intent);
		
	//	SoundPlayer.pauseMusic();
		finish();
		
		overridePendingTransition(R.anim.fade, R.anim.fade);
	}
	

	public void startChooseWorld() {
		Intent intent = new Intent();
		intent.setClass(ChooseActivity.this, ChooseView.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.fade, R.anim.fade);
	}
	class PictureAdapter extends BaseAdapter{ 
	    private LayoutInflater inflater; 
	    private List<LevelPicture> pictures;
	    private List<ViewHolder> viewHolders;
	    
		
	 
	    public PictureAdapter(List<HashMap<String, Object>> list, Context context) 
	    { 
	        super(); 
	        pictures = new ArrayList<LevelPicture>(); 
	        inflater = LayoutInflater.from(context); 
	        viewHolders = new ArrayList<ViewHolder>();
	        
	        for (int i = 0; i < list.size(); i++) 
	        { 
	        	HashMap<String, Object> hashMap = list.get(i);
	            LevelPicture picture = new LevelPicture((String)hashMap.get("textView"), (Integer)hashMap.get("itemImage"), (Integer)hashMap.get("lockImage")); 
	            pictures.add(picture);
		       
	            ViewHolder viewHolder = new ViewHolder();
	            viewHolders.add(viewHolder);
	        } 
	    } 
	 

	    public int getCount() 
	    { 
	        if (null != pictures) 
	        { 
	            return pictures.size(); 
	        } else
	        { 
	            return 0; 
	        } 
	    } 
	 
	
	    public Object getItem(int position) 
	    { 
	        return pictures.get(position); 
	    } 
	 
	
	    public long getItemId(int position) 
	    { 
	        return position; 
	    } 
	 
	  
	    public View getView(int position, View convertView, ViewGroup parent) 
	    { 
	    //    ViewHolder viewHolder; 
	        if (convertView == null) 
	        { 
	            convertView = inflater.inflate(R.layout.level_item, null); 
	       //     viewHolder = new ViewHolder(); 
	            viewHolders.get(position).image = (ImageView) convertView.findViewById(R.id.itemImage); 
	            viewHolders.get(position).title = (TextView) convertView.findViewById(R.id.textView);
	            viewHolders.get(position).lock = (ImageView) convertView.findViewById(R.id.lockImage);
	            
	            Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"f3.ttf");
	            viewHolders.get(position).title.setTypeface(tf);
				
	            convertView.setTag(viewHolders.get(position)); 
	            viewHolders.get(position).title.setText(pictures.get(position).getTitle());
	            viewHolders.get(position).image.setImageResource(pictures.get(position).getImageId());
				
				switch(lockList.get(position)){
				case 0:
					viewHolders.get(position).lock.setImageResource(R.drawable.star0);
					break;
				case 1:
					viewHolders.get(position).lock.setImageResource(R.drawable.star1);
					break;
				case 2:
					viewHolders.get(position).lock.setImageResource(R.drawable.star2);
					break;
				case 3:
					viewHolders.get(position).lock.setImageResource(R.drawable.star3);
					break;
				case 4:
					viewHolders.get(position).lock.setImageResource(R.drawable.lock);
					break;
				}
		        
	        } else{ 
	        	
	    //    	viewHolders.get(position) = (ViewHolder) convertView.getTag(); 
	       // 	ViewHolder viewHolder = viewHolders.get(position);
	        //	viewHolder = (ViewHolder)convertView.getTag();
	        	viewHolders.set(position, (ViewHolder)convertView.getTag());
	        } 
	       
			
	        return convertView; 
	    }
	} 
	class ViewHolder 
	{ 
	    public TextView title; 
	    public ImageView image; 
	    public ImageView lock;
	} 
	class LevelPicture{
		private String title; 
	    private int imageId; 
	    private int lockImageId;
	 
	    public LevelPicture() 
	    { 
	        super(); 
	    } 
	 
	    public LevelPicture(String title, int imageId, int lockImageId) 
	    { 
	        super(); 
	        this.title = title; 
	        this.imageId = imageId; 
	        this.lockImageId = lockImageId;
	    } 
	 
	    public String getTitle() 
	    { 
	        return title; 
	    } 
	 
	    public void setTitle(String title) 
	    { 
	        this.title = title; 
	    } 
	 
	    public int getImageId() 
	    { 
	        return imageId; 
	    } 
	 
	    public void setImageId(int imageId) 
	    { 
	        this.imageId = imageId; 
	    } 
	    public int getLockImageId() 
	    { 
	        return lockImageId; 
	    } 
	 
	    public void setLockImageId(int imageId) 
	    { 
	        this.lockImageId = imageId; 
	    } 
	}
}
