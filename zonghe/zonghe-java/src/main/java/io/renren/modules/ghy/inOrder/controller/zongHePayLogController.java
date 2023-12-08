package io.renren.modules.ghy.inOrder.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.renren.common.utils.R;
import io.renren.modules.ghy.inOrder.pojo.zonghePayLog;
import io.renren.modules.ghy.util.*;
import io.renren.modules.ghy.inOrder.service.impl.zongHePayLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RestController
public class zongHePayLogController {

    @Autowired
    private zongHePayLogServiceImpl zongHePayLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    private Logger log = LoggerFactory.getLogger(zongHePayLogController.class);

    /**
     * 测试当前项目端口
     * */
    @RequestMapping("/text/textInfo")
    public HashMap<String,Object> textInfo(HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("当前端口号：", CommomUtil.getServerUrl(request));
        return hashMap;
    }

    /**
     * 转发返回错误信息
     * */
    @RequestMapping("/text/erro")
    public R erro(@RequestParam("code") int code,@RequestParam("msg") String msg){
        return R.error(code,msg);
    }

    /**
     * 进单controller层
     * @param hashMap 用户请求数据
     * */
    @PostMapping("/inOrder/insertOrder")
    public R insUserGroup(@RequestAttribute("hashMap") HashMap<String,String> hashMap){
        String body = hashMap.get("body");
        //确保传入参数中由于hash排序而进行排序
        JSONObject map = JSONObject.parseObject(body,Feature.OrderedField);

        if(map.getString("out_logno")==null||map.getString("out_logno").equals("")){
            return R.error(500,"商户订单号为空");
        }
        if(map.getString("account")==null||map.getString("account").equals("")){
            return R.error(500,"充值账号为空");
        }
        if(map.get("money")==null){
            return R.error(500,"金额为空");
        }
        if(map.getString("productsn") == null || map.getString("productsn").equals("")){
            return R.error(500,"产品编号为空");
        }

        try {
            zongHePayLogService.insertZongHe(hashMap,map);
        } catch (Exception e) {
            return R.error(e.getMessage());
        }

//        new Thread(()->{
//            try {
////              http://47.118.69.121/createorder
//                String s = SendHttp.sendCashierPost(hashMap,"https://hooks.upyun.com/VvqEpMp7v","form");
//                for (int i = 0; i < 3; i++) {
//                    if(s.split("超时").length>1){
//                        break;
//                    }
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    s = SendHttp.sendCashierPost(hashMap,"https://hooks.upyun.com/VvqEpMp7v","form");
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();

        return R.ok(200,"success");
    }


    /**
     * 获取表总条数
     * */
    @PostMapping("/inOrder/getCountDGroup")
    public String getCountDGroup(@RequestBody String body) throws JsonProcessingException{
        JSONObject jsonObject = JSONObject.parseObject(body);
        String yearNumber = jsonObject.getString("yearNumber");
        HashMap<String, Object> countDGroup = zongHePayLogService.getCountZongHe(yearNumber);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(countDGroup);
        return s;
    }

    /**
     * 根据ID删除订单
     * */
    @PostMapping("/inOrder/delUserGroup")
    public String delUserGroup(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String id = jsonObject.getString("id");
        String tableName = jsonObject.getString("tableName");
        int i = zongHePayLogService.delZongHeById(id,tableName);
        if(i<1){
            return "删除失败";
        }
        return "删除成功";
    }

    /**
     * 删除表中所有订单
     * */
    @PostMapping("/inOrder/delAllUserGroup")
    public String delAllUserGroup(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String yearNumber = jsonObject.getString("yearNumber");
        int i = zongHePayLogService.delAllZongHePayLog(yearNumber);
        if(i<1){

            return "删除失败";
        }
        return "删除成功,删除行数:"+i;
    }

    //分页条件获取所有订单
    @PostMapping("/inOrder/getAllUser")
    public String getAllUser(@RequestBody String body) throws JsonProcessingException{
        JSONObject jsonObject = JSONObject.parseObject(body);
        String account = jsonObject.getString("account");
        String logno = jsonObject.getString("logno");
        String money = jsonObject.getString("money");
        Integer status = jsonObject.getInteger("status");
        int stat = jsonObject.getInteger("stat");
        int end = jsonObject.getInteger("end");
        String yearNumber = jsonObject.getString("yearNumber");
        HashMap<String, Object> map = zongHePayLogService.getSelectZongHelimit(account, logno,money,status, stat, end,yearNumber);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        return s;
    }

    //查看返回日志
    @PostMapping("/inOrder/getTextByLogno")
    public String getTextByLogno(@RequestBody String body){
        JSONObject jsonObject = JSONObject.parseObject(body);
        String logno = jsonObject.getString("logno");
        String filepath = CommomUtil.getApplicationUrl()+"/LOGNO_LOG/"+logno+".text";
        String fileContent = FileUtil.getFileContent(filepath);
        if(Objects.equals(fileContent, "")){
            fileContent = "暂无回调信息";
        }
        return fileContent;
    }

    //通过Id查询单号
    @GetMapping("/inOrder/getDUserGroupById")
    public String getDUserGroupById(int id,String yearNumber) throws JsonProcessingException {
        zonghePayLog zonghePayLogById = zongHePayLogService.getZonghePayLogById(id,yearNumber);
        if(zonghePayLogById==null){
            return "不存在该Id";
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(zonghePayLogById);
        return s;
    }

    @RequestMapping("/text/Demo")
    public R Demo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Random random = new Random();
        map.put("type","create");
        map.put("out_logno",random.nextInt(9999999)+1000000);
        map.put("account","1845612");
        map.put("cashierid","10054");
        map.put("money","100");
        map.put("productsn","U_U_IU_T");
        try {
            String form = SendHttp.sendCashierPost(map, "http://jiesong.ie6000.com/renren-fast/createorder", "form");
            log.error("/text/goodsListMapper.xml"+form);
            System.out.println(form);
        } catch (IOException e) {
            log.error("请求错误===>"+request.getLocalAddr());
        }
        return R.ok();
    }


    @PostMapping("/text/getOnlyId")
    public String getOnlyId(){
        return OutTradeNoGenerator.generateOutTradeNo("J");
    }

}
