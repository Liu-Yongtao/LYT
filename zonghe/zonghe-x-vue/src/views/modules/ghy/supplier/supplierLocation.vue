<template>
   <div>
      <Dialog ref="Dialog"></Dialog>

      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item>
        <!-- 新增按钮 -->
        <el-button type="primary" @click="setShowAddOrUpdate('')">新增</el-button>
      </el-form-item>
      <el-form-item label="供应商名称">
        <el-input v-model="formInline.name" placeholder="供应商名称" style="width:180px"></el-input>
      </el-form-item>
      <el-form-item label="状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="clearSelect()">清空</el-button>
        <el-button type="primary" @click="getLocationList(0)">查询</el-button>
      </el-form-item>
    </el-form>

 <el-table
    v-loading="loading"
    :data="locationList"
    border
    highlight-current-row
    height="60vh"
    style="width: 100%;">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>

    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="name" label="供应商名称" width="200" align="center"></el-table-column>
    <el-table-column prop="categoryid" label="所属类型" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid1" label="渠道ID1" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid2" label="渠道ID2" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid3" label="渠道ID3" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid4" label="渠道ID4" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid5" label="渠道ID5" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid6" label="渠道ID6" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid7" label="渠道ID7" width="100" align="center"></el-table-column>
    <el-table-column prop="supplierid8" label="渠道ID8" width="100" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="120" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 1 ? 'primary':'danger'"
              disable-transitions>{{scope.row.status === 1 ? '启用':'禁用'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="sorts" label="sorts" width="100" align="center"></el-table-column>
    <el-table-column fixed="right" label="操作" width="180">
        <template slot-scope="scope">
          <el-button @click="setShowAddOrUpdate(scope.row)" type="primary" size="small">编辑</el-button>
          <el-button @click="updateStatus(scope.row.id,!scope.row.status?1:0)" :type="!scope.row.status?'primary':'danger'" size="small">{{!scope.row.status?'启用':'禁用'}}</el-button>
        </template>
    </el-table-column>
  </el-table>

    <!-- 分页组件 -->
    <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[50, 100, 200]"
          :page-size="endLimit"
          layout="total, prev, pager, next,sizes"
          :total="sumCount">
        </el-pagination>

        <!-- 新增 or 编辑 -->
        <add-or-update ref="addOrUpdate" @getLocationList="getLocationList" v-if="showAddOrUpdate" />
  </div>
   
</template>

<script>
  import Dialog from '../../../comUtil/dialog.vue';
  import addOrUpdate from './location-add-or-update.vue';
  export default {
    inject: ['refresh'],
    data(){
      return{
        locationList:[],          //数据
        sumCount:0,           //查询数据行数
        staLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          name:'',
          status:''
        },
        loading:false,        //加载
        showAddOrUpdate:false
      }
    },
    created(){
        this.getLocationList(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
      getLocationList(sta){
        this.loading = true;
        if(sta==0){
          this.currentPage = 1
        }
        if(sta===undefined){
          sta = (this.currentPage-1)*this.endLimit;
        }
        this.$http({
              url: this.$http.adornUrl('/supplier/locationList'),
              method: 'post',
              data:{
                name:this.formInline.name,
                status:this.formInline.status,
                staLimit:sta,
                endLimit:this.endLimit,
              }
            }).then(({data}) => {
              this.locationList = data.supplierLocationList;
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
              this.currentPage = val;
              this.getLocationList(limit);
            },500);
      },
      /**
       * 选择一页多少数据量时触发事件
      */
      handleSizeChange(val){
        this.endLimit = val;
        this.getLocationList(0);
      },
      /**
       * 清除条件查询
       */
      clearSelect(){
        this.formInline = {
          name:'',
          status:''
        }
      },
      /**
       * 禁用 / 启用 商户
       */
      updateStatus(id,status){
        this.$http({
              url: this.$http.adornUrl('/supplier/updateSupplierLocation'),
              method: 'post',
              data:{
                id:id,
                status:status
              }
            }).then(({data}) => {
              if(data==='成功'){
                this.getLocationList();
              }else{
                this.$message.error(data)
              }
            })
      },
      /***
       * 打开新增 or 更新弹窗
       */
      setShowAddOrUpdate(data){
          this.showAddOrUpdate = true;
          var d = JSON.stringify(data);
          this.$nextTick(()=>{
            this.$refs.addOrUpdate.init(JSON.parse(d))
          })
      }
    },
    components:{
      Dialog,
      addOrUpdate
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
