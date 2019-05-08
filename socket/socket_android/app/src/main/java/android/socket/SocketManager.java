package android.socket;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2019-05-07.
 */
public class SocketManager {
    public String rootPath = Environment.getExternalStorageDirectory().toString() + "/";
    private ServerSocket server;
    private Handler handler = null;
    private Thread receiveFileThread;

    public SocketManager(Handler handler) {
        this.handler = handler;
    }

    void start() {
        int port = 9999;
        while (port > 9000) {
            try {
                server = new ServerSocket(port);
                break;
            } catch (Exception e) {
                port--;
            }
        }
        SendMessage("port:" + port);
        receiveFileThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ReceiveFile();
                }
            }
        });
        receiveFileThread.start();
    }

    void stop() {
        try {
            receiveFileThread.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void SendMessage(Object obj) {
        if (handler != null) {
            Message.obtain(handler, 0, obj).sendToTarget();
        }
    }

    void ReceiveFile() {
        try {
            Socket name = server.accept();
            InputStream nameStream = name.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(nameStream);
            BufferedReader br = new BufferedReader(streamReader);
            String fileName = br.readLine();
            br.close();
            streamReader.close();
            nameStream.close();
            name.close();
            SendMessage("正在接收:" + fileName);

            Socket data = server.accept();
            InputStream dataStream = data.getInputStream();
            String savePath = rootPath + fileName;
            FileOutputStream file = new FileOutputStream(savePath, false);
            byte[] buffer = new byte[1024];
            int size = -1;
            while ((size = dataStream.read(buffer)) != -1) {
                file.write(buffer, 0, size);
            }
            file.close();
            dataStream.close();
            data.close();
            SendMessage(fileName + "接收完成");
        } catch (Exception e) {
            SendMessage("接收错误:\n" + e.getMessage());
        }
    }

    public void sendFile(final String path, final String ip, final int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    List<File> files = new ArrayList<>();
                    genFiles(files, path);
                    if (files == null || files.size() == 0) {
                        SendMessage("文件不存在" + path);
                        return;
                    }

                    long ts = System.currentTimeMillis();
                    for (File file : files) {
                        if (file.isDirectory()) {
                            continue;
                        }

                        Socket name = new Socket();
                        name.connect(new InetSocketAddress(ip, port));
                        OutputStream outputName = name.getOutputStream();
                        OutputStreamWriter outputWriter = new OutputStreamWriter(outputName);
                        BufferedWriter bwName = new BufferedWriter(outputWriter);
                        bwName.write(ts + "/" + file.getAbsolutePath().substring(path.length()));
                        bwName.close();
                        outputWriter.close();
                        outputName.close();
                        name.close();
                        SendMessage("正在发送" + file.getAbsolutePath());

                        Socket data = new Socket();
                        data.connect(new InetSocketAddress(ip, port));
                        OutputStream outputData = data.getOutputStream();
                        FileInputStream fileInput = new FileInputStream(file.getAbsolutePath());
                        int size = -1;
                        byte[] buffer = new byte[1024];
                        while ((size = fileInput.read(buffer)) != -1) {
                            outputData.write(buffer, 0, size);
                        }
                        outputData.close();
                        fileInput.close();
                        data.close();
                        SendMessage(file.getAbsolutePath() + "  发送完成");
                    }

                    SendMessage("所有文件发送完成");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    SendMessage("发送错误:\n" + e.getMessage());
                }
            }
        }.start();
    }

    private void genFiles(List<File> files, String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (files == null) {
            files = new ArrayList<>();
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                genFiles(files, f.getAbsolutePath());
            }
        } else {
            files.add(file);
        }

    }
}

//https://my.oschina.net/waitforyou/blog/3025309
