<template>
  <el-dialog
    :title="!locationData.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="locationData" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="供应商名称" prop="name">
        <el-input v-model="locationData.name" placeholder="公司名称"></el-input>
      </el-form-item>
      <el-form-item label="产品类型" prop="categoryid">
         <el-select v-model="locationData.categoryid" placeholder="选择产品类型">
                <el-option :label="item.name" :value="item.id" v-for="item in categoryList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道1" prop="supplier1">
         <el-select v-model="locationData.supplierid1" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道2" prop="supplier2">
         <el-select v-model="locationData.supplierid2" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道3" prop="supplier3">
         <el-select v-model="locationData.supplierid3" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道4" prop="supplier4">
         <el-select v-model="locationData.supplierid4" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道5" prop="supplier5">
         <el-select v-model="locationData.ssupplierid5" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道6" prop="supplier6">
         <el-select v-model="locationData.supplierid6" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道7" prop="supplier7">
         <el-select v-model="locationData.supplierid7" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="渠道8" prop="supplier8">
         <el-select v-model="locationData.supplierid8" placeholder="选择渠道">
                <el-option :label="item.name" :value="item.id" v-for="item in supplierList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" size="mini" prop="status">
        <el-radio-group v-model="locationData.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="顺序" size="mini" prop="sorts">
            <el-input v-model="locationData.sorts" placeholder="顺序" style="width:150px"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        locationData:{
          id:0,
          name:'',
          categoryid:1,
          supplierid1:'',
          supplierid2:'',
          supplierid3:'',
          supplierid4:'',
          supplierid5:'',
          supplierid6:'',
          supplierid7:'',
          supplierid8:'',
          status:1,
          sorts:''
        },
        supplierList:[],
        categoryList:[]
      }
    },
    methods: {
      init (data) {
        this.visible = true;
        if(data!==''){
            this.locationData = data;
        }else{
          this.locationData = this.$options.data().locationData;
        }

        //无数据时加载
        if(this.supplierList.length==0){
          this.getSupplierList();
        }
        if(this.categoryList.length==0){
          this.getCatetoryList();
        }
        
        
      },
      // 表单提交
      dataFormSubmit () {
        var url = !this.locationData.id ? '/supplier/insertSupplierLocation' : '/supplier/updateSupplierLocation';
        this.$http({
              url: this.$http.adornUrl(url),
              method: 'post',
              data:this.locationData
            }).then(({data}) => {
              if(data==='成功'){
                this.visible = false;
                this.$message({message: data,type: 'success'});
                this.$emit('getLocationList')
              }else{
                this.$message.error(data)
              }
            })
      },
      /**
       * 获取所有产品类型列表
       */
      getCatetoryList(){
        this.$http({
              url: this.$http.adornUrl("/goods/categoryIdAndName"),
              method: 'post'
            }).then(({data}) => {
             this.categoryList = data
            })
      },
      /**
       * 获取所有供货商列表
       */
      getSupplierList(){
             this.$http({
              url: this.$http.adornUrl("/supplier/supplierIdAndName"),
              method: 'post'
            }).then(({data}) => {

              this.supplierList = data
            })
      }
    }
  }
</script>
