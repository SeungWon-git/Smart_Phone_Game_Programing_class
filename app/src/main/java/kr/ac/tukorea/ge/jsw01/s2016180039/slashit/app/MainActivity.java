package kr.ac.tukorea.ge.jsw01.s2016180039.slashit.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import kr.ac.tukorea.ge.jsw01.s2016180039.slashit.R;

public class MainActivity extends AppCompatActivity {
    protected static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    public void onBtnStart(View view) {
        mediaPlayer.stop();
        startActivity(new Intent(this, GameActivity.class));
    }
}