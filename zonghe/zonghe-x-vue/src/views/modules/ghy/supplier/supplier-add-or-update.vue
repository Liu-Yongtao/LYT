<template>
<div>
  <el-dialog
    :title="!supplierData.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="supplierData" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="供应商名称" prop="name">
        <el-input v-model="supplierData.name" placeholder="公司名称"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="mobile">
        <el-input v-model="supplierData.mobile" placeholder="联系电话"></el-input>
      </el-form-item>
      <el-form-item label="联系人" prop="contact">
        <el-input v-model="supplierData.contact" placeholder="联系人"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="supplierData.address" placeholder="地址"></el-input>
      </el-form-item>
      <el-form-item label="回调地址" size="mini" prop="set">
        <el-input size="small" v-model="supplierData.set" placeholder="回调地址" style="width:400px" readonly></el-input>
        <el-button @click="setShowUpdateCallback(supplierData.set,1)" type="info" size="small">编辑</el-button>
      </el-form-item>
      <el-form-item label="状态" size="mini" prop="status">
        <el-radio-group v-model="supplierData.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="性别" size="mini" prop="sex">
        <el-radio-group v-model="supplierData.sex" >
          <el-radio :label="0">未知</el-radio>
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="顺序" size="mini" prop="sorts">
            <el-input v-model="supplierData.sorts" placeholder="顺序" style="width:150px"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
     <update-callback ref="updateCallback" v-if="showUpdateCallback" @setSet="setSet"></update-callback>
</div>
</template>

<script>
import updateCallback from './supplier-update-callback.vue';
  export default {
    data () {
      return {
        visible: false,
        showUpdateCallback:false,
        supplierData:{
          id:0,
          sn:'',
          name:'',
          contact:'',
          mobile:'',
          address:'',
          sex:0,
          set:'',
          istj:'',
          status:1,
          sorts:''
        }
      }
    },
    methods: {
      init (data) {
        this.visible = true;
        if(data!==""){
          this.supplierData = data;
        }else{
          this.supplierData = this.$options.data().supplierData;
        }
        
      },
      // 表单提交
      dataFormSubmit () {
        var url = !this.supplierData.id ? '/supplier/insterSupplier' : '/supplier/updateSupplier';
        this.$http({
              url: this.$http.adornUrl(url),
              method: 'post',
              data:this.supplierData
            }).then(({data}) => {
              if(data==='成功'){
                this.visible = false;
                this.$message({message: data,type: 'success'});
                this.$emit('getSupplierList')
              }else{
                this.$message.error(data)
              }
            })
      },
      //显示修改回调地址,flag=1修改，flag=0显示
      setShowUpdateCallback(set,flag){
            this.showUpdateCallback = true;
            if(set===undefined || set===''||set===null){
                this.$nextTick(() => {
                    this.$refs.updateCallback.init("",flag);
                })
            }else{
                var d = JSON.stringify(set);
                this.$nextTick(() => {
                    this.$refs.updateCallback.init(JSON.parse(JSON.parse(d)),flag);
                })
            }
      },
      //接收子组件回调
      setSet(set){
        this.supplierData.set = set;
        // 更新组件Dom
        this.$forceUpdate();
      }
    },
    components:{
        updateCallback
    }
  }
</script>
