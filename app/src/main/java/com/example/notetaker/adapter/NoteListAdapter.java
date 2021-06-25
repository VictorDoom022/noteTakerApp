package com.example.notetaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.noteContentTextView.setText(note.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitleTextView, noteContentTextView;

        public NoteViewHolder(View itemView){
            super(itemView);

            noteTitleTextView = itemView.findViewById(R.id.noteTitleTextView);
            noteContentTextView = itemView.findViewById(R.id.noteContentTextView);
        }
    }
}
