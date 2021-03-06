package com.example.notetaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.adapter.NoteListAdapter;
import com.example.notetaker.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Spinner spinnerSortTitle;
    RecyclerView noteListRecyclerView;
    FloatingActionButton addNoteFloatingActionButton;
    EditText searchEditText;

    List<Note> noteList;
    String[] sortItemChoice = {"Default", "Title Asc", "Title Desc", "Date Asc", "Date Desc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        addNoteFloatingActionButton = findViewById(R.id.addNoteFloatingButton);
        spinnerSortTitle = findViewById(R.id.sortTitleSpinner);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        searchEditText = findViewById(R.id.searchEditText);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_notes:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case R.id.nav_archive:
                        intent = new Intent(getApplicationContext(), ArchivedNote.class);
                        break;
                    case R.id.nav_aboutApp:
                        intent = new Intent(getApplicationContext(), AboutAppActivity.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open_drawer, R.string.navigation_close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        spinnerSortTitle.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, sortItemChoice);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSortTitle.setAdapter(arrayAdapter);

        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);
        noteListRecyclerView.setHasFixedSize(true);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBController dbController = new DBController(getApplicationContext());

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noteList = dbController.getNoteByType(0,0);

                NoteListAdapter adapter = new NoteListAdapter(MainActivity.this, noteList);
                noteListRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(searchEditText.getText().toString().length() != 0){
                    noteList = dbController.searchNote(searchEditText.getText().toString());
                    displayData(noteList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

    public void displayData(List<Note> notes){
        NoteListAdapter adapter = new NoteListAdapter(MainActivity.this, notes);
        noteListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedItem = adapterView.getItemAtPosition(i).toString();
        DBController dbController = new DBController(getApplicationContext());

        if(selectedItem == sortItemChoice[0]){
            noteList = dbController.getNoteByType(0,0);
        }else if(selectedItem == sortItemChoice[1]){
            noteList = dbController.getNoteByType(1,0);
        }else if(selectedItem == sortItemChoice[2]){
            noteList = dbController.getNoteByType(2,0);
        }else if(selectedItem == sortItemChoice[3]){
            noteList = dbController.getNoteByType(3,0);
        }else  if(selectedItem == sortItemChoice[4]){
            noteList = dbController.getNoteByType(4,0);
        }
        displayData(noteList);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.aboutAppItemButton){
            Intent intent = new Intent(getApplicationContext(), AboutAppActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}