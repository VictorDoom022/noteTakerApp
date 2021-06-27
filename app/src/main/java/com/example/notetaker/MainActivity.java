package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.adapter.NoteListAdapter;
import com.example.notetaker.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerSortTitle;
    RecyclerView noteListRecyclerView;
    FloatingActionButton addNoteFloatingActionButton;

    List<Note> noteList;
    String[] sortItemChoice = {"Default", "Title Asc", "Title Desc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        addNoteFloatingActionButton = findViewById(R.id.addNoteFloatingButton);
        spinnerSortTitle = findViewById(R.id.sortTitleSpinner);

        spinnerSortTitle.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, sortItemChoice);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSortTitle.setAdapter(arrayAdapter);

        addNoteFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        DBController dbController = new DBController(getApplicationContext());

        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);
        noteListRecyclerView.setHasFixedSize(true);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String selectedItem = adapterView.getItemAtPosition(i).toString();

        if(selectedItem == sortItemChoice[0]){
            noteList = dbController.getAllNote();
        }else if(selectedItem == sortItemChoice[1]){
            noteList = dbController.getNoteByType(1);
        }else if(selectedItem == sortItemChoice[2]){
            noteList = dbController.getNoteByType(2);
        }

        NoteListAdapter adapter = new NoteListAdapter(MainActivity.this, noteList);
        noteListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
}