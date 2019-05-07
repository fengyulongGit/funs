package com.autils.framework.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.ExifInterface;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;

import com.autils.framework.common.log.L;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by fengyulong on 2018/7/20.
 */
public class BitmapUtils {
    @SuppressLint("NewApi")
    public static String bitmapToBase64(Bitmap bitmap) {
        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();
                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return reslut;
    }

    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decode = Base64.decode(base64String.split(",")[1], Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    /**
     * 网络或本地url转换为bitmap
     *
     * @param url
     * @return
     */
    public static Bitmap getNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 2 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[2 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = System.currentTimeMillis() + ".jpg";
        String galleryFilePath = PathUtils.GalleryFilePath(fileName);
        try {
            File file = new File(galleryFilePath);
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            GalleryUtils.insert(context, file);
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //保存签名文件到指定路径
    public static String saveBitmapToFile(Context context, Bitmap bmp) {
        // 首先保存图片
        String fileName = "jsj_" + System.currentTimeMillis() + ".jpg";
        String galleryFilePath = PathUtils.GalleryFilePath(fileName);

        File file = new File(galleryFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            if (isSuccess) {
                L.d("signatureBitmap size: " + file.length());
                return galleryFilePath;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeJpg(Bitmap b, String imagePath) {
        try {
            FileOutputStream out = new FileOutputStream(imagePath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            L.e(e);
        }
    }

    public static String decodeQRCode(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        try {
            result = reader.decode(bitmap1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == null) {
            return null;
        } else {
            return result.getText();
        }
    }

    /**
     * 获取照片其他附属属性
     */
    public static ImageExifInterface getImageExifInterfaceData(String path) {
        try {
            ImageExifInterface imageExifInterface = new BitmapUtils.ImageExifInterface();

            ExifInterface exifInterface = new ExifInterface(path);
            imageExifInterface.setDatetime(exifInterface.getAttribute(ExifInterface.TAG_DATETIME));// 拍摄时间
            imageExifInterface.setDeviceName(exifInterface.getAttribute(ExifInterface.TAG_MAKE));// 设备品牌
            imageExifInterface.setDeviceModel(exifInterface.getAttribute(ExifInterface.TAG_MODEL));// 设备型号
            imageExifInterface.setLatitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
            imageExifInterface.setLongitude(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
            imageExifInterface.setLatitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
            imageExifInterface.setLongitudeRef(exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));

            if (imageExifInterface.getLatitude() != null &&
                    imageExifInterface.getLatitudeRef() != null &&
                    imageExifInterface.getLongitude() != null &&
                    imageExifInterface.getLongitudeRef() != null) {
                try {
                    imageExifInterface.setLongitudeNormal(convertRationalLatLonToFloat(imageExifInterface.getLongitude(), imageExifInterface.getLongitudeRef()) + "");
                    imageExifInterface.setLatitudeNormal(convertRationalLatLonToFloat(imageExifInterface.getLatitude(), imageExifInterface.getLatitudeRef()) + "");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            return imageExifInterface;
        } catch (Exception e) {
            L.e(e);
            e.printStackTrace();
        }

        return null;
    }

    public static void copyImageExifInterfaceData(File source, File target) {
        try {
            ExifInterface sourceInterface = new ExifInterface(source.getAbsolutePath());

            ExifInterface targetInterface = new ExifInterface(target.getAbsolutePath());

            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_DATETIME))) {
                targetInterface.setAttribute(ExifInterface.TAG_DATETIME, sourceInterface.getAttribute(ExifInterface.TAG_DATETIME));//拍摄时间，取决于设备设置的时间
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_MAKE))) {
                targetInterface.setAttribute(ExifInterface.TAG_MAKE, sourceInterface.getAttribute(ExifInterface.TAG_MAKE));//设备品牌
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_MODEL))) {
                targetInterface.setAttribute(ExifInterface.TAG_MODEL, sourceInterface.getAttribute(ExifInterface.TAG_MODEL));//设备型号，整形表示，在ExifInterface中有常量对应表示
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_FLASH))) {
                targetInterface.setAttribute(ExifInterface.TAG_FLASH, sourceInterface.getAttribute(ExifInterface.TAG_FLASH));//闪光灯
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, sourceInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE));//纬度
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, sourceInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));//经度
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, sourceInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));//纬度名（N or S）
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, sourceInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));//经度名（E or W）
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME))) {
                targetInterface.setAttribute(ExifInterface.TAG_EXPOSURE_TIME, sourceInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME));//曝光时间
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_APERTURE))) {
                targetInterface.setAttribute(ExifInterface.TAG_APERTURE, sourceInterface.getAttribute(ExifInterface.TAG_APERTURE));//光圈值
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_ISO))) {
                targetInterface.setAttribute(ExifInterface.TAG_ISO, sourceInterface.getAttribute(ExifInterface.TAG_ISO));//ISO感光度
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED))) {
                targetInterface.setAttribute(ExifInterface.TAG_DATETIME_DIGITIZED, sourceInterface.getAttribute(ExifInterface.TAG_DATETIME_DIGITIZED));//数字化时间
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME))) {
                targetInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME, sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME));//
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIG))) {
                targetInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIG, sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_ORIG));//
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_DIG))) {
                targetInterface.setAttribute(ExifInterface.TAG_SUBSEC_TIME_DIG, sourceInterface.getAttribute(ExifInterface.TAG_SUBSEC_TIME_DIG));//
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, sourceInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE));//海拔高度
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, sourceInterface.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF));//海拔高度
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP, sourceInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));//时间戳
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, sourceInterface.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));//日期戳
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE))) {
                targetInterface.setAttribute(ExifInterface.TAG_WHITE_BALANCE, sourceInterface.getAttribute(ExifInterface.TAG_WHITE_BALANCE));//白平衡
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH))) {
                targetInterface.setAttribute(ExifInterface.TAG_FOCAL_LENGTH, sourceInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));//焦距
            }
            if (StringUtils.isNotNullOrEmpty(sourceInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD))) {
                targetInterface.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD, sourceInterface.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));//用于定位查找的全球定位系统处理方法。
            }

            targetInterface.saveAttributes();
        } catch (Exception e) {
            L.e(e);
        }
    }

    /**
     * 目前想存储Double类型坐标，但是通过getAttribute(String tag)取出来的内容形如：
     * 112/1,39/1,288172/3278,其实就是“度分秒”拆分后，分母除以1的结果
     */
    private static float convertRationalLatLonToFloat(String rationalString, String ref) {

        String[] parts = rationalString.split(",");

        String[] pair;
        pair = parts[0].split("/");
        double degrees = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[1].split("/");
        double minutes = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        pair = parts[2].split("/");
        double seconds = Double.parseDouble(pair[0].trim())
                / Double.parseDouble(pair[1].trim());

        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
        if ((ref.equals("S") || ref.equals("W"))) {
            return (float) -result;
        }
        return (float) result;
    }

    private static String convertGPSToRationalLatLon(double gpsInfo) {
        gpsInfo = Math.abs(gpsInfo);
        String dms = Location.convert(gpsInfo, Location.FORMAT_SECONDS);
        String[] splits = dms.split(":");
        String seconds;
        if (splits[2].indexOf(".") < 0) {
            seconds = splits[2] + "/1";
        } else {
            String s = "/1";
            for (int i = 1; i < splits[2].length() - splits[2].indexOf("."); i++) {
                s += "0";
            }
            seconds = splits[2].replace(".", "") + s;
        }
        String s = splits[0] + "/1," + splits[1] + "/1," + seconds;
        return s;
    }

    /**
     * 获取照片其他附属属性
     */
    public static ImageExifInterface saveGPS2ExifInterface(String path, double longitude, double latitude) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);

            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, convertGPSToRationalLatLon(longitude));//经度
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, longitude > 0 ? "E" : "W");//经度名（E or W）

            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, convertGPSToRationalLatLon(latitude));//纬度
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latitude > 0 ? "N" : "S");//纬度名（N or S）

            exifInterface.saveAttributes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getImageExifInterfaceData(path);
    }

    public static class ImageExifInterface {
//   TAG_DATETIME  时间日期
//　　TAG_FLASH      闪光灯
//　　TAG_GPS_LATITUDE     纬度
//　　TAG_GPS_LATITUDE_REF    纬度参考
//　　TAG_GPS_LONGITUDE         经度
//　　TAG_GPS_LONGITUDE_REF     经度参考
//　　TAG_IMAGE_LENGTH          图片长
//　　TAG_IMAGE_WIDTH          图片宽
//　　TAG_MAKE             设备制造商
//　　TAG_MODEL          设备型号
//　　TAG_ORIENTATION            方向
//　　TAG_WHITE_BALANCE        白平衡

        String datetime;// 拍摄时间
        String deviceName;// 设备品牌
        String deviceModel; // 设备型号
        String latitude;//纬度
        String latitudeRef;//纬度参考
        String latitudeNormal;//最终经度
        String longitude;//经度
        String longitudeRef;//经度参考
        String longitudeNormal;//最终经度

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLatitudeRef() {
            return latitudeRef;
        }

        public void setLatitudeRef(String latitudeRef) {
            this.latitudeRef = latitudeRef;
        }

        public String getLatitudeNormal() {
            return latitudeNormal;
        }

        public void setLatitudeNormal(String latitudeNormal) {
            this.latitudeNormal = latitudeNormal;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLongitudeRef() {
            return longitudeRef;
        }

        public void setLongitudeRef(String longitudeRef) {
            this.longitudeRef = longitudeRef;
        }

        public String getLongitudeNormal() {
            return longitudeNormal;
        }

        public void setLongitudeNormal(String longitudeNormal) {
            this.longitudeNormal = longitudeNormal;
        }

        public boolean isEmpty() {
            return StringUtils.isNullOrEmpty(latitude) ||
                    StringUtils.isNullOrEmpty(latitudeRef) ||
                    StringUtils.isNullOrEmpty(latitudeNormal) ||
                    StringUtils.isNullOrEmpty(longitude) ||
                    StringUtils.isNullOrEmpty(longitudeRef) ||
                    StringUtils.isNullOrEmpty(longitudeNormal);
        }
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();//earlier version
    }

}
