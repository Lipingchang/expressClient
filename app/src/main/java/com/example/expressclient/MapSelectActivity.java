package com.example.expressclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class MapSelectActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int LocationResult = 1212;
    public final static String LocationResult_src = "src";
    public final static String LocationResult_dst = "dst";

    private final static String TAG = MapSelectActivity.class.getSimpleName();

    private EditText et_src_province;
    private EditText et_src_street;
    private EditText et_dst_province;
    private EditText et_dst_street;
    private Button btn_draw_map;
    private Button btn_confirm;
    private Button btn_searchSrc;
    private Button btn_searchDst;
    private Context context;
    private LatLng src;
    private LatLng dst;
    private PoiInfo srcDescription;
    private PoiInfo dstDescription;


    private MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);
        context = this;
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        et_src_province  = findViewById(R.id.et_src_province);
        et_src_street = findViewById(R.id.et_src_street);
        et_dst_province = findViewById(R.id.et_dst_province);
        et_dst_street = findViewById(R.id.et_dst_street);

        btn_confirm = findViewById(R.id.btn_location_confirm);
        btn_draw_map = findViewById(R.id.btn_draw_map);
        btn_searchSrc = findViewById(R.id.btn_src_search);
        btn_searchDst = findViewById(R.id.btn_dst_search);
        btn_confirm.setOnClickListener(this);  // 确定地点
        btn_draw_map.setOnClickListener(this); // 绘制两点的路线
        btn_searchSrc.setOnClickListener(this);
        btn_searchDst.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if ( vid == R.id.btn_location_confirm ){
            if (src==null || dst==null ){
                Toast.makeText(context,"需要两个地址！！",Toast.LENGTH_LONG).show();
                return;
            }
            Intent ret = new Intent();
            ret.putExtra(LocationResult_src,src);
            ret.putExtra(LocationResult_dst,dst);
            setResult(LocationResult,ret);
            finish();
        }
        if ( vid == R.id.btn_draw_map ){  // 查找路线
            if ( src != null && dst != null )
                drawRoute(PlanNode.withLocation(src),PlanNode.withLocation(dst));
            else
                Toast.makeText(context,"需要两个地址！！",Toast.LENGTH_LONG).show();
        }
        if ( vid == R.id.btn_src_search ){
            String sp = et_src_province.getText().toString();
            String ss = et_src_street.getText().toString();
            searchPoint(sp,ss,true);
            closeKeyBoard();
        }
        if ( vid == R.id.btn_dst_search ){
            String dp = et_dst_province.getText().toString();
            String ds = et_dst_street.getText().toString();
            searchPoint(dp,ds,false);
            closeKeyBoard();
        }
    }
    private void closeKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
    private void drawRoute(PlanNode stNode,PlanNode enNode){
        final BaiduMap baiduMap = mMapView.getMap();
        RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {}

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) { }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) { }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) { }

            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) { }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if ( drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR){
                    Log.e(TAG,"驾车导航没找到");
                    return;
                }
                //创建DrivingRouteOverlay实例
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                if (drivingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条路线为例）
                    //为DrivingRouteOverlay实例设置数据
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    //在地图上绘制DrivingRouteOverlay
                    overlay.addToMap();
                }
            }
        };
        mSearch.setOnGetRoutePlanResultListener(listener);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }
    private void searchPoint(final String src_p, final String src_s,final boolean isSrc){
        final BaiduMap baiduMap = mMapView.getMap();
        PoiSearch mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if ( poiResult.error != SearchResult.ERRORNO.NO_ERROR ) {
                    Log.e(TAG, "查找地址失败:" + src_p + "," + src_s);
                    Toast.makeText(context,"查找地址失败:" + src_p + "," + src_s,Toast.LENGTH_LONG).show();
                    return;
                }
                for( PoiInfo info : poiResult.getAllPoi())
                    Log.d(TAG,info.toString());
                PoiInfo info = poiResult.getAllPoi().get(0);
                LatLng point = info.getLocation();
                if ( isSrc ) {
                    src = point;
                    srcDescription = info;
                }
                else {
                    dst = point;
                    dstDescription = info;
                }

                BitmapDescriptor bitmap = isSrc ?
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_express_send_img):
                        BitmapDescriptorFactory.fromResource(R.drawable.ic_express_receive_img);
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                baiduMap.addOverlay(option);
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));


            }
            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {}
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {}
        };
        baiduMap.clear();
        if ( isSrc ){
            src = null;
            if ( dst!= null ){
                baiduMap.addOverlay(new MarkerOptions().position(dst).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_express_receive_img)));
            }
        }else{
            dst = null;
            if ( src!= null ){
                baiduMap.addOverlay(new MarkerOptions().position(src).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_express_send_img)));
            }
        }
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(src_p) //必填
                .keyword(src_s) //必填
                .scope(1)
                .pageNum(1));


    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

}
