package com.example.android.sergon146;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.sergon146.model.Drawable;
import com.example.android.sergon146.model.Point;

import java.util.List;

/**
 * Created by Pashgan on 05.03.2015.
 */
public class LineChartView extends View {
    private Point touch;
    private static final int MIN_LINES = 4;
    private static final int MAX_LINES = 7;
    private static final int[] DISTANCES = {1, 2, 5};

    private float[] datapoints = new float[]{};
    private Paint paint = new Paint();
    private List<Drawable> list;

    public void setList(List<Drawable> list) {
        this.list = list;
    }

    public void setChartData(float[] datapoints) {
        this.datapoints = datapoints.clone();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawFigure(canvas);
    }

    private void drawBackground(Canvas canvas) {
        float maxValue = getMax(datapoints);
        int range = getLineDistance(maxValue);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        for (int y = 0; y < maxValue; y += range) {
            final float yPos = getYPos(y);
            canvas.drawLine(0, yPos, getWidth(), yPos, paint);
        }
    }

    private int getLineDistance(float maxValue) {
        int distance;
        int distanceIndex = 0;
        int distanceMultiplier = 1;
        int numberOfLines = MIN_LINES;

        do {
            distance = DISTANCES[distanceIndex] * distanceMultiplier;
            numberOfLines = (int) FloatMath.ceil(maxValue / distance);

            distanceIndex++;
            if (distanceIndex == DISTANCES.length) {
                distanceIndex = 0;
                distanceMultiplier *= 10;
            }
        } while (numberOfLines < MIN_LINES || numberOfLines > MAX_LINES);

        return distance;
    }


    public void drawFigure(Canvas canvas) {
        boolean select = false;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(0xFF33B5E5);
        paint.setAntiAlias(true);
        paint.setShadowLayer(4, 2, 2, 0x80000000);


        if (list.size() != 0) {
            for (Drawable d : list) {
                if (touch != null)
                    if (d.isNearTouch(touch, 20) && !select) {
                        paint.setColor(0xFFFF0000);
                        select = true;
                    } else
                        paint.setColor(0xFF33B5E5);

                d.draw(canvas, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touch = new Point(event.getX(), event.getY());
        invalidate();
        return true;

    }


    private float getYPos(float value) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();
        float maxValue = getMax(datapoints);

        // масштабирования под высоту view
        value = (value / maxValue) * height;

        // инверсия
        value = height - value;

        // смещение чтобы учесть padding
        value += getPaddingTop();
        return value;
    }

    private float getXPos(float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight();
        float maxValue = datapoints.length - 1;

        // масштабирования под размер view
        value = (value / maxValue) * width;

        // смещение чтобы учесть padding
        value += getPaddingLeft();

        return value;
    }

    private float getMax(float[] array) {
        float max = array[10];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

}
