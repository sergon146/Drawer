package com.sergon146.drawer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sergon146.drawer.model.Drawable;
import com.sergon146.drawer.model.Point;

import java.util.List;

public class DrawView extends View {
    private Point touch;
    private Point currentPoint;


    private Paint paint = new Paint();
    private List<Drawable> list;



    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFigure(canvas);
    }


    public void drawFigure(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(0xFF33B5E5);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x80000000);


        if (list.size() != 0) {
            for (Drawable d : list) {
                if (touch != null)
                    if (d.isChoose()) {
                        paint.setColor(0xFFFF0000);
                    } else {
                        paint.setColor(0xFF33B5E5);
                    }
                d.draw(canvas, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                currentPoint = new Point(event.getX(), event.getY());
                touch = new Point(event.getX(), event.getY());
                for (Drawable d : list) {
                    d.setChoose(false);
                    if (touch != null)
                        if (d.isNearTouch(touch, 20)) {
                            d.setChoose(true);
                            break;
                        }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                    for (Drawable d : list) {
                        if (d.isChoose()) {
                            Point finishPoint = new Point(event.getX(), event.getY());
                            Point delta = new Point(finishPoint.getX() - currentPoint.getX(),
                                    finishPoint.getY() - currentPoint.getY());
                            currentPoint = new Point(finishPoint);
                            d.shift(delta);

                        }

                    }
                break;
        }
        invalidate();
        return true;

    }

    public List<Drawable> getList(){
        return list;
    }

    public void setList(List<Drawable> list) {
        this.list = list;
    }



}
