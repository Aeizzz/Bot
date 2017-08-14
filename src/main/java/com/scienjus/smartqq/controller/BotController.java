package com.scienjus.smartqq.controller;


import com.scienjus.smartqq.Shadowsocks.Shadowsocks;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.Message;
import com.scienjus.smartqq.weather.Weather;
import javaBean.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * bot的控制器
 * 用来加各种功能
 *
 * @author dullwolf
 */

public class BotController {

    private static final int MESSAGE_TYPE_PRIVATE = 0;
    private static final int MESSAGE_TYPE_GROUP = 1;
    private static final int MESSAGE_TYPE_DISCUSS = 2;


    private static SmartQQClient client;

    private static Logger LOGGER = LoggerFactory.getLogger(BotController.class);

    public BotController(SmartQQClient client) {
        BotController.client = client;
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

    /**
     * 发送1~100之间的随机数
     * @param name
     * @param id
     * @param type
     */
    public void roll(String name, Long id, int type) {
        sendMessage(String.format("@%s\nroll点:%d", name, (int) (Math.random() * 100 + 1)), id, type);
    }

    /**
     * 得到参数列表，用于测试
     * @param name
     * @param id
     * @param type
     * @param params
     */
    public void rolls(String name, Long id, int type, String[] params) {
        StringBuilder sb = new StringBuilder("@" + name + "\n");
        for (int i = 1; i <= params.length - 1; i++) {
            sb.append("第").append(i).append("个参数为：").append(params[i]).append("\n");
        }
        sendMessage(sb.toString(), id, type);
    }

    /**
     * 主菜单
     * @param name
     * @param id
     * @param type
     * @param params
     */
    public void home(String name, final Long id, final int type, final String[] params) {
        try {
            final String strs = "@" + name + "\n" + "roll (1~100随机数点)\n"+
                    "天气 [地点1 地点2] (地点天气) ";

            if (params.length > 1) {
                //发送主页面板
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendMessage(strs, id, type);

                    }
                }).start();
            }

            if (null != strs) {
                sendMessage(strs, id, type);
            }
        } catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
    }

    public void weather(String name, Long id, int type, String[] params) {
        if(params.length>1) {
            StringBuffer sb = new StringBuffer("@" + name + "\n" +"天气状况如下\n");
            String s = "地点：{0}\t 天气：{1} \t 温度 {2}";
            for (int i = 1; i < params.length; i++) {
                Results results = new Weather().GetWeather(params[i]);
                String location = results.getLocation().getName();
                String text = results.getNow().getText();
                String temperature = results.getNow().getTemperature();
                String last_update = results.getLast_update();
                String s1 = MessageFormat.format(s, location, text, temperature);
                sb.append(s1 + "\n");
                if (i == params.length - 1)
                    sb.append("最后更新时间:" + last_update);
            }
            System.out.println(sb.toString());
            sendMessage(sb.toString(), id, type);
        }else{
            String s ="请具体指明地点，格式如下\n!天气 北京\n也可以多地区，如\n!天气 北京 上海 深圳\n";
            sendMessage(s,id,type);
        }
    }

    public void Shadowsocks(String name, Long id, int type) {
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
                if(s.length()>0) {
                    System.out.println(s);
                    sendMessage("@" + name + "\n免费ss账号(一天):"+s,id,type);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
