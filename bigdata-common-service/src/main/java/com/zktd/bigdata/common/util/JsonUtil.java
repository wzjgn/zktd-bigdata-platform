package com.zktd.bigdata.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        //日期格式处理
        objectMapper.setDateFormat(DATE_FORMAT);
    }

    /**
     * 按json转化两个对象
     * @param srcObj 源对象
     * @param targetType 目标类型
     * @param <T>
     * @return
     */
    public static <T> T convertByJson(Object srcObj, Class<T> targetType) {
        Assert.notNull(srcObj, "源对象不能为null");

        T ret = null;
        String jsonStr = null;
        try {
            if (srcObj instanceof String) {
                jsonStr = (String) srcObj;
            }
            else {
                jsonStr = objectMapper.writeValueAsString(srcObj);
            }
            ret = objectMapper.readValue(jsonStr, targetType);
        } catch (Exception e) {
            logger.error("转换json出错, srcObj={}, targetType={}, jsonStr={}", srcObj, targetType, jsonStr);
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 对象转json string
     * @param obj
     * @return
     */
    public static String toJsonStr(Object obj) {
        if (obj == null) {
            return "";
        }

        String ret = "";
        try {
            ret = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 解析json str
     * @param jsonStr
     * @param targetType
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String jsonStr, Class<T> targetType) {
        T ret = null;
        try {
            ret = objectMapper.readValue(jsonStr, targetType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

}
