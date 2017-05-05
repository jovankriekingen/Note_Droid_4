package jo.vk.notedroid4.model;

import java.util.ArrayList;

public class NoteDataSource {

    private static ArrayList<Note> notes;

    private NoteDataSource() {
        //blokeert initialisatie
    }

    public static ArrayList<Note> getNotes() {
        if(notes == null)
        {
            createNotes();
        }

        return notes;
    }

    public static void createNotes()
    {
        notes = new ArrayList<>();
    }

    public static void deleteNote(Note n)
    {
        getNotes().remove(n);
    }

    public static void insertNote(Note n)
    {
        getNotes().add(n);
    }

    public static void updateNote(Note oldN, Note newN)
    {
        getNotes().remove(oldN);
        getNotes().add(newN);
    }
}