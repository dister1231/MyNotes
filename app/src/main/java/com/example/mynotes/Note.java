package com.example.mynotes;

public class Note implements Comparable<Note> {

    private final int id;
    private final String text;
    private final String date;

    public Note(int id, String text, String date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Note o) {
        return o.getNumDate() - this.getNumDate();
    }

    private int getNumDate() {
        String[] partsOfDate = date.split("\\.");
        StringBuilder stringDate = new StringBuilder();
        for(String part: partsOfDate) {
            stringDate.append(part);
        }
        return Integer.parseInt(stringDate.toString());
    }
}
