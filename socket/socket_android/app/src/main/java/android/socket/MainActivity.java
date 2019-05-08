package android.socket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RxPermissions(this).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    //启动服务
                    start();
                }
            }
        });

        L.i(getIpAddress());
    }

    private void start() {
        Intent intent = getIntent();

        intent = new Intent();
        intent.putExtra("mode", "2");
        intent.putExtra("path", "0");
        intent.putExtra("ip", "172.16.10.139");
        intent.putExtra("port", 9999);

        if (intent == null) {
            L.i("1接受，2发送");
            return;
        }

        String mode = intent.getStringExtra("mode");

        SocketManager socketManager = new SocketManager(handler);
        if ("1".equals(mode)) {
            String path = "";
            try {
                path = intent.getStringExtra("path");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (path == null || path.trim().length() == 0) {
                path = "/";
            }
            socketManager.rootPath += path + "/";
            socketManager.start();
        } else if ("2".equals(mode)) {
            try {
                String path = intent.getStringExtra("path");
                String ip = intent.getStringExtra("ip");
                int port = intent.getIntExtra("port", 9999);

                socketManager.sendFile(Environment.getExternalStorageDirectory().toString() + "/" + path, ip, port);

                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            L.i("1接受，2发送");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public String getIpAddress() {

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int i = wifiInfo.getIpAddress();
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 24) & 0xFF);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default: {
                    L.i(String.valueOf(msg.obj));
                }
            }
        }
    };
}
