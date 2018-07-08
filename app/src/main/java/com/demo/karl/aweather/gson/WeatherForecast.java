package com.demo.karl.aweather.gson;

import java.util.List;

public class WeatherForecast {

    /**
     * daily_forecast : [{"cond_code_d":"302","cond_code_n":"305","cond_txt_d":"雷阵雨","cond_txt_n":"小雨","date":"2018-07-07","hum":"67","mr":"00:25","ms":"13:14","pcpn":"0.0","pop":"25","sr":"04:53","ss":"19:46","tmp_max":"29","tmp_min":"23","uv_index":"3","vis":"10","wind_deg":"-1","wind_dir":"无持续风向","wind_sc":"1-2","wind_spd":"9"},{"cond_code_d":"302","cond_code_n":"302","cond_txt_d":"雷阵雨","cond_txt_n":"雷阵雨","date":"2018-07-08","hum":"80","mr":"00:56","ms":"14:19","pcpn":"0.0","pop":"25","sr":"04:54","ss":"19:46","tmp_max":"30","tmp_min":"23","uv_index":"4","vis":"14","wind_deg":"170","wind_dir":"南风","wind_sc":"1-2","wind_spd":"9"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-07-09","hum":"76","mr":"01:30","ms":"15:27","pcpn":"0.0","pop":"1","sr":"04:54","ss":"19:46","tmp_max":"31","tmp_min":"22","uv_index":"5","vis":"18","wind_deg":"104","wind_dir":"东南风","wind_sc":"1-2","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"302","cond_txt_d":"多云","cond_txt_n":"雷阵雨","date":"2018-07-10","hum":"68","mr":"02:09","ms":"16:36","pcpn":"0.0","pop":"25","sr":"04:55","ss":"19:45","tmp_max":"30","tmp_min":"23","uv_index":"4","vis":"20","wind_deg":"82","wind_dir":"东风","wind_sc":"1-2","wind_spd":"3"},{"cond_code_d":"302","cond_code_n":"302","cond_txt_d":"雷阵雨","cond_txt_n":"雷阵雨","date":"2018-07-11","hum":"62","mr":"02:55","ms":"17:46","pcpn":"0.0","pop":"25","sr":"04:56","ss":"19:45","tmp_max":"28","tmp_min":"22","uv_index":"4","vis":"13","wind_deg":"108","wind_dir":"东南风","wind_sc":"1-2","wind_spd":"6"},{"cond_code_d":"104","cond_code_n":"101","cond_txt_d":"阴","cond_txt_n":"多云","date":"2018-07-12","hum":"79","mr":"03:49","ms":"18:52","pcpn":"0.0","pop":"2","sr":"04:56","ss":"19:45","tmp_max":"29","tmp_min":"23","uv_index":"11","vis":"17","wind_deg":"173","wind_dir":"南风","wind_sc":"1-2","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-07-13","hum":"84","mr":"04:52","ms":"19:53","pcpn":"0.0","pop":"25","sr":"04:57","ss":"19:44","tmp_max":"32","tmp_min":"24","uv_index":"8","vis":"12","wind_deg":"174","wind_dir":"南风","wind_sc":"1-2","wind_spd":"6"}]
     * basic : {"admin_area":"北京","cid":"CN101010100","cnty":"中国","lat":"39.90498734","location":"北京","lon":"116.4052887","parent_city":"北京","tz":"+8.00"}
     * status : ok
     * update : {"loc":"2018-07-07 17:50","utc":"2018-07-07 09:50"}
     */

    private BasicBean basic;
    private String status;
    private UpdateBean update;
    private List<DailyForecastBean> daily_forecast;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public static class BasicBean {
        /**
         * admin_area : 北京
         * cid : CN101010100
         * cnty : 中国
         * lat : 39.90498734
         * location : 北京
         * lon : 116.4052887
         * parent_city : 北京
         * tz : +8.00
         */

        private String admin_area;
        private String cid;
        private String cnty;
        private String lat;
        private String location;
        private String lon;
        private String parent_city;
        private String tz;

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }
    }

    public static class UpdateBean {
        /**
         * loc : 2018-07-07 17:50
         * utc : 2018-07-07 09:50
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }

    public static class DailyForecastBean {
        /**
         * cond_code_d : 302
         * cond_code_n : 305
         * cond_txt_d : 雷阵雨
         * cond_txt_n : 小雨
         * date : 2018-07-07
         * hum : 67
         * mr : 00:25
         * ms : 13:14
         * pcpn : 0.0
         * pop : 25
         * sr : 04:53
         * ss : 19:46
         * tmp_max : 29
         * tmp_min : 23
         * uv_index : 3
         * vis : 10
         * wind_deg : -1
         * wind_dir : 无持续风向
         * wind_sc : 1-2
         * wind_spd : 9
         */

        private String cond_code_d;
        private String cond_code_n;
        private String cond_txt_d;
        private String cond_txt_n;
        private String date;
        private String hum;
        private String mr;
        private String ms;
        private String pcpn;
        private String pop;
        private String sr;
        private String ss;
        private String tmp_max;
        private String tmp_min;
        private String uv_index;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCond_code_d() {
            return cond_code_d;
        }

        public void setCond_code_d(String cond_code_d) {
            this.cond_code_d = cond_code_d;
        }

        public String getCond_code_n() {
            return cond_code_n;
        }

        public void setCond_code_n(String cond_code_n) {
            this.cond_code_n = cond_code_n;
        }

        public String getCond_txt_d() {
            return cond_txt_d;
        }

        public void setCond_txt_d(String cond_txt_d) {
            this.cond_txt_d = cond_txt_d;
        }

        public String getCond_txt_n() {
            return cond_txt_n;
        }

        public void setCond_txt_n(String cond_txt_n) {
            this.cond_txt_n = cond_txt_n;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        public String getTmp_max() {
            return tmp_max;
        }

        public void setTmp_max(String tmp_max) {
            this.tmp_max = tmp_max;
        }

        public String getTmp_min() {
            return tmp_min;
        }

        public void setTmp_min(String tmp_min) {
            this.tmp_min = tmp_min;
        }

        public String getUv_index() {
            return uv_index;
        }

        public void setUv_index(String uv_index) {
            this.uv_index = uv_index;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }
}
