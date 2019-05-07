package com.autils.framework.data.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.autils.framework.common.log.L;
import com.autils.framework.ui.base.BaseApplication;

/**
 * Created by fengyulong on 2018/6/22.
 */
public class SimpleLocation {
    private String longitude;

    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @SuppressLint("MissingPermission")
    public static SimpleLocation getLocation() {
        SimpleLocation simpleLocation = null;
        try {
            LocationManager manager = (LocationManager) BaseApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();//Criteria类是设置定位的标准信息（系统会根据你的要求，匹配最适合你的定位供应商），一个定位的辅助信息的类
            criteria.setPowerRequirement(Criteria.POWER_LOW);//设置低耗电
            criteria.setAltitudeRequired(true);//设置需要海拔
            criteria.setBearingAccuracy(Criteria.ACCURACY_COARSE);//设置COARSE精度标准
            criteria.setAccuracy(Criteria.ACCURACY_LOW);//设置低精度


            String provider = manager.getBestProvider(criteria, true);

            Location location;

            if (LocationManager.GPS_PROVIDER.equals(provider)) {
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (null == location) {
                    provider = LocationManager.NETWORK_PROVIDER;
                    location = manager.getLastKnownLocation(provider);
                }
            } else {
                location = manager.getLastKnownLocation(provider);
            }

            simpleLocation = new SimpleLocation();
            simpleLocation.setLongitude(location.getLongitude() + "");
            simpleLocation.setLatitude(location.getLatitude() + "");
        } catch (Exception e) {
            L.e(e);
        }
        return simpleLocation;
    }
}
