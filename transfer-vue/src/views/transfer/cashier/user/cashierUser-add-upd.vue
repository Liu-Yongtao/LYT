<template>
    <el-dialog
      :title="!cashierUser.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="cashierUser" :rules="rules" ref="ruleForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="话商名称" prop="title">
          <el-input v-model="cashierUser.title" placeholder="话商名称"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="cashierUser.userName" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="费率" prop="lv">
          <el-input v-model="cashierUser.lv" placeholder="费率"></el-input>
        </el-form-item>
        <el-form-item label="ip白名单" prop="ipList">
            <el-tag
              :key="tag"
              v-for="tag in cashierUser.ipList"
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
          <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
        </el-form-item>
        <el-form-item label="资源类型" size="mini" prop="type">
          <el-radio-group v-model="cashierUser.type">
            <el-radio :label="0">网厅</el-radio>
            <el-radio :label="1">三方</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="回调地址" prop="notifyUrl">
          <el-input v-model="cashierUser.notifyUrl" placeholder="回调地址"></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="sorts">
          <el-input v-model="cashierUser.sorts" placeholder="优先级" type="number" style="width:120px"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input type="textarea" v-model="cashierUser.remake" placeholder="备注"></el-input>
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
          cashierUser:{
            title:'',
            userName:'',
            lv:'',
            ipList:[],
            type:0,
            notifyUrl:'',
            remake:'',
            sorts:''
          },
          rules:{
             title: [
            { required: true, message: '请填写商户名称', trigger: 'blur' }
          ],
          lv: [
            { required: true, message: '请填写商户费率', trigger: 'change' }
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
              this.cashierUser = {
                id:data.id,
                title:data.title,
                userName:data.userName,
                lv:data.lv,
                ipList:JSON.parse(data.ipList),
                type:data.type,
                notifyUrl:data.notifyUrl,
                remake:data.remake,
                sorts:data.sorts
              }
          }else{
              this.cashierUser = this.$options.data().cashierUser;
          }
        },
        // 表单提交
        dataFormSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //校验成功
            var url = !this.cashierUser.id ? '/transfer/insertCashierUser' : '/transfer/updateCashierUser';
            request({
              url: url,
              method: 'post',
              data:{
                id:this.cashierUser.id,
                title:this.cashierUser.title,
                userName:this.cashierUser.userName,
                lv:this.cashierUser.lv,
                ipList:JSON.stringify(this.cashierUser.ipList),
                type:this.cashierUser.type,
                notifyUrl:this.cashierUser.notifyUrl,
                remake:this.cashierUser.remake,
                sorts:this.cashierUser.sorts
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
      // 标签关闭
      handleClose(tag){
        console.log(tag);
        var list = [];
        for(let i=0;i<this.cashierUser.ipList.length;i++){
          if(this.cashierUser.ipList[i] !== tag){
            list.push(this.cashierUser.ipList[i]);
          }
        }
        this.cashierUser.ipList = list;
      },
      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.cashierUser.ipList.push(inputValue);
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