package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private Context context;
    private int screenX,screenY=0;
    private boolean isPlaying;
    private Paint paint;
    private int score=0;
    private Canvas canvas;
    private PlayerShip playerShip;
    private boolean pause = true;
    private List<Bullet> bullets;
    private MainActivity activity;
    public static float screenRatioX, screenRatioY;
    Rect rect;
    public GameView(MainActivity activity, int x, int y){
        super(activity);
        this.activity = activity;
        this.screenX = x;
        this.screenY = y;
        paint = new Paint();

        //scale screen
        screenRatioX = 1080f/screenX;
        screenRatioY = 2160f/screenY;

        playerShip = new PlayerShip(this,screenX,screenY, getResources());
        bullets = new ArrayList<>();
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
        playerShip.update(screenX);

        List<Bullet> trash = new ArrayList<>();
        for(Bullet bullet:bullets){
            if(bullet.y < 0)
                trash.add(bullet);
            bullet.y -= 40*screenRatioY;
        }
        for(Bullet bullet:trash){
            bullets.remove(bullet);
        }

    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Score: "+score,20,60,paint);

            canvas.drawBitmap(playerShip.getShip(),playerShip.x,playerShip.y,paint);

            for(Bullet bullet: bullets){
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

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
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pause = false;
                    if (event.getX() > playerShip.x+playerShip.width) {
                        playerShip.getMovingState(playerShip.RIGHT);
                    } else if (event.getX() < playerShip.x) {
                        playerShip.getMovingState(playerShip.LEFT);
                    } else if(event.getX()==playerShip.x+playerShip.width/2)
                        pause=true;
                    playerShip.toShoot++;
                 break;

                //no movement
            case MotionEvent.ACTION_UP:
                pause = true;
                playerShip.getMovingState(playerShip.STOP);

                break;
        }
        return true;
    }

    public void newBullet(){
        Bullet bullet = new Bullet(getResources(),playerShip);

        bullet.x = playerShip.x + playerShip.width/2 - bullet.width/2;
        bullet.y = playerShip.y - bullet.height/2;
        bullets.add(bullet);
}


}
