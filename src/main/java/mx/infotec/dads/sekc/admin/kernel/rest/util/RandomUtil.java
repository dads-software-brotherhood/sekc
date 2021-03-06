package mx.infotec.dads.sekc.admin.kernel.rest.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

/**
 *
 * @author wisog
 */
public final class RandomUtil {
    private RandomUtil(){}
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RandomUtil.class);
    
    private static List<Field> getFields(Object obj){
        List<Field> allFields = new ArrayList<>();
        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
            
            Field[] fields = c.getDeclaredFields();
            allFields.addAll(Arrays.asList(fields));
        }
        return allFields;
    }
    
    private static List<Method> getMethods(Object obj){
        List<Method> allMethods = new ArrayList<>();
        for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
            Method[] methods = c.getDeclaredMethods();
            allMethods.addAll(Arrays.asList(methods));
        }
        return allMethods;
    }
    
    private static Object runGetter(Field field, Object  o) {
        for (Method method : getMethods(o)) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)) && 
                    method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                try {
                    return method.invoke(o);
                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException ex) {
                    LOGGER.info("Could not determine method: " + method.getName(), ex);
                }
            }
        }
        return null;
    }
    
    public static Map filterResponseFields(Object content, List<String> filter) {
        List<Field> camposClase = getFields(content);
        
        Map<String, Object> map = new HashMap<>();
        for (Field f : camposClase){
            if (filter.contains(f.getName())){
                // It's a filtered property, we put it on the response
                map.put(f.getName(), runGetter(f, content));
            }
        }
        return map;
    }
}
