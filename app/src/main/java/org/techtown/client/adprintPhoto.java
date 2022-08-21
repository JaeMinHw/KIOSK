package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Handler;
import android.os.Bundle;

import android.widget.VideoView;

public class adprintPhoto extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adprint_photo);

//        kiosk_login soft = new kiosk_login();
//        soft.hidesoftkey();

        videoView = findViewById(R.id.videoView);

        Uri videoUri = Uri.parse("http://192.168.0.18:8000/mufiApp/kiosk/pictures/play/ads");
        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        if(videoView!=null) {
            videoView.stopPlayback();
            Intent intent = new Intent(getApplicationContext(),choo_pic.class);
        }
        if(videoView!=null) videoView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //비디오 일시 정지
        if(videoView!=null && videoView.isPlaying()) videoView.pause();
    }

}

