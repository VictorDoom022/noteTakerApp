package com.example.notetaker.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notetaker.EditNoteActivity;
import com.example.notetaker.R;
import com.example.notetaker.model.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> noteList;

    public NoteListAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, null);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {

        Note note = noteList.get(position);

        holder.noteTitleTextView.setText(note.getNoteTitle());
        holder.noteDateTextView.setText(note.getNoteAddDate());

        if(note.getNoteIsPinned() == 0){
            holder.notePinImageView.setVisibility(View.GONE);
        }

        holder.relativeLayoutItem.setBackgroundColor(Color.parseColor(note.getNoteColor()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                confirmDeleteDialog(note);
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("NOTE_ID", note.getNoteID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayoutItem;
        TextView noteTitleTextView, noteDateTextView;
        ImageView notePinImageView;

        public NoteViewHolder(View itemView){
            super(itemView);

            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            noteDateTextView = itemView.findViewById(R.id.noteDateTextView);
            notePinImageView = itemView.findViewById(R.id.pin_logo);
            relativeLayoutItem = itemView.findViewById(R.id.relativeLayoutItem);
        }
    }

    public void confirmDeleteDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DBController dbController = new DBController(context);
                        dbController.deleteNote(note);
                        ((Activity)context).recreate();
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
