package com.example.mohit.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.print.PrintAttributes;

import java.util.Random;

/**
 * Created by mohit on 9/6/16.
 */
public class Missile extends GameObjects {

    private Animation animation;
    private int speed;
    private Random random;

    Missile(Bitmap res, int x, int y, int w, int h,int numberFrames) {

        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        animation = new Animation();
        Bitmap[] images = new Bitmap[numberFrames];
        for(int i = 0; i < numberFrames; i++) {
            images[i] = Bitmap.createBitmap(res,0,i*height,width,height);
        }
        animation.setFrames(images);
        animation.setAnimDelay(5);

        random = new Random();
        speed = 10 + (int)((random.nextDouble()*100)%20) + GamePanel.score%20;
       // System.out.println(random.nextDouble());
        if(speed > 40){
            speed = 40;
        }


    }

    public void update() {
        x-=speed;
        animation.update();
    }

    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch (Exception e){

        }
    }
}
