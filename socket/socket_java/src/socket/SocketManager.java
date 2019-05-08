package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketManager {
    private String rootPath = "/Users/jisuyun/";
    private ServerSocket server;
    private Thread receiveFileThread;

    public SocketManager() {
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
        System.out.println("port:" + port);
//        receiveFileThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    ReceiveFile();
//                }
//            }
//        });
        receiveFileThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    ReceiveFile();
                }
            }
        };
        receiveFileThread.start();
    }

    void stop() {
        try {
            receiveFileThread.stop();
        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("正在接收:" + fileName);

            Socket data = server.accept();
            InputStream dataStream = data.getInputStream();
            String savePath = rootPath + fileName;
            new File(savePath).getParentFile().mkdirs();

            FileOutputStream file = new FileOutputStream(savePath, false);
            byte[] buffer = new byte[1024];
            int size = -1;
            while ((size = dataStream.read(buffer)) != -1) {
                file.write(buffer, 0, size);
            }
            file.close();
            dataStream.close();
            data.close();
            System.out.println(fileName + "接收完成");
        } catch (Exception e) {
            System.out.println("接收错误:\n" + e.getMessage());
        }
    }

    public void sendFile(String path, String ip, int port) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("文件不存在" + path);
                return;
            }
            File[] files;
            if (file.isDirectory()) {
                files = file.listFiles();
            } else {
                files = new File[]{file};
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    sendFile(files[i].getAbsolutePath(), ip, port);
                    continue;
                }

                Socket name = new Socket(ip, port);
                OutputStream outputName = name.getOutputStream();
                OutputStreamWriter outputWriter = new OutputStreamWriter(outputName);
                BufferedWriter bwName = new BufferedWriter(outputWriter);
                bwName.write(files[i].getAbsolutePath().substring(path.length()));
                bwName.close();
                outputWriter.close();
                outputName.close();
                name.close();
                System.out.println("正在发送" + files[i].getAbsolutePath());

                Socket data = new Socket(ip, port);
                OutputStream outputData = data.getOutputStream();
                FileInputStream fileInput = new FileInputStream(files[i].getAbsolutePath());
                int size = -1;
                byte[] buffer = new byte[1024];
                while ((size = fileInput.read(buffer, 0, 1024)) != -1) {
                    outputData.write(buffer, 0, size);
                }
                outputData.close();
                fileInput.close();
                data.close();
                System.out.println(files[i].getAbsolutePath() + "  发送完成");
            }
            System.out.println("所有文件发送完成");
        } catch (Exception e) {
            System.out.println("发送错误:\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[]{"1", ""};
        }

        String mode = args[0];
        SocketManager socketManager = new SocketManager();
        if ("1".equals(mode)) {
            String path = "";
            try {
                path = args[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (path == null || path.trim().length() == 0) {
                path = "Downloads/";
            }
            socketManager.rootPath += path;
            socketManager.start();
        } else if ("2".equals(mode)) {
            try {
                String path = args[1];
                String ip = args[2];
                int port = Integer.parseInt(args[3]);

                socketManager.sendFile(path, ip, port);

                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("1接受，2发送");
        }
    }
}
