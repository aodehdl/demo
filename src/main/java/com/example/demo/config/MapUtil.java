package com.example.demo.config;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@UtilityClass
public class MapUtil {
    //MapUtil.getData(gson.fromJson(json, Map.class), ArrayList.class, "addresses", 0, "addressElements", 1);
    public static <T> T getData(Map map, Class<T> out_class,Object... keys) throws InstantiationException, IllegalAccessException {
        out_class = ("java.util.List".equals(out_class.getName()))? (Class<T>) ArrayList.class :out_class;
        out_class = ("java.util.Map".equals(out_class.getName()))? (Class<T>) Map.class :out_class;
        var key_list = Arrays.asList(keys);
        BiFunction<Object, Object, Object> fun = (map_param, key)->{
            var temp = (key instanceof String)?((Map)map_param).get((String)key)
                                                     :((List)map_param).get((Integer)key);
            return temp;
        };

        Object result = map;

        for(Object key : key_list){
            try{
                result = fun.apply(result, key);
            }catch (Exception e){
                result = null;
                break;
            }

        }

        return (result==null)?out_class.newInstance():(T) result;
    }
}
