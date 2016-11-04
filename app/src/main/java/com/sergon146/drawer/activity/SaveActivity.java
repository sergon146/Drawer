package com.sergon146.drawer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.sergon146.drawer.R;
import com.sergon146.drawer.recycler.RecyclerAdapter;
import com.sergon146.drawer.recorder.Record;
import com.sergon146.drawer.recycler.RecyclerItemClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveActivity extends ActionBarActivity {

    EditText nameText;
    ArrayList<String> names;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;
        nameText = (EditText) findViewById(R.id.saveName);
        File rootFolder = getExternalCacheDir();
        assert rootFolder != null;
        File[] filesArray = rootFolder.listFiles();
        String path;
        names = new ArrayList<>();
        for (File f : filesArray) {
            path = f.toString();
            names.add(path.substring(path.lastIndexOf('/') + 1, path.length()));
        }
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter mAdapter = new RecyclerAdapter(names);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String path = names.get(position);
                        nameText.setText(path.substring(0, path.length() - 4));
                    }
                })
        );
    }


    public void onClick(View view) {
        Intent intent = getIntent();
        Record record = (Record) intent.getSerializableExtra("record");
        File file = new File(getExternalCacheDir(), nameText.getText() + ".xdr");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(record);
            oos.flush();
            oos.close();
            Toast.makeText(context,
                    "Файл сохранён",
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}
