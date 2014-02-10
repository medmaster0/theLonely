package com.example.thelonely;

import java.util.Random;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Background {
	
	private Bitmap back;
	public Bitmap fore;
	public int backX;
	public int width; //width of the display
	public int height; //height of the display
	public int prim, seco;
	public int oldp, olds;
	
	/**
	 * 
	 * @param r = resources from view
	 * @param width = width of screen
	 * @param height = height of screen
	 */
	public Background(Resources r, int width, int height){
		
		//So we can make a mutable (changeable) bitmap
		BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        opt.inScaled = false;
		
		back = BitmapFactory.decodeResource(r, R.drawable.citypixelcolor, opt);
		fore = BitmapFactory.decodeResource(r, R.drawable.tree, opt);
        //back = Bitmap.createScaledBitmap(back, width, height, false);
		nearestNeighbor(back, width, height);
		nearestNeighbor(fore, width, height);
		this.width = width;
		this.height = height;
		this.oldp = Color.BLACK;
		this.olds = Color.WHITE;
		
	}
	
	public void drawBackground(Canvas canvas){
		//We're going to draw two of the same bitmap side by side
		Rect src = new Rect(0, 0, back.getWidth(), back.getHeight());
		Rect dst = new Rect(backX, 0, backX + width, height);
		Rect dst2 = new Rect(backX + width, 0 , backX + (2 * width), height);
		canvas.drawBitmap(back, src, dst, null);
		canvas.drawBitmap(back, src, dst2, null);
		//Update the backX values and check when you have to reset to create scrolling motion
		backX--;
		if(backX == -width){
			randomizeBmp();
			backX = 0; //reset
		}
		
		//Draw the foreground
		Rect fsrc = new Rect(0,0, fore.getWidth(), fore.getHeight());
		Rect fdst = new Rect(0,0, width, height);
		canvas.drawBitmap(fore, fsrc, fdst, null);
		
		
		
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
	
public void randomizeBmp(){
		
		/*Make some random colors*/
		Random rnd = new Random();
		prim = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
		seco = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
		
		/*We don't want the skin and dooRag to be the same*/
		while(prim == seco) {
			prim = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
		}
		
		/*Setup pixel buffer*/
		int[] pixels = new int[this.back.getHeight()*this.back.getWidth()];
		this.back.getPixels(pixels, 0, this.back.getWidth(), 0, 0, this.back.getWidth(), this.back.getHeight());
			
		/*Go through each pixel and change accordingly*/
		for (int i=0; i<pixels.length; i++){
			if(pixels[i] == this.oldp){
				pixels[i] = prim;
			} else if (pixels[i] == this.olds){
				pixels[i] = seco;
			}
		    
		}
		this.back.setPixels(pixels, 0, this.back.getWidth(), 0, 0, this.back.getWidth(), 
				this.back.getHeight());
		this.olds = seco;
		this.oldp = prim;
	
	}

		
	
	
	

}
