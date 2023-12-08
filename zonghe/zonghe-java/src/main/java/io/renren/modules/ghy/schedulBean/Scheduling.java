package io.renren.modules.ghy.schedulBean;

import io.renren.modules.ghy.inOrder.dao.zongHePayLogMapper;
import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;
import io.renren.modules.ghy.util.SendHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

@Service
public class Scheduling {

    @Autowired
    private zongHePayLogMapper zongHePayLogMapper;

    private Logger logger = LoggerFactory.getLogger(Scheduling.class);

    /**
     * 定时任务01，
     * */
//    @Scheduled(fixedDelay=500)
    public void timeOutIn() throws IOException {
        Random random = new Random();
        int i = random.nextInt(zongHePayLogMapper.getTableMaxId("w_cashier_pay_log_2023","id"));
        zonghePayLog zonghePayLog = zongHePayLogMapper.getZonghePayLogById(i, "w_cashier_pay_log_2023");
        HashMap<String, Object> map = new HashMap<>();
        map.put("logno",zonghePayLog.getLogno());
        map.put("money",zonghePayLog.getMoney());
        map.put("status",random.nextInt(2)+1);
        /**
         * 发送Http协议发送
         * */
        SendHttp.sendCashierPost(map,"http://localhost:8080/renren-fast/text/callback","json");
    }

}
