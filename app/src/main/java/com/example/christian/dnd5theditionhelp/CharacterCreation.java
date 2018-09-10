package com.example.christian.dnd5theditionhelp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CharacterCreation extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        Spinner spinner = (Spinner) findViewById(R.id.raceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.races_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                System.out.println("##TEST: "+ parent.getItemAtPosition(pos));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("##TEST: "+ "NOTHING SELECTED");
            }


        });

    }


}

