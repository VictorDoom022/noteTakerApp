package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button showToastDataButton;
    FloatingActionButton addNoteFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBController dbController = new DBController(getApplicationContext());

        addNoteFloatingActionButton = findViewById(R.id.addNoteFloatingButton);
        showToastDataButton = findViewById(R.id.showNoteDataButton);

        showToastDataButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        List<Note> noteList = dbController.getAllNote();
                        String data="";

                        for (Note note: noteList){
                            data += note.getNoteTitle() + "\n";
                        }

                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        addNoteFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, "Button touched", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}