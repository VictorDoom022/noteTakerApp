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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.notetaker.adapter.DBController;
import com.example.notetaker.adapter.NoteListAdapter;
import com.example.notetaker.model.Note;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ArchivedNote extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Spinner spinnerSortTitle;
    RecyclerView noteListRecyclerView;

    List<Note> noteList;
    String[] sortItemChoice = {"Title Asc", "Title Desc", "Date Asc", "Date Desc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_note);
        spinnerSortTitle = findViewById(R.id.sortTitleSpinner);
        Toolbar toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Archive Note");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
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
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                startActivity(intent);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        DBController dbController = new DBController(getApplicationContext());

        noteListRecyclerView = findViewById(R.id.noteListRecyclerView);
        noteListRecyclerView.setHasFixedSize(true);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String selectedItem = adapterView.getItemAtPosition(i).toString();

        if(selectedItem == sortItemChoice[0]){
            noteList = dbController.getNoteByType(1,1);
        }else if(selectedItem == sortItemChoice[1]){
            noteList = dbController.getNoteByType(2,1);
        }else if(selectedItem == sortItemChoice[2]){
            noteList = dbController.getNoteByType(3,1);
        }else if(selectedItem == sortItemChoice[3]){
            noteList = dbController.getNoteByType(4,1);
        }

        NoteListAdapter adapter = new NoteListAdapter(ArchivedNote.this, noteList);
        noteListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.menu_option, menu);
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