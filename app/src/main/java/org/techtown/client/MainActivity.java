package org.techtown.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.BindException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.Buffer;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    // 소켓통신에 필요한것
    private Handler mHandler;
    JSONObject json = null;


    private String loca;

    int portNumber = 5555;

    private ImageView iv;
    private String text;
    Socket sock;

    String memberID;
    String name;
    int check=0;

    int backhome=0;

    Handler handler = new Handler();

    private Context idkey;
    private Context passkey;

    TextView textView;

    public void onBackPressed() {
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView3);

        hidesoftkey();


        Intent intetnt = getIntent();

        idkey = this;
        passkey = this;

        Button button2;

        iv = (ImageView) findViewById(R.id.qrcode);
        loca = getLocalIpAddress();
        Log.d("My Ip Address is ", loca);
        text = loca + "/" + kiosk_info.store_id + "/" + kiosk_info.kiosk_id;


        Timer timer = new Timer();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backhome++;
                if(backhome == 15) {
                    SharedPreferences sf = getSharedPreferences("test",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sf.edit();
                    editor.remove("ID");
                    editor.remove("PASSKEY");
                    editor.commit();
                    backhome =0;
                    Intent intent = new Intent(getApplicationContext(),kiosk_login.class);
                    startActivity(intent);
                }

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        backhome=0;
                    }
                };
                timer.schedule(timerTask,8000);

            }
        });

        // 테스트용 버튼
        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_mem.ID = "woals99";
                login_mem.name = "황재민";
                Intent intent = new Intent(getApplicationContext(), next_login.class);
                startActivity(intent);
            }
        });

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        } catch (Exception e) {
        }


        Log.w("connect","연결 하는중");
// 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {
                synchronized (this) {
                    startServer();
                }
            }
        };

        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();

    }

    // IP 주소 받는
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // 서버 연결
    public void startServer() {
        try {
            ServerSocket server = new ServerSocket();
            server.setReuseAddress(true);
            server.bind(new InetSocketAddress(portNumber));
            printServerLog("서버 시작함 : " + portNumber);

            while (check != 1) {
                sock = server.accept();
                InetAddress clientHost = sock.getLocalAddress();
                int clientPort = sock.getPort();
                printServerLog("클라이언트 연결됨 : " + clientHost + " : " + clientPort);

                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                String obj = in.readLine();
                printServerLog("데이터 받음 : " + obj.toString());

                DataOutputStream outstream = new DataOutputStream(sock.getOutputStream());
                outstream.writeUTF(obj.toString() + " from Server.");
                outstream.flush();
                printServerLog("데이터 보냄.");
                if(obj != null) {
                    check = 1;
                }

                json = new JSONObject(obj);
                Log.w("변환한 값 ", " :" + json.toString());

                memberID = json.getString("userid");
                name = json.getString("name");

                login_mem.ID = memberID;
                login_mem.name = name;

                Log.w("멤버 ID : ", "" + memberID);
                Log.w("이름 : ", "" + name);

                // 화면 전환해서 사용자 이름 출력시키기
                Intent intent = new Intent(getApplicationContext(), next_login.class);
                startActivity(intent);

                //sock.close();
            }
        } catch(BindException e) {
            Log.d("BIND_LOG", "바인드 예외: " + e.getMessage());
        } catch(StreamCorruptedException e) {
            Log.d("SCE_LOG", "SCE 예외: " + e.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printServerLog( final String data) {
        Log.d("MainActivity", data);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.w("받은 값",""+data);
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



