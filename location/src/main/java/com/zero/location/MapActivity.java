package com.zero.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.zero.location.listener.LocListener;
import com.zero.location.listener.LocListener.LocChange;

/**
 * Created by Jin_ on 2016/8/5
 * 邮箱：Jin_Zboy@163.com
 */
public class MapActivity extends AppCompatActivity implements LocChange {
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Button mBtn;

    private LocListener mLocListener; // 定位监听器
    private LatLng mLatLng;
    private boolean isFirstLoc = true; // 是否首次定位

    private ImageView mLocButton;

    private double lng;
    private double lat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);

        init();
        mMapView = (TextureMapView) findViewById(R.id.mapView);
        mBtn = (Button) findViewById(R.id.btn);
        if (mBaiduMap == null) {
            mBaiduMap = mMapView.getMap();
            setUpMap();
        }

        initEvent();
    }

    private void init() {

        mLocButton = (ImageView) findViewById(R.id.location);
        mLocListener = new LocListener(this);

        mLocListener.setListener(this);
    }

    private void initEvent() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                updateMapState(mapStatus);
            }
        });

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("取点位置信息","\n经度：" + lng + "\n纬度：" + lat);
            }
        });

        mLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLocationData locData = new MyLocationData.Builder()
                        /** 此处设置开发者获取到的方向信息，顺时针0-360 */
                        .accuracy(100.0f)
                        .latitude(mLatLng.latitude)
                        .longitude(mLatLng.longitude).build();
                mBaiduMap.setMyLocationData(locData);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(mLatLng).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });
    }

    private void updateMapState(MapStatus status) {
        LatLng mCenterLatLng = status.target;
        /**获取经纬度*/
        lat = mCenterLatLng.latitude;
        lng = mCenterLatLng.longitude;

//        Log.i("位置信息","经度：" + lng + "\n纬度：" + lat);
    }

    private void setUpMap() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        mLocListener.stopLocation();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        mLocListener.startLocation();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void sendLoc(BDLocation location) {
        if (location == null || mMapView == null) {
            return;
        }
        LatLng newLatLng = new LatLng(location.getLatitude(),
                location.getLongitude());
        if (25.0f <= DistanceUtil.getDistance(newLatLng, mLatLng)) {
            mLatLng = newLatLng;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    /** 此处设置开发者获取到的方向信息，顺时针0-360 */
                    .latitude(mLatLng.latitude)
                    .longitude(mLatLng.longitude).build();
            mBaiduMap.setMyLocationData(locData);
        }
        if (isFirstLoc) {
            isFirstLoc = false;
            mLatLng = newLatLng;
            MyLocationData locData = new MyLocationData.Builder()
                    /** 此处设置开发者获取到的方向信息，顺时针0-360 */
                    .accuracy(location.getRadius())
                    .latitude(mLatLng.latitude)
                    .longitude(mLatLng.longitude).build();
            mBaiduMap.setMyLocationData(locData);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(mLatLng).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }
}
