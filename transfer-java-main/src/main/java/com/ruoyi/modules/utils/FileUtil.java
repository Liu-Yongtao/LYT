package com.ruoyi.modules.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 获取文件夹下的所有文件
     * */
    public static List<File> getFilesInFolder(String folderPath) {

        List<File> fileList = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            traverseFolder(folder, fileList);
        }

        return fileList;
    }
    /**
     * 递归获取文件，并添加到列表里
     * */
    private static void traverseFolder(File folder, List<File> fileList) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    traverseFolder(file, fileList);
                } else {
                    fileList.add(file);
                }
            }
        }
    }
    /**
     * 读取文件内容
     * */
    public static String getFileContent(String filepath) {
        File file = new File(filepath);
        StringBuilder content = new StringBuilder();
        BufferedReader br;
        try{
            String line;
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {    //一行行读取文件
                content.append(line).append("\n");
            }
            br.close();
        } catch (Exception e) {
            return "";
        }
        return content.toString();
    }
}
