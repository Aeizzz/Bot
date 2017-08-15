package test;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.scienjus.smartqq.Tuling.Tuling;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11296 on 2017/8/15.
 */
public class main {
    public static void main(String[] args) {
        String url = "http://www.tuling123.com/openapi/api";
        String key = "59af6b4df2c9a7c850bedeb10b67a605";
        String info = "讲个笑话";
        Map<String,String> map = new HashMap<>();
        map.put("key",key);
        map.put("info",info);
        map.put("userid","123456");
        HttpRequest httpRequest = HttpRequest.post(url,map,Boolean.TRUE);
        String text = httpRequest.body();
        JSONObject data = (JSONObject) JSONObject.parse(text);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Tuling tuling = mapper.readValue(text, Tuling.class);
            int code = tuling.getCode();
            System.out.println(code);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
