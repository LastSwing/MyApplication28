package com.example.administrator.myapplication28;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private MediaPlayer mediaPlayer;
    private Button start,pause,stop,loop;
    private TextView state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        state = (TextView)findViewById(R.id.tv_loopState);
        start = (Button)findViewById(R.id.btn_start);
        pause = (Button)findViewById(R.id.btn_pause);
        stop = (Button)findViewById(R.id.btn_Stop);
        loop = (Button)findViewById(R.id.btn_loop);

        pause.setEnabled(false);
        stop.setEnabled(false);
        loop.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Log.v(TAG, "start");
                    mediaPlayer.reset();

                    AssetManager assetManager = getAssets();
                    AssetFileDescriptor assetFileDescriptor = assetManager.openFd("Ivan Torrent&Julie Elven-Icarus.mp3");
                    mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    pause.setEnabled(true);
                    stop.setEnabled(true);
                    loop.setEnabled(true);

                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) {
                    pause.setText("Play");
                    mediaPlayer.pause();
                } else {
                    pause.setText("Pause");
                    mediaPlayer.start();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();

            }
        });

        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "Looping");

                boolean loop = mediaPlayer.isLooping();
                mediaPlayer.setLooping(!loop);

                if (!loop)
                    state.setText("循环播放");
                else
                    state.setText("一次播放");
            }
        });
    }
}