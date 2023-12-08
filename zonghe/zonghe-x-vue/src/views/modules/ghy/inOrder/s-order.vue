<template>
   <div>
      数据量：{{userCount}}，总金额：{{moneyCount}}￥ 
      <el-button @click="delAllUser()">删除全部数据</el-button>
      <el-button @click="updateHtml()">刷新页面</el-button>
      <Dialog ref="Dialog"></Dialog>


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
      <el-form-item label="充值账户">
        <el-input v-model="formInline.account" placeholder="充值账户" style="width:130px"></el-input>
      </el-form-item>
     <el-form-item label="流水号">
        <el-input v-model="formInline.logno" placeholder="流水号" style="width:130px"></el-input>
      </el-form-item>

      <el-form-item label="金额">
        <el-input v-model="formInline.money" placeholder="金额" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="充值中" value="0"></el-option>
          <el-option label="充值成功" value="1"></el-option>
          <el-option label="充值失败" value="2"></el-option>
      </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="clearSelect()">清空</el-button>
        <el-button type="primary" @click="getUserCount(0)">查询</el-button>
      </el-form-item>
    </el-form>

 <el-table
    v-loading="loading"
    :data="userList"
    border
    height="60vh"
    row-key="id"
    highlight-current-row
    :expand-row-keys="rowKes"
    @expand-change="toggleRowExpansion"
    style="width: 100%;">
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
            <el-table-column prop="logno" label="流水号" width="200" align="center"></el-table-column>
            <el-table-column prop="supplierid" label="渠道id" width="120" align="center"></el-table-column>
            <el-table-column prop="qdordersn" label="供货商id" width="120" align="center"></el-table-column>
            <el-table-column prop="supplierStatus" label="渠道状态" width="120" align="center">
              <template slot-scope="scope">
                <el-tag
                  :type="scope.row.supplierStatus === 0 ? 'primary' : scope.row.supplierStatus===1?'success':'danger'"
                  disable-transitions>{{scope.row.supplierStatus === 0 ? '充值中' : scope.row.supplierStatus===1?'充值成功':'充值失败'}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100" align="center">
                <template slot-scope="scope">
                  <el-button @click="consologDemo(scope.row)" type="text" size="small">打印</el-button>
                </template>
            </el-table-column>
          </el-table>
  </template>
    </el-table-column>
    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="account" label="充值账户" width="120" align="center"></el-table-column>
    <el-table-column prop="cashierid" label="商户ID" width="120" align="center"></el-table-column>
    <el-table-column prop="supplierid" label="供货商ID" width="120" align="center"></el-table-column>
    <el-table-column prop="goodsid" label="产品ID" width="120" align="center"></el-table-column>
    <el-table-column prop="productsn" label="产品编号" width="120" align="center"></el-table-column>
    <el-table-column prop="location" label="策略ID" width="120" align="center"></el-table-column>
    <el-table-column prop="logno" label="平台流水" width="250" align="center"></el-table-column>
    <el-table-column prop="money" label="金额" width="120" align="center"></el-table-column>
    <el-table-column prop="outLogno" label="商户订单号" width="120" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 0 ? 'primary' : scope.row.status===1?'success':'danger'"
              disable-transitions>{{scope.row.status === 0 ? '充值中' : scope.row.status===1?'充值成功':'充值失败'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="createtime" label="创建时间" width="200" align="center"></el-table-column>
    <el-table-column prop="paytime" label="完成时间" width="200" align="center"></el-table-column>
    <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="delUserGroup(scope.row.id)" type="text" size="small">删除</el-button>
          <el-button @click="getTextLognoLog(scope.row.logno)" type="text" size="small">回调</el-button>
        </template>
    </el-table-column>
  </el-table>

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
  import Dialog from '../../../comUtil/dialog.vue';
  export default {
    inject: ['refresh'],
    data(){
      return{
        userCount:0,          //总数量
        moneyCount:'0.00',    //总金额
        userList:[],          //数据
        sumCount:0,           //查询数据行数
        statLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          yearNumber:'',
          account:'',
          logno:'',
          money:'',
          status:'-1'
        },
        loading:false,        //加载
        clearable:false,
        rowKes:[],
        expandArray:[]
      }
    },
    created(){
        var date = new Date();
        //注意date.getFullYear()返回是整型的
        this.formInline.yearNumber = date.getFullYear()+"";
        this.getCount();
        this.getUserCount(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
      getCount(){
        this.$http({
              url: this.$http.adornUrl('/inOrder/getCountDGroup'),
              method: 'post',
              data:{
                yearNumber:this.formInline.yearNumber
              }
            }).then(({data}) => {
              this.userCount = data.userCount,
              this.moneyCount = data.moneyCount==''?'0.00':data.moneyCount.toFixed(2);
            })
      },
      getUserCount(stat){
        this.loading = true;
        if(stat==0){
          this.currentPage = 1
        }else{
          stat = (this.currentPage-1)*this.endLimit;
        }
        this.$http({
              url: this.$http.adornUrl('/inOrder/getAllUser'),
              method: 'post',
              data:{
                account:this.formInline.account,
                logno:this.formInline.logno,
                money:this.formInline.money,
                status:this.formInline.status,
                stat:stat,
                end:this.endLimit,
                yearNumber:this.formInline.yearNumber
              }
            }).then(({data}) => {
              this.sumCount = data.sumCount;
              this.userList = data.userList;
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
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getUserCount(limit);
            },500);
          
      },
      handleSizeChange(val){
        this.endLimit = val;
        this.getUserCount(0);
      },
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
      updateHtml(){
          this.getCount();
          this.getUserCount(0);
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
      clearSelect(){
        var time = new Date();
        var year = time.getFullYear();

        this.formInline = {
          account:'',
          logno:'',
          money:'',
          status:'-1',
          yearNumber:year+""
        }
      },
      //展开行进行数据处理
      toggleRowExpansion(row,expanded){
         this.expandArray = [];
          var data = [];
          if(row.id==this.rowKes[0]){
            this.rowKes = [];
            return;
          }
          for(let i=1;i<9;i++){
              var j = i==1?'':i
              if(row['logno'+j]===null||row['logno'+j]===''){
                break;
              }
              var l = {
                "logno":row['logno'+j],
                "supplierid":row['supplierid'+j],
                "supplierStatus":row['supplierStatus'+j],
                "qdordersn":row['qdordersn'+j]
              }
              data.push(l);
          }
          this.expandArray = data;
          this.rowKes = [];
          this.rowKes.push(row.id);
      },
      consologDemo(row){
        console.log(row);
      }
    },
    components:{
      Dialog
    }
  }
</script>

<style scoped>
.el-row {
    margin-bottom: 5px;
    
  }
  .el-row :last-child {
      margin-bottom: 0;
    }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
   
    background: #89aede;
    
    
  }
  .bg-purple {
    background: #c7daed;
  }
  .bg-purple-light {
    background: #d2ecf0;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
  .bg-sty{
     height: 30px;
    line-height: 30px;
    text-align: center;
  }
  .money-sty{
    color: rgb(123, 77, 61);
  }
  .btn-size{
    width: 20px;
    height: 20px;
    position: relative;
    
    bottom: 5px;
  border-radius: 50%;

  }
  .btn-size i{
    position: absolute;
    top: 5px;
    left: 5px;
  }
   .text {
    font-size: 14px;
  }
  /* .pagination{
    position: fixed;
    left: 50%;
    transform:translate(-50%,-2vh)
  } */
 
</style>
