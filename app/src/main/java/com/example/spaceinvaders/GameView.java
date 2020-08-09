package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private Context context;
    private int screenX,screenY;
    private boolean isPlaying;
    private Paint paint;
    private int score=0;
    private Canvas canvas;

    public GameView(Context context, int x, int y){
        super(context);
        this.context = context;
        this.screenX = x;
        this.screenY = y;
    }

    @Override
    public void run() {
        while(isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update() {
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Score: "+score,20,60,paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep(){

    }

    public void resume(){
        isPlaying=true;
        thread = new Thread(this);
        thread.start();
    }
    //stop thread
    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
