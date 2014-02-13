package com.dave.bombman.app;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dave.bombman.LoadData;
import com.dave.bombman.MenuActivity;
import com.dave.bombman.R;
import com.dave.bombman.midp.TiledLayer;

public class TileBg {
	private Bitmap tiled1;
	
	private TiledLayer tiledbg1;
	private TiledLayer tiledbg2;
	private TiledLayer tiledbg3;
//	private TiledLayer tiledbg4;
	
	private LoadData ld = null;
	
	public TileBg(Context context) {
		
		try {
			tiled1 = ReadBitMap(context, R.drawable.map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void getLoadData(LoadData ld) {
		this.ld = ld;
	}

	public TiledLayer getTiledbg1() {
		if (tiledbg1 == null) {
			int[][] tiles = ld.getMap1();
			tiledbg1 = new TiledLayer(tiles.length, tiles[0].length, tiled1, MenuActivity.SIZE, MenuActivity.SIZE);
			for (int row = 0; row < tiles.length; row++) {
				for (int col = 0; col < tiles[0].length; col++) {
					tiledbg1.setCell(row, col, tiles[row][col]);
				}
			}
			
		}

		return tiledbg1;
	}
	public TiledLayer getTiledbg2() {
		if (tiledbg2 == null) {
			int[][] tiles = ld.getMap2();
			tiledbg2 = new TiledLayer(tiles.length, tiles[0].length, tiled1, MenuActivity.SIZE, MenuActivity.SIZE);
			
			for (int row = 0; row < tiles.length; row++) {
				for (int col = 0; col < tiles[0].length; col++) {
					tiledbg2.setCell(row, col, tiles[row][col]);
				}
			}
		}

		return tiledbg2;
	}
	public TiledLayer getTiledbg3() {
		if (tiledbg3 == null) {
			int[][] tiles = ld.getMap3();
			tiledbg3 = new TiledLayer(tiles.length, tiles[0].length, tiled1, MenuActivity.SIZE, MenuActivity.SIZE);
			
			for (int row = 0; row < tiles.length; row++) {
				for (int col = 0; col < tiles[0].length; col++) {
					tiledbg3.setCell(row, col, tiles[row][col]);
				}
			}
		}

		return tiledbg3;
	}
/*	public TiledLayer getTiledbg4() {
		if (tiledbg4 == null) {
			int[][] tiles = ld.getMap4();
			tiledbg4 = new TiledLayer(tiles.length, tiles[0].length, tiled1, MenuActivity.SIZE, MenuActivity.SIZE);
			
			for (int row = 0; row < tiles.length; row++) {
				for (int col = 0; col < tiles[0].length; col++) {
					tiledbg4.setCell(row, col, tiles[row][col]);
				}
			}
		}

		return tiledbg4;
	}
	*/

	public Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}
	public void Release(){
		tiled1 = null;
		tiledbg1 = null;
		tiledbg2 = null;
		tiledbg3 = null;
	//	tiledbg4 = null;
		ld = null;
	}

}
