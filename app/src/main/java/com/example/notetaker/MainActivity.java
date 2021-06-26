package com.example.notetaker;

import androidx.appcompat.app.AppCompatActivity;
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

    String[] sortItemChoice = {"Title Asc", "Title Desc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBController dbController = new DBController(getApplicationContext());

        addNoteFloatingActionButton = findViewById(R.id.addNoteFloatingButton);
        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);
        spinnerSortTitle = findViewById(R.id.sortTitleSpinner);

        spinnerSortTitle.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, sortItemChoice);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSortTitle.setAdapter(arrayAdapter);

        noteListRecyclerView.setHasFixedSize(true);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Note> noteList = dbController.getAllNote();
        NoteListAdapter adapter = new NoteListAdapter(MainActivity.this, noteList);
        noteListRecyclerView.setAdapter(adapter);

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
        String selectedItem = adapterView.getItemAtPosition(i).toString();

        Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
}