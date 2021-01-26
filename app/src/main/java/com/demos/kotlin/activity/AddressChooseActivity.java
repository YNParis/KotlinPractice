package com.demos.kotlin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.demos.kotlin.R;
import com.demos.kotlin.adaper.AddressAdapter;
import com.demos.kotlin.utils.CoordinateTransform;
import com.demos.kotlin.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 选择地址列表
 */
public class AddressChooseActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<PoiInfo> list = new ArrayList<>();
    private EditText editText;
    private PoiSearch mPoiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initMap();
    }

    private void initMap() {
        mPoiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                dealSearchResult(poiResult);
                Log.e("map", poiResult.toString());
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
                Log.e("map", poiDetailSearchResult.toString());
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Log.e("map", poiIndoorResult.toString());
            }

            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Log.e("map", poiDetailResult.toString());
            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
    }

    private void dealSearchResult(PoiResult result) {
        list.clear();
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            list.addAll(result.getAllPoi());
        }
        refreshList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressListWithLatLon();
    }

    /**
     * 根据经纬度获取附近的地址列表
     */
    private void getAddressListWithLatLon() {

        double longitude = 116.316833;
        double latitude = 39.998877;
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(latitude, longitude))
                .radius(1000)
                //支持多个关键字并集检索，不同关键字间以$符号分隔，最多支持10个关键字检索。如:”银行$酒店”
                .keyword("北京大学")
                .pageNum(0));

    }

    /**
     * 根据关键字模糊查询附近的地址列表
     */
    private void getAddressListWithKeyword(String keyword) {
        double longitude = 116.316833;
        double latitude = 39.998877;
        // 配置请求参数
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .location(new LatLng(latitude, longitude))
                .radius(1000)
                //支持多个关键字并集检索，不同关键字间以$符号分隔，最多支持10个关键字检索。如:”银行$酒店”
                .keyword(keyword)
                .pageNum(0);
        // 发起检索
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    private void initView() {
        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.edit_contact);
        editText.setHint("搜索附近位置");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    /*TODO 搜索地址*/
                    getAddressListWithKeyword(editable.toString());
                } else {
                    /*清空地址列表*/
                    list.clear();
                    refreshList();
                }
            }
        });

    }

    AddressAdapter adapter;

    private void initData() {
        adapter = new AddressAdapter(this, list);
        adapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onAddressItemClick(position);
            }
        });
        listView.setAdapter(adapter);
    }

    private void onAddressItemClick(int position) {
        ToastUtil.show(AddressChooseActivity.this, "位置信息：" + list.get(position).getAddress());
        Log.e("map", "之前的经纬度" + list.get(position).getLocation().toString());
        double[] latLon = CoordinateTransform.transformBD09ToWGS84(list.get(position).getLocation().longitude, list.get(position).getLocation().latitude);
        Log.e("map", "之后的经纬度" + Arrays.toString(latLon));
        double[] latLon2 = CoordinateTransform.transformWGS84ToBD09(latLon[0], latLon[1]);
        Log.e("map", "再转回之前的经纬度" + Arrays.toString(latLon2));
        CoordinateConverter converter = new CoordinateConverter()
                .from(CoordinateConverter.CoordType.GPS)
                .coord(new LatLng(latLon[1], latLon[0]));

        //desLatLng 转换后的坐标
        LatLng desLatLng = converter.convert();
        Log.e("map", "使用官方的转换方法转换：" + desLatLng.toString());

    }

    private void refreshList() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            initData();
        }
    }


    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("address", "朝阳区创达三路");
        intent.putExtra("longitude", "130.99");
        intent.putExtra("latitude", "40.9");
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (mPoiSearch != null)
            mPoiSearch.destroy();
        super.onDestroy();
    }
}
