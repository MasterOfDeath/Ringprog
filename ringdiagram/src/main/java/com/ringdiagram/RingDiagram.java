package com.ringdiagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RingDiagram extends SurfaceView implements SurfaceHolder.Callback {

    private Canvas c;
    private AnimationThread thread;

    int h1 = 200;
    int h2 = 110;
    double maxH1 = 180;
    double maxH2 = 60;
    long animSpeed = 10;
    int progress = 135;
    double angle;
    int angle1 = 0;
    int colorR1 = Color.MAGENTA;
    int colorR2 = Color.BLUE;
    int colorText = Color.BLACK;
    int colorBackGround = Color.WHITE;

    Paint p1 = new Paint();
    Paint p2 = new Paint();
    RectF rectf1 = new RectF(0,0,h1,h1);

    Paint pBack = new Paint();
    Paint pText = new Paint();

    Bitmap bmIcon;
    float leftBmIcon = 0;
    float topBmIcon = 0;
    float leftText = 0;
    float topText = 0;

    public RingDiagram(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        //thread = new AnimationThread(holder);
    }

    public void setValue(int progress) {
            this.progress = progress;
    }

    public void setFirstLoopMaxValue(Double maxH1) {
        this.maxH1 = maxH1;
    }

    /*public void setFirstLoopColor(int resId){
        this.colorR1 = getResources().getColor(resId);
    }*/
    //Временно
    public void setFirstLoopColor(String resId){
        this.colorR1 = Color.parseColor(resId);
    }

    public void setSecondLoopMaxValue(Double maxH2) {
        this.maxH2 = maxH2;
    }

    /*public void setSecondLoopColor(int resId){
        this.colorR2 = getResources().getColor(resId);
    }*/
    //Временно
    public void setSecondLoopColor(String resId){
        this.colorR2 = Color.parseColor(resId);
    }

    public void setTextColor(int resId){
        this.colorText = getResources().getColor(resId);
    }

    public void setBackGroundColor(int resId){
        this.colorBackGround = getResources().getColor(resId);
    }

    public void setIcon(int drawableResId){
        bmIcon = BitmapFactory.decodeResource(getResources(), drawableResId);
        leftBmIcon = h1/2-bmIcon.getWidth();
        topBmIcon = h1/2-bmIcon.getHeight()/2;
    }

    public void setAnimationDuration(long msec){
        this.animSpeed = msec;
    }

    class AnimationThread extends Thread {
        private SurfaceHolder mSurfaceHolder;

        public AnimationThread(SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
            angle1 =0;
            angle = 360*progress/maxH1;

            p1.setColor(colorR1);
            p1.setStrokeWidth(40);
            p1.setAntiAlias(true);
            p2.setColor(colorR2);
            p2.setStrokeWidth(40);
            p2.setAntiAlias(true);
            pBack.setColor(colorBackGround);
            pBack.setAntiAlias(true);
            pText.setColor(colorText);
            //Вычисляем размер шрифта, 4.4 - коэффицент (определен методом тыка)
            double d = h2/4.4;
            pText.setTextSize((float)d);
            //Положение текста
            leftText = h1/2+2;
            topText = h1/2+pText.getTextSize()/4;

            //Если превышаем 1-й максимум
            if (progress > maxH1){
                angle = 360+360*(progress-maxH1)/maxH2;
            }

            mSurfaceHolder.setFixedSize(h1,h1);
        }

        @Override
        public void run() {
            while (angle1 < angle) {
                c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    doDraw(c);
                    mSurfaceHolder.unlockCanvasAndPost(c);
                    sleep(animSpeed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*} finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }*/

                /*try {
                    sleep(animSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        }

        private void doDraw(Canvas canvas) {
            canvas.drawColor(colorBackGround);
            angle1 = angle1+10;

            if (angle1 < 370){
                canvas.drawArc(rectf1, 270, angle1, true, p1);
            }

            if (angle1 > 360) {
                canvas.drawCircle(h1/2,h1/2,h1/2,p1);
                canvas.drawArc(rectf1, 270, angle1-360, true, p2);
            }

            canvas.drawCircle(h1/2,h1/2,h2/2,pBack);
            if (bmIcon != null){
                canvas.drawBitmap(bmIcon,leftBmIcon,topBmIcon,p1);
            }
            canvas.drawText(Integer.toString(progress),leftText,topText,pText);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void surfaceCreated(SurfaceHolder holder) {
        thread = new AnimationThread(holder);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}