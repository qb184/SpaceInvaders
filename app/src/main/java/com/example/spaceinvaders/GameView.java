package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

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
    private Invaders[]  invaders = new Invaders[24];

    public GameView(MainActivity activity, int x, int y){
        super(activity);
        this.activity = activity;
        this.screenX = x;
        this.screenY = y;
        paint = new Paint();

        //scale screen
        screenRatioX = 1440f/screenX;
        screenRatioY = 2960f/screenY;

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
        createInvaders(invaders);
//        for(Invaders invader:invaders){
//            invader.update();
//        }
        playerShip.update(screenX);

        List<Bullet> trash = new ArrayList<>();
        for(Bullet bullet:bullets){
            if(bullet.y < 0)
                trash.add(bullet);
            bullet.y -= 40*screenRatioY;    //move bullet upward
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
            paint.setTextSize(80*screenRatioX);
            canvas.drawText("Score: "+score,30*screenRatioX,80*screenRatioY,paint);

            //draw ship
            canvas.drawBitmap(playerShip.getShip(),playerShip.x,playerShip.y,paint);

            //draw bullet
            for(Bullet bullet: bullets){
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

            //draw invaders
//            for(Invaders invader: invaders) {
//                canvas.drawBitmap(invader.bitmap, invader.getX(), invader.getY(), paint);
//            }
            
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void sleep(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            case MotionEvent.ACTION_MOVE:
                pause = false;
                if (event.getX() > playerShip.x+playerShip.width) {
                    playerShip.setMovingDirection(playerShip.RIGHT);
                } else if (event.getX() < playerShip.x) {
                    playerShip.setMovingDirection(playerShip.LEFT);
                } else if(event.getX()== playerShip.x+playerShip.width/2)
                    pause=true;
                playerShip.toShoot++;
                break;

            //no movement
            case MotionEvent.ACTION_UP:
                pause = true;
                playerShip.setMovingDirection(playerShip.STOP);

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

    public void createInvaders(Invaders[] invaders){
        for(int i =0; i< 4;i++){
            for(int j=0;j<6;j++){
                invaders[6*i+j] = new Invaders(getResources(),i,j,screenX,screenY);
            }
        }
    }

}
