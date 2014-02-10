package com.example.thelonely;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends Activity {
	
	LonelyView lonelyview;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lonelyview = new LonelyView(this);
        setContentView(lonelyview);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.main, menu);
    	return true;    	
    }
    
    public void onOptionLoad(MenuItem i){
    	boolean retry = true;
        lonelyview.updateThread.setRunning(false);
        while (retry) {
            try {
                lonelyview.updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    	startActivity(new Intent(this, LoadActivity.class));
    }
    
    
}

