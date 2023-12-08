package io.renren.modules.ghy.routerController;

import io.renren.modules.ghy.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class routerController {

    private Logger log = LoggerFactory.getLogger(routerController.class);
    /**
     * 进单路由，'create','query','checkaccount'
     * type="create"        代表进单
     * type="query"         代表查单
     * type="checkaccount"  代表查单数
     * */
    @RequestMapping("/createorder")
    public String createorder(HttpServletRequest request){
        HashMap<String, String> hashMap = (HashMap<String, String>) HttpRequestUtil.commonHttpRequestParamConvert(request, null);

        if(hashMap.get("type")==null||hashMap.get("type").equals("")){
            return "forward:/text/erro?code=500&msg=接口参数错误";
        }
        if(hashMap.get("client_id")==null||hashMap.get("client_id").equals("")){
            return "forward:/text/erro?code=500&msg=client_id为空";
        }
        if(hashMap.get("body")==null||hashMap.get("body").equals("")){
            return "forward:/text/erro?code=500&msg=参数字段为空";
        }
        if(hashMap.get("client_secret")==null||hashMap.get("client_secret").equals("")){
            return "forward:/text/erro?code=500&msg=client_secret为空";
        }

        request.setAttribute("hashMap",hashMap);
        //根据名称找对应接口
        if(hashMap.get("type").equals("create")){
            return "forward:/inOrder/insertOrder";
        } else if (hashMap.get("type").equals("query")) {
            return "forward:/text/erro?code=502&msg=未完成";
        } else if (hashMap.get("type").equals("checkaccount")) {
            return "forward:/text/erro?code=502&msg=未完成";
        }
        return "forward:/text/erro?code=404&msg=错误，未找到地址";
    }

}
