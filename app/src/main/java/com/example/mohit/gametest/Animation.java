package com.example.mohit.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by mohit on 9/6/16.
 */
public class Animation {
    private Bitmap[] frames;
    private int frameCount;
    private long startTime;
    private boolean playedOnce;
    private int animDelay;

    Animation() {
        frameCount = 0;
        playedOnce = false;
    }

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
    }


    public void delay(int x) {
        startTime = System.currentTimeMillis();
        long waittime = x;

        long currenttime = System.currentTimeMillis();

        while (currenttime - startTime <= waittime){
            currenttime = System.currentTimeMillis();
        }
    }


    public void update(){
        delay(animDelay);
        frameCount++;
        if(frameCount >= frames.length){
            playedOnce = true;
            frameCount = 0;
        }
    }

    public Bitmap getImage(){
        return this.frames[frameCount];
    }

    public boolean getPlayedOnce(){
        return playedOnce;
    }

    public void setPlayedOnce(boolean playedOnce) {
        this.playedOnce = playedOnce;
    }

    public void setAnimDelay(int animDelay) {
        this.animDelay = animDelay;
    }

    public Bitmap getImageByFrame(int i) {
        return this.frames[i];
    }
}
