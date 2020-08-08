package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    Context context;
    private Thread thread;
    boolean isPlaying, isGameOver = false;
    private PlayerShip player;
    private boolean paused = true;

    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder surfaceHolder;

    //size of screen in pixels
    private int screenX;
    private int screenY;

    private PlayerShip playerShip;
    private Bullet bullet;

    //invaders bullets
    private Bullet[] invaderBullets = new Bullet[200];

    //total invaders
    private Invaders[] invaders = new Invaders[60];
    int numsInvaders = 0;   //keep track of invaders

    //score
    int score = 0;

    public GameView(Context context, int x, int y){
        super(context);
        this.context = context;

        //initialize holder and paint object
        this.surfaceHolder = getHolder();
        this.paint = new Paint();

        this.screenX = x;
        this.screenY = y;

        getStarted();   //prepareLevel()

    }
    public void getStarted(){

    }

    @Override
    public void run() {
        while(isPlaying){
            long startFrameTime = System.currentTimeMillis();
            if(!paused){
                update();
            }
        }

    }
    public void update(){

    }
}
