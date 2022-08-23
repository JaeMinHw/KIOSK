package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class next_login extends AppCompatActivity {
    TextView text;
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_login);

        hidesoftkey();

        Intent intent = getIntent();


        text= (TextView)findViewById(R.id.textView_who);

        text.setText(login_mem.name);

        ImageButton button = findViewById(R.id.imageButton1);
        ImageButton button2 = findViewById(R.id.imageButton2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_mem.frame_num = "4장_세로_프레임";
                login_mem.price_per = 2000;

                Intent intent = new Intent(getApplicationContext(), choose_count.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_mem.frame_num = "4장_사각_프레임";
                login_mem.price_per = 3000;

                Intent intent = new Intent(getApplicationContext(), choose_count.class);
                startActivity(intent);
            }
        });
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