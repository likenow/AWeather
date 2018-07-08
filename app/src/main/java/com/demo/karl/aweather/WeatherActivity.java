package com.demo.karl.aweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.karl.aweather.gson.WeatherNow;
import com.demo.karl.aweather.gson.WeatherForecast;
import com.demo.karl.aweather.gson.AirNow;
import com.demo.karl.aweather.gson.WeatherLifeStyle;
import com.demo.karl.aweather.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // 初始化控件
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);

        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);

        if (weatherString != null) {
            // TODO 有缓存时直接解析天气数据
//            Weather weather = Utility.handleWeatherResponse(weatherString);
//            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
//            weatherLayout.setVisibility(View.INVISIBLE);
//            weatherLayout.setVisibility(View.VISIBLE);
            // 加载动画
            showProgressDialog();
            requestHeWeatherInfo();
        }

        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
    }
    /**
     * 加载必应每日一图
     * https://www.dujin.org/sys/bing/1920.php
     */
    private void loadBingPic() {
        String requestBingPic = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ret = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(ret);
                    JSONArray jsonArray = jsonObject.getJSONArray("images");
                    JSONObject imgobj = jsonArray.getJSONObject(0);
                    String url = imgobj.getString("url");
                    final String picUrl = "http://s.cn.bing.net"+url;
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                    editor.putString("bing_pic", picUrl);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(WeatherActivity.this).load(picUrl).into(bingPicImg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void requestHeWeatherInfo() {
        // 即时天气
        HeWeather.getWeatherNow(this, "CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("getWeatherNow-Log", "onError: ", e);
                Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List dataObject) {
                Gson gson = new Gson();

                String ret = gson.toJson(dataObject);
                Log.i("getWeatherNow-Log", "onSuccess: " + ret);

                JsonObject jsonObj = getJsonObjFromResponse(ret);
                // GSON直接解析成对象
                final WeatherNow weatherNow = new Gson().fromJson(jsonObj.toString(),WeatherNow.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 刷新数据
                        // 城市
                        String cityName = weatherNow.getBasic().getParent_city();
                        titleCity.setText(cityName);
                        // 温度
                        String degree = weatherNow.getNow().getTmp() + "℃";
                        degreeText.setText(degree);

                        // 天气信息
                        String weatherInfo = weatherNow.getNow().getCond_txt();
                        weatherInfoText.setText(weatherInfo);
                    }
                });
            }
        });
        // 3-10天天气预报
        HeWeather.getWeatherForecast(this,"CN101010100", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("getWeatherForecast-Log", "onError:", e);
                Toast.makeText(WeatherActivity.this, "获取天气预报信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List dataObject) {
                String ret = new Gson().toJson(dataObject);
                Log.i("getWeatherForecast-Log", "onSuccess: " + ret);

                JsonObject jsonObj = getJsonObjFromResponse(ret);
                // GSON直接解析成对象
                final WeatherForecast weatherForecast = new Gson().fromJson(jsonObj.toString(),WeatherForecast.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        forecastLayout.removeAllViews();

                        for (WeatherForecast.DailyForecastBean forecast : weatherForecast.getDaily_forecast()) {
                            View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item, forecastLayout, false);
                            TextView dateText = (TextView) view.findViewById(R.id.date_text);
                            TextView infoText = (TextView) view.findViewById(R.id.info_text);
                            TextView maxText = (TextView) view.findViewById(R.id.max_text);
                            TextView minText = (TextView) view.findViewById(R.id.min_text);

                            dateText.setText(forecast.getDate());
                            infoText.setText("白天："+forecast.getCond_txt_d()+"\n"+"晚上："+forecast.getCond_txt_n());
                            maxText.setText("最高气温"+forecast.getTmp_max() + "℃");
                            minText.setText("最低气温"+forecast.getTmp_min() + "℃");
                            forecastLayout.addView(view);
                        }
                        closeProgressDialog();
                    }
                });
            }
        });
        // 空气质量
        HeWeather.getAirNow(this, "北京", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("getAirNow-Log", "onError", e);
                Toast.makeText(WeatherActivity.this, "获取空气质量信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List dataObject) {
                String ret = new Gson().toJson(dataObject);
                Log.i("getAirNow-Log", "onSuccess"+ ret);

                JsonObject jsonObj = getJsonObjFromResponse(ret);

                // GSON直接解析成对象
                final AirNow airNow = new Gson().fromJson(jsonObj.toString(),AirNow.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (airNow.getAir_now_city().getAqi() != null) {
                            aqiText.setText(airNow.getAir_now_city().getAqi());
                            pm25Text.setText(airNow.getAir_now_city().getPm25());
                        }
                    }
                });
            }
        });
        // 生活指数
        HeWeather.getWeatherLifeStyle(this, "北京", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherLifeStyleBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("getWeatherLifeStyle-Log", "onError", e);
                Toast.makeText(WeatherActivity.this, "获取生活指数信息失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List dataObject) {
                String ret = new Gson().toJson(dataObject);
                Log.i("getWeatherLifeStyle-Log", "onSuccess"+ ret);

                JsonObject jsonObj = getJsonObjFromResponse(ret);
                // GSON直接解析成对象
                final WeatherLifeStyle weatherLifeStyle = new Gson().fromJson(jsonObj.toString(),WeatherLifeStyle.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (WeatherLifeStyle.LifestyleBean lifeStyle :
                                weatherLifeStyle.getLifestyle()) {
                            if (lifeStyle.getType().toString().equalsIgnoreCase("comf")) {
                                String comfort = "舒适度：" + lifeStyle.getBrf();
                                comfortText.setText(comfort);
                            } else if (lifeStyle.getType().toString().equalsIgnoreCase("cw")) {
                                String carWash = "洗车指数：" + lifeStyle.getBrf();
                                carWashText.setText(carWash);
                            } else if (lifeStyle.getType().toString().equalsIgnoreCase("sport")) {
                                String sport = "运动建议：" + lifeStyle.getBrf();
                                sportText.setText(sport);
                            }
                        }
                    }
                });
            }
        });
    }
    public JsonObject getJsonObjFromResponse(String response) {
        // Json的解析类对象
        JsonParser parser = new JsonParser();
        // 将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(response).getAsJsonArray();
        JsonElement element = jsonArray.get(0);
        JsonObject jsonObj = element.getAsJsonObject();
        return jsonObj;
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
