package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        Intent intent = getIntent();


        text= (TextView)findViewById(R.id.textView_who);

        text.setText(login_mem.name);

        ImageButton button = findViewById(R.id.imageButton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.imageButton1:
                        login_mem.frame_num = "4장_세로_프레임";
                        login_mem.price_per = 2000;
                        break;
                    case R.id.imageButton2:
                        login_mem.frame_num = "4장_사각_프레임";
                        login_mem.price_per = 3000;
                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), choose_count.class);
                startActivity(intent);
            }
        });
    }
}