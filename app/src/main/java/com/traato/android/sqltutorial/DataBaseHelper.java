package com.traato.android.sqltutorial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Naresh on 06-05-2017.
 */
//https://github.com/sanathp/DatabaseManager_For_Android
public class DataBaseHelper{

    DataBaseOpenHelper mDataBaseOpenHelper;
    public DataBaseHelper(Context context)
    {
        mDataBaseOpenHelper = new DataBaseOpenHelper(context);
    }

    public void ShowAllColumns()
    {
        SQLiteDatabase db = mDataBaseOpenHelper.getWritableDatabase();

        String[] columns = { DataBaseOpenHelper.DATABASE_ID, DataBaseOpenHelper.DATABASE_CUSTOMER_NAME};
        Cursor cursor = db.query(DataBaseOpenHelper.DATABASE_TABLE, columns, null, null, null, null, null);

        String allData = "";
        while(cursor.moveToNext())
        {
            String idString = cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.DATABASE_ID));
            String name = cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.DATABASE_CUSTOMER_NAME));

            allData += idString + " " + name + "\n";
        }

        Message.message(mDataBaseOpenHelper.getContext(), allData);

    }

    public void Create() throws IOException
    {
        mDataBaseOpenHelper.CreateDataBase();
    }

    public void openDB() throws IOException
    {
        mDataBaseOpenHelper.openDataBase();
    }

    static class DataBaseOpenHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "customers.db";
        private static final String DATABASE_TABLE = "users";
        private static final String DATABASE_ID = "_id";
        private static final String DATABASE_CUSTOMER_NAME = "name";
        private static final int DATABASE_VERSION = 1;
        private static final String TAG = "DataBaseHelper";

        //The Android's default system path of your application database.
        //replace com.traato.android.sqltutorial with you Application package nae
        //This should be same as which you used package section in your manifest
        private static String DATABASE_PATH = "/data/data/com.traato.android.sqltutorial/databases/";

        private Context mContext;
        SQLiteDatabase mDataBase;

        DataBaseOpenHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
            mContext = context;
        }

        public void CreateDataBase() throws IOException
        {
            boolean mDataBaseExists = CheckDataBase();
            if( mDataBaseExists == false )
            {
                //By calling this method an empty database will be created into the default system path
                //of your application so we are gonna be able to overwrite that database with our database.
                this.getReadableDatabase();
                this.close();
                try{
                    copyDataBase();
                    Log.e(TAG, "createDatabase database created");
                }

                catch (SQLiteException mIOException)
                {
                    throw new Error("ErrorCopyingDataBase");
                } catch (IOException e) {
                    throw new Error("ErrorCopyingDataBase");
                }


            }
        }

        public void openDataBase() throws IOException
        {
            String dataBaseFile = DATABASE_PATH + DATABASE_NAME;
            mDataBase = SQLiteDatabase.openDatabase(dataBaseFile, null, SQLiteDatabase.OPEN_READWRITE);
        }

        private void copyDataBase() throws IOException {
            String dbPath = DATABASE_PATH+DATABASE_NAME;

            InputStream myInput = getContext().getAssets().open(DATABASE_NAME);

            String outputPath = DATABASE_PATH + DATABASE_NAME;
            OutputStream dbFile = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            while(myInput.read(buffer) >= 0)
            {
                dbFile.write(buffer);
            }

            dbFile.flush();
            dbFile.close();
            myInput.close();
        }

        private boolean CheckDataBase() {
            boolean checkDB = false;
            try {
                String dbPath = DATABASE_PATH+DATABASE_NAME;
                File dbFile = new File(dbPath);
                checkDB = dbFile.exists();
            }
            catch (SQLiteException e)
            {

            }
            return checkDB;
        }

        @Override
        public synchronized void close() {
            if(mDataBase != null)
                mDataBase.close();

            super.close();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //CREATE TABLE TestDataBase (_ID INTEER PRIMARY KEY AUTOINCREMENT, Customer name VARCHAR(255));
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public Context getContext() {
            return mContext;
        }
    }
}
