package com.demo.karl.aweather;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.karl.aweather.util.HttpUtil;
import com.demo.karl.aweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.search.Search;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseHotAreaFragment extends Fragment {

    private ProgressDialog progressDialog;

    /**
     * 市列表
     */
    private List<Basic> cityList = new ArrayList<>();
    /**
     * 选中的城市
     */
    private Basic selectedCity;

    private TextView titleText;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    public ChooseHotAreaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_hot_area, container, false);
        titleText = (TextView)view.findViewById(R.id.title_text);
        listView = (ListView)view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityList.get(position);

                if (getActivity() instanceof WeatherActivity) {
                    SharedPreferences.Editor editor = getContext().getSharedPreferences("selectedCity",MODE_PRIVATE).edit();
                    editor.putString("cid",selectedCity.getCid());
                    editor.apply();

                    WeatherActivity activity = (WeatherActivity)getActivity();
                    activity.drawerLayout.closeDrawers();
                    activity.swipeRefresh.setRefreshing(true);
                    activity.requestHeWeatherInfo(selectedCity.getCid());
                }
            }
        });

        queryHotCity();
    }

    /**
     * 查询全国所有的省，优先从数据库查找，如果数据库没有查找到在请求网络，数据库更新 TODO
     */
    private void queryHotCity() {
        showProgressDialog();

        titleText.setText("热门城市列表");

        // 地区查询
        HeWeather.getSearch(getContext(), "中国", "cn,scenic", 20, Lang.CHINESE_SIMPLIFIED, new HeWeather.OnResultSearchBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("getSearch-Log", "onError: ", throwable);
                closeProgressDialog();
                Toast.makeText(getContext(), "获取地区信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Search search) {
                Log.i("getWeatherNow-Log", "onSuccess: " + search.toString());
                List<Basic> list = search.getBasic();
                cityList = list;
                if (list.size()>0) {
                    dataList.clear();
                    for (Basic basic : list) {
                        dataList.add(basic.getLocation());
                    }
                    adapter.notifyDataSetChanged();
                    listView.setSelection(0);
                }
                closeProgressDialog();
            }
        });
//        String address = "https://search.heweather.com/top?";
//
//        showProgressDialog();
//        HttpUtil.sendOKHttpRequest(address, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeProgressDialog();
//                        // TODO 处理数据
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // 通过runOnUiThread()方法回到主线程处理逻辑
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        closeProgressDialog();
//                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}
