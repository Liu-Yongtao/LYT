<template>
   <div>
      <Dialog ref="Dialog"></Dialog>

      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
        <!-- 新增按钮 -->
        <el-button type="primary" @click="setShowAddOrUpdate('')">新增</el-button>
     </el-form-item>
      <el-form-item label="商户名称">
        <el-input v-model="formInline.title" placeholder="商户名称（模糊）" style="width:150px"></el-input>
      </el-form-item>
     <el-form-item label="联系人">
        <el-input v-model="formInline.name" placeholder="联系人" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="formInline.mobile" placeholder="联系电话" style="width:150px"></el-input>
      </el-form-item>
      <el-form-item label="身份ID">
        <el-input v-model="formInline.clientId" placeholder="身份ID" style="width:180px"></el-input>
      </el-form-item>
      <el-form-item label="接口状态" >
        <el-select v-model="formInline.api" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="正常" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="商户状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="正常" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="预充值状态" >
        <el-select v-model="formInline.nstatus" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="未启用" value="0"></el-option>
          <el-option label="已启用" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="clearSelect()">清空</el-button>
        <el-button type="primary" @click="getCashierUserList(0)">查询</el-button>
      </el-form-item>
    </el-form>

 <el-table
    v-loading="loading"
    :data="cashierUserList"
    border
    height="60vh"
    highlight-current-row
    style="width: 100%;">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>

    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="title" label="商户名称" width="200" align="center"></el-table-column>
    <el-table-column prop="logo" label="商户logo" width="150" align="center"></el-table-column>
    <el-table-column prop="name" label="联系人" width="200" align="center"></el-table-column>
    <el-table-column prop="mobile" label="联系电话" width="120" align="center"></el-table-column>
    <el-table-column prop="withdrawmoney" label="商户余额" width="100" align="center"></el-table-column>
    <el-table-column prop="closemoney" label="冻结余额" width="200" align="center"></el-table-column>
    <el-table-column prop="clientId" label="身份ID" width="200" align="center"></el-table-column>
    <el-table-column prop="clientSecret" label="身份密钥" width="250" align="center"></el-table-column>
    <el-table-column prop="api" label="接口状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.api === 1 ? 'primary':'danger'"
              disable-transitions>{{scope.row.api === 1 ? '正常':'禁用'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="status" label="商户状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 1 ? 'primary':'danger'"
              disable-transitions>{{scope.row.status === 1 ? '正常':'禁用'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="nstatus" label="开启预充值" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.nstatus === 1 ? 'primary':'danger'"
              disable-transitions>{{scope.row.nstatus === 1 ? '开启':'不开启'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="account" label="账户" width="120" align="center"></el-table-column>
    <el-table-column prop="category" label="支持产品" width="100" align="center"></el-table-column>
    <el-table-column prop="sqmoney" label="授信金额" width="100" align="center"></el-table-column>
    <el-table-column prop="notifyurl" label="回调地址" width="200" align="center"></el-table-column>
    <el-table-column prop="iplist" label="ip白名单" width="200" align="center"></el-table-column>
    <el-table-column prop="perm" label="策略设置" width="100" align="center"></el-table-column>
    <el-table-column prop="version" label="version" width="100" align="center"></el-table-column>
    <el-table-column prop="isArea" label="is_area" width="100" align="center"></el-table-column>
    <el-table-column prop="isMiaofan" label="is_miaofan" width="100" align="center"></el-table-column>
    <el-table-column prop="isMiaoFan1" label="is_miaofan_1" width="110" align="center"></el-table-column>
    <el-table-column prop="isMiaoFan2" label="is_miaofan_2" width="110" align="center"></el-table-column>
    <el-table-column prop="isMiaoFan3" label="is_miaofan_3" width="110" align="center"></el-table-column>
    <el-table-column prop="isMiaoFan4" label="is_miaofan_4" width="110" align="center"></el-table-column>
    <el-table-column prop="istj" label="istj" width="100" align="center"></el-table-column>
    <el-table-column prop="sorts" label="排序" width="100" align="center"></el-table-column>
    <el-table-column fixed="right" label="操作" width="350">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="primary" size="small">编辑</el-button>
          <el-button @click="updateStatus(scope.row.id,!scope.row.api?1:0)" :type="!scope.row.api?'primary':'danger'" size="small">{{!scope.row.status?'启用接口':'禁用接口'}}</el-button>
          <el-button @click="updateStatus(scope.row.id,!scope.row.status?1:0)" :type="!scope.row.status?'primary':'danger'" size="small">{{!scope.row.status?'启用':'禁用'}}</el-button>
          <el-button @click="updateStatus(scope.row.id,!scope.row.nstatus?1:0)" :type="!scope.row.nstatus?'primary':'danger'" size="small">{{!scope.row.nstatus?'开启预支付':'关闭预支付'}}</el-button>
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
        cashierUserList:[],          //数据
        sumCount:0,           //查询数据行数
        staLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          title:'',
          name:'',
          mobile:'',
          clientId:'',
          api:'',
          nstatus:'',
          status:''
        },
        loading:false,        //加载
        clearable:false
      }
    },
    created(){
        this.getCashierUserList(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
      getCashierUserList(sta){
        this.loading = true;
        if(sta==0){
          this.currentPage = 1
        }
        this.$http({
              url: this.$http.adornUrl('/goods/cashierUserList'),
              method: 'post',
              data:{
                title:this.formInline.title,
                name:this.formInline.name,
                mobile:this.formInline.mobile,
                clientId:this.formInline.clientId,
                api:this.formInline.api,
                nstatus:this.formInline.nstatus,
                status:this.formInline.status,
                staLimit:sta,
                endLimit:this.endLimit,
              }
            }).then(({data}) => {
              this.cashierUserList = data.cashierUserList;
              this.sumCount = data.sumCount;
              this.loading = false;
            })
            
      },
      /**
       * 选择页时触发事件
      */
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getCashierUserList(limit);
            },500);
      },
      /**
       * 选择一页多少数据量时触发事件
      */
      handleSizeChange(val){
        this.endLimit = val;
        this.getCashierUserList(0);
      },
      /**
       * 清除条件查询
       */
      clearSelect(){
        this.formInline = {  
          title:'',
          name:'',
          mobile:'',
          clientId:'',
          api:'',
          nstatus:'',
          status:''
        }
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
 
</style>
