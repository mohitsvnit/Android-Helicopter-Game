package com.example.mohit.gametest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by mohit on 9/6/16.
 */
public class Player extends GameObjects {
    private Bitmap spritesheet;
    private int score;
    private long starttime;
    private long elapsedTime;
    private boolean dirUp;
    private int dy;
    private float dya;
    private boolean playing;
    private Animation animation = new Animation();

    Player(Bitmap res,int width,int height, int numberFrame) {
        this.width = width;
        this.height = height;

        playing = false;
        x = GamePanel.screenW/5;
        y = GamePanel.screenH/2;
        dirUp = false;

        Bitmap[] images = new Bitmap[numberFrame];

        for(int i = 0; i < images.length; i++){
            images[i] = Bitmap.createBitmap(res,i*width,0,width,height);
        }

        animation.setFrames(images);
        animation.setAnimDelay(10);
        starttime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public void update(){

        elapsedTime = System.currentTimeMillis();
        if(elapsedTime - starttime >= 100){
            score++;
            starttime = System.currentTimeMillis();
        }

        animation.update();
        if(dirUp){
            dy = (int)(dya -= 1.2);
        }
        else{
            dy = (int)(dya += 0.2);
        }

        if(dy > 5){dy = 5;}
        if(dy < -5){dy = -5;}

        y+=dy*2;
        dy = 0;
    }

    public void setDirUp(boolean dirUp) {
        this.dirUp = dirUp;
    }


    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean getPlaying(){
        return this.playing;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDya(float dya) {
        this.dya = dya;
    }
}
