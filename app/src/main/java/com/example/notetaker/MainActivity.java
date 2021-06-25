package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.adapter.NoteListAdapter;
import com.example.notetaker.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView noteListRecyclerView;
    FloatingActionButton addNoteFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBController dbController = new DBController(getApplicationContext());

        addNoteFloatingActionButton = findViewById(R.id.addNoteFloatingButton);
        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);

        noteListRecyclerView.setHasFixedSize(true);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Note> noteList = dbController.getAllNote();
        NoteListAdapter adapter = new NoteListAdapter(MainActivity.this, noteList);
        noteListRecyclerView.setAdapter(adapter);

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