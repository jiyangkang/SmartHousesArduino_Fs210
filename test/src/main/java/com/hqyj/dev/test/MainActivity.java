package com.hqyj.dev.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Byte[] data1 = new Byte[]{1,2,3,4,5,6};

        ArrayList<Byte> arrayList = new ArrayList<>();

        Collections.addAll(arrayList, data1);

        Byte[] data2 = new Byte[]{7,8,9,0};

        Collections.addAll(arrayList, data2);
//        for (int i = 0; i < arrayList.size(); i++){
//            Log.d("Show", String.format("i = %d, value = %X", i, arrayList.get(i)));
//        }

        arrayList.remove(0);

        for (int i = 0; i < arrayList.size(); i++){
            Log.d("Show", String.format("i = %d, value = %X", i, arrayList.get(i)));
        }

    }
}
