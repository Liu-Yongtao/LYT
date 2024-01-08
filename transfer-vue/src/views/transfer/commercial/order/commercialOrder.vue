<template>
   <div class="app-container">
        <div class="headTitle">
        <div>数据量：{{sumCount}}，总金额：{{moneyCount}}￥ </div>
        <div>
            <el-button @click="delAllUser()" size="mini">删除全部数据</el-button>
            <el-button @click="updateHtml()" size="mini">刷新数据</el-button>
      </div></div>

      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="表年份">
        <el-date-picker
          style="width: 100px"
          v-model="formInline.yearNumber"
          type="year"
          format="yyyy"
          value-format="yyyy"
          :clearable=false
          align="center"
          placeholder="选择年">
      </el-date-picker>
      </el-form-item>
      <el-form-item label="商户ID">
        <el-input v-model="formInline.commercialId" placeholder="商户ID" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="商户订单号">
        <el-input v-model="formInline.commercialNo" placeholder="充值账户" style="width:130px"></el-input>
      </el-form-item>
     <el-form-item label="平台订单号">
        <el-input v-model="formInline.logno" placeholder="平台订单号" style="width:180px"></el-input>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="formInline.money" placeholder="金额" style="width:160px" type="number"></el-input>
      </el-form-item>
       <el-form-item label="通道编号">
        <el-input v-model="formInline.passage" placeholder="通道编号" style="width:160px"></el-input>
      </el-form-item>
      <el-form-item label="支付状态" >
        <el-select v-model="formInline.payStatus" placeholder="选择状态" style="width:160px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="待支付" value="0"></el-option>
          <el-option label="支付失败" value="1"></el-option>
          <el-option label="支付成功" value="2"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="通知状态" >
        <el-select v-model="formInline.notifyStatus" placeholder="选择状态" style="width:160px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="待通知" value="0"></el-option>
          <el-option label="通知失败" value="1"></el-option>
          <el-option label="通知成功" value="2"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="匹配状态" >
        <el-select v-model="formInline.normalStatus" placeholder="选择状态" style="width:160px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="待匹配" value="0"></el-option>
          <el-option label="匹配失败" value="1"></el-option>
          <el-option label="匹配成功" value="2"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="支付类型" >
        <el-select v-model="formInline.zfType" placeholder="选择状态" style="width:140px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="微信" value="0"></el-option>
          <el-option label="支付宝" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="时间选择">
          <el-date-picker
              v-model="formInline.time"
              type="datetimerange"
              size="small"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCommercialrOrderList(0)" size="mini">查询</el-button>
        <el-button type="primary" @click="clearSelect()" size="mini">重置</el-button>
      </el-form-item>
    </el-form>

 <el-table
    v-loading="loading"
    :data="orderList"
    border
    row-key="id"
    highlight-current-row
    :expand-row-keys="rowKes"
    @expand-change="toggleRowExpansion"
    style="width: 100%;min-height:60vh">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>
    <!-- 展开行选项 -->
<el-table-column type="expand">
  <template>
        <el-table
            :data="expandArray"
            style="width: 760px;"
            border
            >
            <el-table-column prop="location" label="渠道编号" width="150" align="center"></el-table-column>
             <el-table-column prop="linkSupplierNo" label="渠道单号" width="150" align="center"></el-table-column>
            <el-table-column prop="urlStatus" label="产码状态" width="150" align="center">
              <template slot-scope="scope">
                <el-tag
                  :type="scope.row.urlStatus === 0 ? 'primary' : scope.row.urlStatus===1?'danger':'success'"
                  disable-transitions>{{scope.row.urlStatus === 0 ? '产码中' : scope.row.urlStatus===1?'产码失败':'产码成功'}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="endTime" label="结束时间" width="200" align="center"></el-table-column>
            <el-table-column fixed="right" label="操作" width="100" align="center">
                <template slot-scope="scope">
                  <el-button @click="consologDemo(scope.row)" type="text" size="small">打印</el-button>
                </template>
            </el-table-column>
          </el-table>
  </template>
    </el-table-column>
    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="commercialId" label="商户ID" width="80" align="center"></el-table-column>
    <el-table-column prop="commercialNo" label="商户订单号" width="200" align="center"></el-table-column>
    <el-table-column prop="logno" label="平台订单号" width="200" align="center"></el-table-column>
    <el-table-column prop="money" label="金额" width="120" align="center"></el-table-column>
    <el-table-column prop="payStatus" label="状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.payStatus === 0 ? 'primary' : scope.row.payStatus===1?'danger':'success'"
              disable-transitions>{{scope.row.payStatus === 0 ? '待支付' : scope.row.payStatus===1?'支付失败':'支付成功'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="normalStatus" label="匹配状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.normalStatus === 0 ? 'primary' : scope.row.normalStatus===1?'danger':'success'"
              disable-transitions>{{scope.row.normalStatus === 0 ? '待匹配' : scope.row.normalStatus===1?'匹配失败':'匹配成功'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="urlStatus" label="产码状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.urlStatus === 0 ? 'primary' : scope.row.urlStatus===1?'danger':'success'"
              disable-transitions>{{scope.row.urlStatus === 0 ? '产码中' : scope.row.urlStatus===1?'产码失败':'产码成功'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="notifyStatus" label="通知状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.notifyStatus === 0 ? 'primary' : scope.row.notifyStatus===1?'danger':'success'"
              disable-transitions>{{scope.row.notifyStatus === 0 ? '待通知' : scope.row.notifyStatus===1?'通知失败':'通知成功'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="statTime" label="创建时间" width="200" align="center"></el-table-column>
    <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button @click="handleDetails(scope.row)" type="text" size="small">详细</el-button>
          <el-button @click="getTextLognoLog(scope.row.logno)" type="text" size="small">回调</el-button>
        </template>
    </el-table-column>
  </el-table>

    <!-- 详细页 -->
  <commercial-order-details v-if="details" ref="commercialOrderDetails" />
    <!-- 分页组件 -->
    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[20, 50, 100, 200]"
          :page-size="endLimit"
          layout="total, prev, pager, next,sizes"
          :total="sumCount">
        </el-pagination>

  </div>
   
</template>

<script>
import request from '@/utils/request.js';
import commercialOrderDetails from './commercialOrder-Details.vue';

  export default {
    inject: ['refresh'],
    data(){
      return{
        moneyCount:'0.00',    //总金额
        orderList:[],          //数据
        sumCount:0,           //查询数据行数
        statLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          yearNumber:'',
          commercialId:'',
          commercialNo:'',
          logno:'',
          money:'',
          zfType:'',
          payStatus:'',
          notifyStatus:'',
          normalStatus:'',
          passage:'',
          time:'',
        },
        loading:false,        //加载
        rowKes:[],
        expandArray:[],
        details:false
      }
    },
    components:{
      commercialOrderDetails
    },
    created(){
        var date = new Date();
        // 时间
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        //注意date.getFullYear()返回是整型的
        this.formInline.yearNumber = y+"";
        this.formInline.time = [y+"-"+m+"-"+d+" 00:00:00",y+"-"+m+"-"+d+" 23:59:59"];
        // 获取数据
        this.getCommercialrOrderList(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
        //获取话商订单号
      getCommercialrOrderList(stat){
        this.loading = true;
        if(stat==0){
          this.currentPage = 1
        }else{
          stat = (this.currentPage-1)*this.endLimit;
        }
        request({
              url: '/transfer/getCommercialOrderList',
              method: 'post',
              data:{
                commercialId:this.formInline.commercialId,
                commercialNo:this.formInline.commercialNo,
                logno:this.formInline.logno,
                money:this.formInline.money,
                payStatus:this.formInline.payStatus,
                notifyStatus:this.formInline.notifyStatus,
                normalStatus:this.formInline.normalStatus,
                zfType:this.formInline.zfType,
                passage:this.formInline.passage,
                startTime:this.formInline.time[0],
                endTime:this.formInline.time[1],
                stat:stat,
                end:this.endLimit,
                yearNumber:this.formInline.yearNumber
              }
            }).then(data => {
                if(data === "未知错误"){
                    this.$message.error("未知错误");
                    this.loading = false;
                    return;
                }
                console.log(data);
              this.sumCount = data.sum.sumCount,
              this.moneyCount = data.sum.moneyCount==''||data.sum.moneyCount=== undefined?'0.00':data.sum.moneyCount.toFixed(2);
              this.orderList = data.commercialOrderList;
              this.loading = false;
            })
            
      },
      delAllUser(){
        this.$http({
              url: this.$http.adornUrl('/inOrder/delAllUserGroup'),
              method: 'post',
              data:{
                yearNumber:this.formInline.yearNumber
              }
            }).then(({data}) => {
              if(data=="删除失败"){
                this.$refs.Dialog.open(data,'erro')
              }else{
                this.$refs.Dialog.open(data,'success')
                this.refresh();
              }
            })
      },
      // 详细页
      handleDetails(data){
          this.details = true;
          this.$nextTick(()=>{
            this.$refs.commercialOrderDetails.init(data);
          })
          
      },
      // 分页数据处理
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getCommercialrOrderList(limit);
            },500);
          
      },
      // 当重新选择页面显示数时
      handleSizeChange(val){
        this.endLimit = val;
        this.getCommercialrOrderList(0);
      },
      // 删除所有数据，测试环境
      delUserGroup(id){
        this.$http({
              url: this.$http.adornUrl('/inOrder/delUserGroup'),
              method: 'post',
              data:{
                id:id,
                yearNumber: this.formInline.yearNumber
              }
            }).then(({data}) => {
              if(data=="删除失败"){
                this.$refs.Dialog.open(data,'erro')
              }else{
                this.$refs.Dialog.open(data,'success')
                this.refresh();
              }
            })
      },
      getTextLognoLog(logno){
          this.$http({
              url: this.$http.adornUrl('/inOrder/getTextByLogno'),
              method: 'post',
              data:{
                logno:logno
              }
            }).then(({data}) => {
                    this.$alert("<div style='width:400px;height:300px;overflow: scroll;display: table-cell;text-align: left;white-space: pre-wrap;'>"+data+"</div>", '回调', {
                      dangerouslyUseHTMLString: true,
                      confirmButtonText: '确定',
                      callback: action => {}
                    })
            })
      },
      //展开行进行数据处理
      toggleRowExpansion(row,expanded){
         this.expandArray = [];
          // var data = [];
          if(row.id==this.rowKes[0]){
            this.rowKes = [];
            return;
          }
          // for(let i=1;i<9;i++){
          //     var j = i==1?'':i
          //     if(row['logno'+j]===null||row['logno'+j]===''){
          //       break;
          //     }
          //     var l = {
          //       "logno":row['logno'+j],
          //       "supplierid":row['supplierid'+j],
          //       "supplierStatus":row['supplierStatus'+j],
          //       "qdordersn":row['qdordersn'+j]
          //     }
          //     data.push(l);
          // }
          this.expandArray = JSON.parse(row['node']);
          this.rowKes = [];
          this.rowKes.push(row.id);
      },
      clearSelect(){
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
    
        this.formInline = {  
          yearNumber:y+"",
          cashierId:'',
          account:'',
          outLogno:'',
          logno:'',
          money:'',
          status:'',
          passage:'',
          notifyStatus:'',
          normalStatus:'',
          telephoneType:'',
          type:'',
          time:[y+"-"+m+"-"+d+" 00:00:00",y+"-"+m+"-"+d+" 23:59:59"],
        }
      }
    }
  }
</script>

<style scoped>
.headTitle{
    width: 100%;
    height: 50px;
    font-size: 16px;
    line-height: 50px;
    background: #8cb2d9;
    border: 2px solid #63a7eb;
    color: white;
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-content: center;
    position: relative;
}
.headTitle div{
    width: 40%;
}
</style>
