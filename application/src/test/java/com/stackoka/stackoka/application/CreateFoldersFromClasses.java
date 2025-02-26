package com.stackoka.stackoka.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CreateFoldersFromClasses {

    public static void main(String[] args) {
        // 指定包路径
        String packageName = "com.stackoka.stackoka.application.bo";
        // 指定目标文件夹路径
        String targetPath = "/Users/liuxin/Desktop/stakoak-server/repository/src/main/java/com/stackoka/stackoka/repository";

        try {
            // 获取包路径下所有的类名
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();

                if ("file".equals(protocol)) {
                    // 如果是文件系统路径
                    File directory = new File(resource.toURI());
                    File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
                    if (files != null) {
                        for (File file : files) {
                            String className = file.getName().replace(".class", "");
                            createDirectory(targetPath, className);
                        }
                    }
                } else if ("jar".equals(protocol)) {
                    // 如果是 JAR 文件路径
                    String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));
                    JarFile jar = new JarFile(jarPath);
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(path) && name.endsWith(".class")) {
                            String className = name.substring(path.length() + 1).replace(".class", "").replace("/", ".");
                            createDirectory(targetPath, className);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDirectory(String targetPath, String className) {
        // 将类名转换为小写并创建文件夹
        String folderName = className.toLowerCase();
        File folder = new File(targetPath, folderName);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Created folder: " + folder.getAbsolutePath());
        } else {
            System.out.println("Folder already exists: " + folder.getAbsolutePath());
        }
    }
}
