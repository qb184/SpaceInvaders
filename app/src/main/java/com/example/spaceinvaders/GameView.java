package com.example.spaceinvaders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private Context context;
    private int screenX,screenY=0;
    private Paint paint;
    private int score=0;
    private Canvas canvas;
    private PlayerShip playerShip;
    private boolean pause = true;
    private List<Bullet> bullets;
    private List<Bullet> trash;
    private MainActivity activity;
    public static float screenRatioX, screenRatioY;
    private Invaders[]  invaders;
    private Handler handler;
    boolean changeDirection;
    int countInvader;
    private boolean gameOver;
    private boolean level2;

    public GameView(MainActivity activity, int x, int y){
        super(activity);
        this.activity = activity;
        this.screenX = x;
        this.screenY = y;
        paint = new Paint();
        countInvader = 0;
        gameOver=false;
        level2 = false;
        invaders = new Invaders[24];
        //scale screen
//        screenRatioX = 1440f/screenX;
//        screenRatioY = 2960f/screenY;

        screenRatioX = 1;
        screenRatioY = 1;
        playerShip = new PlayerShip(this.getResources(),screenX,screenY);
        bullets = new ArrayList<>();
        trash = new ArrayList<>();
        createInvaders(invaders);
    }

    @Override
    public void run() {
        while(!gameOver && !level2){
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        changeDirection = false; //set it here bc whenever update() is called again, , each invader will be checked to move horizontally again
        for (Invaders invader : invaders) {
            if (invader.getVisibility()) {
                invader.update();
                if (invader.getX() > screenX - invader.getWidth() || invader.getX() < 0) {
                    changeDirection = true; //when any invader reaches either side of screen
                }
            }
        }
        if (changeDirection) {
            for (Invaders invader : invaders) {
                invader.movingDown();
                if (invader.getVisibility() && invader.getY() >= playerShip.y-playerShip.height) {
                    gameOver = true;
                }
            }
        }

        playerShip.update(screenX);

        for(Bullet bullet:bullets){
            if(bullet.y < 0)
                trash.add(bullet);
            bullet.y -= 70*screenRatioY;    //move bullet upward
            for (Invaders invader:invaders){
                if (invader.getVisibility() && Rect.intersects(invader.getCollisionShape(),bullet.getCollisionShape())){
                    score++;
                    invader.setY(screenY+300);
                    bullet.setY(-300);
                    trash.add(bullet);
                    invader.setInvisible();
                    if(score==invaders.length)  {
                        //gameOver=true; ///will remove later
                        level2 = true;
                    }
                }
            }
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

            if (gameOver){
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                getContext().startActivity(gameOverIntent);
            } else if(score == invaders.length) {
                Intent levelIntent = new Intent(getContext(), NextLevelActivity.class);
                getContext().startActivity(levelIntent);
            } else {
                canvas.drawText("Score: " + score, 30 * screenRatioX, 80 * screenRatioY, paint);

                //draw ship
                canvas.drawBitmap(playerShip.getShip(), playerShip.x, playerShip.y, paint);
                if(playerShip.toShoot!=0){
                    newBullet();
                    playerShip.toShoot--;
                }

                //draw bullet
                for (Bullet bullet : bullets) {
                    canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
                }
                //draw invaders
                for (Invaders invader : invaders) {
                    canvas.drawBitmap(invader.bitmap, invader.getX(), invader.getY(), paint);
                }
            }

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
        pause=false;
        thread = new Thread(this,"one");
        thread.start();
    }
    //stop thread
    public void pause(){
        try {
            pause = true;
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
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
        System.out.println("level1");
    }

}
