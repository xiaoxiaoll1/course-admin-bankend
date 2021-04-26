package util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 * @author xiaozj
 */
@SuppressWarnings("rawtypes")
public class GenericClassUtils {

    public static Class getSuperClassGenericType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return null;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return null;
        }

        if (!(params[index] instanceof Class)) {
            return null;
        }
        return (Class) params[index];
    }
}