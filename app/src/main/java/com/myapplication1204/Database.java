package com.myapplication1204;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NName = "products.sqLite";
    public static final int DB_VERSION = 1;

    public static final String TBL_NAME = "Product";
    public static final String COL_CODE = "ProductCode";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";
    public Database(@Nullable Context context) {
        super(context,DB_NName,null,DB_VERSION);
    }

//    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }
//
//    public Database(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " (" + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " VARCHAR(50), " + COL_PRICE + "REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_NAME);
        onCreate(db);
    }

    // SELECT
    public Cursor queryCreate (String sql ){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    // INSERT, UPDATE, DELETE,....
    public void execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public int getNumbOfRow(){
        Cursor cursor  = queryCreate("SELECT * FROM " + TBL_NAME);
        int numbOfRows = cursor.getCount();
        cursor.close();
        return numbOfRows;
    }

    public void createSampleData(){
        if(getNumbOfRow()==0){
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null,'Heineken','19000')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null,'Tiger','18000')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null,'Sapporo','22000')");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null,'Blace 1664','24000')");
            }catch(Exception e){
                Log.e("Error: ",e.getMessage().toString());
            }
        }
    }
    public Cursor queryData(String sql){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(sql, null);

    }
}

