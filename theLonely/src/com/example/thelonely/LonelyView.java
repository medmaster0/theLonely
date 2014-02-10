package com.example.thelonely;

import java.util.ArrayList;

import com.example.thelonely.UpdateThread;

import android.R.color;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LonelyView extends SurfaceView implements SurfaceHolder.Callback, OnGestureListener {	
	private float xPos;
    private float yPos;
    private int xVel;
    private int yVel;
    private int width;
    private int height;
    private int circleRadius;
    private Paint circlePaint;
    UpdateThread updateThread;
    
    /*Used for detecting input*/
    GestureDetector gestureScanner;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    /*Bully system*/
    float followRes = (float)200.0;
    
    /*Take care of score*/
    int proScore = 0; //amount of product taken to crate
    int eggScore = 0; //amount of egg taken to crate
    Paint scorePaint;
    
    Background back;
    
    Product product;
    Egg egg;
    //ArrayList<Product> products; /*Contains all the active products that can be picked up on list*/
    
    Crate crate;
    Level level;
    Creature llc; 
    FlyCre flycre;
    ArrayList<Creature> creatures;
    Creature builder; //used to populate the creature list
    
    int numCreatures = 3;
    
    @SuppressWarnings("deprecation")
	public LonelyView(Context context) {
        super(context);
        getHolder().addCallback(this);
        circleRadius = 10;
        circlePaint = new Paint();
        circlePaint.setColor(Color.WHITE);
        
        scorePaint = new Paint();
        scorePaint.setColor(Color.GREEN);
        scorePaint.setTextSize(14);
        
        xVel = 2;
        yVel = 2; 
        gestureScanner = new GestureDetector(this);
        
    }
    @Override
    protected void onDraw(Canvas canvas) {
    	//canvas.drawBitmap(back, 0, 0, null);
    	back.drawBackground(canvas);
    	level.draw(canvas);
        canvas.drawCircle(xPos, yPos, circleRadius, circlePaint);
        scorePaint.setColor(Color.GREEN);
        canvas.drawText(String.valueOf(proScore), 20, 20, scorePaint);
        scorePaint.setColor(Color.argb(255, 255, 175, 00));
        canvas.drawText(String.valueOf(eggScore), width - 20, 20, scorePaint);
        
        
        llc.draw(canvas);
        for(Creature cre : creatures){
        	cre.draw(canvas);
        }
        flycre.draw(canvas);
        
        product.draw(canvas);
        if(egg != null)egg.draw(canvas);
        crate.draw(canvas);
        
       
    }
    
    public void updatePhysics() {
        xPos += xVel;
        yPos += yVel;
        
        //Circle moving
        if (yPos - circleRadius < 0 || yPos + circleRadius > height) {
            if (yPos - circleRadius < 0) {
                yPos = circleRadius;
            }else{
                yPos = height - circleRadius;
            }
            yVel *= -1;
        }
        if (xPos - circleRadius < 0 || xPos + circleRadius > width) {
            if (xPos - circleRadius < 0) {
                xPos = circleRadius;
            } else {
                xPos = width - circleRadius;
            }
            xVel *= -1;
        }
        
        //*move creatures*/
        llc.move(width,height);
        for(Creature cre: creatures){
        	cre.move(width, height);
        }
        
        //move flycre
        flycre.move(width, height);
        flycre.update();
        
        //check to see if creatures get product
        if(Collision.creatureProductCollision(this.llc, this.product)){
        	product.setX(llc.getxPos() + 5);
        	product.setY(llc.getyPos() + 14);
        }
        
        //check to see if creatures get egg
        if(egg != null){
	        if(Collision.creatureEggCollision(this.llc, this.egg)){
	        	egg.setX(llc.getxPos() + 5);
	        	egg.setY(llc.getyPos() + 14);
	        }
        }
        
        //check to see if product is taken to depot crate
        if(Collision.productCrateCollision(product, crate)){
        	proScore++;  //update score
        	product = new Product(getResources()); //create a new product 
        }
        
      //check to see if egg is taken to depot crate
        if(egg != null){
	        if(Collision.eggCrateCollision(egg, crate)){
	        	eggScore++;  //update score
	        	egg = null; //create a new product
	        }
        }
        
        //check to see if FLYCRE reaches PRODUCT
        if(Collision.flyCreProductCollision(flycre, product)){
        	egg = new Egg(getResources(), level.platforms[1].y + flycre.r);
        	product = new Product(getResources());
        	flycre = new FlyCre(getResources());
        }
        
        //check to see if EGG reaches FIRE
        if(egg !=  null){
	        if(Collision.eggFireCollision(egg, level.fire)){
	        	egg.isHatching = true;
	        	//Start the hatch sequence
	        	new Thread(new Runnable() {
			        public void run() {
			        	egg.hatch();
			        	builder = new Creature(getResources());
			        	builder.x = egg.x; //new creature takes place of egg
			        	builder.y = egg.y; //
			        	builder.vx = 0;
			        	creatures.add(builder); //create new creature
			        	//Half-Second Timer
			        	long start = System.currentTimeMillis();
						long now;
						while(true){
							xPos += ((egg.x - xPos) / followRes);
							yPos += ((egg.y - yPos) / followRes);
							//xPos = builder.x;
							//yPos = builder.y;
							circleRadius = 20;
							circlePaint.setColor( Color.rgb((200+(int)(Math.random()*55)), (150+(int)(Math.random()*100)), 0));
							now = System.currentTimeMillis();
							if((now - start) > 1500){
								break;
							}
						}
						circleRadius = 10;
						builder.vx = -5 + (int)(Math.random()*10); //random number between -5 and 5
			        }
			    }).start();
	        	//End of hatch sequence
	        }
        }
        //check to see if product is taken to egg
//        if(Collision.eggProductCollision(egg, product)){
//        	product = new Product(getResources()); //new product is formed
//        	
//        }
        
        //check to see if creature is on platform
        for(Platform platform: level.platforms){
        	if(Collision.creaturePlatformCollision(llc, platform)){
        		//We still need to check which direction creature is moving
        		//if going down (lands on top of platform)
        		if((llc.y - llc.oldy) > 0 ){
        			llc.vy = -10;
        		//if going up (hits under platform)	
        		}else{
        			llc.vy = -llc.vy;
        		}
        	}
        }
        //do the same for all the rest
        for(Creature cre: creatures){
        for(Platform platform: level.platforms){
        	if(Collision.creaturePlatformCollision(cre, platform)){
        		//We still need to check which direction creature is moving
        		if((cre.y - cre.oldy) > 0 ){
        			cre.vy = -10;
        		}else{
        			cre.vy = -cre.vy;
        		}
        	}
        }
        }
        
        //Move egg and see if it's landed
        if(egg!= null){
        	egg.eggDrop();
	        for(Platform plat: level.platforms){
	        	if(Collision.eggPlatformCollision(egg, plat)){
	        		//stop moving
	        		egg.vy= 0;
	        		egg.gravity = 0;
	        		egg.y = plat.y - 2*egg.r;
	        	}
	        }
        }
    
    }
    
    
    
    
    public void surfaceCreated(SurfaceHolder holder) {
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();
        back = new Background(getResources(),width,height);
        
        product = new Product(getResources());
        /*Egg is a special version of more general product*/
        /*BUt they're also two different things*/
        //egg = new Egg(getResources());
        //products.add(product);
        //products.add(egg);
        
        //create all the creatures
        creatures = new ArrayList<Creature>();
        for(int i = 0; i < numCreatures; i++){
        	builder = new Creature(getResources());
        	creatures.add(builder);
        }
        
        llc = new Creature(getResources());
        flycre = new FlyCre(getResources());
        crate = new Crate(getResources());
        level = new Level(getResources(),width,height);
        xPos = width / 2;
        yPos = circleRadius;
        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
        
        
        //Odds and ends
        llc.setyPos((int)((float) height / 2.0));
        

                 
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        updateThread.setRunning(false);
        while (retry) {
            try {
                updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
    	
    	/*The Bully system :))))*/
		//llc.setxPos(llc.getxPos() + (e.getRawX() - llc.getxPos())/followRes);
		//llc.setyPos(llc.getyPos() + (e.getRawY() - llc.getyPos())/followRes);
    	if(e.getRawX() > llc.x){
    		llc.vx = Math.abs(llc.vx);
    	}else{
    		llc.vx = -Math.abs(llc.vx);
    	}
    	
    	
		return gestureScanner.onTouchEvent(e);
    }
 
	@Override
	public boolean onDown(MotionEvent e) {
		
		return true;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && 
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//From Right to Left
			flycre.randomizeBmp();
			llc.randomizeBmp();
			llc.vx = -5 + (int)(Math.random()*10); //random number between -5 and 5
			llc.vy = -5 + (int)(Math.random()*10); //random number between -5 and 5
			product.randomizeBmp();
			if(egg != null)egg.randomizeBmp();
			return true;
		}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			//From Left to Right
			return true;
		}
  
		if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && 
               Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			//From Bottom to Top
			return true;
		}  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && 
               Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
			//From Top to Bottom
			return true;
		}
		return false;
		
	}
	@Override
	public void onLongPress(MotionEvent e) {
		
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	    
}


