package com.example.spaceinvaders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.spaceinvaders.GameView.screenRatioX;
import static com.example.spaceinvaders.GameView.screenRatioY;

public class Invaders {
    private int x, y, width, height;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private boolean isVisible;
    Bitmap bitmap;
    private int direction;

    public Invaders(Resources res, int row, int column, int screenX, int screenY) {
        width = screenX / 18;
        height = screenY / 18;
        direction = RIGHT;
        isVisible = true;

        int padding = screenX / 25;

        x = column * (width + padding);
        y = (int) (100*screenRatioY + row * (height + padding / 3));

        bitmap = BitmapFactory.decodeResource(res, R.drawable.alien2);

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    //move invaders to left or right
    public void update() {
        if (direction == LEFT)
            x -= 10 * screenRatioX;
        else if (direction == RIGHT)
            x += 10 * screenRatioX;
    }

    public void movingDown() {
        if (direction == LEFT)
            direction = RIGHT;
        else
            direction = LEFT;
        y += height;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setY(int a) {
        y = a;
    }

    public int getDirection() {
        return direction;
    }

    public Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

}
