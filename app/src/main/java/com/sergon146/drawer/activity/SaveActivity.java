package com.sergon146.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.sergon146.R;
import com.sergon146.drawer.recorder.Record;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        Record record = (Record) intent.getSerializableExtra("record");
        Context context = this.getApplicationContext();
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput("line.xdr", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(record);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
