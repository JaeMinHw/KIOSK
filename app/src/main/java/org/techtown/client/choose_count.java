package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class choose_count extends AppCompatActivity {
    private TextView Count, text_who;
    private Button btnmin,  btnplus, button;
    private int count=0;
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_count);

        Intent intent = getIntent();

        Count = findViewById(R.id.textView_count);
        Count.setText(""+count);
        text_who = findViewById(R.id.textView_who);
        btnmin = findViewById(R.id.btnmin);
        btnplus = findViewById(R.id.btnplus);
        button = findViewById(R.id.button);

        text_who.setText(login_mem.name);
        button.setVisibility(View.INVISIBLE);

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
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 수량 선택하고 결제 요청 부분으로 이동하고 결제 완료가 나면 사진찍기
//                login_mem.photo_count = count;
//                login_mem.goods = login_mem.frame_num + login_mem.photo_count;
//                Intent intent = new Intent(getApplicationContext(), payments.class);
//                startActivity(intent);
//            }
//        });


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
}