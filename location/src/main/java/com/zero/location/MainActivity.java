package com.zero.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.zero.location.Contract.View;
import com.zero.location.listener.LocListener;
import com.zero.location.utils.FileHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements LocListener.LocChange,View {

    private static final String fileName =  "logInfo.txt";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    private TextView mLocInfo;
    private TextView mBaiduInfo;
    private TextView mVendor;
    private TextView mTime;
    private Button mButton;
    private LocationManager mLocationManager;

    private LocListener mLocListener; // 获取经纬度

    private FileHelper mFileHelper;

    private String mGpsLon;
    private String mGpsLat;
    private String mBaiduLon;
    private String mBaiduLat;

    private MyModel mModel;
    private MyPresenter mPresenter;

    private static final String TAG = "GpsActivity";

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //要做的事情
//            Log.i("操作","上传数据");
            mFileHelper.writeSDFile(String.format(getString(R.string.info),
                    Build.BRAND, df.format(new Date()),mGpsLon, mGpsLat,
                    mBaiduLon, mBaiduLat), fileName);

            mPresenter.uploadData(Build.BRAND, mGpsLon, mGpsLat, mBaiduLon, mBaiduLat,
                    df.format(new Date()));
            mHandler.postDelayed(this, 1000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFileHelper = new FileHelper(getApplicationContext());

        mModel = new MyModel();
        mPresenter = new MyPresenter();
        mPresenter.setVM(this,mModel);

        try {
            mFileHelper.createSDFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mLocInfo = (TextView) findViewById(R.id.tv_gps_location);
        mBaiduInfo = (TextView) findViewById(R.id.tv_baidu_location);
        mVendor = (TextView) findViewById(R.id.tv_vendor);
        mTime = (TextView) findViewById(R.id.tv_time);
        mButton = (Button) findViewById(R.id.btn);

        mTime.setText(df.format(new Date()));
        mVendor.setText("手机品牌：" + Build.BRAND);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 百度
        mLocListener = new LocListener(this);

        mLocListener.setListener(this);
        mLocListener.startLocation();

        // 判断GPS是否正常启动
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            // 返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
            return;
        }

        // 为获取地理位置信息时设置查询条件
        String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(bestProvider);
        updateView(location);
        // 监听状态
        mLocationManager.addGpsStatusListener(listener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);

//        mHandler.postDelayed(mRunnable, 1000);

        mButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                toMapView();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        if (null != mLocListener) {
            mLocListener.onDestroy();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(locationListener);
    }

    // 位置监听
    private LocationListener locationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            updateView(location);
//            Log.i(TAG, "时间：" + location.getTime());
//            Log.i(TAG, "经度：" + location.getLongitude());
//            Log.i(TAG, "纬度：" + location.getLatitude());
//            Log.i(TAG, "海拔：" + location.getAltitude());
        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                // GPS状态为可见时
                case LocationProvider.AVAILABLE:
//                    Log.i(TAG, "当前GPS状态为可见状态");
                    break;
                // GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
//                    Log.i(TAG, "当前GPS状态为服务区外状态");
                    break;
                // GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
//                    Log.i(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = mLocationManager.getLastKnownLocation(provider);
            updateView(location);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
            updateView(null);
        }

    };

    // 状态监听
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                // 第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
//                    Log.i(TAG, "第一次定位");
                    break;
                // 卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                    Log.i(TAG, "卫星状态改变");
                    // 获取当前状态
                    GpsStatus gpsStatus = mLocationManager.getGpsStatus(null);
                    // 获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    // 创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
                            .iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
//                    System.out.println("搜索到：" + count + "颗卫星");
                    break;
                // 定位启动
                case GpsStatus.GPS_EVENT_STARTED:
//                    Log.i(TAG, "定位启动");
                    break;
                // 定位结束
                case GpsStatus.GPS_EVENT_STOPPED:
//                    Log.i(TAG, "定位结束");
                    break;
            }
        }
    };

    /**
     * 实时更新文本内容
     *
     * @param location
     */
    private void updateView(Location location) {
        if (location != null) {
            mGpsLon = String.valueOf(location.getLongitude());
            mGpsLat = String.valueOf(location.getLatitude());
            mLocInfo.setText("经度：");
            mLocInfo.append(mGpsLon);
            mLocInfo.append("\n纬度：");
            mLocInfo.append(mGpsLat);
        } else {
            // 清空EditText对象
//            mLocInfo.getEditableText().clear();
        }
    }

    public void toMapView() {
        Intent intent = new Intent();
        intent.setClass(this, MapActivity.class);
        startActivity(intent);
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    @Override
    public void sendLoc(BDLocation location) {
        mBaiduLon = String.valueOf(location.getLongitude());
        mBaiduLat = String.valueOf(location.getLatitude());
        mBaiduInfo.setText("经度：");
        mBaiduInfo.append(mBaiduLon);
        mBaiduInfo.append("\n纬度：");
        mBaiduInfo.append(mBaiduLat);
    }

    @Override
    public void uploadSuc() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
