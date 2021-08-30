package com.example.notetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.model.Note;
import com.google.android.material.textfield.TextInputEditText;

public class EditNoteActivity extends AppCompatActivity {

    Button editNoteButton;
    TextInputEditText editTitleEditText, editContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Note");

        Intent intent = getIntent();
        int noteID = intent.getIntExtra("NOTE_ID", 0);

        DBController dbController = new DBController(getApplicationContext());
        Note note = dbController.getNoteByID(noteID);

        editNoteButton = findViewById(R.id.editNoteButton);
        editTitleEditText = findViewById(R.id.editNoteTitleEditText);
        editContentEditText = findViewById(R.id.editNoteContentEditText);

        editTitleEditText.setText(note.getNoteTitle());
        editContentEditText.setText(note.getNoteContent());

        editNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditNoteActivity.this, "Note Edited", Toast.LENGTH_SHORT).show();
                dbController.updateNote(
                        new Note(note.getNoteID(), editTitleEditText.getText().toString(), editContentEditText.getText().toString(),note.getNoteAddDate(), note.getNoteIsArchived(), note.getNoteIsPinned(), note.getNoteColor())
                );
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Intent intent = getIntent();
        int noteID = intent.getIntExtra("NOTE_ID", 0);

        DBController dbController = new DBController(getApplicationContext());
        Note note = dbController.getNoteByID(noteID);

        if(id == R.id.archiveNoteButton){
            dbController.archiveNote(noteID, note.getNoteIsArchived());
            Intent intentForRedirect = new Intent(getApplicationContext(), MainActivity.class);
            intentForRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentForRedirect);
            finish();
        }else if(id == R.id.deleteNoteButton){
            confirmDeleteDialog(note);
        }else if(id == R.id.pinNoteButton){
            dbController.pinNote(noteID, note.getNoteIsPinned());
            Intent intentForRedirect = new Intent(getApplicationContext(), MainActivity.class);
            intentForRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentForRedirect);
            finish();
        }else if(id == R.id.changeColorDialogButton){
            selectColorDialog(noteID);
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectColorDialog(int noteID){
        String[] colors = {"Default", "Blue", "Green", "Red"};
        String[] colorHex = {"#e8dff0", "#1ff4ff", "#52ff94", "#ff895e"};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Pick a color");
        alertDialogBuilder.setItems(colors, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBController dbController = new DBController(getApplicationContext());
                dbController.changeNoteColor(noteID, colorHex[i]);
                Intent intentForRedirect = new Intent(getApplicationContext(), MainActivity.class);
                intentForRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForRedirect);
                finish();
            }
        });
        alertDialogBuilder.show();
    }

    public void confirmDeleteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditNoteActivity.this);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DBController dbController = new DBController(getApplicationContext());
                dbController.deleteNote(note);
                Intent intentForRedirect = new Intent(getApplicationContext(), MainActivity.class);
                intentForRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentForRedirect);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
            }
        });
        builder.show();
    }
}