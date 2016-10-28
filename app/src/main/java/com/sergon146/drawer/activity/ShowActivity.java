package com.sergon146.drawer.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sergon146.drawer.R;
import com.sergon146.drawer.recorder.Record;
import com.sergon146.drawer.view.ShowView;

public class ShowActivity extends ActionBarActivity {
    ShowView lineChart;
    Record record;
    Menu topmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();

        record = (Record) intent.getSerializableExtra("record");
        record.setId(0);

        lineChart = (ShowView) findViewById(R.id.linechart);
        lineChart.setList(record.getList(0));
        lineChart.invalidate();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                lineChart.setList(record.getList(record.prevList()));
                lineChart.invalidate();
                break;
            case R.id.right:
                lineChart.setList(record.getList(record.nextList()));
                lineChart.invalidate();
                break;
        }

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
                //TODO automatic play
                break;
            case (R.id.stop):
                //TODO stop play
                topmenu.setGroupVisible(R.id.stopGr, false);
                topmenu.setGroupVisible(R.id.playGr, true);
                action = (ImageView) findViewById(R.id.left);
                action.setVisibility(View.VISIBLE);
                action = (ImageView) findViewById(R.id.right);
                action.setVisibility(View.VISIBLE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
