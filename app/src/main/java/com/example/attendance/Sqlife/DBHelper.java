package com.example.attendance.Sqlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendance.Model.Acount;
import com.example.attendance.Model.AcountDetail;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final  String DBName="mydatabase2.db";
    private static final int VERSION = 3;
    private static final  String TABLE_NAME="acount";
    private static final  String TABLE_NAME1="chitiettaikhoan";
    //private static final  String ID="_id";
    private static final  String USERNAME="username";
    private static final  String NAME="name";
    private static final  String ID="_id";
    private static final  String SDT="sdt";
    private static final  String DIACHI ="diachi";
    private static final  String PASSWORD="password";
    private SQLiteDatabase myDB;
    public DBHelper(Context context) {
        super(context, DBName, null, VERSION);
    }

//    public static String getID() {
//        return ID;
//    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable1 = "CREATE TABLE " + TABLE_NAME1+ " ( " +ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT NOT NULL, "+SDT+" TEXT NOT NULL, "+DIACHI+" TEXT NOT NULL,"+USERNAME+" TEXT NOT NULL"+")";
        String queryTable = "CREATE TABLE " + TABLE_NAME+ " ( " +USERNAME +" TEXT PRIMARY KEY NOT NULL, "+PASSWORD+" TEXT NOT NULL"+")";
        db.execSQL(queryTable1);
        db.execSQL(queryTable);
    }
    public void openDB()
    {
        myDB = getWritableDatabase();
    }
    public void closeDB()
    {
        if(myDB!=null && myDB.isOpen())
        {
            myDB.close();
        }
    }
    public long Insert(String username,String password)
    {
        ContentValues values = new ContentValues();
        //values.put(ID,id);
        values.put(USERNAME,username);
        values.put(PASSWORD,password);
        return myDB.insert(TABLE_NAME,null ,values);
    }
    public long Insert_chitiettaikhoan(String name,String sdt,String diachi,String username) {
        ContentValues values = new ContentValues();
        //values.put(ID,id);
        values.put(NAME, name);
        values.put(SDT, sdt);
        values.put(DIACHI, sdt);
        values.put(USERNAME,username);
        return myDB.insert(TABLE_NAME1, null, values);
    }
        public long Update(String username,String password)
    {
        ContentValues values = new ContentValues();
//        values.put(ID,id);
        values.put(USERNAME,username);
        values.put(PASSWORD,password);
        String where = USERNAME + " = " +username;
        return myDB.update(TABLE_NAME,values,where,null);
    }
    public long Delete(String username)
    {
        String where = USERNAME + " = " +username;
        return myDB.delete(TABLE_NAME,where,null);
    }
//    public Cursor getAllRecord()
//    {
//        String query = "SELECT * FROM "+TABLE_NAME;
//        return myDB.rawQuery(query,null);
//    }

    public ArrayList<Acount> getAll()
    {
        ArrayList<Acount> listAcount = new ArrayList<Acount>();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = myDB.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            listAcount.add(new Acount(cursor.getString(0),cursor.getString(1)));
        }
        return listAcount;
    }
    public ArrayList<AcountDetail> getAll_Detail()
    {
        ArrayList<AcountDetail> listAcountdetail = new ArrayList<AcountDetail>();
        String query = "SELECT * FROM "+ TABLE_NAME1;
        Cursor cursor = myDB.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            listAcountdetail.add(new AcountDetail(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
        }
        return listAcountdetail;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
