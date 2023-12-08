<template>
  <el-dialog
    :title="!flag?'查看':'修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="supplierSetData" @keyup.enter.native="dataFormSubmit()" label-width="100px">
      <el-form-item label="desc1" prop="desc1">
        <el-input v-model="supplierSetData.desc1" placeholder="desc1" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="callback_url" prop="callback_url">
        <el-input v-model="supplierSetData.callback_url" placeholder="callback_url" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="desc2" prop="desc2">
        <el-input v-model="supplierSetData.desc2" placeholder="desc2" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="apply_url" prop="apply_url">
        <el-input v-model="supplierSetData.apply_url" placeholder="apply_url" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="balance_url" prop="balance_url">
        <el-input size="small" v-model="supplierSetData.balance_url" placeholder="balance_url" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="query_url" prop="query_url">
        <el-input size="small" v-model="supplierSetData.query_url" placeholder="query_url" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="client_secret" prop="client_secret">
        <el-input size="small" v-model="supplierSetData.client_secret" placeholder="client_secret" :readonly="!flag"></el-input>
      </el-form-item>
      <el-form-item label="client_id" prop="client_id">
        <el-input size="small" v-model="supplierSetData.client_id" placeholder="client_id" :readonly="!flag"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button :hidden="!flag" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        flag:0,
        supplierSetData:{
          desc1:"",
          callback_url:"",
          desc2:"",
          apply_url:"",
          balance_url:"",
          query_url:"",
          client_secret:"",
          client_id:""
        }
      }
    },
    methods: {
      init (data,flag) {
        this.visible = true;
        if(data!==""){
            this.supplierSetData = data;
        }else{
          this.supplierSetData = this.$options.data().supplierSetData;
        }
        this.flag = flag;
      },
      /**
       * 表单提交
       * 将数据传给父组件 emit
       */
      dataFormSubmit () {
            this.visible = false;
            this.$emit('setSet',JSON.stringify(this.supplierSetData))
      }
    }
  }
</script>
