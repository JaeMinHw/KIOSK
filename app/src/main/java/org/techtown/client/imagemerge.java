package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class imagemerge extends AppCompatActivity {
    Bitmap frame;
    Bitmap resized0;
    Bitmap resized1;
    Bitmap resized2;
    Bitmap resized3;
    ImageView image16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagemerge);

        image16 = findViewById(R.id.imageView16);

        frame = BitmapFactory.decodeResource(getResources(),R.drawable.realsize_frame);

        resized0 = Bitmap.createScaledBitmap(choo_pic.choose_p.get(0), 520, 336, true);
        resized1 = Bitmap.createScaledBitmap(choo_pic.choose_p.get(1), 520, 336, true);
        resized2 = Bitmap.createScaledBitmap(choo_pic.choose_p.get(2), 520, 336, true);
        resized3 = Bitmap.createScaledBitmap(choo_pic.choose_p.get(3), 520, 336, true);
        frame = merge(frame, resized0,resized1,resized2,resized3);

        image16.setImageBitmap(frame);
        saveBitmapToJpeg(frame);

        Intent intent = new Intent(getApplicationContext(),adprintPhoto.class);
        startActivity(intent);

    }

    private Bitmap merge(Bitmap bitmap0, Bitmap bitmap1, Bitmap bitmap2,Bitmap bitmap3, Bitmap bitmap4) {

        Bitmap mergeB = Bitmap.createBitmap(bitmap0.getWidth(),bitmap0.getHeight(),bitmap0.getConfig());
        Canvas canvas = new Canvas(mergeB);
        canvas.drawBitmap(bitmap0,0,0,null);
        canvas.drawBitmap(bitmap1,24,26,null);
        canvas.drawBitmap(bitmap2,24,382,null);
        canvas.drawBitmap(bitmap3,24,742,null);
        canvas.drawBitmap(bitmap4,24,1102,null);

        return mergeB;
    }

    private void saveBitmapToJpeg(Bitmap bitmap) {

        //내부저장소 캐시 경로를 받아옵니다.
        File storage = getCacheDir();

        //저장할 파일 이름
        String fileName = kiosk_info.kiosk_id+"_"+"merge"+".png";

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


}