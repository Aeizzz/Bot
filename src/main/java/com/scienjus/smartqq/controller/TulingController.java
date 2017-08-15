package com.scienjus.smartqq.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.scienjus.smartqq.Tuling.NewList;
import com.scienjus.smartqq.Tuling.Tuling;
import com.scienjus.smartqq.client.SmartQQClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 11296 on 2017/8/15.
 */
public class TulingController {
    private static final int MESSAGE_TYPE_PRIVATE = 0;
    private static final int MESSAGE_TYPE_GROUP = 1;
    private static final int MESSAGE_TYPE_DISCUSS = 2;

    private static SmartQQClient client;

    private static Logger LOGGER = LoggerFactory.getLogger(BotController.class);

    public TulingController(SmartQQClient client) {
        this.client = client;
    }


    private void sendMessage(String msg, Long id, int type) {
        switch (type) {
            case MESSAGE_TYPE_PRIVATE:
                System.out.println("发送私聊信息");
                client.sendMessageToFriend(id, msg);
                break;
            case MESSAGE_TYPE_GROUP:
                System.out.println("发送群信息");
                client.sendMessageToGroup(id, msg);
                break;
            case MESSAGE_TYPE_DISCUSS:
                System.out.println("发送讨论组信息");
                client.sendMessageToDiscuss(id, msg);
                break;
            default:
                LOGGER.error(String.format("发送类型错误(%s)", type));
                break;
        }
    }

    public void sendTuling(String msg,String name,Long id,int type){
        try {
            msg = msg.substring(1);
            String url = "http://www.tuling123.com/openapi/api";
            String key = "59af6b4df2c9a7c850bedeb10b67a605";
            Map<String, String> map = new HashMap<>();
            map.put("key", key);
            map.put("info", msg);
            map.put("userid", id.toString());
            HttpRequest httpRequest = HttpRequest.post(url, map, Boolean.TRUE);
            String text = httpRequest.body();
            JSONObject data = (JSONObject) JSONObject.parse(text);
            ObjectMapper mapper = new ObjectMapper();
            Tuling tuling = mapper.readValue(text, Tuling.class);
            int code = tuling.getCode();
            switch (code) {
                case 100000:
                    sendMessage("@" + name + tuling.getText(), id, type);
                    break;
                case 200000:
                    sendMessage("@" + name + tuling.getText() + "\n" + tuling.getUrl(), id, type);
                    break;
                case 302000:
                    StringBuffer sb = new StringBuffer("");
                    sb.append("@" + name);
                    sb.append(tuling.getText());
                    List<NewList> lists = tuling.getList();
                    String s = "article:{0}\nsource{1}\ndetailurl{2}\n";
                    for (NewList item : lists) {
                        sb.append(MessageFormat.format(s,
                                item.getArticle(), item.getSource(), item.getDetailurl()));

                    }
                    sb.append("以上共有" + lists.size() + "条");
                    sendMessage(sb.toString(), id, type);
                    break;
                case 308000:
                    sendMessage("@" + name + "暂不支持该类型\n", id, type);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
