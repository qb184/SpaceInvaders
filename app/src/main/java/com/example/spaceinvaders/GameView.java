package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private int screenX, screenY = 0;
    private boolean isPlaying;
    private Paint paint;
    private int score = 0;
    private Canvas canvas;
    private PlayerShip playerShip;
    private boolean pause = true;
    private List<Bullet> bullets;
    private MainActivity activity;
    public static float screenRatioX, screenRatioY;
    Invaders[] invaders;
    private boolean changeDirection;
    private boolean gameOver;

    public GameView(MainActivity activity, int x, int y) {
        super(activity);
        this.activity = activity;
        this.screenX = x;
        this.screenY = y;
        paint = new Paint();
        invaders = new Invaders[35];
        //scale screen
        screenRatioX = 1080f / screenX;
        screenRatioY = 2160f / screenY;

        playerShip = new PlayerShip(this, screenX, screenY, getResources());
        bullets = new ArrayList<>();
    }


    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        //update playerShip
        playerShip.update(screenX);
        getInvaders(invaders);
        changeDirection = false;

        //update invaders
        for (Invaders invader : invaders) {
            if (invader.getVisibility()) {
                invader.update();
                if (invader.getX() > screenX - invader.getWidth() || invader.getX() < 0)
                    changeDirection = true;
            }
        }
        if (changeDirection) {
            for (Invaders invader : invaders) {
                invader.movingDown();
                if (invader.getY() >= screenY - invader.getHeight())
                    gameOver = true;
            }
        }

        //update bullet
        List<Bullet> trash = new ArrayList<>();
        for (Bullet bullet : bullets) {
//            if(bullet.getY() < 0) { //bullet is out of screen
//                trash.add(bullet);
//            }
            bullet.setY((int) (bullet.getY() - 100 * screenRatioY));  //move up

            for (Invaders invader : invaders) {
                if (invader.getVisibility() && Rect.intersects(invader.getCollisionShape(), bullet.getCollisionShape())) {
                    score++;
                    invader.setY(screenY + 500);
                    bullet.setY(-500);
                    trash.add(bullet);  //need this??
                    invader.setInvisible();
                }
            }



        }
        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            canvas.drawText("Score: " + score, 20, 60, paint);

            canvas.drawBitmap(playerShip.getShip(), playerShip.getX(), playerShip.getY(), paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.bullet, bullet.getX(), bullet.getY(), paint);
            }

            for (Invaders invader : invaders) {
                if (invader.getVisibility()) {
                    canvas.drawBitmap(invader.bitmap, invader.getX(), invader.getY(), paint);
                }
            }
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void sleep() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    //stop thread
    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pause = false;

                if (event.getX() > playerShip.getX() + playerShip.getWidth()) {
                    playerShip.getMovingState(playerShip.RIGHT);
                } else if (event.getX() < playerShip.getX()) {
                    playerShip.getMovingState(playerShip.LEFT);
                } else if (event.getX() == playerShip.getX() + playerShip.getWidth() / 2)
                    pause = true;
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

    //create new bullet, update its x and y
    public void newBullet() {
        Bullet bullet = new Bullet(getResources(), playerShip);
        bullet.setX(playerShip.getX() + playerShip.getWidth() / 2 - bullet.width / 2);
        bullet.setY(playerShip.getY() - bullet.height / 2);
        bullets.add(bullet);
    }

    //create invaders array
    public void getInvaders(Invaders[] invaders) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                invaders[i * 7 + j] = new Invaders(getResources(), i, j, screenX, screenY);
            }
        }
    }
}
