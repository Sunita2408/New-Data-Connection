package com.sunita.mynote.dto.notes;

public class UpdateNoteRequestBody {
    private Long noteId;
    private String title;
    private String content;

    public Long getNoteId() {
        return noteId;
    }

    // Setter for noteId
    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }

}
