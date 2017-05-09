package com.traato.android.sqltutorial;

import android.database.sqlite.SQLiteQueryBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_main);

        mDataBaseHelper = new DataBaseHelper(this);

        try {
            mDataBaseHelper.Create();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mDataBaseHelper.openDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mDataBaseHelper.ShowAllColumns();

    }



}
