package jo.vk.notedroid4.util;

import java.util.Comparator;

import jo.vk.notedroid4.model.Note;

/**
 * Created by jo on 03/05/2017.
 */

public class NoteSorters {

    //comperators
    private static Comparator<Note> alfabeticNotes = new Comparator<Note>() {
        @Override
        public int compare(Note lhs, Note rhs) {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    };

    private static Comparator<Note> oldestNotesFirst = new Comparator<Note>() {
        @Override
        public int compare(Note lhs, Note rhs) {
            return rhs.getPublishDate().compareTo(lhs.getPublishDate());
        }
    };

    private static Comparator<Note> newestNotesFirst = new Comparator<Note>() {
        @Override
        public int compare(Note lhs, Note rhs) {
            return lhs.getPublishDate().compareTo(rhs.getPublishDate());
        }
    };

    //get comperators
    public static Comparator<Note> getAlfabeticNotes() {
        return alfabeticNotes;
    }
    public static Comparator<Note> getOldestNotesFirst() {
        return oldestNotesFirst;
    }
    public static Comparator<Note> getNewestNotesFirst() {
        return newestNotesFirst;
    }
}