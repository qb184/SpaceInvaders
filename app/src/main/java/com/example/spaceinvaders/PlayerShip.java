package com.example.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.spaceinvaders.GameView.screenRatioX;
import static com.example.spaceinvaders.GameView.screenRatioY;


public class PlayerShip {
    private Bitmap ship;
    public int x,y;
    int width, height;  //of the ship
    private int shipMoving;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    public final int STOP = 0;
    private GameView gameView;
    int toShoot = 0;

    public PlayerShip(GameView gameView, int screenX, int screenY,Resources res){
        this.gameView = gameView;
        //draw spaceship
        ship = BitmapFactory.decodeResource(res,R.drawable.ship);

        width = ship.getWidth();
        height = ship.getHeight();

        width/=2;
        height/=2;

        width *= screenRatioX;
        height *= screenRatioY;

        ship = Bitmap.createScaledBitmap(ship, width, height,false);
        x = screenX/2;
        y = (int) (screenY-60*screenRatioY);

    }

    public void setMovingDirection(int direction){
        shipMoving = direction;
    }

    public int getShipMoving(){
        return shipMoving;
    }

    public void update(int screenX){
        //update position of x
        if(shipMoving == LEFT){
            x -= 20*screenRatioX;
        }
        if (shipMoving == RIGHT){
            x += 20*screenRatioX;
        }

        //check if the ship reach 2 sides of screen
        if(x < 0)
            x = 0;
        if(x >= screenX - width)
            x = screenX - width;

    }

    public Bitmap getShip(){
        if(toShoot!=0){
            gameView.newBullet();
            toShoot--;
        }
        return ship;
    }

    public Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }

}