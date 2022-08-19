package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class choo_pic extends AppCompatActivity {

    static ArrayList<Bitmap> choose_p = new ArrayList<>();
    
    ImageView imageView12;
    Button button3;
    Button button4;

    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choo_pic);

        Intent intent = getIntent();

        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        imageView12 = findViewById(R.id.imageView12);



        String path = "/data/data/org.techtown.client/cache/" + kiosk_info.kiosk_id+"_"+"0"+".png";
        File file = new File(path);
        Bitmap myBitmap = BitmapFactory.decodeFile(path);


        imageView12.setImageBitmap(myBitmap);


        uploadFile();




        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean_info.clean();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean_info.semi_clean();
                Intent intent = new Intent(getApplicationContext(), next_login.class);
                startActivity(intent);
            }
        });
    }


    private boolean uploadFile() {
        String path[] = new String[11];
        File[] file = new File[11];
        for(int i=1;i<=10;i++) {
            path[i] = "/data/data/org.techtown.client/cache/" + kiosk_info.kiosk_id + "_" + i + ".png";
            file[i]=new File(path[i]);
        }
        path[0] = "/data/data/org.techtown.client/cache/" + kiosk_info.kiosk_id+"_"+"0"+".png";
        file[0] = new File(path[0]);

        try{
            RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image0",file[0].getName(),RequestBody.create(MediaType.parse("image/*"),file[0]))
                    .addFormDataPart("image1",file[1].getName(),RequestBody.create(MediaType.parse("image/*"),file[1]))
                    .addFormDataPart("image2",file[2].getName(),RequestBody.create(MediaType.parse("image/*"),file[2]))
                    .addFormDataPart("image3",file[3].getName(),RequestBody.create(MediaType.parse("image/*"),file[3]))
                    .addFormDataPart("image4",file[4].getName(),RequestBody.create(MediaType.parse("image/*"),file[4]))
                    .addFormDataPart("image5",file[5].getName(),RequestBody.create(MediaType.parse("image/*"),file[5]))
                    .addFormDataPart("image6",file[6].getName(),RequestBody.create(MediaType.parse("image/*"),file[6]))
                    .addFormDataPart("image7",file[7].getName(),RequestBody.create(MediaType.parse("image/*"),file[7]))
                    .addFormDataPart("image8",file[8].getName(),RequestBody.create(MediaType.parse("image/*"),file[8]))
                    .addFormDataPart("image9",file[9].getName(),RequestBody.create(MediaType.parse("image/*"),file[9]))
                    .addFormDataPart("image10",file[10].getName(),RequestBody.create(MediaType.parse("image/*"),file[10]))
                    .addFormDataPart("submit","submit")
                    .build();

            Request request=new Request.Builder()
                    .url("http://192.168.0.18:8000/mufiApp/kiosk/pictures/upload/"+login_mem.ID+"/"+login_mem.orderId)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {

                }

            });
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}