package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.model.Note;

public class AddNoteActivity extends AppCompatActivity {

    Button addNoteButton;
    EditText titleEditText, contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        DBController dbController = new DBController(getApplicationContext());

        addNoteButton = findViewById(R.id.addNoteButton);
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        String date = "TempDate";


        addNoteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AddNoteActivity.this, "Button touched", Toast.LENGTH_SHORT).show();
                        dbController.addNote(
                                new Note(titleEditText.getText().toString(), contentEditText.getText().toString(), date)
                        );
                    }
                }
        );
    }
}