package com.example.thelonely;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.Window;

public class LoadActivity extends Activity {
	
	LoadView loadview;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		loadview = new LoadView(this);
		setContentView(loadview);
		
		//Steps needed to draw out our surface
		//SurfaceHolder surfaceholder = loadview.getHolder();
		//Canvas c = surfaceholder.lockCanvas();
		//loadview.onDraw(c);
		//surfaceholder.unlockCanvasAndPost(c);
		
	}

}
