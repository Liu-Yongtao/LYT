package io.renren.modules.ghy.inOrder.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

/**
 * 综合实体类
 * */
@Data
@Alias("zonghePayLog")
@AllArgsConstructor
@NoArgsConstructor
public class zonghePayLog {
    private int id;     //自增ID
    private Integer cashierid;   // 商户ID
    private int supplierid;     // 供货商ID
    private int goodsid;        //产品ID
    private String productsn;     //产品编号  对应数据库字段====>goodssn
    private Integer location;   //策略ID
    private String logno;       //平台流水号
    private BigDecimal money;   //订单金额
    private BigDecimal realmoney;   //实际订单金额
    private BigDecimal sellmoney;
    private BigDecimal costmoney;
    private Integer status;         //状态|0充值中1充值成功2充值失败
    private String  createtime;     //创建时间
    private String paytime;          //完成时间
    private int ybstatus;             //是否异步通知|0未异步1已异步
    private long ybtime;            //异步通知时间
    private String outLogno;          //商户订单号     对应数据库字段====>out_logno
    private String userinfo;          //官方流水号
    private String account;             //充值账号
    private String notifyurl;           //异步通知地址
    private int category;               //产品
    private int fromid;
    private int small;                  //供货商
    private int istrue;//是否结算成功|0未成功1已成功
    private long applytime;//提交成功时间
    private String remark;//手动操作原因
    private String ybcontent;//通知⽅回复内容
    private long ybfronttime;//通知间隔时间
    private int jscount;//通知次数
    private int tkstatus;//是否需要撤单|1待撤单2撤单中3撤单成功
    private int tktrue;//是否撤单成功|0未成功1已成功
    private String qdordersn;//供货商单号
    private int qdstatus;//供货商下单状态|0未下单1已下单
    private long sbtime;//渠道下单时间
    private int sbnumber;       //渠道编号
    private int sbstatus;   //新渠道状态|1待下单2下单中 3已下单
    private int nstatus;//是否预充值
    private int nstatustime;//预充值时间
    private String paytruetime;//真实付款时间
    private int mtype;//1移动2联通3电信
    private String tablename = "w_cashier_pay_log";
    private String logno2;
    private String logno3;
    private String logno4;
    private String logno5;
    private String logno6;
    private String logno7;
    private String logno8;
    private int supplierid2;
    private int supplierid3;
    private int supplierid4;
    private int supplierid5;
    private int supplierid6;
    private int supplierid7;
    private int supplierid8;
    private int supplierStatus;
    private int supplierStatus2;
    private int supplierStatus3;
    private int supplierStatus4;
    private int supplierStatus5;
    private int supplierStatus6;
    private int supplierStatus7;
    private int supplierStatus8;
    private int isyc;
    private String province;        //归属地，粒度到省
    private String qdordersn2;
    private String qdordersn3;
    private String qdordersn4;
    private String qdordersn5;
    private String qdordersn6;
    private String qdordersn7;
    private String qdordersn8;
    private String text;

}
