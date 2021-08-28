package com.example.notetaker.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.notetaker.model.Note;

import java.util.ArrayList;
import java.util.List;

public class DBController extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "testSeven";
  private static final String TABLE_NAME = "note";

  private static final String KEY_ID = "id";
  private static final String KEY_TITLE = "title";
  private static final String KEY_CONTENT = "content";
  private static final String KEY_ADDDATE = "addDate";
  private static final String KEY_ISARCHIVE = "isArchive";
  private static final String KEY_ISPIN = "isPin";

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
            KEY_ADDDATE + " TEXT," +
            KEY_ISARCHIVE + " INTEGER, " +
            KEY_ISPIN + " INTEGER " +
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
        contentValues.put(KEY_ISARCHIVE, 0);
        contentValues.put(KEY_ISPIN, 0);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public Note getNoteByID(int id){
      SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
            TABLE_NAME,
            new String[]{
                KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_ADDDATE, KEY_ISARCHIVE, KEY_ISPIN
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
            cursor.getString(3),
            cursor.getInt(4),
            cursor.getInt(5)
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
                      cursor.getString(3),
                      cursor.getInt(4),
                      cursor.getInt(5)
              );

              noteList.add(note);
          }while (cursor.moveToNext());
      }

      return noteList;
    }

  public List<Note> getNoteByType(int type, int isArchive){
    List<Note> noteList = new ArrayList<Note>();

    // Type 0 = Default (Pinned)
    // Type 1 = Title ASC
    // Type 2 = Title DESC
    // Type 3 = Date ASC
    // Type 4 = Date Desc
    // Type 5 = Unarchived
    // Type 6 = Archived
    String queryOrderColumn = type==1||type==2 ? KEY_TITLE : type==0 ? KEY_ISPIN :KEY_ADDDATE;
    String querySortString = type == 1||type==3 ? " ASC " : " DESC ";
    int queryArchive = isArchive==0 ? 0 : 1 ;

    String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ISARCHIVE + "=" + queryArchive +" ORDER BY " + queryOrderColumn + querySortString;

    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(query,null);

    if (cursor.moveToFirst()){
      do {
        Note note = new Note(
                Integer.parseInt(cursor.getString(0))  ,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
        );

        noteList.add(note);
      }while (cursor.moveToNext());
    }

    return noteList;
  }

  public List<Note> searchNote(String searchTerm){
    List<Note> noteList = new ArrayList<Note>();

    String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " LIKE " + "'%" + searchTerm + "%'";

    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery(query,null);

    if (cursor.moveToFirst()){
      do {
        Note note = new Note(
                Integer.parseInt(cursor.getString(0))  ,
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
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

    public int archiveNote(int noteID, int archiveStatus){
      SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

      int archiveCode = archiveStatus == 0 ? 1 : 0;

      ContentValues contentValues = new ContentValues();
      contentValues.put(KEY_ISARCHIVE, archiveCode);

      return sqLiteDatabase.update(
              TABLE_NAME, contentValues,
              KEY_ID + "=?",
              new String[] {String.valueOf((noteID))}
      );
  }

  public int pinNote(int noteID, int pinStatus){
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    int archiveCode = pinStatus == 0 ? 1 : 0;

    ContentValues contentValues = new ContentValues();
    contentValues.put(KEY_ISPIN, archiveCode);

    return sqLiteDatabase.update(
            TABLE_NAME, contentValues,
            KEY_ID + "=?",
            new String[] {String.valueOf((noteID))}
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
