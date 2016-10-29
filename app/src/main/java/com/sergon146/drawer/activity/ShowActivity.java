package com.sergon146.drawer.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sergon146.drawer.R;
import com.sergon146.drawer.recorder.Record;
import com.sergon146.drawer.view.ShowView;

import java.util.Timer;
import java.util.TimerTask;

public class ShowActivity extends ActionBarActivity {
    ShowView lineChart;
    Record record;
    Menu topmenu;
    TextView cadre;
    Timer timer;
    Morf morf;
    double t;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        cadre = (TextView) findViewById(R.id.cadreText);

        record = (Record) intent.getSerializableExtra("record");
        record.setId(0);

        lineChart = (ShowView) findViewById(R.id.linechart);
        lineChart.setList(record.getList(record.getId()));
        cadre.setText("Кадр: " + (record.getId() + 1) + " из " + record.size());
        lineChart.invalidate();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                lineChart.setList(record.getList(record.prevList()));
                break;
            case R.id.right:
                lineChart.setList(record.getList(record.nextList()));
                break;
        }
        cadre.setText("Кадр: " + (record.getId() + 1) + " из " + record.size());
        lineChart.invalidate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        topmenu = menu;
        getMenuInflater().inflate(R.menu.showmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ImageView action;
        switch (item.getItemId()) {
            case (R.id.play):
                topmenu.setGroupVisible(R.id.stopGr, true);
                topmenu.setGroupVisible(R.id.playGr, false);
                action = (ImageView) findViewById(R.id.left);
                action.setVisibility(View.INVISIBLE);
                action = (ImageView) findViewById(R.id.right);
                action.setVisibility(View.INVISIBLE);
                cadre.setVisibility(View.INVISIBLE);
                //TODO automatic play

                record.setId(0);
                t = 0;

                index = 0;
                timer = new Timer();
                morf = new Morf();
                timer.schedule(morf, 0, 2);


                break;
            case (R.id.stop):
                //TODO stop play
                morf.cancel();
                topmenu.setGroupVisible(R.id.stopGr, false);
                topmenu.setGroupVisible(R.id.playGr, true);
                action = (ImageView) findViewById(R.id.left);
                action.setVisibility(View.VISIBLE);
                action = (ImageView) findViewById(R.id.right);
                action.setVisibility(View.VISIBLE);
                cadre.setVisibility(View.VISIBLE);

                lineChart.setList(record.getList(0));
                record.setId(0);
                cadre.setText("Кадр: " + (record.getId() + 1) + " из " + record.size());
                lineChart.invalidate();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    class Morf extends TimerTask {

        @Override
        public void run() {
            if (t < 1) {
                t += 0.001;
                lineChart.setList(record.getMorfList(t, index));


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lineChart.invalidate();
                    }
                });
            } else {
                if (index < record.size()-1)
                    index++;
                else index=0;
                t=0;
            }
        }
    }
}
