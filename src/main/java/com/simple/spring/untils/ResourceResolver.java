package com.simple.spring.untils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author l_hl
 * <p>
 * 根据指定的包名,返回该包以及子包下面所有的Class对象.
 */
public class ResourceResolver {
    private String basePackage;

    public ResourceResolver(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<Class<?>> listClass() throws ClassNotFoundException {
        String basePath = basePackage.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(basePath);
        File directory = new File(url.getFile());
        return scanFile(directory, basePackage);
    }

    private List<Class<?>> scanFile(File directory, String basePackage) throws ClassNotFoundException {
        List<Class<?>> list = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                list.addAll(scanFile(file, basePackage + "." + file.getName()));
            } else {
                Class<?> cl = Class.forName(basePackage + "." + file.getName().substring(0, file.getName().length() - 6));
                list.add(cl);
            }
        }
        return list;
    }
}
