package com.example.mohit.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

/**
 * Created by mohit on 9/6/16.
 */
public class Explosion {
    private int x;
    private int y;
    private Animation animation;
    private int scale;
    private int width;
    private int height;
    private boolean explosioncomplete = false;
    Explosion(Bitmap res, int x, int y,int w, int h, int numberFrames){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        animation = new Animation();
        Bitmap[] images = new Bitmap[numberFrames];
        int row = -1;
        for(int i = 0; i < numberFrames; i++) {
            if(i%5 == 0){row++;}
            images[i] = Bitmap.createBitmap(res,(i-5*row)*width,row*width,width,height);
        }

        animation.setFrames(images);
        animation.setAnimDelay(5);
        animation.setPlayedOnce(false);
    }

    public void update() {
        animation.update();
    }

    public void draw(Canvas canvas){
        if(!animation.getPlayedOnce()) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }
        else{
            explosioncomplete = true;
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getExplosionStatus(){
        return this.explosioncomplete;
    }

    public void setExplosioncomplete(boolean explosioncomplete) {
        this.explosioncomplete = explosioncomplete;
        animation.setPlayedOnce(false);
    }
}
