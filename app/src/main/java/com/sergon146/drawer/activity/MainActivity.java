package com.sergon146.drawer.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.PopupMenu;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sergon146.drawer.view.DrawView;
import com.sergon146.drawer.R;
import com.sergon146.drawer.model.Circle;
import com.sergon146.drawer.model.Drawable;
import com.sergon146.drawer.model.Line;
import com.sergon146.drawer.model.Rectangle;
import com.sergon146.drawer.model.Trinagle;
import com.sergon146.drawer.recorder.Record;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Context context;
    List<Drawable> list;
    int w, h, countCadre, id;
    DrawView lineChart;
    ToggleButton tgBut, local;
    Menu topmenu;
    Record record;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        list = new ArrayList<>();
        lineChart = (DrawView) findViewById(R.id.linechart);
        lineChart.setList(list);


        tgBut = (ToggleButton) findViewById(R.id.zoom);
        local = (ToggleButton) findViewById(R.id.local);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        w = size.x;
        h = size.y - 350;
        id = 0;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        topmenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ActionMenuItemView recordNA;

        switch (id) {
            case (R.id.action_recordStart):
                record = new Record();
                topmenu.setGroupVisible(R.id.groupRec, false);
                topmenu.setGroupVisible(R.id.groupInRec, true);
                topmenu.setGroupVisible(R.id.groupPlay, false);
                countCadre = 0;
                recordNA = (ActionMenuItemView) findViewById(R.id.action_recordStop);
                recordNA.setIcon(getDrawable(R.drawable.stop_na));
                recordNA.setEnabled(false);


                break;
            case (R.id.action_saveCadre):
                List<Drawable> newList = new ArrayList<>(lineChart.getList());
                for (int i = 0; i < newList.size(); i++) {
                    if (newList.get(i) instanceof Line)
                        newList.set(i, new Line((Line) newList.get(i)));
                    else if (newList.get(i) instanceof Circle)
                        newList.set(i, new Circle((Circle) newList.get(i)));
                    else if (newList.get(i) instanceof Trinagle)
                        newList.set(i, new Trinagle((Trinagle) newList.get(i)));
                    else if (newList.get(i) instanceof Rectangle)
                        newList.set(i, new Rectangle((Rectangle) newList.get(i)));
                }
                for (Drawable d : newList) {
                    d.setChoose(false);
                }

                record.addList(new ArrayList<>(newList));
                countCadre++;
                if (countCadre < 2) {
                    recordNA = (ActionMenuItemView) findViewById(R.id.action_recordStop);
                    recordNA.setIcon(getDrawable(R.drawable.stop_na));
                    recordNA.setEnabled(false);
                } else {
                    recordNA = (ActionMenuItemView) findViewById(R.id.action_recordStop);
                    recordNA.setIcon(getDrawable(R.drawable.stop));
                    recordNA.setEnabled(true);
                }
                break;

            case (R.id.action_recordStop):
                topmenu.setGroupVisible(R.id.groupRec, true);
                topmenu.setGroupVisible(R.id.groupInRec, false);
                topmenu.setGroupVisible(R.id.groupPlay, true);

                intent = new Intent(this, SaveActivity.class);
                intent.putExtra("record", record);
                startActivity(intent);
                break;

            case (R.id.action_recordClose):
                record.clear();
                topmenu.setGroupVisible(R.id.groupRec, true);
                topmenu.setGroupVisible(R.id.groupInRec, false);
                topmenu.setGroupVisible(R.id.groupPlay, true);
                break;

            case (R.id.action_recordPlay):
                intent = new Intent(this, ChooseActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.draw:
                showPopupMenu(view);
                break;

            case R.id.undo:
                if (list.size() > 0) {
                    list.remove(list.size() - 1);
                    Toast.makeText(context,
                            "Последний фрагмент удалён",
                            Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context,
                        "Лист чист!",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.delete:
                boolean flag = false;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChoose()) {
                            list.remove(i);
                            flag = true;
                        }
                    }
                    if (flag)
                        Toast.makeText(context,
                                "Выбранный фрагмент удалён",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context,
                                "Выберете фрагмент для удаления",
                                Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context,
                        "Лист чист!",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        lineChart.invalidate();

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
                                list.add(new Line(w, h, id));
                                id++;
                                lineChart.invalidate();

                                Toast.makeText(context,
                                        "Добавлен отрезок",
                                        Toast.LENGTH_SHORT).show();

                                return true;
                            case R.id.menu2:
                                list.add(new Circle(w, h, id));
                                id++;
                                lineChart.invalidate();
                                Toast.makeText(context,
                                        "Добавлен круг",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:
                                list.add(new Trinagle(w, h, id));
                                id++;
                                lineChart.invalidate();
                                Toast.makeText(context,
                                        "Добавлен треугольник",
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu4:
                                list.add(new Rectangle(w, h, id));
                                id++;
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (!tgBut.isChecked()) {
                    System.out.println("zoom +");
                    for (Drawable d : list) {
                        if (d.isChoose()) {
                            if (local.isChecked())
                                d.globalScale(true);
                            else
                                d.localSacle(true);
                        }
                    }
                } else {
                    for (Drawable d : list) {
                        if (d.isChoose()) {
                            if (local.isChecked())
                                d.globalRotate(true);
                            else
                                d.localRotate(true);
                        }
                    }
                }

                lineChart.invalidate();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (!tgBut.isChecked()) {
                    System.out.println("zoom +");
                    for (Drawable d : list) {
                        if (d.isChoose()) {
                            if (local.isChecked())
                                d.globalScale(false);
                            else
                                d.localSacle(false);
                        }
                    }
                } else {
                    for (Drawable d : list) {
                        if (d.isChoose()) {
                            if (local.isChecked())
                                d.globalRotate(false);
                            else
                                d.localRotate(false);
                        }
                    }
                }

                lineChart.invalidate();
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}

