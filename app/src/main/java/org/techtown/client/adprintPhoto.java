package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.VideoView;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class adprintPhoto extends AppCompatActivity {
    VideoView videoView;
    ProgressBar progressBar;
    Handler handler;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adprint_photo);

        videoView = findViewById(R.id.videoView);

        Uri videoUri = Uri.parse("http://192.168.0.12:8000/mufiApp/kiosk/pictures/play/ads");

        videoView.setVideoURI(videoUri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
                progres();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        //
        if(videoView!=null) {
            videoView.stopPlayback();
            Intent intent = new Intent(getApplicationContext(),choo_pic.class);
        }
    }

    public void progres() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(i=0;i<= 300; i++) {
                    progressBar.setProgress(i);
                    Message msg = handler.obtainMessage();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(100);
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }


    class Task_ad extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            URL url;
            try {
                url = new URL("http://192.168.0.12:8000/mufiApp/kiosk/pictures/play/ads");
                Log.e("Address Tag : ",""+url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


                if (conn.getResponseCode() == conn.HTTP_OK) {
                    Uri videoUri = Uri.parse("http://192.168.0.12:8000/mufiApp/kiosk/pictures/play/ads");
                    videoView.setVideoURI(videoUri);

                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            videoView.start();
                        }
                    });
                }
                else if(conn.getResponseCode() == 404) {
                    Log.e("Mytag","what");
                }
                else {
                    Log.e("결과", conn.getResponseCode() + "Error");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                Log.e("Mytag","내용: "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "dsa";
        }
    }
}

