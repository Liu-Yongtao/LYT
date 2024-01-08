<template>
   <div class="app-container">
      
      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="话商名称">
        <el-input v-model="formInline.title" placeholder="话商名称" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="formInline.userName" placeholder="用户名" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="渠道类型" >
        <el-select v-model="formInline.type" placeholder="选择类型" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="快充" value="0"></el-option>
          <el-option label="慢充" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="支付类型" >
        <el-select v-model="formInline.zfType" placeholder="选择类型" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="微信" value="0"></el-option>
          <el-option label="支付宝" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item label="状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getLinkSupplierUserList(0)" size="mini">查询</el-button>
        <el-button type="primary" @click="clearSelect()" size="mini">重置</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="handleUpdate(0,'')" style="margin-bottom:5px">+新增</el-button>
 <el-table
    v-loading="loading"
    :data="linkSupplierUserList"
    border
    highlight-current-row
    @expand-change="toggleRowExpansion"
    style="width: 100%;min-height:60vh">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>
    <el-table-column prop="id" label="渠道ID" width="80" align="center"></el-table-column>
    <el-table-column prop="title" label="渠道名称" width="150" align="center"></el-table-column>
    <el-table-column prop="userName" label="用户名" width="150" align="center"></el-table-column>
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
        <span>{{scope.row.type === 0 ? '快充' : '慢充'}}</span>
      </template>
    </el-table-column>

  <el-table-column prop="phoneType" label="支付类型" width="120" align="center">
      <template slot-scope="scope">
        <span>{{scope.row.phoneType === 0 ? 'Android' : scope.row.phoneType === 1 ? 'IOS':'双端'}}</span>
      </template>
    </el-table-column>

    <el-table-column prop="zfType" label="支付类型" width="120" align="center">
      <template slot-scope="scope">
        <span>{{scope.row.zfType === 0 ? '微信' : scope.row.zfType === 1 ? '支付宝':'-'}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="time" label="创建时间" width="200" align="center"></el-table-column>

    <el-table-column fixed="right" label="操作" width="140" align="center">
        <template slot-scope="scope">
          <el-button @click="handleDetails(scope.row)" type="text" size="small">详细</el-button>
          <el-button @click="handleUpdate(1,scope.row)" type="text" size="small">编辑</el-button>
        </template>
    </el-table-column>
  </el-table>

    <!-- 详细页 -->
  <link-supplier-user-details v-if="details" ref="linkSupplierUserDetails" />
  <!-- 新增or编辑页 -->
  <link-supplier-user-add-upd v-if="add" ref="linkSupplierUserAddUpd" @fromSubmit="getLinkSupplierUserList" />
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
import linkSupplierUserDetails from './linkSupplierUser-details.vue';
import linkSupplierUserAddUpd from './linkSupplierUser-add-upd.vue';
  export default {
    inject: ['refresh'],
    data(){
      return{
        linkSupplierUserList:[],          //数据
        sumCount:0,           //查询数据行数
        statLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{   
          title:'',
          type:'',
          zfType:'',
          status:''
        },
        loading:false,        //加载
        details:false,
        add:false
      }
    },
    components:{
      linkSupplierUserDetails,
      linkSupplierUserAddUpd
    },
    created(){
        this.getLinkSupplierUserList(0);
    },
    methods:{
        //获取话商用户页
      getLinkSupplierUserList(stat){
        console.log(this.formInline.time);
        this.loading = true;
        if(stat==0){
          this.currentPage = 1
        }else{
          stat = (this.currentPage-1)*this.endLimit;
        }
        request({
              url: '/transfer/getLinkSupplierUserList',
              method: 'post',
              data:{
                title:this.formInline.title,
                userName:this.formInline.userName,
                type:this.formInline.type,
                zfType:this.formInline.zfType,
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
              this.linkSupplierUserList = data.linkSupplierUserList;
              this.loading = false;
            })
            
      },
      // 详细页
      handleDetails(data){
          this.details = true;
          this.$nextTick(()=>{
            this.$refs.linkSupplierUserDetails.init(data);
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
            this.$refs.linkSupplierUserAddUpd.init(d);
          })
      },
      // 分页数据处理
      handleCurrentChange(){
            return this.$JsUtil.debounce((val)=>{
              var limit = (val - 1) * this.endLimit;
              this.getLinkSupplierUser(limit);
            },500);
          
      },
      // 当重新选择页面显示数时
      handleSizeChange(val){
        this.endLimit = val;
        this.getLinkSupplierUser(0);
      },
      // 启用禁用时
      changeSwitch(row){
          request({
              url: '/transfer/updateLinkSupplierUser',
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
      clearSelect(){
        this.formInline = {  
          title:'',
          type:'',
          zfType:'',
          status:''
        }
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
