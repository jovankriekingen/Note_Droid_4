package jo.vk.notedroid4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import jo.vk.notedroid4.R;
import jo.vk.notedroid4.model.Note;
import jo.vk.notedroid4.model.NoteDataSource;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText title, content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_screen);

        title = (EditText)findViewById(R.id.title_et);
        content = (EditText)findViewById(R.id.content_et);

        if(!getIntent().getExtras().getBoolean("isNew"))
        {
            Note n = getIntent().getParcelableExtra("selectedNote");

            title.setText(n.getTitle());
            content.setText(n.getContent());
        }

    }

    private String currentDateAsString()
    {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        return sdf.format(now);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.actions_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        if(item.getItemId() == R.id.save_item)
        {
            //new
            if(getIntent().getExtras().getBoolean("isNew"))
            {
                Note n = new Note(title.getText().toString(), content.getText().toString(), currentDateAsString());
                NoteDataSource.insertNote(n);
            }

            //update
            if(!getIntent().getExtras().getBoolean("isNew"))
            {
                Note oldN = getIntent().getParcelableExtra("selectedNote");
                Note newNote = new Note(oldN.getId(),
                        title.getText().toString(),
                        content.getText().toString(),
                        oldN.getPublishDate(),
                        oldN.getLastModifiedDate());

                NoteDataSource.updateNote(oldN, newNote);
            }

            //sowieso terug naar lijst gaan, onCreate() zal lijst opnieuw maken
            Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}