package com.autils.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.autils.framework.Launcher;
import com.autils.framework.common.network.NetworkMonitor;
import com.autils.framework.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by fengyulong on 2018/6/26.
 */
public class Phone {

    private String device_type = "android";


    private System system;


    private CPU cpu;


    private SDCARD sdcard;


    private SIM sim;


    private Network network;


    private Battery battery;


    private Area area;


    private List<Sensor> sensors;

    public Phone() {
        system = new System();
        cpu = new CPU();
        sdcard = new SDCARD();
        sim = new SIM();
        network = new Network();
        battery = new Battery();
        area = new Area();
        sensors = Sensor.sensors();
    }

    private class System {

        @SuppressLint("MissingPermission")
        public System() {
            imei = DeviceUtils.getIMEI(0);
            imei2 = DeviceUtils.getIMEI(1);
            imsi = DeviceUtils.getIMSI(0);
            imsi2 = DeviceUtils.getIMSI(1);
            if (Build.VERSION.SDK_INT >= 17) {
                bootTime = java.lang.System.currentTimeMillis() - SystemClock.elapsedRealtimeNanos() / 1000000 + "";
            }
            macAddress = DeviceUtils.getMacAddress();
        }

        private String macAddress;
        /**
         * IMEI(International Mobile Equipment Identity,国际移动身份识别码)：是由15位数字组成的”电子串号”，
         * 其组成结构为TAC（6位数字）+FAC（两位数字）+SNR（6位数字）+SP （1位数字）。
         * 它与每台手机一一对应，而且该码是全世界唯一的。每一只手机在组装完成后都将被赋予一个全球唯一的一组号码，这个号码从生产到交付使用都将被制造生产的厂商所记录。
         * IMEI码贴在手机背面的标志上，并且读写于手机内存中。它也是该手机在厂家的”档案”和”身份证号”。
         */
        private String imei;
        private String imei2;

        /**
         * IMSI(International Mobile Subscriber Identification Number,国际移动用户识别码)：是区别移动用户的标志，
         * 储存在SIM卡中，可用于区别移动用户的有效信息。其总长度不超过15位，同样使用0～9的数字。
         * 其中MCC是移动用户所属国家代号，占3位数字，中国的MCC规定为460；MNC是移动网号码，最多由两位数字组成，用于识别移动用户所归属的移动通信网；
         * MSIN是移动用户识别码，用以识别某一移动通信网中的移动用户。
         */

        private String imsi;//手机唯一识别号
        private String imsi2;

        /**
         * 基带版本
         */

        private String basebandVer;

        /**
         * 开机时间
         */

        private String bootTime;

        /**
         * 获取设备基板名称
         */

        private String board = Build.BOARD;

        /**
         * 系统启动程序版本号
         */

        private String bootloader = Build.BOOTLOADER;

        /**
         * 设备品牌
         */

        private String brand = Build.BRAND;

        /**
         * CPU指令集
         */

        private String cpu_abi = Build.CPU_ABI;

        /**
         * CPU指令集2
         */

        private String cpu_abi2 = Build.CPU_ABI2;

        /**
         * 驱动名称
         */

        private String device = Build.DEVICE;

        /**
         * 设备显示的版本包（在系统设置中显示为版本号）和ID一样
         */

        private String display = Build.DISPLAY;

        /**
         * 硬件识别码
         */

        private String fingerprint = Build.FINGERPRINT;

        /**
         * 设备硬件名称,一般和基板名称一样（BOARD）
         */

        private String hardware = Build.HARDWARE;

        /**
         * host
         */

        private String host = Build.HOST;

        /**
         * 修订版本列表
         */

        private String id = Build.ID;

        /**
         * 硬件制造商
         */

        private String manufacturer = Build.MANUFACTURER;

        /**
         * 版本
         */

        private String model = Build.MODEL;

        /**
         * 产品的名称
         */

        private String product = Build.PRODUCT;

        /**
         * 无线电固件版本号，通常是不可用的 显示unknown
         */

        private String radio = Build.RADIO;

        /**
         * 设备标签。如release-keys 或测试的 test-keys
         */

        private String tags = Build.TAGS;

        /**
         * time
         */

        private String time = Build.TIME + "";

        /**
         * 设备版本类型  主要为"user" 或"eng"
         */

        private String type = Build.TYPE;

        /**
         * 设备用户名 基本上都为android-build
         */

        private String user = Build.USER;

        /**
         * 获取系统版本字符串。如4.1.2 或2.2 或2.3等
         */

        private String release = Build.VERSION.RELEASE;

        /**
         * 设备当前的系统开发代号，一般使用REL代替
         */

        private String codeName = Build.VERSION.CODENAME;

        /**
         * 系统源代码控制值，一个数字或者git hash值
         */

        private String incremental = Build.VERSION.INCREMENTAL;

        /**
         * 系统的API级别 一般使用下面大的SDK_INT 来查看
         */

        private String sdk = Build.VERSION.SDK;

        /**
         * 系统的API级别 数字表示
         */

        private String sdkInt = Build.VERSION.SDK_INT + "";
    }

    private class CPU {
        public CPU() {
            cpu = getCpuInfo();
            cores = getCoresNumber() + "";
            max = getMaxCPU() + "";
            min = getMinCPU() + "";
            cur = getCurCPU() + "";
        }

        /**
         * CPU型号
         */

        private String cpu;

        /**
         * CPU个数
         */

        private String cores;

        /**
         *
         */

        private String max;

        /**
         *
         */

        private String cur;

        /**
         *
         */

        private String min;

        private int getCoresNumber() {
            // Private Class to display only CPU devices in the directory listing
            class CpuFilter implements FileFilter {
                @Override
                public boolean accept(File pathname) {
                    // Check if filename is "cpu", followed by a single digit number
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }

            try {
                // Get directory containing CPU info
                File dir = new File("/sys/devices/system/cpu/");
                // Filter to only list the devices we care about
                File[] files = dir.listFiles(new CpuFilter());
                // Return the number of cores (virtual CPU devices)
                return files.length;
            } catch (Exception e) {
                // Default to return 1 core
                return 1;
            }
        }

        private final String CurPath = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";//保存当前CPU频率

        //获取当前CPU频率
        public int getCurCPU() {
            int result = 0;
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(CurPath);
                br = new BufferedReader(fr);
                String text = br.readLine();
                result = Integer.parseInt(text.trim());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fr != null)
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return result;
        }

        private final String MaxPath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";//保存CPU可运行最大频率

        //获取CPU可运行最大频率
        public int getMaxCPU() {
            int result = 0;
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(MaxPath);
                br = new BufferedReader(fr);
                String text = br.readLine();
                result = Integer.parseInt(text.trim());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fr != null)
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return result;
        }

        private final String MinPath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";//保存CPU可运行最小频率

        //获取CPU可运行最小频率
        public int getMinCPU() {
            int result = 0;
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(MinPath);
                br = new BufferedReader(fr);
                String text = br.readLine();
                result = Integer.parseInt(text.trim());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fr != null)
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if (br != null)
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return result;
        }

        public String getCpuInfo() {
            String str1 = "/proc/cpuinfo";
            String str2 = "";
            String[] cpuInfo = {"", ""};
            String[] arrayOfString;
            try {
                FileReader fr = new FileReader(str1);
                BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
                str2 = localBufferedReader.readLine();
                arrayOfString = str2.split("\\s+");
                for (int i = 2; i < arrayOfString.length; i++) {
                    cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
                }
                str2 = localBufferedReader.readLine();
                arrayOfString = str2.split("\\s+");
                cpuInfo[1] += arrayOfString[2];
                localBufferedReader.close();
            } catch (IOException e) {
            }
            return cpuInfo[0];
        }
    }

    private class SDCARD {
        public SDCARD() {
            getSDCardMemory();
        }

        /**
         * 总大小
         */

        private long total;

        /**
         * 可用大小
         */

        private long available;

        private void getSDCardMemory() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());
                long bSize = sf.getBlockSize();
                long bCount = sf.getBlockCount();
                long availBlocks = sf.getAvailableBlocks();

                total = bSize * bCount;//总大小
                available = bSize * availBlocks;//可用大小
            }
        }
    }

    private class SIM {
        @SuppressLint("MissingPermission")
        public SIM() {
            TelephonyManager manager = (TelephonyManager) Launcher.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            imsi = DeviceUtils.getIMSI(0);
            imsi2 = DeviceUtils.getIMSI(1);
            operatorName = manager.getNetworkOperatorName();
        }

        /**
         * IMSI(International Mobile Subscriber Identification Number,国际移动用户识别码)：是区别移动用户的标志，
         * 储存在SIM卡中，可用于区别移动用户的有效信息。其总长度不超过15位，同样使用0～9的数字。
         * 其中MCC是移动用户所属国家代号，占3位数字，中国的MCC规定为460；MNC是移动网号码，最多由两位数字组成，用于识别移动用户所归属的移动通信网；
         * MSIN是移动用户识别码，用以识别某一移动通信网中的移动用户。
         */

        private String imsi;//手机唯一识别号
        private String imsi2;


        private String operatorName;//运营商
    }

    private class Network {

        /**
         * 功能描述：通过手机信号获取基站信息
         * # 通过TelephonyManager 获取lac:mcc:mnc:cell-id
         * # MCC，Mobile Country Code，移动国家代码（中国的为460）；
         * # MNC，Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）；
         * # LAC，Location Area Code，位置区域码；
         * # CID，Cell Identity，基站编号；
         * # BSSS，Base station signal strength，基站信号强度。
         */
        @SuppressLint({"MissingPermission", "NewApi"})
        public Network() {
            TelephonyManager manager = (TelephonyManager) Launcher.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            // 返回值MCC + MNC
            String operator = manager.getNetworkOperator();
            if (!StringUtils.isNullOrEmpty(operator)) {
                MCC = Integer.parseInt(operator.substring(0, 3));
                MNC = Integer.parseInt(operator.substring(3));
            }

            try {
                // 中国移动和中国联通获取LAC、CID的方式
                CellLocation cellLocation = manager.getCellLocation();
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation location = (GsmCellLocation) cellLocation;
                    LAC = location.getLac();
                    CID = location.getCid();
                } else if (cellLocation instanceof CdmaCellLocation) {
                    // 中国电信获取LAC、CID的方式
                    CdmaCellLocation location1 = (CdmaCellLocation) manager.getCellLocation();
                    LAC = location1.getNetworkId();
                    CID = location1.getBaseStationId();
                    CID /= 16;
                }
                // 获取邻区基站信息
                List<NeighboringCellInfo> infos = manager.getNeighboringCellInfo();
                StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
                for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
                    sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
                    sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
                    sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
                }

                NeighboringCellInfo = sb.toString();

                CellInfo stationinfo = manager.getAllCellInfo().get(0);//这个方式获取信息比较简单，但是有些手机不支持。
                if (stationinfo instanceof CellInfoGsm) {
                    CellInfoGsm cellInfoGsm = (CellInfoGsm) stationinfo;
                    CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
                    CellSignalStrengthGsm cellrsp = cellInfoGsm.getCellSignalStrength();
                    BSSS = cellrsp.getDbm();
                    LAC = cellIdentity.getLac();
                    CID = cellIdentity.getCid();
                } else if (stationinfo instanceof CellInfoWcdma) {
                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) stationinfo;
                    CellIdentityWcdma cellIdentity = cellInfoWcdma
                            .getCellIdentity();
                    CellSignalStrengthWcdma cellrsp = cellInfoWcdma.getCellSignalStrength();
                    BSSS = cellrsp.getDbm();
                    LAC = cellIdentity.getLac();
                    CID = cellIdentity.getCid();
                    String sss = Integer.toHexString(CID);
                    sss = sss.substring(sss.length() - 4);
                    CID = Integer.parseInt(sss, 16);

                } else if (stationinfo instanceof CellInfoLte) {
                    CellInfoLte cellInfoLte = (CellInfoLte) stationinfo;
                    CellIdentityLte cellIdentity = cellInfoLte.getCellIdentity();
                    CellSignalStrengthLte cellrsp = cellInfoLte
                            .getCellSignalStrength();
                    BSSS = cellrsp.getDbm();
                    LAC = cellIdentity.getTac();
                    CID = cellIdentity.getCi();
                    String sss = Integer.toHexString(CID);
                    sss = sss.substring(sss.length() - 4);
                    CID = Integer.parseInt(sss, 16);
                } else {
                }
            } catch (Exception e) {
                //handle exception  有些手机不支持上面的接口，下面的代码可直接获取所有手机的LAC、CID,但是获取场强需要另外加监听器。
                try {
                    GsmCellLocation lc = (GsmCellLocation) manager.getCellLocation();
                    LAC = lc.getLac();
                    CID = lc.getCid();
                    String sss = Integer.toHexString(CID);
                    sss = sss.substring(sss.length() - 4);
                    CID = Integer.parseInt(sss, 16);
                } catch (Exception e2) {
                }
            }

            type = NetworkMonitor.getCurrentNetworkType();

            SSID = DeviceUtils.getWIFISSID();
        }


        private int MCC;//Mobile Country Code，移动国家代码（中国的为460）；

        private int MNC;//Mobile Network Code，移动网络号码（中国移动为0，中国联通为1，中国电信为2）；

        private int LAC;//Location Area Code，位置区域码；

        private int CID;//Cell Identity，基站编号；

        private int BSSS;//Base station signal strength，基站信号强度。

        private String NeighboringCellInfo;//邻区基站信息

        private String type;

        private String SSID;
    }

    private class Battery {
        @SuppressLint("NewApi")
        public Battery() {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = Launcher.getInstance().getApplication().registerReceiver(null, ifilter);
            //你可以读到充电状态,如果在充电，可以读到是usb还是交流电
            //是否在充电
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
            // 怎么充
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            switch (chargePlug) {
                case BatteryManager.BATTERY_PLUGGED_AC: {
                    chargeType = "AC charger";
                    break;
                }
                case BatteryManager.BATTERY_PLUGGED_USB: {
                    chargeType = "USB port";
                    break;
                }
                case BatteryManager.BATTERY_PLUGGED_WIRELESS: {
                    chargeType = "wireless";
                    break;
                }
            }

            //当前剩余电量
            level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            //电量最大值
            scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            //电量百分比
            batteryPct = level / (float) scale;
        }


        private int level;

        private int scale;


        private float batteryPct;


        private boolean isCharging;


        private String chargeType;
    }

    private class Area {

        private String country = Locale.getDefault().getCountry();

        private String language = Locale.getDefault().getLanguage();

        private String timeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
    }

    private static class Sensor {
        public static List<Sensor> sensors() {
            String[] mSensorType = {
                    "加速度", "磁场", "方向", "陀螺仪", "光线",
                    "压力", "温度", "距离", "重力", "线性加速度",
                    "旋转矢量", "湿度", "环境温度", "无标定磁场", "无标定旋转矢量",
                    "未校准陀螺仪", "特殊动作", "步行检测", "计步器", "地磁旋转矢量",
                    "心跳", "倾斜检测", "唤醒手势", "瞥一眼", "捡起来"};
            Map<Integer, String> mapSensor = new HashMap<>();


            SensorManager manager = (SensorManager) Launcher.getInstance().getApplication().getSystemService(Context.SENSOR_SERVICE);
            List<android.hardware.Sensor> sensorList = manager.getSensorList(android.hardware.Sensor.TYPE_ALL);
            for (android.hardware.Sensor sensor : sensorList) {
                if (sensor.getType() >= mSensorType.length) {
                    continue;
                }
                mapSensor.put(sensor.getType(), sensor.getName());
            }

            List<Sensor> sensors = new ArrayList<>();
            for (Map.Entry<Integer, String> item_map : mapSensor.entrySet()) {
                int type = item_map.getKey();
                String name = item_map.getValue();

                Sensor sensor = new Sensor();
                sensor.type = type;
                sensor.name = name;
                sensor.sensorType = mSensorType[type - 1];

                sensors.add(sensor);
            }
            return sensors;
        }


        private int type;

        private String name;

        private String sensorType;
    }
}
