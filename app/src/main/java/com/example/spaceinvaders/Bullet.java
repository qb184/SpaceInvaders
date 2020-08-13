package com.example.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.spaceinvaders.GameView.screenRatioX;
import static com.example.spaceinvaders.GameView.screenRatioY;

public class Bullet {
    private int x, y;
    Bitmap bullet;

    //width and height of bullet
     int width;
     int height;

    public Bullet(Resources res,PlayerShip playerShip){
        bullet = BitmapFactory.decodeResource(res,R.drawable.bullet);
        width = bullet.getWidth();
        height = bullet.getHeight();

        width*=2;
        height*=2;

        width *= screenRatioX;
        height *= screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);

//        x = playerShip.x + playerShip.width/2 - width/2;
//        y = playerShip.y-height/2;

    }

    //get rect shape for collision
    public Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
    public void setX(int a){ x=a;}
    public void setY(int a){
        y=a;
    }
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}
