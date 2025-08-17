package org.atria.Configurations;

import com.alibaba.fastjson2.JSON;

public class JsonParsingConfig {

    public static <T> T getClass(String json,Class<T> clazz){
        return JSON.parseObject(json,clazz);
    }

}
