<template>
   <div class="app-container">
      
      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="话商ID">
        <el-input v-model="formInline.cashierId" placeholder="话商ID" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="话商名称">
        <el-input v-model="formInline.title" placeholder="充值账户" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="formInline.userName" placeholder="用户名" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:160px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getCashierUserList(0)" size="mini">查询</el-button>
        <el-button type="primary" @click="clearSelect()" size="mini">重置</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleUpdate(0,'')" style="margin-bottom:5px">+新增</el-button>
 <el-table
    v-loading="loading"
    :data="cashierUserList"
    border
    highlight-current-row
    @expand-change="toggleRowExpansion"
    style="width: 100%;min-height:60vh">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>
    <el-table-column prop="id" label="话商ID" width="80" align="center"></el-table-column>
    <el-table-column prop="title" label="话商名称" width="150" align="center"></el-table-column>
    <el-table-column prop="userName" label="用户名" width="150" align="center"></el-table-column>
    <el-table-column prop="withdrawMoney" label="余额" width="120" align="center"></el-table-column>
    <el-table-column prop="lv" label="费率" width="120" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="120" align="center">
        <template slot-scope="scope">
            <el-switch
            v-model="scope.row.status"
            class="isShow"
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="禁用"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="changeSwitch(scope.row)">
            </el-switch>
        </template>
    </el-table-column>
  
    <el-table-column prop="type" label="渠道类型" width="120" align="center">
      <template slot-scope="scope">
        <span>{{scope.row.type === 0 ? '网厅' : '三方'}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="time" label="创建时间" width="200" align="center"></el-table-column>

    <el-table-column fixed="right" label="操作" width="220" align="center">
        <template slot-scope="scope">
          <el-button @click="handleDetails(scope.row)" type="text" size="small">详细</el-button>
          <el-button @click="handleUpdate(1,scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="restApiKey(scope.row.title,scope.row.id)" type="text" size="small">重置ApiKey</el-button>
          <el-button @click="updateMoney(scope.row.title,scope.row.id,scope.row.apiKey)" type="text" size="small">增减款</el-button>
        </template>
    </el-table-column>
  </el-table>

    <!-- 详细页 -->
  <cashier-user-details v-if="details" ref="cashierUserDetails" />
  <!-- 新增or编辑页 -->
  <cashier-user-add-upd v-if="add" ref="cashierUserAddUpd" @fromSubmit="getCashierUserList" />
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
import cashierUserDetails from './cashierUser-details.vue';
import cashierUserAddUpd from './cashierUser-add-upd.vue';
  export default {
    inject: ['refresh'],
    data(){
      return{
        cashierUserList:[],          //数据
        sumCount:0,           //查询数据行数
        statLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{   
          cashierId:'',
          title:'',
          status:''
        },
        loading:false,        //加载
        details:false,
        add:false,
        userName:""
      }
    },
    components:{
      cashierUserDetails,
      cashierUserAddUpd
    },
    created(){
        this.getCashierUserList(0);
        this.getUserName();
    },
    methods:{
        //获取话商用户页
      getCashierUserList(stat){
        this.loading = true;
        if(stat==0){
          this.currentPage = 1
        }else{
          stat = (this.currentPage-1)*this.endLimit;
        }
        request({
              url: '/transfer/getCashierUserList',
              method: 'post',
              data:{
                cashierId:this.formInline.cashierId,
                title:this.formInline.title,
                status:this.formInline.status,
                stat:stat,
                end:this.endLimit,
              }
            }).then(data => {
                if(data === "未知错误"){
                    this.$message.error(data);
                    this.loading = false;
                    return;
                }
              this.sumCount = data.sumCount,
              this.cashierUserList = data.cashierUserList;
              this.loading = false;
            })
            
      },
      // 详细页
      handleDetails(data){
          this.details = true;
          this.$nextTick(()=>{
            this.$refs.cashierUserDetails.init(data);
          })
          
      },
      // 新增or编辑页
      handleUpdate(i,data){
          this.add = true;
          var d = '';
          if(i===1){
              //深拷贝
              var da = JSON.stringify(data);
              d = JSON.parse(da);
            }
          this.$nextTick(()=>{
            this.$refs.cashierUserAddUpd.init(d);
          })
      },
      // 分页数据处理
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getCashierUserList(limit);
            },500);
          
      },
      // 当重新选择页面显示数时
      handleSizeChange(val){
        this.endLimit = val;
        this.getCashierUserList(0);
      },
      // 启用禁用时
      changeSwitch(row){
          request({
              url: '/transfer/updateCashierUser',
              method: 'post',
              data:{
                id:row.id,
                status:row.status
              }
            }).then(data => {
                if(data === "成功"){
                  this.$message({
                        message: data,
                        type: 'success'
                    });
                }else{
                     this.$message.error(data);
                }
        
            })
      },
      // 重置ApiKey
      restApiKey(name,id){
        this.$confirm('确定要重置 '+name+' 的ApiKey?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            request({
              url: '/transfer/restCashierUserApiKey',
              method: 'post',
              data:{
                id:id
              }
            }).then(data => {
                if(data === "未知错误" || data === '失败'){
                    this.$message.error(data);
                }else{
                    this.$message({
                        message: data,
                        type: 'success'
                    });
                    this.getCashierUserList();
                }
            })
        });

      },
      // 资金变动
      updateMoney(name,id,apiKey){
        this.$prompt('请输入为 '+name+' 增款/扣款操作金额，例：1为增款，-1为扣款', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern:/^([-+])?\d+(\.[0-9]{1,2})?$/,
          inputErrorMessage: '金额输入不正确'
        }).then(({ value }) => {
            request({
              url: '/transfer/updateCashierUserMoney',
              method: 'post',
              data:{
                id:id,
                userName: this.userName,
                apiKey:apiKey,
                money:value
              }
            }).then(data => {
                if(data === "成功"){
                  this.$message({
                        message: data,
                        type: 'success'
                    });
                    this.getCashierUserList();
                }else{
                    this.$message.error(data);
                }
            })
        });
      },
      clearSelect(){
        this.formInline = {  
          cashierId:'',
          title:'',
          status:''
        }
      },
      // 获取当前登录用户
      getUserName(){
        request({
            url: '/system/user/profile',
            method: 'get'
          }).then(res=>{
            this.userName = res.data.userName;
          })
      }
    }
  }
</script>

<style>
  .isShow .el-switch__label {
    position: absolute;
    display: none;
    color: #fff;
  }
  /*打开时文字位置设置*/
  .isShow .el-switch__label--right {
    z-index: 1;
   right: 8px;
 }
 /*关闭时文字位置设置*/
 .isShow .el-switch__label--left {
   z-index: 1;
   left: 8px;

 }
 /*显示文字*/
 .isShow .el-switch__label.is-active {
   display: block;
 }
 .isShow.el-switch .el-switch__core,
 .el-switch .el-switch__label {
   width: 50px !important;
 }
</style>
