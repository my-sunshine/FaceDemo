package com.hqgj.facedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hqgj.facedemo.adapter.ListViewAdapter;
import com.hqgj.facedemo.view.ExtendListView;
import com.hqgj.mylibrary.view.FaceContainerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FaceContainerView faceContainerView;
    private ExtendListView listView;
    private ListViewAdapter adapter;
    private ArrayList<String> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lists=new ArrayList<>(2);
        lists.add("[test]");
        lists.add("111");
        lists.add("222");
        lists.add("[街舞]");lists.add("[回头]");lists.add("222");

        initView();
    }

    private void initView() {
        faceContainerView= (FaceContainerView) findViewById(R.id.faceContainerView);
        faceContainerView.setOnSendMessageListener(new FaceContainerView.OnSendMessageListener() {
            @Override
            public void onSendMessageListener(String message) {

                lists.add(message);

                adapter.notifyDataSetInvalidated();

            }
        });

        faceContainerView.setTakePhotoShow(true);

        faceContainerView.setOnTakePhotoListener(new FaceContainerView.OnTakePhotoListener() {
            @Override
            public void onTakePhotoListener() {
                Toast.makeText(MainActivity.this,"onTakePhotoListener",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTakeCameraListener() {
                Toast.makeText(MainActivity.this,"onTakeCameraListener",Toast.LENGTH_LONG).show();
            }
        });


        listView= (ExtendListView) findViewById(R.id.listView);
        adapter=new ListViewAdapter(this,lists);
        listView.setAdapter(adapter);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceContainerView.showKeyBoard();
            }
        });

        findViewById(R.id.btnHidden).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceContainerView.hiddenKeyBoard();
            }
        });

    }
}
