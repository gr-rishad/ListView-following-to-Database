package com.example.rishad.listviewdemodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Information.db";
    private static final String TABLE_NAME = "info_details";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String PHONE_NUMBER = "PhoneNumber";
    private static final int VERSION_NAME = 1;
    private Context context;

    private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255),"+PHONE_NUMBER+" VARCHAR(255));";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, VERSION_NAME);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context,"onCreate is called.",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL(DROP_TABLE);
            Toast.makeText(context,"onUpdate is called.",Toast.LENGTH_LONG).show();
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Exception :"+e,Toast.LENGTH_LONG).show();
        }
    }

    public Long save(InfoDetails infoDetails){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(NAME,infoDetails.getName());
        contentValues.put(PHONE_NUMBER,infoDetails.getPhoneNumber());
       long rowId= sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
       return rowId;
    }

    public Cursor showAllData(){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
       Cursor cursor= sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public Boolean updateData(String id, String name, String phone){

        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(PHONE_NUMBER,phone);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID + "= ?",new String[]{id});
        return true;
    }
    public int deleteData(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int value= sqLiteDatabase.delete(TABLE_NAME , ID+" = ?",new String[]{id});
        return value;
    }
}
