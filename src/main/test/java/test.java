
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.scienjus.smartqq.Shadowsocks.SS;
import com.scienjus.smartqq.Shadowsocks.Shadowsocks;
import com.scienjus.smartqq.weather.Weather;
import com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;
import javaBean.JsonRootBean;
import javaBean.Results;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import javax.enterprise.inject.New;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by 11296 on 2017/8/14.
 */
public class test {

    @Test
    public void fun(){
        try {
        Properties prop = new Properties();
        InputStream inStream = ClassLoader.getSystemResourceAsStream("ss.properties");
        prop.load(inStream);
        Shadowsocks ss = new Shadowsocks();
        List<String> ss_str = new ArrayList<>();
        ss_str.add(prop.getProperty("jp01"));
        ss_str.add(prop.getProperty("jp02"));
        ss_str.add(prop.getProperty("jp03"));
        ss_str.add(prop.getProperty("us01"));
        ss_str.add(prop.getProperty("us02"));
        ss_str.add(prop.getProperty("us03"));
        for (String s_i:ss_str){
            String s = ss.get_ss(s_i);
            if(s.length()>0)
                System.out.println(s);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
