package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class choose_count extends AppCompatActivity {
    private TextView Count, text_who;
    TextView textUI;
    private Button btnmin,  btnplus, button;
    private int count=0;
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_count);

        hidesoftkey();

        Intent intent = getIntent();

        Count = findViewById(R.id.textView_count);
        Count.setText(""+count);
        text_who = findViewById(R.id.textView_who);
        btnmin = findViewById(R.id.btnmin);
        btnplus = findViewById(R.id.btnplus);
        button = findViewById(R.id.button);

        text_who.setText(login_mem.name);
        button.setVisibility(View.INVISIBLE);

        textUI = findViewById(R.id.textViewUI);
        Timer timer = new Timer();

        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count > 0) {
                    count--;
                    Count.setText("" + count);
                    if(count > 0) {
                        button.setVisibility(View.VISIBLE);
                    }
                    else {
                        button.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    button.setVisibility(View.INVISIBLE);
                }
            }
        });
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Count.setText(""+count);
                button.setVisibility(View.VISIBLE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수량 선택하고 결제 요청 부분으로 이동하고 결제 완료가 나면 사진찍기
                login_mem.photo_count = count;
                login_mem.goods = login_mem.frame_num + login_mem.photo_count;
                timer.cancel();
                Intent intent = new Intent(getApplicationContext(), payments.class);
                startActivity(intent);
            }
        });

        countUI c = new countUI();
        c.execute();

        textUI.setText(String.valueOf(serveraddress.count/1000));



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(timerTask,serveraddress.count);


        //테스트용
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수량 선택하고 결제 요청 부분으로 이동하고 결제 완료가 나면 사진찍기
                login_mem.photo_count = count;
                login_mem.goods = login_mem.frame_num + login_mem.photo_count;
                Intent intent = new Intent(getApplicationContext(), cameraCapture.class);
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
            textUI.setText(String.valueOf(values[0]));
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