package com.example.pokedex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class PokemonListActivity extends AppCompatActivity {

    PokemonDBHelper dbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_list);

        listView = findViewById(R.id.pokemonListView);
        dbHelper = new PokemonDBHelper(this);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            new AlertDialog.Builder(PokemonListActivity.this)
                    .setTitle("Delete Entry")
                    .setMessage("Are you sure you want to delete this Pokémon?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        Uri deleteUri = ContentUris.withAppendedId(DBContract.PokemonEntry.CONTENT_URI, id);
                        getContentResolver().delete(deleteUri, null, null);
                        loadData();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });

        loadData();
    }

    private void loadData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DBContract.PokemonEntry.TABLE_NAME,
                new String[]{
                        DBContract.PokemonEntry._ID,
                        DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER,
                        DBContract.PokemonEntry.COLUMN_NAME
                },
                null, null, null, null,
                DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER + " ASC"
        );

        String[] fromColumns = {
                DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER,
                DBContract.PokemonEntry.COLUMN_NAME
        };

        int[] toViews = {
                R.id.textNational,
                R.id.textName
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.pokemon_list,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listView.setAdapter(adapter);
    }
}
