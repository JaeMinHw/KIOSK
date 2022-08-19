package org.techtown.client;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;



public class cameraCapture extends AppCompatActivity {


    CameraSurfaceView surfaceView;
    ImageView imageView;
    int cut=11;
    String[] binarypi = new String[cut];
    byte[][] bytepi = new byte[cut][];


    private int cou=0;
    Bitmap[] bitmap= new Bitmap[cut];

    Timer timer = new Timer();
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_capture);

        surfaceView = findViewById(R.id.surfaceview);
        imageView = findViewById(R.id.imageView);
        imageView.setClipToOutline(true);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(cou <10) {
                    capture();
                }
            }
        };

        timer.schedule(timerTask,5000,7000);



    }

    // 배열[11] -> 마지막에는 4장으로 편집된 사진 한장
    public void capture(){
        surfaceView.capture(new Camera.PictureCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap[cou] = BitmapFactory.decodeByteArray(data, 0, data.length);
                bitmap[cou] = rotateImage(bitmap[cou]);
                bitmap[cou] = mirrorImage(bitmap[cou]);

                imageView.setImageBitmap(bitmap[cou]);

                saveBitmapToJpeg(bitmap[cou]);




                camera.startPreview();
                cou++;
                if(cou == 10)
                {
                    imageList.bitmap = bitmap.clone();
                    Intent intent = new Intent(getApplicationContext(), imageList.class);
                    startActivity(intent);
                    cou=0;
                }

            }
        });
    }




    private void saveBitmapToJpeg(Bitmap bitmap) {

        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getCacheDir();

        //저장할 파일 이름
        String fileName = kiosk_info.kiosk_id+"_"+cou+".png";

        //storage 에 파일 인스턴스를 생성합니다.
        File tempFile = new File(storage, fileName);
        Log.e("MyTag","location : " + storage + fileName);

        try {

            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile();

            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);

            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            // 스트림 사용후 닫아줍니다.
            out.close();

        } catch (FileNotFoundException e) {
            Log.e("MyTag","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("MyTag","IOException : " + e.getMessage());
        }
    }

    public Bitmap rotateImage(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.preRotate(180);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }

    public Bitmap mirrorImage(Bitmap src) {
        Matrix matrix = new Matrix();
        matrix.setScale(1,-1);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),matrix,true);
    }


    public static String byteArrayToBinaryString(byte[] b){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<b.length;++i) {
            sb.append(byteToBinaryString(b[i]));
        }
        return sb.toString();
    }

    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for(int bit=0;bit<8;bit++) {
            if(((n>>bit)&1)>0) {
                sb.setCharAt(7-bit,'1');
            }
        }
        return sb.toString();
    }


}


