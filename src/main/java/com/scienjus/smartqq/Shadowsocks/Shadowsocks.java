package com.scienjus.smartqq.Shadowsocks;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.codehaus.jackson.map.ObjectMapper;
import sun.misc.BASE64Decoder;
import java.io.IOException;
import java.text.MessageFormat;


/**
 * Created by 11296 on 2017/8/14.
 */
public class Shadowsocks {
    public String get_ss(String params) throws IOException {
        String url = "http://api.wwei.cn/dewwei.html?data={0}&apikey=20170814166190";
        url = MessageFormat.format(url,params);
        System.out.println(url);
        HttpRequest httpRequest=null;
        int i=0;
        JSONObject jsStr = new JSONObject();
        String text = null;
        for (i=0;i<10;i++){
            httpRequest = HttpRequest.get(url, Boolean.TRUE);
             text= httpRequest.body();
             jsStr= (JSONObject) JSONObject.parse(text);
            if(jsStr.get("status").equals(1)){
                break;
            }
        }
        if(i==10) return null;
        ObjectMapper mapper = new ObjectMapper();
        SS ss = mapper.readValue(text,SS.class);
        String rawdata = ss.getData().getRawText();
        rawdata = rawdata.substring(5);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] ss_str = decoder.decodeBuffer(rawdata);
        String result = new String(ss_str,"utf-8");
        return result;
    }
}
