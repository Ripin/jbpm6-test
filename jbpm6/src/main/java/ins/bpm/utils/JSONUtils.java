/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package ins.bpm.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: pandy Date: 13-7-6 Time: 下午5:26 To change
 * this template use File | Settings | File Templates.
 */
public class JSONUtils {

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    public static String bean2str(Object obj) {
        try {
            ObjectMapper mapper = getObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
            mapper.writeValue(gen, obj);
            gen.close();
            String json = writer.toString();
            writer.close();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object str2bean(String json, Class<?> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            Object domain = mapper.readValue(json, clazz);
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object str2list(String json, TypeReference valueTypeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            Object domain = mapper.readValue(json, valueTypeRef);
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object str2list(String json, Class<?> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);

            Object domain = mapper.readValue(json, type);
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // new TypeReference<Map<String,Object>>() { }
    public static Object str2map(String json, TypeReference valueTypeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            Object domain = mapper.readValue(json, valueTypeRef);
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> str2map(String json) {
        try {
            ObjectMapper mapper = getObjectMapper();
            Map<String, Object> domain = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String list2JsonString(List l) {
        StringWriter sw = new StringWriter();

        try {
            ObjectMapper mapper = getObjectMapper();
            mapper.writeValue(sw, l);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static Object map2bean(Map map, Class<?> clazz) {

        String json = map2str(map);

        try {
            ObjectMapper mapper = getObjectMapper();
            Object domain = mapper.readValue(json, clazz);
            return domain;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String map2str(Map map) {
        StringWriter sw = new StringWriter();

        try {
            ObjectMapper mapper = getObjectMapper();
            mapper.writeValue(sw, map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static Map bean2map(Object o){

        String s = bean2str(o);
        Map map = str2map(s);
        return map;
    }

}
