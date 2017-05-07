package jo.vk.notedroid4.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jo on 7/05/2017.
 */


//this class manages the connection to our sqlite database
//has methods to get reference, create the db or update
public final class DBHelper extends SQLiteOpenHelper {

    //constructor
    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase mDatabase) {

        String createStatement = "CREATE table " + DBContract.TABLE_NOTES + " ( "
                + DBContract._ID + " Integer primary key autoincrement, "
                + DBContract.NOTES_TITLE + " text not null, "
                + DBContract.NOTES_DESCRIPTION + " text not null "
                + DBContract.NOTE_LASTMODIFIEDDATE + " text not null "
                + DBContract.NOTE_PUBLISHDATE + " text not null "
                + ")";

        mDatabase.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase mDatabase, int oldVersion, int newVersion) {

    }
}
