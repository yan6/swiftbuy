package com.ywj.swiftbuy.service.common.Impl;

import com.ywj.swiftbuy.dao.model.tables.records.AddressRecord;
import com.ywj.swiftbuy.service.common.AddressIpService;
import com.ywj.swiftbuy.service.common.CommonService;
import com.ywj.swiftbuy.web.Address;
import org.jooq.Field;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ywj
 * 使用的是百度地图服务  ，到百度开发者网站，注册一个定位服务，获取AK秘钥
 */
@Service
public class AddressIpServiceImpl extends CommonService implements AddressIpService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressIpServiceImpl.class);

    private static final String BAIDU_AK = "http://api.map.baidu.com/location/ip?ak=Gs8yLZzDwqvDawbuzpiU8bPG7hxFW4Hx&ip=";

    private static final com.ywj.swiftbuy.dao.model.tables.Address TABLE = com.ywj.swiftbuy.dao.model.tables.Address.ADDRESS;

    @Override
    public boolean insert(String city, String county) {
        Map<Field<?>, Object> toUpdate = new HashMap<>();
        toUpdate.put(TABLE.CITY, city);
        toUpdate.put(TABLE.COUNTY, county);
        toUpdate.put(TABLE.UPDATE_TIME, new Date());
        return insert(TABLE,toUpdate);
    }

    @Override
    public boolean update(Address address) {
        AddressRecord record = objectToRecord(address, AddressRecord.class);
        return insert(TABLE,record);
    }

    @Override
    public boolean exist(String city,String county) {
        return exists(TABLE, TABLE.CITY.eq(city).and(TABLE.COUNTY.eq(county)));
    }

    @Override
    public int getId(String city,String county){
        return selectOneValue(TABLE,TABLE.ID,TABLE.CITY.eq(city).and(TABLE.COUNTY.eq(county)));
    }


    @Override
    public String getCurrentIdAddress() throws Exception {
        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = readJsonFromUrl(BAIDU_AK + getPublicIP());
        System.out.println("json=" + json.toString());

        JSONObject content = json.getJSONObject("content");              //获取json对象里的content对象
        JSONObject addr_detail = content.getJSONObject("address_detail");//从content对象里获取address_detail
        String city = addr_detail.get("city").toString();                //获取市名，可以根据具体需求更改，如果需要获取省份的名字，可以把“city”改成“province”...
        return city;
    }

    @Override
    public List<Address> getCountyList(String city) {
        //如果要使用redis，后面再装
        return select(TABLE,
                TABLE.CITY.eq(city),
                Address.class);
    }


    /**
     * 得到本机的外网ip，出现异常时返回空串""
     *
     * @return
     */
    private String getPublicIP() {
        String ip = "";

        Document doc = null;
        Connection con = null;

        con = Jsoup.connect("http://www.ip138.com/ip2city.asp").timeout(10000);

        try {
            doc = con.get();

            // 获得包含本机ip的文本串：您的IP是：[xxx.xxx.xxx.xxx]
            Elements els = doc.body().select("center");
            for (org.jsoup.nodes.Element el : els) {
                ip = el.text();
            }
            // 从文本串过滤出ip，用正则表达式将非数字和.替换成空串""
            ip = ip.replaceAll("[^0-9.]", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }


    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            //关闭输入流
            is.close();
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
