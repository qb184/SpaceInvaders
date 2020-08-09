package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private Context context;
    private int screenX,screenY;
    private boolean isPlaying;
    private Paint paint;
    private int score=0;
    private Canvas canvas;
    private PlayerShip playerShip;
    private boolean pause = true;

    public static float screenRatioX, screenRatioY;

    public GameView(Context context, int x, int y){
        super(context);
        this.context = context;
        this.screenX = x;
        this.screenY = y;
        paint = new Paint();

        //scale screen
        screenRatioX = 1080f/screenX;
        screenRatioY = 2160f/screenY;

        playerShip = new PlayerShip(context,screenX,screenY);

        prepareGame();
    }

    public void prepareGame(){
        playerShip = new PlayerShip(context,screenX,screenY);
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
        playerShip.update();
        if(playerShip.x < 0)
            playerShip.x = 0;
        if(playerShip.x >= screenX - playerShip.width )
            playerShip.x = screenX - playerShip.width;

    }


    private void draw(){
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Score: "+score,20,60,paint);

            canvas.drawBitmap(playerShip.getShip(),playerShip.x,playerShip.y,paint);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pause = false;
                    if (event.getX() > screenX / 2) {
                        playerShip.getShipMoving(playerShip.RIGHT);
                    } else if (event.getX() < screenX / 2) {
                        playerShip.getShipMoving(playerShip.LEFT);
                    }

                break;

                //no movement
            case MotionEvent.ACTION_UP:
                pause = true;
                    playerShip.getShipMoving(playerShip.STOP);

                break;
        }
        return true;
    }
}
