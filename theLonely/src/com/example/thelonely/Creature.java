package com.example.thelonely;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Creature {
	/*take care of animation*/
	private int bmpRows = 1;
	private int bmpCols = 2;
	private int currentFrame = 1; //collumn in sprite sheet
	private int currentDir = 0; //row in sprite sheet
	

	private int count = 0; //for animation speed (very hacky)
	/*general fields*/
	private int width; //width of individual sprite (frame)
	private int height; //height of individual sprite (frame)
	private float r = 32; //half of what is actually drawn on screen
	public float x = 100;
	public float y = 100;
	public float oldy = 0;
	public float vx = 4;
	public float vy = 4;
	private float gravity = (float)0.2;
	private Bitmap bmp;
	private int prim, seco;
	private int oldp, olds; //keeps track of the creature's color's
	
	public Creature(Resources r){
		/*Always Add the opt so we get a mutable bitmap that wwe can scale*/
		BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        opt.inScaled = false;
		bmp = BitmapFactory.decodeResource(r, R.drawable.creature, opt);
		this.oldp = Color.BLACK;
		this.olds = Color.WHITE;
		this.width = bmp.getWidth() / bmpCols;
		this.height = bmp.getHeight() / bmpRows;
		this.vx = -5 + (int)(Math.random()*10); //random number between -5 and 5
		this.vy = -5 + (int)(Math.random()*10); //random number between -5 and 5
		this.x = 100 + (int)(Math.random()*100);
		this.y = 100 + (int)(Math.random()*100);
		randomizeBmp();
	}

	public float getxPos() {
		return x;
	}

	public void setxPos(float xPos) {
		this.x = xPos;
	}

	public float getyPos() {
		return y;
	}

	public void setyPos(float yPos) {
		this.y = yPos;
	}

	
	//todo: maybe add a class containing this function
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
		int[] pixels = new int[this.bmp.getHeight()*this.bmp.getWidth()];
		this.bmp.getPixels(pixels, 0, this.bmp.getWidth(), 0, 0, this.bmp.getWidth(), this.bmp.getHeight());
			
		/*Go through each pixel and change accordingly*/
		for (int i=0; i<pixels.length; i++){
			if(pixels[i] == this.oldp){
				pixels[i] = prim;
			} else if (pixels[i] == this.olds){
				pixels[i] = seco;
			}
		    
		}
		this.bmp.setPixels(pixels, 0, this.bmp.getWidth(), 0, 0, this.bmp.getWidth(), 
				this.bmp.getHeight());
		this.olds = seco;
		this.oldp = prim;
	
	}
	
//	public void update(){
//		if(count > 30){
//		currentFrame = (++currentFrame % bmpCols); //iterate to next frame
//		count = 0;
//		}else{
//			count++;
//		}
//	}
	
	public void move(int width, int height){
		//effects of gravitah!
	    vy += gravity;
	    if(vy > 10)vy = 10;
	    if(vy < -10)vy = -10;
	    
	    //actually move the position;
	    x += vx;
	    oldy = y;
	    y += vy;
	    
	    if((x + r) > width){
	        x -= 2*((x + r) - width); //correct for (perfect) bouncing
	        vx = -vx;
	      }else if(x < 0){
	        x += 2*(Math.abs(x));
	        vx = -vx;
	      }
	      
	      if((y + r) > height){
	        y -= 2*((y + r) - height); //correct for (perfect) bouncing
	        //vy = -vy;
	        vy = -10;
	      }else if(y < 0){
	        y += 2*(Math.abs(y));
	        vy = -vy;
	      }
	      
//	      if(((y + r) > height) | y - r < 0){
//	        vy = -vy;
//	        
//	      }
//	      
	      //determine correct animation
	      if(y - oldy > 0){
	    	  currentFrame = 1;
	      }else{
	    	  currentFrame = 0;
	      }
	       	
		
	}
	
	public void jump(){
		vy = -10 + (int)Math.random()*10;
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

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
	
	public int getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(int currentDir) {
		this.currentDir = currentDir;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}
	
	
	

}
