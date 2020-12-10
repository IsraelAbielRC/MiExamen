package com.abiel.abiel.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.abiel.abiel.Models.MovieModel;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String MOVIES_TABLE_CREATE = "CREATE TABLE movies(_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, link TEXT)";
    private static final String DB_NAME = "movies.sqlite";
    private static final int DB_VERSION = 1;
    public  SQLiteHelper(Context _context){
        super(_context,DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void InsertData(MovieModel _movie){
        SQLiteDatabase db= this.getWritableDatabase();
        if(!Existe(_movie.getDetail()))
        {
            db.execSQL("INSERT INTO movies (titulo, link) VALUES('"+ _movie.getDetail()+"','"+_movie.getLink()+"')");
        }
    }
    public Cursor GetAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursos = db.rawQuery("SELECT * FROM movies", null);
        return  cursos;
    }
    public  Boolean Existe(String _detail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursos = db.rawQuery("SELECT * FROM movies WHERE titulo='" + _detail +"'", null);
        int record = cursos.getCount();
        return record > 0 ? true : false;
    }
}
