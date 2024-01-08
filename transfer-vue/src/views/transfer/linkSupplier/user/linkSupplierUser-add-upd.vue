<template>
    <el-dialog
      :title="!userData.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="userData" :rules="rules" ref="ruleForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="渠道名称" prop="title">
          <el-input v-model="userData.title" placeholder="渠道名称"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="userData.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="费率" prop="lv">
          <el-input v-model="userData.lv" placeholder="费率"></el-input>
        </el-form-item>
        <el-form-item label="渠道类型" size="mini" prop="type">
          <el-radio-group v-model="userData.type">
            <el-radio :label="0">快充</el-radio>
            <el-radio :label="1">慢充</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付类型" size="mini" prop="type">
          <el-radio-group v-model="userData.zfType">
            <el-radio :label="0">微信</el-radio>
            <el-radio :label="1">支付宝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付类型" size="mini" prop="type">
          <el-radio-group v-model="userData.phoneType">
            <el-radio :label="0">Android</el-radio>
            <el-radio :label="1">IOS</el-radio>
            <el-radio :label="2">双端</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="通信地址" prop="url">
          <el-input v-model="userData.url" placeholder="通信地址"></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="sorts">
          <el-input v-model="userData.sorts" placeholder="优先级" type="number" style="width:120px"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input type="textarea" v-model="userData.remake" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit('ruleForm')">确定</el-button>
      </span>
    </el-dialog>
</template>

<script>
import request from '@/utils/request.js';
export default {
    data () {
        return {
          visible: false,
          userData:{
            title:'',
            userName:'',
            lv:'',
            url:'',
            type:0,
            zfType:1,
            phoneType:2,
            remake:'',
            sorts:''
          },
          rules:{
             title: [
            { required: true, message: '请填写渠道名称', trigger: 'blur' }
          ],
          lv: [
            { required: true, message: '请填写渠道费率', trigger: 'change' }
          ]
          },
          inputVisible: false,
          inputValue: ''
        }
      },
      methods: {
        init (data) {
          this.visible = true;
          if(data!==''){
              this.userData = {
                id:data.id,
                title:data.title,
                userName:data.userName,
                lv:data.lv,
                url:data.url,
                type:data.type,
                zfType:data.zfType,
                phoneType:data.phoneType,
                remake:data.remake,
                sorts:data.sorts
              }
          }else{
              this.userData = this.$options.data().userData;
          }
        },
        // 表单提交
        dataFormSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //校验成功
            var url = !this.userData.id ? '/transfer/insertLinkSupplierUser' : '/transfer/updateLinkSupplierUser';
            request({
              url: url,
              method: 'post',
              data:{
                id:this.userData.id,
                title:this.userData.title,
                userName:this.userData.userName,
                lv:this.userData.lv,
                zfType:this.userData.zfType,
                phoneType:this.userData.phoneType,
                type:this.userData.type,
                url:this.userData.url,
                remake:this.userData.remake,
                sorts:this.userData.sorts
              }
            }).then(data => {
                if(data === "成功"){
                    this.$message({
                        message: data,
                        type: 'success'
                    });
                    this.visible = false;
                    this.$emit('fromSubmit');
                }else{
                    this.$message.error(data);
                }
        
            })
          } else {
            return false;
          }
        });
      }
    }
}
</script>

  <style>
  </style>