package com.example.hamid.anotherjson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by hamid on 01/25/2016.
 */

public class myDatabase extends SQLiteOpenHelper {
    static final String DATABASE_NAME="jsonBase";
    static final String TABLE_NAME="json_table";

    static final String ID="_id";
    static final String YEAR="year";
    static final String DURATION="duration";
    static final String DIRECTOR="director";
    static final String CAST="cast";
    static final String IMAGE="image";
    static final String RATING="rating";
    static final String STORY="story";
    static final String MOVIE="movie";
    static final String TAGLINE="tagline";
    static final String CREATE_QUERY= "CREATE TABLE "+TABLE_NAME+" ("+ ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+YEAR +" INTEGER,"+DURATION+" TEXT,"+
            DIRECTOR+" TEXT,"+CAST+" TEXT,"+IMAGE+" TEXT,"+TAGLINE+" TEXT,"+ STORY+" TEXT,"+MOVIE+" TEXT,"+RATING+" INTEGER);";
    public myDatabase(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void INSERTDTA(Integer year,String duration,String director,String image,Integer rating,String story,String movie,String tagline){
      SQLiteDatabase sq = myDatabase.this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(YEAR,year);
        contentValues.put(DURATION,duration);
        contentValues.put(DIRECTOR,director);
        contentValues.put(IMAGE,image);
        contentValues.put(RATING,rating);
        contentValues.put(STORY,story);
        contentValues.put(MOVIE,movie);
        contentValues.put(TAGLINE, tagline);
        sq.insert(TABLE_NAME, null, contentValues);
    }
    public ArrayList<MovieModel> ReturnAll(){
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        SQLiteDatabase sq = myDatabase.this.getWritableDatabase();
       Cursor cursor= sq.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        if (cursor.getCount()==0){
            return null;
        }else {
            cursor.moveToFirst();
           do {
               int year =cursor.getInt(cursor.getColumnIndexOrThrow(YEAR));
                int rating = cursor.getInt(cursor.getColumnIndexOrThrow(RATING));
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(DURATION));
                String director = cursor.getString(cursor.getColumnIndexOrThrow(DIRECTOR));
                String image=cursor.getString(cursor.getColumnIndexOrThrow(IMAGE));
                String story=cursor.getString(cursor.getColumnIndexOrThrow(STORY));
                String movi=cursor.getString(cursor.getColumnIndexOrThrow(MOVIE));
                String tagline=cursor.getString(cursor.getColumnIndexOrThrow(TAGLINE));
                MovieModel movieModel = new MovieModel(year,rating,duration,director,tagline,image,story,movi);
                movieModels.add(movieModel);
            } while (cursor.moveToNext());
        }
        return movieModels;
    }
}
