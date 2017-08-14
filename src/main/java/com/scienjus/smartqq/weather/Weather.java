package com.scienjus.smartqq.weather;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import javaBean.JsonRootBean;
import javaBean.Results;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by 11296 on 2017/8/14.
 */
public class Weather {
    public Results GetWeather(String location){
        String url = "https://api.seniverse.com/v3/weather/now.json?" +
                "key=pcn3xbj2ke1qgtxj&location={0}&language=zh-Hans&unit=c";
        url = MessageFormat.format(url,location);
        HttpRequest httpRequest = HttpRequest.get(url,Boolean.TRUE);
        String text = httpRequest.body();
        JSONObject jsStr = (JSONObject) JSONObject.parse(text);
        JsonRootBean wether = (JsonRootBean) JSONObject.toJavaObject(jsStr,JsonRootBean.class);
        List<Results> resultList = wether.getResults();
        return  resultList.get(0);
    }
}
