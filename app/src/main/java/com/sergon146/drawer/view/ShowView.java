package com.sergon146.drawer.view;

import android.content.Context;
import android.graphics.Canvas;
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
        paint.setColor(0xFF33B5E5);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x80000000);


        if (list.size() > 0) {
            for (Drawable d : list) {
                    if (d.isChoose()) {
                        paint.setColor(0xFFFF0000);
                    } else {
                        paint.setColor(0xFF33B5E5);
                    }
                d.draw(canvas, paint);
            }
        }
    }
}
