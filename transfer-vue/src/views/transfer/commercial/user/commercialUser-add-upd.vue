<template>
    <el-dialog
      :title="!Data.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="Data" :rules="rules" ref="ruleForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="商户名称" prop="title">
          <el-input v-model="Data.title" placeholder="话商名称"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="Data.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="ip白名单" prop="ipList">
            <el-tag
              :key="tag"
              v-for="tag in Data.ipList"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
                class="input-new-tag"
                v-if="inputVisible"
                v-model="inputValue"
                ref="saveTagInput"
                size="small"
                @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm"
              >
           </el-input>
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New IP</el-button>
        </el-form-item>
        <el-form-item label="回调地址" prop="notifyUrl">
          <el-input v-model="Data.notifyUrl" placeholder="回调地址"></el-input>
        </el-form-item>

        <el-form-item label="配置通道">
          <el-select v-model="Data.passage" placeholder="添加通道" style="width:120px;margin-bottom:10px">
              <el-option label="--  --" value=""></el-option>
              <el-option  v-for="(item,index) in passageList" :key="index" :label="item.title" 
              :value="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="优先级" prop="sorts">
          <el-input v-model="Data.sorts" placeholder="优先级" type="number" style="width:120px"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input type="textarea" v-model="Data.remake" placeholder="备注"></el-input>
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
          Data:{
            title:'',
            userName:'',
            ipList:[],
            notifyUrl:'',
            passage:'',
            remake:'',
            sorts:''
          },
          rules:{
             title: [
            { required: true, message: '请填写商户名称', trigger: 'blur' }
          ]},
          inputVisible: false,
          inputValue: '',
          passageList:[]
        }
      },
      methods: {
        init (data) {
          this.visible = true;
          if(data!==''){
              this.Data = {
                id:data.id,
                title:data.title,
                userName:data.userName,
                ipList:JSON.parse(data.ipList),
                notifyUrl:data.notifyUrl,
                passage:data.passage,
                remake:data.remake,
                sorts:data.sorts
              }
          }else{
              this.Data = this.$options.data().Data;
          }
          this.getSupplierLocation();
        },
        // 表单提交
        dataFormSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //校验成功
            var url = !this.Data.id ? '/transfer/insertCommercialUser' : '/transfer/updateCommercialUser';
            request({
              url: url,
              method: 'post',
              data:{
                id:this.Data.id,
                title:this.Data.title,
                userName:this.Data.userName,
                ipList:JSON.stringify(this.Data.ipList),
                passage:this.Data.passage,
                notifyUrl:this.Data.notifyUrl,
                remake:this.Data.remake,
                sorts:this.Data.sorts
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
      },
      //获取通道
      getSupplierLocation(){
            request({
              url: '/transfer/getPassageIdAndName',
              method: 'post',
            }).then(data => {
                this.passageList = data;
            })
      },
      // 标签关闭
      handleClose(tag){
        var list = [];
        for(let i=0;i<this.Data.ipList.length;i++){
          if(this.Data.ipList[i] !== tag){
            list.push(this.Data.ipList[i]);
          }
        }
        this.Data.ipList = list;
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.Data.ipList.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      }
    
    }
}
</script>

  <style>
      .el-tag + .el-tag {
        margin-left: 10px;
      }
      .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
      }
      .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
      }
  </style>