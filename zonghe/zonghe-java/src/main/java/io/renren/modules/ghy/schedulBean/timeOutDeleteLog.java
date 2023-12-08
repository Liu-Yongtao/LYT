package io.renren.modules.ghy.schedulBean;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.ghy.util.CommomUtil;
import io.renren.modules.ghy.util.FileUtil;
import io.renren.modules.ghy.util.LoggerUtil;
import io.renren.modules.job.task.ITask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component("timeOutDeleteLog")
public class timeOutDeleteLog implements ITask {

    private Logger logger = LoggerFactory.getLogger(timeOutDeleteLog.class);

    /**
     * 一个清理文件夹下文件的定时任务
     * */
    @Override
    public void run(String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        String filePathName = jsonObject.getString("filePathName");
        Long time = jsonObject.getLong("time");
        int sum = 0;
        int fileSum = 0;
        List<File> filesInFolder = FileUtil.getFilesInFolder(CommomUtil.getApplicationUrl() + filePathName);
        for (File fliename:filesInFolder) {
            sum++;
            if(LoggerUtil.deleteExpiredLogs(fliename,time)){
                fileSum++;
            }
        }
        logger.info("定时任务: 定时删除文件内过期文件，文件共："+sum+",已被清理："+fileSum+"个文件");
    }
}