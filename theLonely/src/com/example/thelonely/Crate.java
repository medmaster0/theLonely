package com.example.thelonely;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Crate {
	public Bitmap bmp;
	public float x = 10;
	public float y = 775;
	public float r = 32;
	public int width, height;

	/*take care of animation*/
	private int bmpRows = 1;
	private int bmpCols = 1;
	private int currentFrame = 0; //collumn in sprite sheet
	private int currentDir = 0; //row in sprite sheet
	
	public Crate(Resources r){
		BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        opt.inScaled = false;
		bmp = BitmapFactory.decodeResource(r, R.drawable.crate, opt);
		nearestNeighbor(bmp,(int)this.r,(int)this.r);
		this.width = bmp.getWidth() / bmpCols;
		this.height = bmp.getHeight() /  bmpRows;
	}
	
	public void draw(Canvas canvas){
		int srcX = currentFrame * width;
		int srcY = currentDir * height;
		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect((int)(x),(int)(y), 
				(int)(x + (2 * width)), (int)(y + (2 * height)));
				//the scalar coeffeiciet of width and height SCALE the sprite by that much
		canvas.drawBitmap(bmp, src, dst, null);
		
	}
	
	public void nearestNeighbor(Bitmap img, int w, int h){
		/*Setup pixel buffer*/
		int[] pixels = new int[img.getHeight()*img.getWidth()];
		img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
		
		int[] out = new int[w * h];
		
		float x_ratio = (float)img.getWidth() / (float)w;
	    float y_ratio = (float)img.getHeight() / (float)h;
	    float px, py;
	    float i, j;
	    for ( i =0 ;i<h;i++) {
	      for ( j = 0;j<w;j++) {
	        px = (float)Math.floor(j * x_ratio);
	        py = (float)Math.floor(i * y_ratio);
	        out[(int)(i*w)+(int)j] = pixels[(int)(Math.floor((py*img.getWidth())+px))] ;
	      }
	    }
	    img.setPixels(out, 0, width, 0, 0, width, height);
	    
	 }

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}
	
	
	
	
	
}
