package com.example.pokedex;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static final String AUTHORITY = "com.example.pokedex.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_POKEMON = "pokemon";

    public static final class PokemonEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POKEMON).build();

        public static final String TABLE_NAME = "pokemon";

        public static final String COLUMN_NATIONAL_NUMBER = "national_number";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_HP = "hp";
        public static final String COLUMN_ATTACK = "attack";
        public static final String COLUMN_DEFENSE = "defense";
    }
}
