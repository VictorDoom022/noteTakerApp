package com.example.notetaker.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.notetaker.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBController extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "testOne";
  private static final String TABLE_NAME = "note";

  private static final String KEY_ID = "id";
  private static final String KEY_TITLE = "title";
  private static final String KEY_CONTENT = "content";
  private static final String KEY_ADDDATE = "addDate";

  public DBController(Context context){
      super(context, DATABASE_NAME,null,DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_NOTE_TABLE =
        "CREATE TABLE " + TABLE_NAME +
        "(" +
            KEY_ID + "INTEGER PRIMARY KEY," +
            KEY_TITLE + "TEXT," +
            KEY_CONTENT + "TEXT," +
            KEY_ADDDATE + "TEXT" +
        ")";

    sqLiteDatabase.execSQL(CREATE_NOTE_TABLE);
  }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop old table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
}
