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
  private static final String DATABASE_NAME = "testFive";
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
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_TITLE + " TEXT," +
            KEY_CONTENT + " TEXT," +
            KEY_ADDDATE + " TEXT" +
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

    public void addNote(Note note){
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_TITLE, note.getNoteTitle());
        contentValues.put(KEY_CONTENT, note.getNoteContent());
        contentValues.put(KEY_ADDDATE, note.getNoteAddDate());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public Note getNoteByID(int id){
      SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
            TABLE_NAME,
            new String[]{
                KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_ADDDATE
            },
            KEY_ID + "=?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null,
            null
        );

        if (cursor != null){
            cursor.moveToFirst();
        }

        Note note = new Note(
            Integer.parseInt(cursor.getString(0)),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3)
        );

        return note;
    }

    public List<Note> getAllNote(){
      List<Note> noteList = new ArrayList<Note>();

      String query = "SELECT * FROM " + TABLE_NAME;

      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      Cursor cursor = sqLiteDatabase.rawQuery(query,null);

      if (cursor.moveToFirst()){
          do {
              Note note = new Note(
                      Integer.parseInt(cursor.getString(0))  ,
                      cursor.getString(1),
                      cursor.getString(2),
                      cursor.getString(3)
              );

              noteList.add(note);
          }while (cursor.moveToNext());
      }

      return noteList;
    }

  public List<Note> getNoteByType(int type){
    List<Note> noteList = new ArrayList<Note>();

    // Type 1 = Title ASC
    // Type 2 = Title DESC
    // Type 3 = Date ASC
    // Type 4 = Date Desc
    String queryOrderColumn = type==1||type==2 ? KEY_TITLE : KEY_ADDDATE;
    String querySortString = type == 1||type==3 ? " ASC " : " DESC ";

    String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + queryOrderColumn + querySortString;

    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(query,null);

    if (cursor.moveToFirst()){
      do {
        Note note = new Note(
                Integer.parseInt(cursor.getString(0))  ,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );

        noteList.add(note);
      }while (cursor.moveToNext());
    }

    return noteList;
  }

    public int updateNote(Note note){
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

      ContentValues contentValues = new ContentValues();
      contentValues.put(KEY_TITLE, note.getNoteTitle());
      contentValues.put(KEY_CONTENT, note.getNoteContent());
      contentValues.put(KEY_ADDDATE, note.getNoteAddDate());

      return sqLiteDatabase.update(
        TABLE_NAME, contentValues,
        KEY_ID + "=?",
        new String[] {String.valueOf(note.getNoteID())}
      );
    }

    public void deleteNote(Note note){
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      sqLiteDatabase.delete(
        TABLE_NAME,
        KEY_ID + "=?",
        new String[]{String.valueOf(note.getNoteID())}
      );
      sqLiteDatabase.close();
    }

    public int getAllNoteCount(){
      String query = "SELECT * FROM " + TABLE_NAME;
      SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
      Cursor cursor = sqLiteDatabase.rawQuery(query, null);
      cursor.close();

      return cursor.getCount();
    }
}
