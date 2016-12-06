package com.sergon146.drawer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sergon146.drawer.model.Drawable;

import java.util.List;

public class ShowView extends View {


    private Paint paint = new Paint();
    private List<Drawable> list;

    public void setList(List<Drawable> list) {
        this.list = list;
    }

    public ShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawFigure(canvas);
    }


    public void drawFigure(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);


        if (list.size() > 0) {
            for (Drawable d : list) {
                        switch (d.getColor()){
                            case 0:
                                paint.setColor(Color.BLUE);
                                break;
                            case 1:
                                paint.setColor(Color.GREEN);
                                break;
                            case 2:
                                paint.setColor(Color.BLACK);
                                break;
                            case 3:
                                paint.setColor(Color.WHITE);
                                break;
                        }

                d.draw(canvas, paint);
            }
        }
    }
}
