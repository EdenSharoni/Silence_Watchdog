package com.example.silencewatchdog;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SoundControllerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner sound_selector;
    private Button backBtn;
    private ArrayAdapter<CharSequence> adapter;
    private SharedPreferences preferences;
    Editor prefEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_controller);

        sound_selector = findViewById(R.id.soundSpinner);
        backBtn = findViewById(R.id.backBtn);
        adapter = ArrayAdapter.createFromResource(this, R.array.soundModes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sound_selector.setAdapter(adapter);
        sound_selector.setOnItemSelectedListener(this);
        preferences = getApplicationContext().getSharedPreferences("mySettings", 0);
        prefEditor = preferences.edit();

        setPreferredValues();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setPreferredValues() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}