package com.example.pokedex;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Main_Activity extends AppCompatActivity {

    // UI elements
    private EditText nationalInput, nameInput, speciesInput, heightInput, weightInput, hpInput, attackInput, defenseInput;
    private RadioGroup genderGroup;
    private RadioButton maleButton, femaleButton, unknownButton;
    private Spinner levelSpinner;
    private Button resetButton, saveButton;

    // Text views for validation errors
    private TextView nationalText, nameText, speciesText, genderText, heightText, weightText, levelText, hpText, attackText, defenseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);

        // Initialize all UI elements
        initializeViews();

        // Set up level spinner
        setupLevelSpinner();

        // Set up button listeners
        setupButtonListeners();
    }

    private void initializeViews() {
        // Initialize EditText fields
        nationalInput = findViewById(R.id.nationalInput);
        nameInput = findViewById(R.id.nameInput);
        speciesInput = findViewById(R.id.speciesInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        hpInput = findViewById(R.id.hpInput);
        attackInput = findViewById(R.id.attackInput);
        defenseInput = findViewById(R.id.defenseInput);

        // Initialize RadioGroup and RadioButtons
        genderGroup = findViewById(R.id.genderGroup);
        maleButton = findViewById(R.id.maleButton);
        femaleButton = findViewById(R.id.femaleButton);
        unknownButton = findViewById(R.id.unknownButton);

        // Initialize Spinner
        levelSpinner = findViewById(R.id.spinner);

        // Initialize Buttons
        resetButton = findViewById(R.id.resetButton);
        saveButton = findViewById(R.id.saveButton);

        // Initialize TextViews for validation
        nationalText = findViewById(R.id.nationalText);
        nameText = findViewById(R.id.nameText);
        speciesText = findViewById(R.id.speciesText);
        genderText = findViewById(R.id.genderText);
        heightText = findViewById(R.id.heightText);
        weightText = findViewById(R.id.weightText);
        levelText = findViewById(R.id.levelText);
        hpText = findViewById(R.id.hpText);
        attackText = findViewById(R.id.attackText);
        defenseText = findViewById(R.id.defenseText);
    }

    private void setupLevelSpinner() {
        // Create a list of levels from 1 to 50
        List<String> levels = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            levels.add(String.valueOf(i));
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                levels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        // Reset button listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });

        // Save button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSave();
            }
        });
    }

    private void resetForm() {
        // Reset all input fields to default values
        nationalInput.setText("");
        nameInput.setText("");
        speciesInput.setText("");
        heightInput.setText("");
        weightInput.setText("");
        hpInput.setText("");
        attackInput.setText("");
        defenseInput.setText("");

        // Reset gender selection
        unknownButton.setChecked(true);

        // Reset spinner to first position
        levelSpinner.setSelection(0);

        // Reset text colors
        resetTextColors();

        Toast.makeText(this, "Form reset to default values", Toast.LENGTH_SHORT).show();
    }

    private void validateAndSave() {
        // Reset all text colors before validation
        resetTextColors();

        // List to collect error messages
        List<String> errors = new ArrayList<>();

        // Validate National Number
        String nationalStr = nationalInput.getText().toString().trim();
        if (nationalStr.isEmpty()) {
            nationalText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("National Number is required");
        } else {
            try {
                int nationalNum = Integer.parseInt(nationalStr);
                if (nationalNum < 0 || nationalNum > 1010) {
                    nationalText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("National Number must be between 0 and 1010");
                }
            } catch (NumberFormatException e) {
                nationalText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("National Number must be a valid integer");
            }
        }

        // Validate Name
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            nameText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Name is required");
        } else if (name.length() < 3 || name.length() > 12) {
            nameText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Name must be between 3 and 12 characters");
        } else if (!name.matches("[a-zA-Z. ]+")) {
            nameText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Name can only contain letters, dots, and spaces");
        }

        // Validate Species
        String species = speciesInput.getText().toString().trim();
        if (species.isEmpty()) {
            speciesText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Species is required");
        } else if (!species.matches("[a-zA-Z ]+")) {
            speciesText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Species can only contain letters and spaces");
        }

        // Validate Gender
        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            genderText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Gender selection is required");
        } else {
            RadioButton selectedGender = findViewById(selectedGenderId);
            String gender = selectedGender.getText().toString();
            if (!gender.equals("Male") && !gender.equals("Female")) {
                genderText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("Gender must be Male or Female");
            }
        }

        // Validate Height
        String heightStr = heightInput.getText().toString().trim();
        if (heightStr.isEmpty()) {
            heightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Height is required");
        } else {
            try {
                double height = Double.parseDouble(heightStr);
                if (height < 0.1 || height > 500.0) {
                    heightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("Height must be between 0.1 and 500.0 meters");
                }
            } catch (NumberFormatException e) {
                heightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("Height must be a valid number");
            }
        }

        // Validate Weight
        String weightStr = weightInput.getText().toString().trim();
        if (weightStr.isEmpty()) {
            weightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Weight is required");
        } else {
            try {
                double weight = Double.parseDouble(weightStr);
                if (weight < 0.1 || weight > 1000.0) {
                    weightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("Weight must be between 0.1 and 1000.0 kg");
                }
            } catch (NumberFormatException e) {
                weightText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("Weight must be a valid number");
            }
        }

        // Validate Level (spinner selection)
        if (levelSpinner.getSelectedItem() == null) {
            levelText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Level selection is required");
        }

        // Validate HP
        String hpStr = hpInput.getText().toString().trim();
        if (hpStr.isEmpty()) {
            hpText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("HP is required");
        } else {
            try {
                int hp = Integer.parseInt(hpStr);
                if (hp < 1 || hp > 500) {
                    hpText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("HP must be between 1 and 500");
                }
            } catch (NumberFormatException e) {
                hpText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("HP must be a valid integer");
            }
        }

        // Validate Attack
        String attackStr = attackInput.getText().toString().trim();
        if (attackStr.isEmpty()) {
            attackText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Attack is required");
        } else {
            try {
                int attack = Integer.parseInt(attackStr);
                if (attack < 0 || attack > 500) {
                    attackText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("Attack must be between 0 and 500");
                }
            } catch (NumberFormatException e) {
                attackText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("Attack must be a valid integer");
            }
        }

        // Validate Defense
        String defenseStr = defenseInput.getText().toString().trim();
        if (defenseStr.isEmpty()) {
            defenseText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            errors.add("Defense is required");
        } else {
            try {
                int defense = Integer.parseInt(defenseStr);
                if (defense < 10 || defense > 500) {
                    defenseText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    errors.add("Defense must be between 10 and 500");
                }
            } catch (NumberFormatException e) {
                defenseText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                errors.add("Defense must be a valid integer");
            }
        }

        if (errors.isEmpty()) {
            Toast.makeText(this, "Pokemon information stored in database", Toast.LENGTH_LONG).show();
        } else {
            StringBuilder errorMessage = new StringBuilder();
            for (String error : errors) {
                errorMessage.append(error).append("\n");
            }
            Toast.makeText(this, errorMessage.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void resetTextColors() {
        // Reset all text colors to default
        int defaultColor = getResources().getColor(android.R.color.black);
        nationalText.setTextColor(defaultColor);
        nameText.setTextColor(defaultColor);
        speciesText.setTextColor(defaultColor);
        genderText.setTextColor(defaultColor);
        heightText.setTextColor(defaultColor);
        weightText.setTextColor(defaultColor);
        levelText.setTextColor(defaultColor);
        hpText.setTextColor(defaultColor);
        attackText.setTextColor(defaultColor);
        defenseText.setTextColor(defaultColor);
    }
}