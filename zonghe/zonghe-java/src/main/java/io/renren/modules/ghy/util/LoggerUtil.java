package io.renren.modules.ghy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerUtil {
    /***
     * 自定义输出文件方法
     * @Param pag 目录，需要判断目录是否存在。为空则忽略
     * @Param filepath 文件名字
     * @Param message 需要写入的数据
     */
    public static void log(String pag,String filename,String message) {
        File file = new File(pag);
        if(!file.exists()){
            file.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pag+filename, true))) {
            writer.write(message+"\n\n\n");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件，根据时间删除文件
     * */
    public static Boolean deleteExpiredLogs(File file,long time) {
        long currentTime = System.currentTimeMillis();
        long fileLastModified = file.lastModified();
        long elapsedTimeSinceLastModified = currentTime - fileLastModified;

        if (elapsedTimeSinceLastModified > time) {
            if (file.exists()) {
                file.delete();
                return true;
            }
        }
        return false;
    }
}
