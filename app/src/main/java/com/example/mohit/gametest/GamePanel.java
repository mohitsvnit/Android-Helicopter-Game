package com.example.mohit.gametest;

import android.content.Context;
import android.content.SyncAdapterType;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mohit on 8/6/16.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int GameSpeed = -5;


    public static int screenW;
    public static int screenH;
    private float scale;
    MainThread mainThread;
    private Background bg;
    private Player player;
    private List<SmokePuff> smokePuffList;
    private long smokeStartTime;
    private Missile missile;
    private List<Missile> missileList;
    private long missileStartTime;
    private int missilecnt;
    private Random random = new Random();
    private Explosion explosion;
    private boolean playerDisapper = false;
    private boolean explode = false;
    private Paint textPaint;
    public static int score;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(),this);
        smokePuffList = new ArrayList<>();
        missileList = new ArrayList<>();
        textPaint = new Paint();
        scale = getResources().getDisplayMetrics().density;

        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
        textPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        textPaint.setTextSize(20*scale);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.screenW = getWidth();
        this.screenH = getHeight();

        Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.grassbg1);
        //float scaledWidth = (getHeight()/image.getHeight());
        image = Bitmap.createScaledBitmap(image,getWidth(),getHeight(),false);

        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter),130,40,3);

        bg = new Background(image);
        bg.setDx(5);
        smokeStartTime = System.currentTimeMillis();
        missile = new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),screenW+100,(int)((random.nextDouble()*1000)%(screenH-10)),100,30,13);
        missileList.add(missile);
        missilecnt++;
        missileStartTime = System.currentTimeMillis();
        explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.explosion),player.getX(),player.getY(),200,200,25);


        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        screenH = height;
        screenW = width;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
                retry = false;

            } catch (Exception e){
                //e.printStackTrace();
            }
        }
    }

    public void update() {
      //  System.out.println("screen Width: " + getWidth() + " screen height: " + getHeight());
        if(player.getPlaying()) {
            if(!playerDisapper) {
                player.update();
            }
            bg.update();
            //missile.update();
            for(int i = 0; i < smokePuffList.size(); i++) {
                smokePuffList.get(i).update();
            }
            long smokeCurrenttime = System.currentTimeMillis();
            if(smokeCurrenttime - smokeStartTime > 120 && !playerDisapper){
                smokePuffList.add(new SmokePuff(player.getX()-2,player.getY()+15));
                smokeStartTime = System.currentTimeMillis();
            }

            for(int i = 0; i < missileList.size(); i++){
                missileList.get(i).update();
            }

            long missileElapsedTime = System.currentTimeMillis();
            if(missileElapsedTime - missileStartTime > 2000 && missilecnt <= (2 + (int)player.getScore()/50)) {
                missile = new Missile(BitmapFactory.decodeResource(getResources(),R.drawable.missile),screenW+100,(int)((random.nextDouble()*1000)%(screenH-10)),100,30,13);
                missileList.add(missile);
                missilecnt++;
                missileStartTime = System.currentTimeMillis();
            }

            if(explode){
                explosion.update();
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        bg.draw(canvas);
        score = player.getScore();
        if(!playerDisapper) {
            canvas.drawText("Score: " + player.getScore(),10,textPaint.getTextSize()+15,textPaint);
            player.draw(canvas);
        }
       // missile.draw(canvas);
       // explosion.draw(canvas);

        for(int i = 0; i < smokePuffList.size(); i++){
            smokePuffList.get(i).draw(canvas);
            if(smokePuffList.get(i).getX() < -10){
                smokePuffList.remove(i);
            }
        }

        for(int i = 0; i < missileList.size(); i++) {
            missileList.get(i).draw(canvas);

            if(collision(player,missileList.get(i))){
                missileList.remove(i);
                playerDisapper = true;
                explode = true;
                explosion.setX(player.getX());
                explosion.setY(player.getY()-(int)(50*scale));
            }
            else if(player.getY() <= 0 || player.getY() >= (screenH - player.getHeight() - 35 )){
                playerDisapper = true;
                explode = true;
                explosion.setX(player.getX());
                explosion.setY(player.getY()-(int)(50*scale));
            }

            if(missileList.get(i).getX() < -100){
                missileList.remove(i);
                missilecnt--;
            }
        }

        if(explode){
            explosion.draw(canvas);
            if(explosion.getExplosionStatus()){
                player.setPlaying(false);
                textPaint.setTextSize(40*scale);
                canvas.drawText("Game Over :-P",screenW/4,screenH/3,textPaint);
                canvas.drawText("Your Score: " + player.getScore(),screenW/4,screenH/3 + textPaint.getTextSize()+10,textPaint);
                canvas.drawText("Tap Screen to Restart! Yo Khupach",20,screenH/3 + textPaint.getTextSize()*2 + 20,textPaint);
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!player.getPlaying()){
                    player.setPlaying(true);
                    smokePuffList.clear();
                    missileList.clear();
                    player.setScore(0);
                    explode = false;
                    playerDisapper = false;
                    missilecnt = 0;
                    explosion.setExplosioncomplete(false);
                    player.setDya(0);
                    textPaint.setTextSize(20*scale);
                    player.setX(screenW/5);
                    player.setY(screenH/2);
                }
                else {
                    player.setDirUp(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                player.setDirUp(false);
                break;
        }

        return true;
    }

    public boolean collision(GameObjects a, GameObjects b) {
        if(Rect.intersects(a.getRectangle(),b.getRectangle())){
            return true;
        }

        return false;
    }



}
