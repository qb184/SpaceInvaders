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

        width*=2;
        height*=2;

        width *= screenRatioX;
        height *= screenRatioY;

        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);

        rect = new Rect();

//        x = playerShip.x + playerShip.width/2 - width/2;
//        y = playerShip.y-height/2;

    }

//    public void getRect(){
//        rect.left = x;
//        rect.right = x + width;
//        rect.top = y;
//        rect.bottom = y + height;
//    }


}
