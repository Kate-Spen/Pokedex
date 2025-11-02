package com.example.pokedex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PokemonDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pokedex.db";
    private static final int DATABASE_VERSION = 1;

    public PokemonDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TABLE =
                "CREATE TABLE " + DBContract.PokemonEntry.TABLE_NAME + " (" +
                        DBContract.PokemonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER + " INTEGER UNIQUE, " +
                        DBContract.PokemonEntry.COLUMN_NAME + " TEXT, " +
                        DBContract.PokemonEntry.COLUMN_SPECIES + " TEXT, " +
                        DBContract.PokemonEntry.COLUMN_GENDER + " TEXT, " +
                        DBContract.PokemonEntry.COLUMN_HEIGHT + " REAL, " +
                        DBContract.PokemonEntry.COLUMN_WEIGHT + " REAL, " +
                        DBContract.PokemonEntry.COLUMN_LEVEL + " INTEGER, " +
                        DBContract.PokemonEntry.COLUMN_HP + " INTEGER, " +
                        DBContract.PokemonEntry.COLUMN_ATTACK + " INTEGER, " +
                        DBContract.PokemonEntry.COLUMN_DEFENSE + " INTEGER" +
                        ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.PokemonEntry.TABLE_NAME);
        onCreate(db);
    }
}

