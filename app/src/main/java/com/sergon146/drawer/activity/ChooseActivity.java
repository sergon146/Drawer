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
import com.sergon146.drawer.recorder.Record;
import com.sergon146.drawer.recycler.RecyclerAdapter;
import com.sergon146.drawer.recycler.RecyclerItemClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ChooseActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    ArrayList<String> names;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = this;



        File rootFolder = getExternalCacheDir();
        assert rootFolder != null;
        File[] filesArray = rootFolder.listFiles();
        String path;
        names = new ArrayList<>();
        for (File f: filesArray) {
            path=f.toString();
            names.add(path.substring(path.lastIndexOf('/')+1, path.length()));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(names);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String path=names.get(position);
                        Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                        File file = new File(getExternalCacheDir(), path.substring(0, path.length()));
                        try {
                            FileInputStream fis = new FileInputStream(file);
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            Record record =  (Record) ois.readObject();
                            intent.putExtra("record",record);
                            ois.close();
                            Toast.makeText(context,
                                    "Файл загружен",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } catch (IOException | ClassNotFoundException e) {
                            Toast.makeText(context,
                                    "Файл повреждён",
                                    Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }
                })
        );


    }

}