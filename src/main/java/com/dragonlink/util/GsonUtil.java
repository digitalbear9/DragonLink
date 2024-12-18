package com.dragonlink.util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {
    //使用Gson实现JSON的序列化与反序列化（带有默认的日期格式）
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    //获取默认的Gson实例
    public static Gson getGson() {
        return gson;
    }
    //使用指定的日期格式获取Gson实例
    public static Gson getGson(String dateFormat) {
        return new GsonBuilder().setDateFormat(dateFormat).create();
    }
    // 序列化方法：将Java对象转换为JSON字符串
    public static String serialize(Object obj) {
        try {
            return gson.toJson(obj);
        } catch (JsonSyntaxException e) {
            // 捕获JSON语法异常
            System.err.println("JSON Syntax Error during serialization: " + e.getMessage());
        } catch (JsonParseException e) {
            // 捕获JSON解析异常
            System.err.println("JSON Parse Error during serialization: " + e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常
            System.err.println("Unexpected error during serialization: " + e.getMessage());
        }
        // 如果发生错误，返回null
        return null;
    }
    // 反序列化方法：将JSON字符串转换为Java对象
    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            // 捕获JSON语法异常
            System.err.println("JSON Syntax Error during deserialization: " + e.getMessage());
        } catch (JsonParseException e) {
            // 捕获JSON解析异常
            System.err.println("JSON Parse Error during deserialization: " + e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常
            System.err.println("Unexpected error during deserialization: " + e.getMessage());
        }
        // 如果发生错误，返回null
        return null;
    }
    // 使用指定日期格式的反序列化方法
    public static <T> T deserializeWithDateFormat(String json, Class<T> clazz, String dateFormat) {
        try {
            Gson customGson = new GsonBuilder().setDateFormat(dateFormat).create();
            return customGson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            // 捕获JSON语法异常
            System.err.println("JSON Syntax Error during deserialization with custom date format: " + e.getMessage());
        } catch (JsonParseException e) {
            // 捕获JSON解析异常
            System.err.println("JSON Parse Error during deserialization with custom date format: " + e.getMessage());
        } catch (Exception e) {
            // 捕获其他异常
            System.err.println("Unexpected error during deserialization with custom date format: " + e.getMessage());
        }
        // 如果发生错误，返回null
        return null;
    }
}
