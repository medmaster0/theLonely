package com.example.thelonely;

import android.content.res.Resources;
import android.graphics.Canvas;

public class Level {

	public Platform[] platforms;
	public int numPlats = 5;
	public int width, height; //width and height of the screen
	public Fire fire;
	
	/**
	 * 
	 * @param width of screen
	 * @param height of screen
	 */
	public Level(Resources r, int width, int height){
		
		this.width = width;
		this.height = height;
		int y;
		fire = new Fire(r);
		
		/*Generate Platforms*/
		platforms = new Platform[numPlats];
		for(int i = 0; i < numPlats; i++ ){
			//calculate initial y pos of platforms*/
			y = (i)*(int)((float)height /(float)numPlats ); 
			//TOP OF SCREEN
			//platform[0]:
			//platform[1]:XXflyguy
			//platform[2]:XXfire
			//platform[3]:XXegg
			//platform[4]:XXberry
			//floor:crate
			//BOTTOM
			platforms[i] = new Platform(r, 0, y);
			/*Skew the platforms*/
			if(i%2 == 0) //if is even
			{
				platforms[i].x = (width - platforms[i].length); //move it all the way to the right
			}
		}
		fire.x = (float) ((3.0/4.0)*width);
		//to place object (fire) on platform #(2)
		//see fire.draw method
		fire.y = platforms[2].y - (float)(2*(fire.r+1));
		
	}
	
	public void draw(Canvas canvas){
		for(Platform plat: platforms){
			plat.drawPlatform(canvas);
			plat.tickTock();
		}
		fire.draw(canvas);
		fire.tickTock();
		
	}
	
}
