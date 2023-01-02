package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Loader.OnLoadCompleteListener;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.myapplication.R;

public class MainActivity extends Activity implements OnTouchListener {
    private SoundPool soundPool;
    private int idMusic;
    private int idDog;
    boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view1 = findViewById(R.id.textView1);
        view1.setOnTouchListener(this);
        // Set the hardware buttons to control the music
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> flag = true);
        idMusic = soundPool.load(this, R.raw.music, 1);
        idDog = soundPool.load(this, R.raw.dog, 1);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            if (flag) {
                soundPool.play(idMusic, volume, volume, 1, 1, 1f);
                soundPool.play(idDog, volume, volume, 1, -1, 1f);
            }
        }
        return false;
    }
}
