package config;

import com.alibaba.fastjson.JSON;


import javax.persistence.AttributeConverter;

/**
 * JPA的json格式存储
 * @author xiaozj
 */
public class JpaConverterListJson implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return JSON.toJSONString(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return JSON.parseArray(s);
    }
}