package com.example.mohit.gametest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Window;

/**
 * Created by mohit on 8/6/16.
 */
public class Background {
    private Bitmap image;
    private int x = 0;
    private int y = 0;
    private int dx;

    Background(Bitmap image) {
        this.image = image;
        dx = GamePanel.GameSpeed;
    }

    public Bitmap getImage() {
        return image;
    }

    public void update() {
        x-=dx;
        if(x < -GamePanel.screenW){
            x = 0;
        }
    }

    public void draw(Canvas canvas) {


        canvas.drawBitmap(this.image,x,y,null);

        if(x < 0){
            canvas.drawBitmap(this.image,x + GamePanel.screenW,y,null);
           // System.out.println("width: " + image.getWidth() + " height: " + image.getHeight()+ " gamepanelWidth: " + GamePanel.WIDTH);
        }

    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
