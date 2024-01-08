<template>
   <div class="app-container">
      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
  
      <el-form-item label="话商ID">
        <el-input v-model="formInline.cashierId" placeholder="话商ID" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="formInline.money" placeholder="金额" style="width:160px"></el-input>
      </el-form-item>
      <el-form-item label="操作类型" >
        <el-select v-model="formInline.type" placeholder="选择状态" style="width:140px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="增款" value="0"></el-option>
          <el-option label="扣款" value="1"></el-option>
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
        <el-button type="primary" @click="getCashierUserCheckLog(0)" size="mini">查询</el-button>
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
    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="cashierId" label="话商ID" width="80" align="center"></el-table-column>
    <el-table-column prop="userName" label="操作人员" width="200" align="center"></el-table-column>
    <el-table-column prop="beforeMoney" label="变动前金额" width="120" align="center"></el-table-column>
    <el-table-column prop="type" label="操作类型" width="120" align="center">
      <template slot-scope="scope">
        <span>{{scope.row.type === 0 ? '增款' : scope.row.type === 1 ? '扣款':'-'}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="money" label="金额" width="120" align="center"></el-table-column>
    <el-table-column prop="afterMoney" label="变动后余额" width="120" align="center"></el-table-column>
    
    <el-table-column prop="time" label="创建时间" width="200" align="center"></el-table-column>
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
import request from '@/utils/request.js'
  export default {
    inject: ['refresh'],
    data(){
      return{
        checkLogList:[],          //数据
        sumCount:0,           //查询数据行数
        statLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          yearNumber:'',
          cashierId:'',
          money:'',
          type:'',
          time:'',
        },
        loading:false,        //加载
        // rowKes:[],
        // expandArray:[],
        details:false
      }
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
        this.getCashierUserCheckLog(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
        //获取话商订单号
      getCashierUserCheckLog(stat){
        this.loading = true;
        if(stat==0){
          this.currentPage = 1
        }else{
          stat = (this.currentPage-1)*this.endLimit;
        }
        request({
              url: '/transfer/getCashierUserCheckLog',
              method: 'post',
              data:{
                cashierId:this.formInline.cashierId,
                money:this.formInline.money,
                type:this.formInline.type,
                startTime:this.formInline.time[0],
                endTime:this.formInline.time[1],
                stat:stat,
                end:this.endLimit,
              }
            }).then(data => {
                if(data === "未知错误"){
                    this.$message.error("未知错误");
                    this.loading = false;
                    return;
                }
              this.sumCount = data.sumCountCheck,
              this.orderList = data.cashierUserCheckLog;
              this.loading = false;
            })
            
      },

      // 分页数据处理
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getCashierUserCheckLog(limit);
            },500);
          
      },
      // 当重新选择页面显示数时
      handleSizeChange(val){
        this.endLimit = val;
        this.getCashierUserCheckLog(0);
      },
      clearSelect(){
        var date = new Date();
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
    
        this.formInline = {  
          yearNumber:y+"",
          cashierId:'',
          money:'',
          type:'',
          time:[y+"-"+m+"-"+d+" 00:00:00",y+"-"+m+"-"+d+" 23:59:59"],
        }
      }
    }
  }
</script>

<style scoped>
</style>
