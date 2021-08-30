package com.example.notetaker.model;

public class Note {
    private int noteID;
    private String noteTitle;
    private String noteContent;
    private String noteAddDate;
    private int noteIsArchived;
    private int noteIsPinned;
    private String noteColor;

    public Note() { };

    public Note(String noteTitle, String noteContent, String noteAddDate) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteAddDate = noteAddDate;
    }

    public Note(int noteID, String noteTitle, String noteContent, String noteAddDate, int noteIsArchived, int noteIsPinned, String noteColor) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteAddDate = noteAddDate;
        this.noteIsArchived = noteIsArchived;
        this.noteIsPinned = noteIsPinned;
        this.noteColor = noteColor;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

    public int getNoteIsArchived() {
        return noteIsArchived;
    }

    public void setNoteIsArchived(int noteIsArchived) {
        this.noteIsArchived = noteIsArchived;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteAddDate() {
        return noteAddDate;
    }

    public void setNoteAddDate(String noteAddDate) {
        this.noteAddDate = noteAddDate;
    }

    public int getNoteIsPinned() {
        return noteIsPinned;
    }

    public void setNoteIsPinned(int noteIsPinned) {
        this.noteIsPinned = noteIsPinned;
    }
}
