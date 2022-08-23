package org.techtown.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class kiosk_login extends AppCompatActivity {
    TextView id;
    TextView pw;
    EditText idEdit;
    EditText pwEdit;
    ImageView logo;

    private Context idkey;
    private Context passkey;
    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk_login);

        idkey = this;
        passkey = this;

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        idEdit = findViewById(R.id.idEdit);
        pwEdit = findViewById(R.id.pwEdit);

        logo = findViewById(R.id.imageView15);


        SharedPreferences sf = getSharedPreferences("test",MODE_PRIVATE);
        String idvalue = sf.getString("ID","");
        String pwvalue = sf.getString("PASSKEY","");

        Toast.makeText(getApplicationContext(),"체크"+idvalue+"/"+pwvalue,Toast.LENGTH_SHORT).show();

        if(!idvalue.equals("")&&!pwvalue.equals("")) {
            kiosk_info.kiosk_id = idvalue;
            kiosk_info.store_id = pwvalue;
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idsave = idEdit.getText().toString();
                String pwsave = pwEdit.getText().toString();

                String result;

                if(!idsave.equals("") && !pwsave.equals("")) {

                    // 서버로 아이디와 비밀번호 확인

                    SharedPreferences sf = getSharedPreferences("test",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();

                    editor.putString("ID",idsave);
                    editor.putString("PASSKEY",pwsave);

                    kiosk_info.kiosk_id = idsave;
                    kiosk_info.store_id = pwsave;

                    editor.apply();

                    Toast.makeText(getApplicationContext(),"아이디 : " + kiosk_info.store_id + "비밀번호 : " + kiosk_info.kiosk_id,Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


//                    Task t = new Task();
//                    try {
//                        result = t.execute(serveraddress.address+"/mufiApp/kiosk/payments/"+idsave+"/"+pwsave).get();
//                        if(result.equals("SUCCESS")) {
//                            PreferenceManager.setString(idkey, "ID", idEdit.getText().toString());
//                            PreferenceManager.setString(passkey, "PASSKEY", pwEdit.getText().toString());
//                            kiosk_info.kiosk_id = idEdit.getText().toString();
//                            kiosk_info.store_id = pwEdit.getText().toString();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(),"아이디와 패스키를 확인해주세요",Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"아이디와 패스 키를 입력해주세요",Toast.LENGTH_SHORT).show();
                    PreferenceManager.clear(idkey);
                    PreferenceManager.clear(passkey);
                }
            }
        });

    }

}

