package com.example.spaceinvaders;

public class PlayerShip extends Character{
    boolean moveLeft;
    boolean moveRight;

    public PlayerShip(int x, int y, int speed){
        super(x,y,speed);
        moveLeft=false;
        moveRight=false;
    }
}
