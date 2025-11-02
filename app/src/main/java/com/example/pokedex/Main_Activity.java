package com.example.pokedex;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Main_Activity extends AppCompatActivity {

    private EditText nationalInput, nameInput, speciesInput, heightInput, weightInput, hpInput, attackInput, defenseInput;
    private RadioGroup genderGroup;
    private Spinner levelSpinner;
    private Button saveButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nationalInput = findViewById(R.id.nationalInput);
        nameInput = findViewById(R.id.nameInput);
        speciesInput = findViewById(R.id.speciesInput);
        genderGroup = findViewById(R.id.genderGroup);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        levelSpinner = findViewById(R.id.spinner);
        hpInput = findViewById(R.id.hpInput);
        attackInput = findViewById(R.id.attackInput);
        defenseInput = findViewById(R.id.defenseInput);
        saveButton = findViewById(R.id.saveButton);
        resetButton = findViewById(R.id.resetButton);

        saveButton.setOnClickListener(v -> savePokemon());
        resetButton.setOnClickListener(v -> clearForm());
        Button btnViewList = findViewById(R.id.btnViewList);
        btnViewList.setOnClickListener(v -> {
            Intent i = new Intent(Main_Activity.this, PokemonListActivity.class);
            startActivity(i);
        });

    }

    private void savePokemon() {

        String nationalStr = nationalInput.getText().toString().trim();
        if (nationalStr.isEmpty()) {
            showDialog("Missing Data", "National number is required.");
            return;
        }

        int nationalNumber = Integer.parseInt(nationalStr);

        Cursor cursor = getContentResolver().query(
                DBContract.PokemonEntry.CONTENT_URI,
                null,
                DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER + "=?",
                new String[]{String.valueOf(nationalNumber)},
                null
        );

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            showDialog("Duplicate Entry", "A Pokémon with National Number #" + nationalNumber + " already exists.");
            return;
        }
        if (cursor != null) cursor.close();

        String name = nameInput.getText().toString().trim();
        String species = speciesInput.getText().toString().trim();
        String gender = ((RadioButton) findViewById(genderGroup.getCheckedRadioButtonId())).getText().toString();
        double height = heightInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(heightInput.getText().toString());
        double weight = weightInput.getText().toString().isEmpty() ? 0 : Double.parseDouble(weightInput.getText().toString());
        int level = Integer.parseInt(levelSpinner.getSelectedItem().toString());
        int hp = hpInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(hpInput.getText().toString());
        int attack = attackInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(attackInput.getText().toString());
        int defense = defenseInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(defenseInput.getText().toString());

        ContentValues values = new ContentValues();
        values.put(DBContract.PokemonEntry.COLUMN_NATIONAL_NUMBER, nationalNumber);
        values.put(DBContract.PokemonEntry.COLUMN_NAME, name);
        values.put(DBContract.PokemonEntry.COLUMN_SPECIES, species);
        values.put(DBContract.PokemonEntry.COLUMN_GENDER, gender);
        values.put(DBContract.PokemonEntry.COLUMN_HEIGHT, height);
        values.put(DBContract.PokemonEntry.COLUMN_WEIGHT, weight);
        values.put(DBContract.PokemonEntry.COLUMN_LEVEL, level);
        values.put(DBContract.PokemonEntry.COLUMN_HP, hp);
        values.put(DBContract.PokemonEntry.COLUMN_ATTACK, attack);
        values.put(DBContract.PokemonEntry.COLUMN_DEFENSE, defense);

        Uri newUri = getContentResolver().insert(DBContract.PokemonEntry.CONTENT_URI, values);

        if (newUri != null) {
            showDialog("Success!", "Pokémon saved to your Pokédex!");
            clearForm();
        }
    }

    private void clearForm() {
        nationalInput.setText("");
        nameInput.setText("");
        speciesInput.setText("");
        genderGroup.check(R.id.unknownButton);
        heightInput.setText("");
        weightInput.setText("");
        levelSpinner.setSelection(0);
        hpInput.setText("");
        attackInput.setText("");
        defenseInput.setText("");
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}
