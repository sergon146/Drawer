package com.example.android.sergon146.activity;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.sergon146.LineChartView;
import com.example.android.sergon146.R;
import com.example.android.sergon146.model.Circle;
import com.example.android.sergon146.model.Drawable;
import com.example.android.sergon146.model.Line;
import com.example.android.sergon146.model.Rectangle;
import com.example.android.sergon146.model.Trinagle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Context context;
    List<Drawable> list;
    int w, h;
    LineChartView lineChart;
    Boolean touchFlag = false;
    LayoutParams imageParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        context = this;
        list = new ArrayList<Drawable>();
        lineChart = (LineChartView) findViewById(R.id.linechart);
        lineChart.setList(list);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        w = size.x;
        h = size.y - 350;

        lineChart.setChartData(getRandomData());

    }

    private float[] getRandomData() {
        return new float[]{10, 12, 7, 14, 15, 19, 13, 2, 10, 13, 13, 10, 15, 14};
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.draw:
                showPopupMenu(view);
                break;

            case R.id.undo:
                Toast.makeText(context,
                        "Последний фрагмент удалён",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete:
                Toast.makeText(context,
                        "Выбранный фрагмент удалён",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.addpopup);
        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.menu1:
                                list.add(new Line(w, h));
                                lineChart.invalidate();

                                Toast.makeText(context,
                                        "Добавлен отрезок",
                                        Toast.LENGTH_SHORT).show();

                                return true;
                            case R.id.menu2:
                                list.add(new Circle(w, h));
                                lineChart.invalidate();
                                Toast.makeText(context,
                                        "Добавлен круг",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:
                                list.add(new Trinagle(w, h));
                                lineChart.invalidate();
                                Toast.makeText(context,
                                        "Добавлен треугольник",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu4:
                                list.add(new Rectangle(w, h));
                                lineChart.invalidate();
                                Toast.makeText(context,
                                        "Добавлен прямоугольник",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }


                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
        lineChart.setList(list);

    }


    public List<Drawable> getList() {
        return list;
    }


}

