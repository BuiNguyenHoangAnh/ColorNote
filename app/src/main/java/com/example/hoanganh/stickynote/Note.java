package com.example.hoanganh.stickynote;

import java.io.Serializable;

        import java.io.Serializable;

public class Note implements Serializable {

    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String noteDate;
    private String noteColor;
    public Note()  {

    }

    public Note(  String noteTitle, String noteContent,String noteDate,String noteColor) {
        this.noteTitle= noteTitle;
        this.noteContent= noteContent;
        this.noteDate=noteDate;
        this.noteColor=noteColor;
    }

    public Note(int noteId, String noteTitle, String noteContent,String noteDate,String noteColor) {
        this.noteId= noteId;
        this.noteTitle= noteTitle;
        this.noteContent= noteContent;
        this.noteDate=noteDate;
        this.noteColor=noteColor;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
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

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

}
