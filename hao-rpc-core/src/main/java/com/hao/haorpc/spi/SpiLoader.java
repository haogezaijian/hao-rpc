package com.hao.haorpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.hao.haorpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
 public class SpiLoader {

     /**
      * 存储已经加载的类
      */
     private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

     /**
      * 对象实力缓存
      */
     private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

     /**
      * 系统SPI目录
      */
     private static final String RPC_SYSTEM_SPI_DIR = "META-INFO/rpc/system/";

     /**
      * 用户自定义SPI目录
      */
     private static final String RPC_CUSTOM_SPI_DIR = "META-INFO/rpc/custom/";

     /**
      * 扫描路径
      */
     private static final String[] SCAN_DIRS = new String[]{RPC_CUSTOM_SPI_DIR, RPC_SYSTEM_SPI_DIR};

     /**
      * 动态加载的类列表
      */
     private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);

     /**
      * 加载所有类型
      *
      * @author haoge
      * @version 5.0.0
      * @date 2024/05/02
      */
     public static void loadAll() {
         log.info("加载所有SPI");
         for (Class<?> aClass : LOAD_CLASS_LIST) {
             load(aClass);
         }
     }

     /**
      * 获取某个接口实例
      *
      * @param tClass t class
      * @param key    key
      * @return {@code T }
      * @author haoge
      * @version 5.0.0
      * @date 2024/05/02
      */
     public static <T> T getInstance(Class<?> tClass, String key) {
         String tClassName = tClass.getName();
         Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
         if (keyClassMap == null) {
             throw new RuntimeException(String.format("SpiLoader未加载%s类型", tClassName));
         }
         if (!keyClassMap.containsKey(key)) {
             throw new RuntimeException(String.format("SpiLoader 的 %s不存在key=%s的类型",tClassName, key));
         }
                 //获取到要加载的实现类型
                 Class implClass = keyClassMap.get(key);
                 //从实例缓存中加载指定类型的实例
                 String implClassName = implClass.getName();
                 if (!instanceCache.containsKey(implClassName)) {
                     try {
                         instanceCache.put(implClassName, implClass.newInstance());
                     } catch (Exception e) {
                         String errorMsg = String.format("%s类实例化失败", implClassName);
                         throw new RuntimeException(errorMsg, e);

                     }
                 }
         return (T) instanceCache.get(implClassName);

     }

     /**
      * 加载某个类型
      *
      * @param loadClass load class
      * @return {@code Map<String, Class<?>> }
      * @author haoge
      * @version 5.0.0
      * @date 2024/05/02
      */
     public static Map<String, Class<?>> load(Class<?> loadClass) {
         log.info("加载类型为 {} 的 SPI", loadClass.getName());
         //扫描路径，用户自定义的SPI优先级高于系统SPI
         Map<String, Class<?>> keyClassMap = new HashMap<>();
         for (String scanDir : SCAN_DIRS) {
             String path = scanDir + loadClass.getName();
             List<URL> resources = ResourceUtil.getResources(path);
             //读取每个资源文件
             for (URL resource : resources) {
                 try {
                     InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                     String line;
                     while ((line = bufferedReader.readLine()) != null) {
                         String[] strArray = line.split("=");
                         if (strArray.length > 1) {
                             String key = strArray[0];
                             String className = strArray[1];
                             keyClassMap.put(key, Class.forName(className));
                         }
                     }
                 } catch (Exception e) {
                     log.error("spi resource load error", e);
                 }
             }
         }
         loaderMap.put(loadClass.getName(), keyClassMap);
         return keyClassMap;
     }

 }