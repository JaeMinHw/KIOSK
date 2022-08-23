package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class payments extends AppCompatActivity {


    String resultText ="";
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        try {
            send_pay();
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 가격/ 사용자ID/ 프레임 종류/매수
    public void send_pay() throws ExecutionException, InterruptedException, JSONException {
        resultText = new Task().execute().get();
        JSONObject jsonObject = new JSONObject(resultText);
        String success_state = jsonObject.getString("ispaymentSuccess");
        login_mem.orderId = jsonObject.getString("orderid");


        if(success_state.equals(1)) { // 실제 결과 성공이면 넘어가게 수정

             Intent intent = new Intent(getApplicationContext(), cameraCapture.class);
             startActivity(intent);
         }
         else { // 결제가 안되면 처음으로 돌아가게
             clean_info c = new clean_info();
             c.clean();

             Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             startActivity(intent);
         }
    }


}

class Task extends AsyncTask<String, Void, String> {

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            url = new URL(serveraddress.address+"/mufiApp/kiosk/payments/"+login_mem.ID+"/"+kiosk_info.kiosk_id+"/"+kiosk_info.store_id+"/"+login_mem.price+"/"+login_mem.goods); // 마지막에는 / 넣지 말기

            Log.e("Address Tag : ",""+url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");


            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.e("receiveMsg : ", receiveMsg);

                reader.close();
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

        return receiveMsg;
    }
}

