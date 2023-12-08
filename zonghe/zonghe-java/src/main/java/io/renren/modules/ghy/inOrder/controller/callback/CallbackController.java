package io.renren.modules.ghy.inOrder.controller.callback;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.R;
import io.renren.modules.ghy.inOrder.controller.zongHePayLogController;
import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;
import io.renren.modules.ghy.inOrder.service.impl.zongHePayLogServiceImpl;
import io.renren.modules.ghy.util.CommomUtil;
import io.renren.modules.ghy.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


@RestController
public class CallbackController {

    @Autowired
    private zongHePayLogServiceImpl zongHe;

    private Logger log = LoggerFactory.getLogger(zongHePayLogController.class);


    @PostMapping("/text/callback")
    public R callback(@RequestBody String body){
        long timer = System.currentTimeMillis();
        //获取年份
        String yearNumber = CommomUtil.getTimeorTableName("","YYYY",timer,"");

        JSONObject jsonObject = JSONObject.parseObject(body);
        String logno = jsonObject.getString("logno").split("_")[0];
        BigDecimal money = jsonObject.getObject("money", BigDecimal.class);
        Integer status = jsonObject.getInteger("status");

        zonghePayLog zonghePayLog = new zonghePayLog();
        zonghePayLog.setLogno(logno);
        HashMap<String, Object> selectUserlimit = zongHe.getSelectZongHelimit("", logno, money.toEngineeringString(),-1, 0, 1,yearNumber);
        if((int)selectUserlimit.get("sumCount")<1){
            return R.error(500,"不存在该流水号");
        }
        ArrayList<zonghePayLog> userList = (ArrayList<zonghePayLog>)selectUserlimit.get("userList");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(timer);
        //记录回调
        LoggerUtil.log( CommomUtil.getApplicationUrl()+ "/LOGNO_LOG/",
                     userList.get(0).getLogno()+".text",
                    CommomUtil.getJsonToString(jsonObject,"time: "+time));

        if(userList.get(0).getStatus()!=0){
            log.error("回调信息重复提交；"+body);
            return R.error(201,"回调信息请勿重复提交");
        }
        zonghePayLog.setStatus(status);
        zonghePayLog.setTablename(userList.get(0).getTablename());
        zonghePayLog.setPaytime(time);
        try {
            zongHe.updateZongHe(zonghePayLog);
        } catch (Exception e) {
            log.error("回调更新错误："+body,e);
            e.printStackTrace();
        }
        return R.ok(200,"成功");
    }

    @GetMapping("/text/randomTest")
    public String randomTest(){
        Random random = new Random();
        return String.format("%05d",random.nextInt(10000));
    }
}
