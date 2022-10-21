package com.niukedemo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;
import org.thymeleaf.util.StringUtils;

import java.util.Map;
import java.util.UUID;

/**
 * @author 刘欢
 * @date 2022年10月08日 16:58
 */

public class CommunityUtil {
    /**
     * @Description: 生成随机字符串
     * @Param: []
     * @return: java.lang.String
     * @Author: 刘欢
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @Description: md5加密，为防止黑客破解，后缀加盐（自定义的几个字母），
     * @Param: [key]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    public static String md5(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * @Description: fastjson工具
     * @Param: [code, msg, map]
     * @return: java.lang.String
     * @Author: 刘欢
     */
    public static String getJsonString(int code, String msg, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        }
        return jsonObject.toString();
    }

    public static String getJsonString(int code, String msg) {
        return getJsonString(code, msg, null);
    }

    public static String getJsonString(int code) {
        return getJsonString(code, null, null);
    }
}
