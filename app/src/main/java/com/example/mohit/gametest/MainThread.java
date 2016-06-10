package com.example.mohit.gametest;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by mohit on 8/6/16.
 */
public class MainThread extends Thread {

    private int FPS = 30;
    private int averageFPS;
    private int frameCount = 0;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private Canvas canvas;

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        super.run();

        long startTime;
        long timeinmillies;
        long waittime;
        long targetTime = 1000/FPS;
        long totalTime = 0;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);

                }
            }catch (Exception e) {

            }finally {
                if(canvas != null){
                    try {
                        this.surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){

                    }
                }
            }



            timeinmillies = (System.nanoTime() - startTime)/1000000;
            waittime = targetTime - timeinmillies;

            if(waittime < 0){ waittime = 0;}

            try {
                this.sleep(waittime);
            } catch (InterruptedException e) {

            }

           // System.out.println("WaitTime: " + waittime + " FrameCount: " + frameCount + " Total TIme: " + totalTime);

            totalTime += (System.nanoTime() - startTime)/1000000;
            frameCount++;

            if(frameCount >= 30) {
                averageFPS = (int) (1000/(totalTime/FPS));
                System.out.println(averageFPS);
                frameCount = 0;
                totalTime = 0;
            }

        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
