package com.example.thelonely;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;

public class LoadView extends SurfaceView implements SurfaceHolder.Callback{
	
	private int width,height;
	Product product;
	Paint paint = new Paint();
	LoadThread loadthread;
	
	
	public LoadView (Context context){
		super(context);
		product = new Product(getResources());
		product.x = 400;
		product.y = 200;
	}
	
	public void onDraw(Canvas c){
		paint.setColor(color.holo_orange_dark);
		c.drawCircle(100, 100, 200, paint);
		product.draw(c);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		loadthread = new LoadThread(this);
		loadthread.setRunning(true);
		loadthread.run();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
        loadthread.setRunning(false);
        while (retry) {
            try {
                loadthread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
		
	}
	
}
