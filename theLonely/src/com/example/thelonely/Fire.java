package com.example.thelonely;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fire extends Product {

	public Fire(Resources r){
		super(r);
		this.x = 450;
		this.y = 100;
		this.bmpRows = 1;
		this.bmpCols = 4;
		/*Always Add the opt so we get a mutable bitmap that wwe can scale*/
		BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        opt.inScaled = false;
		bmp = BitmapFactory.decodeResource(r, R.drawable.fire, opt);
	}
	
	//Update the animation of the creature
	public void update(){
		if(count > 30){
			currentFrame = (++currentFrame % bmpCols); //iterate to next frame
			count = 0;
		}else{
			count++;
		}
	}
	
	public void tickTock(){
		if(count > 30){
			currentFrame = (++currentFrame % bmpCols); //iterate to next frame
			count = 0;
		}else{
			count++;
		}
	}
	
}
