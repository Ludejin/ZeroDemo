package com.zero.location.listener;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by Jin_ on 2016/6/6
 * 邮箱：dejin_lu@creawor.com
 */
public class LocListener implements BDLocationListener {
    private LocChange mListener;

    private LocationClient mLocationClient = null;

    public void setListener(LocChange listener) {
        mListener = listener;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            if (null != mListener) {
                mListener.sendLoc(bdLocation);
                Log.i("定位类型", "GPS定位结果");
            }
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            if (null != mListener) {
                Log.i("定位类型", "网络定位结果");
                mListener.sendLoc(bdLocation);
            }
        } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            if (null != mListener) {
                mListener.sendLoc(bdLocation);
            }
            Log.i("定位", "离线定位成功，离线定位结果也是有效的");
        } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
            Log.e("定位", "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
            Log.e("定位", "网络不同导致定位失败，请检查网络是否通畅");
        } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
            Log.e("定位", "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
    }

    public interface LocChange {
        void sendLoc(BDLocation location);
    }

    public LocListener(Context context) {
        mLocationClient = new LocationClient(context);
        mLocationClient.registerLocationListener(this);
        initOption();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void onDestroy() {
        mLocationClient.unRegisterLocationListener(this);
        mLocationClient = null;
    }

    private void initOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll"); //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
}
