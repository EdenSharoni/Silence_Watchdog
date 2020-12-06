package com.example.silencewatchdog;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;

public class SoundControllerActivity extends AppCompatActivity {

    private Button backBtn;
    private Button playBtn;

    private Spinner sound_selector;

    private SeekBar soundSeekBar;

    private ArrayAdapter<CharSequence> adapter;

    private SharedPreferences preferences;

    private MediaPlayer soundTest;

    private Editor prefEditor;

    private float count;

    private CheckBox shuffle_check_box;

    private boolean isToShuffle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_controller);

        initGUIelements();

        arrayListSpinnerAdaptor();
        shuffle_check_box.setVisibility(View.GONE);
        isToShuffle = false;
        preferences = getApplicationContext().getSharedPreferences("silence_app", 0);
        prefEditor = preferences.edit();
        final String sound_key = this.getApplicationContext().getString(R.string.PREF_SOUNDNAME_KEY);
        final String volume_key = this.getApplicationContext().getString(R.string.PREF_VOLUME_KEY);

        String[] soundModes = getResources().getStringArray(R.array.soundModes);
        for (int i = 0; i < soundModes.length; i++)
            if (soundModes[i].equals(preferences.getString(sound_key, "shhh")))
                sound_selector.setSelection(i);

        sound_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int soundId = getResources().getIdentifier(sound_selector.getSelectedItem().toString(), "raw", getPackageName());
                soundTest = MediaPlayer.create(getApplicationContext(), soundId);
                prefEditor.putString(sound_key, sound_selector.getSelectedItem().toString());
                prefEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //DO NOTHING
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        count = soundSeekBar.getProgress();
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = progress;
                prefEditor.putString(volume_key, progress + "");
                prefEditor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //DO NOTHING
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //DO NOTHING
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float vol = count / 100f;
                soundTest.setVolume(vol, vol);
                soundTest.start();
            }
        });

        shuffle_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isToShuffle = !isToShuffle;
            }
        });
    }

    private void initGUIelements() {
        backBtn = findViewById(R.id.backBtn);
        playBtn = findViewById(R.id.playBtn);
        sound_selector = findViewById(R.id.soundSpinner);
        shuffle_check_box = findViewById(R.id.shuffle_check_box);
        soundSeekBar = findViewById(R.id.soundSeekBar);

    }

    private void arrayListSpinnerAdaptor() {
        adapter = ArrayAdapter.createFromResource(this, R.array.soundModes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sound_selector.setAdapter(adapter);
    }

    public void shuffleSound() {
        if (isToShuffle) {
            int ran = (int) (Math.random() * sound_selector.getCount());
            sound_selector.setSelection(ran);
        }
    }
}
