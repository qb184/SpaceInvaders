package com.example.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.spaceinvaders.GameView.screenRatioX;
import static com.example.spaceinvaders.GameView.screenRatioY;

public class Bullet {
    int x, y;
    Bitmap bullet;
    //width and height of bullet
    int width;
    int height;
    Rect rect;

    public Bullet(Resources res,PlayerShip playerShip){
        bullet = BitmapFactory.decodeResource(res,R.drawable.bullet);
        width = bullet.getWidth();
        height = bullet.getHeight();

        width *=2;
        height *=2;

        width *= screenRatioX;
        height *= screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);

        rect = new Rect();

    }
    public Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    public void setY(int newY){
        y = newY;
    }

}