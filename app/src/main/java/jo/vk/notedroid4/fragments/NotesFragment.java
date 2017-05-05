package jo.vk.notedroid4.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import jo.vk.notedroid4.R;
import jo.vk.notedroid4.activities.NoteDetailsActivity;
import jo.vk.notedroid4.adapters.NotesAdapter;
import jo.vk.notedroid4.model.Note;
import jo.vk.notedroid4.model.NoteDataSource;

public class NotesFragment extends Fragment {

    private ListView lv;
    private NotesAdapter notesAdapter;
    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Note n = (Note) parent.getItemAtPosition(position);

            Intent i = new Intent(getActivity(), NoteDetailsActivity.class);
            i.putExtra("isNew", false);
            i.putExtra("selectedNote", n);
            startActivity(i);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notes, container, false);

        lv = (ListView)v.findViewById(R.id.notes_lv);

        notesAdapter = new NotesAdapter(getActivity(), NoteDataSource.getNotes());

        lv.setAdapter(notesAdapter);
        lv.setOnItemClickListener(onItemClickListener);
        registerForContextMenu(lv);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fragments toegevoegd aan activity,
        // dit is nodig om menu van een fragment aan menu van de activity te mogen toevoegen
        setHasOptionsMenu(true);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {
        super.onStart();
        //bij verschijnen scherm lijst laten updaten
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.actions_note, menu);

        MenuItem item = menu.findItem(R.id.mi_search_view);
        SearchView sv = (SearchView) item.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                notesAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notesAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.new_item)
        {
            Intent i = new Intent(getActivity(), NoteDetailsActivity.class);
            i.putExtra("isNew", true);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.context_main, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.delete_item :
                Note n = (Note) notesAdapter.getItem(info.position);
                //verwijder uit datasource
                NoteDataSource.deleteNote(n);
                //verwijder uit listview
                notesAdapter.remove(n);
                notesAdapter.notifyDataSetChanged();

                break;
        }
        return super.onContextItemSelected(item);
    }
}