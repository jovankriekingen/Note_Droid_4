package jo.vk.notedroid4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Jo on 7/05/2017.
 */

public class NoteDAO {

    //singleton
    public static final NoteDAO INSTANCE = new NoteDAO();
    //variabelen
    private DBHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public void openConnection (Context context){
        mDbHelper = new DBHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public  boolean insertNote (Note newNote){
        if (mDatabase == null)
            return false;

        //values -> key/value pairs
        //key == column name
        //value == value to insert/update for that column
        ContentValues mValues = new ContentValues();
        //giving null as value to an autoincrement column will generate a new id
        mValues.put(DBContract.NOTES_TITLE, newNote.getTitle());
        mValues.put(DBContract.NOTES_DESCRIPTION, newNote.getContent());
        mValues.put(DBContract.NOTE_PUBLISHDATE, newNote.getPublishDate());
        mValues.put(DBContract.NOTE_LASTMODIFIEDDATE, newNote.getLastModifiedDate());
        //insertmethode
        long resultID = mDatabase.insert(DBContract.TABLE_NOTES, null, mValues);

        if (resultID == -1)
            return false;
        else
            return true;


    }

    public void updateNote(Note oldNote){
        ContentValues mValues = new ContentValues();

        mValues.put(DBContract._ID, oldNote.getId());
        mValues.put(DBContract.NOTES_TITLE, oldNote.getTitle());
        mValues.put(DBContract.NOTES_DESCRIPTION, oldNote.getContent());
        mValues.put(DBContract.NOTE_PUBLISHDATE, oldNote.getPublishDate());
        mValues.put(DBContract.NOTE_LASTMODIFIEDDATE, oldNote.getLastModifiedDate());

        long idToUpdate = oldNote.getId();

        mDatabase.update(DBContract.TABLE_NOTES, //which table needs to update
                mValues, // what are the new values
                DBContract._ID + " = " + idToUpdate, //where clausule, don't update every row but only rows with the id
                null);//if using args (?) this has the values for the args
    }

    public boolean deleteNote(Note oldNote){
        if(mDatabase == null)
            return false;

        long idToRemove = oldNote.getId();

        int nrOfRemovedRows = mDatabase.delete(DBContract.TABLE_NOTES, DBContract._ID + " = " + idToRemove, null);
        //prevents SQL injection : veiliger
        //String [] arguments = {DBContract._ID, String.valueOf(idToRemove)};
        //int nrOfRemovedRows = mDatabase.delete(DBContract.TABLE_COMMENTS, "? = ?", arguments);

        if(nrOfRemovedRows == 0)
            return false;
        else
            return true;
    }

    public ArrayList<Note> getAllNotes(){

        ArrayList<Note> notes = new ArrayList<>();

        //Cursor = resultset met verwijzing naar specifieke rij in resultatenset
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DBContract.TABLE_NOTES, null );
        //zeker zijn dat we op de eerste rij starten
        mCursor.moveToFirst();
        //alle rijen overlopen
        //loopen zolang de laatste rij nog niet is verwerkt
        while (!mCursor.isAfterLast()){

            //comment maken van huidige rij
            Note temp = new Note();

            int idIndex = mCursor.getColumnIndex(DBContract._ID);
            temp.setId(mCursor.getLong(idIndex));

            int titleIndex = mCursor.getColumnIndex(DBContract.NOTES_TITLE);
            temp.setTitle(mCursor.getString(titleIndex));

            int contentIndex = mCursor.getColumnIndex(DBContract.NOTES_DESCRIPTION);
            temp.setContent(mCursor.getString(contentIndex));

            int publishDateIndex = mCursor.getColumnIndex(DBContract.NOTE_PUBLISHDATE);
            temp.setPublishDate(mCursor.getString(publishDateIndex));

            int lastModifiedDateIndex = mCursor.getColumnIndex(DBContract.NOTE_LASTMODIFIEDDATE);
            temp.setLastModifiedDate(mCursor.getString(lastModifiedDateIndex));

            notes.add(temp);

            //niet vergeten naar volgende rij te gaan, anders oneindige loop
            mCursor.moveToNext();
        }
        return notes;
    }
}
