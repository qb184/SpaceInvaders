package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;

import static com.example.spaceinvaders.GameView.screenRatioX;
import static com.example.spaceinvaders.GameView.screenRatioY;


public class PlayerShip {
    private Bitmap ship;
    private float speed;
    public float x,y;
    private Context context;
     int width, height;  //of the ship
    private RectF rectF;
    private int shipMoving;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int STOP = 0;

    public PlayerShip(Context context, int screenX, int screenY){
        this.context = context;
        //draw spaceship
        ship = BitmapFactory.decodeResource(context.getResources(),R.drawable.ship);

        width = ship.getWidth();
        height = ship.getHeight();

        width/=2;
        height/=2;

        width *= screenRatioX;
        height *= screenRatioY;

        ship = Bitmap.createScaledBitmap(ship,
                width,
                height,false);
        x=screenX/2;
        y=screenY-100*screenRatioY;

        speed = 350;
    }

    public void getShipMoving(int direction){
        shipMoving = direction;
    }

    public void update(){
        if(shipMoving == LEFT){
            x -= 5*screenRatioX;
        }
        if (shipMoving == RIGHT){
            x += 5*screenRatioX;
        }

    }

    public Bitmap getShip(){
        return ship;
    }

    public float getX() {
        return x;
    }

}
