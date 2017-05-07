package jo.vk.notedroid4.model;

import android.provider.BaseColumns;

/**
 * Created by Jo on 7/05/2017.
 */

public class DBContract implements BaseColumns {

    //DB name
    public static final String DB_NAME = "dbnotes";

    //table names
    public static final String TABLE_NOTES = "notes";

    //column names
    public static final String NOTES_TITLE = "title";
    public static final String NOTES_DESCRIPTION = "description";
    public static final String NOTE_PUBLISHDATE ="publishdate";
    public static final String NOTE_LASTMODIFIEDDATE="lastmodifieddate";

    //all columns from table comment
    public static final String[] TABLE_NOTES_COLUMNS = {_ID, NOTES_TITLE, NOTES_DESCRIPTION, NOTE_PUBLISHDATE, NOTE_LASTMODIFIEDDATE};
}
