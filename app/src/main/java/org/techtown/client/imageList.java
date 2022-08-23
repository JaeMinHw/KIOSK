package org.techtown.client;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class imageList extends AppCompatActivity {

    int num=10;
    int click=0;
    int[] chArr;


    ImageView imgView[] = new ImageView[num];

    static Bitmap[] bitmap;
    Integer[] idArr = {R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,R.id.imageView5,R.id.imageView6,R.id.imageView7,R.id.imageView8,R.id.imageView9,R.id.imageView10};

    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        hidesoftkey();

        Button button = findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);

        for (int i = 0; i < num; i++) {
            imgView[i] = (ImageView) findViewById(idArr[i]);
            imgView[i].setImageBitmap(bitmap[i]);
            imgView[i].setClipToOutline(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), imagemerge.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < 10; i++) {
            int tmp = i;
            imgView[i].setOnClickListener(new View.OnClickListener() {
                int thisCheck = 0;

                @Override
                public void onClick(View v) {

                    if (thisCheck == 0) {
                        if (click >= 4) {
                            // 4장을 이미 선택하셨습니다. 출력하는 toast
                            Toast.makeText(getApplicationContext(), "이미 4장을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                            button.setVisibility(View.VISIBLE);
                        } else {
                            imgView[tmp].setAlpha(100);
                            click++;
                            thisCheck = 1;
                            choo_pic.choose_p.add(bitmap[tmp]);
                            button.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        imgView[tmp].setAlpha(255);
                        click--;
                        thisCheck = 0;
                        choo_pic.choose_p.remove(bitmap[tmp]);
                        button.setVisibility(View.INVISIBLE);
                    }
                    if (click == 4) {
                        button.setVisibility(View.VISIBLE);
                    }
                }

            });

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