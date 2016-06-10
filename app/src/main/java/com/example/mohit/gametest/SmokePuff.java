package com.example.mohit.gametest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by mohit on 9/6/16.
 */
public class SmokePuff  extends GameObjects{

    private int r = 5;
    private Paint smokePaint;

    SmokePuff(int x,int y){
        this.x = x;
        this.y = y;

        smokePaint = new Paint();
        smokePaint.setColor(Color.GRAY);
        smokePaint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x,y,r,smokePaint);
        canvas.drawCircle(x+2,y+2,r+2,smokePaint);
        canvas.drawCircle(x+4,y+4,r,smokePaint);
    }

    public void update(){
        x-=10;
    }

}
