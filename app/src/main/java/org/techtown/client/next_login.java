package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class next_login extends AppCompatActivity {
    TextView text;
    TextView text2;
    countUI c;
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_login);

        hidesoftkey();

        Timer timer = new Timer();

        Intent intent = getIntent();


        text= (TextView)findViewById(R.id.textView_who);
        text2 = findViewById(R.id.textView2);

        text.setText(login_mem.name);

        ImageButton button = findViewById(R.id.imageButton1);
        ImageButton button2 = findViewById(R.id.imageButton2);

        text2.setText(String.valueOf(serveraddress.count/1000));

        this.c = new countUI();
        this.c.execute();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(timerTask,serveraddress.count);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_mem.frame_num = "4장_세로_프레임";
                login_mem.price_per = 2000;

                c.cancel(true);
                timer.cancel();
                Log.d("Task Debug", "cancel 호출함");
                Intent intent = new Intent(getApplicationContext(), choose_count.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_mem.frame_num = "4장_사각_프레임";
                login_mem.price_per = 3000;
                c.cancel(true);
                timer.cancel();
                Intent intent = new Intent(getApplicationContext(), choose_count.class);
                startActivity(intent);
            }
        });
    }

    class countUI extends AsyncTask<Void,Integer,Void> {
        protected Void doInBackground(Void...voids) {
            for(int i=serveraddress.count/1000-1;i>=0;i--) {
                if (isCancelled()) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    Log.e("Task Exception", "내용: " + e.getMessage());
                    e.printStackTrace();
                }
                final int time2 = i;
                publishProgress(time2);
            }
            cancel(true);
            return null;
        }

        protected void onProgressUpdate(Integer...values) {
            text2.setText(String.valueOf(values[0]));
        }

        protected void onCancelled() {
            Log.d("Task DEBUG", "Task 종료");
            super.onCancelled();
        }

    }


    public void hidesoftkey() {
        getWindow().setWindowAnimations(0);
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}