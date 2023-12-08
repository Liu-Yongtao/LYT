<template>
   <div>
      <Dialog ref="Dialog"></Dialog>
    
      <!-- 条件查询区域 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
     <el-form-item>
        <!-- 新增按钮 -->
        <el-button type="primary" @click="setShowAddOrUpdate('')">新增</el-button>
     </el-form-item>
     <el-form-item label="类型名称">
        <el-input v-model="formInline.name" placeholder="类型名称" style="width:130px"></el-input>
      </el-form-item>
      <el-form-item label="状态" >
        <el-select v-model="formInline.status" placeholder="选择状态" style="width:120px">
          <el-option label="--  --" value=""></el-option>
          <el-option label="已上架" value="1"></el-option>
          <el-option label="未上架" value="0"></el-option>
      </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="clearSelect()">清空</el-button>
        <el-button type="primary" @click="getGoodsCategoryList(0)">查询</el-button>
      </el-form-item>
    </el-form>

 <el-table
    v-loading="loading"
    :data="goodsCategoryList"
    border
    highlight-current-row
    height="60vh"
    style="width: 830px">
    <!-- 多选 -->
    <el-table-column
      type="selection"
      width="55" align="center">
    </el-table-column>
    <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
    <el-table-column prop="name" label="产品名称" width="200" align="center"></el-table-column>
    <el-table-column prop="status" label="状态" width="150" align="center">
        <template slot-scope="scope">
            <el-tag
              :type="scope.row.status === 1 ? 'primary':'danger'"
              disable-transitions>{{scope.row.status === 1 ? '已上架':'未上架'}}</el-tag>
        </template>
    </el-table-column>
    <el-table-column prop="displaycover" label="displaycover" width="120" align="center"></el-table-column>
    <el-table-column label="操作" width="180" align="center">
        <template slot-scope="scope">
          <el-button @click="setShowAddOrUpdate(scope.row)" type="info" size="small">编辑</el-button>
<el-button @click="updateGoodsCategoryList(scope.row.id,!scope.row.status?1:0)" :type="!scope.row.status?'primary':'danger'" size="small">{{!scope.row.status?'上架':'下架'}}</el-button>
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

<!-- 新增 or 修改 -->
  <addor-update ref="addOrUpdate" v-if="showAddOrUpdate" @getGoodsCategoryList="getGoodsCategoryList"></addor-update>
  </div>
   
</template>

<script>
  import AddorUpdate from './goodsCategory-add-or-update.vue';
  import Dialog from '../../../comUtil/dialog.vue';
  export default {
    inject: ['refresh'],
    data(){
      return{
        goodsCategoryList:[],          //数据
        sumCount:0,           //查询数据行数
        staLimit:0,          //起始分页
        currentPage:1,        //当前页数
        endLimit:20,          //一页显示数据，默认20行/页
        formInline:{  
          name:'',
          status:''
        },
        loading:false,        //加载
        clearable:false,
        showAddOrUpdate:false,    //显示 新增 or 更新
      }
    },
    created(){
        this.getGoodsCategoryList(0);
        this.handleCurrentChange = this.handleCurrentChange();
    },
    methods:{
      /**
       * 获取产品列表，sta代表着两种环境发送的请求
       * sta = 0时，代表着一开始打开这个页面
       * sta = undefined ，代表着修改，但是不想从第一个页面找
       */
      getGoodsCategoryList(sta){
        this.loading = true;
        if(sta==0){
          this.currentPage = 1
        }
        if(sta===undefined){
          sta = (this.currentPage-1)*this.endLimit;
        }
        this.$http({
              url: this.$http.adornUrl('/goods/goodsCategoryList'),
              method: 'post',
              data:{
                name:this.formInline.name,
                status:this.formInline.status,
                staLimit:sta,
                endLimit:this.endLimit,
              }
            }).then(({data}) => {
              this.goodsCategoryList = data.goodsCategoryList;
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
              this.getGoodsList(limit);
            },500);
      },
      /**
       * 选择一页多少数据量时触发事件
      */
      handleSizeChange(val){
        this.endLimit = val;
        this.getCashierCategoryList(0);
      },
      /**
       * 清除条件查询
       */
      clearSelect(){
        this.formInline = {
          name:'',
          status:'',
        }
      },
      updateGoodsCategoryList(id,status){
          this.$http({
              url: this.$http.adornUrl('/goods/updateGoodsCategory'),
              method: 'post',
              data:{
                id:id,
                status:status,
              }
            }).then(({data}) => {
              if(data==='成功'){
                this.$refs.Dialog.open(data,'success');
                this.getGoodsCategoryList()
              }else{
                this.$refs.Dialog.open(data,'erro');
              }
            })
      },
      setShowAddOrUpdate(data){
          this.showAddOrUpdate = true;
          var d = JSON.stringify(data);
          this.$nextTick(() => {
            this.$refs.addOrUpdate.init(JSON.parse(d))
        })
      }
    },
    components:{
      AddorUpdate,
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
