package com.example.thelonely;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread {
    private long time;
    private final int fps = 20;
    private boolean toRun = false;
    private LonelyView lonelyView;
    private SurfaceHolder surfaceHolder;
    
    public UpdateThread(LonelyView rLonelyView) {
        lonelyView = rLonelyView;
        surfaceHolder = lonelyView.getHolder();
    }
    public void setRunning(boolean run) {
        toRun = run;
    }
    @Override
    public void run() {
        Canvas c;
        while (toRun) {
            long cTime = System.currentTimeMillis();
            if ((cTime - time) <= (1000 / fps)) {
                c = null;
                try {
                    c = surfaceHolder.lockCanvas();
                    lonelyView.updatePhysics();
                    lonelyView.onDraw(c);
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
            time = cTime;
        }
    }
}