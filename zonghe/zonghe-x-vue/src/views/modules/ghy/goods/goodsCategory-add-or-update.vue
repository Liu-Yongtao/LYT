<template>
    <el-dialog
      :title="!goodsCategoryData.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="goodsCategoryData" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px">
        <el-form-item label="产品名称" prop="name">
          <el-input v-model="goodsCategoryData.name" placeholder="产品名称"></el-input>
        </el-form-item>
        <el-form-item label="是否上架" size="mini" prop="status">
          <el-radio-group v-model="goodsCategoryData.status">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="displaycover" prop="name">
          <el-input v-model="goodsCategoryData.displaycover" placeholder="displaycover"></el-input>
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
          goodsCategoryData:{
            id:0,
            name:'',
            status:1,
            displaycover:0
          }
        }
      },
      methods: {
        init (data) {
          this.visible = true;
          if(data!==''){
              this.goodsCategoryData = data;
          }else{
              this.goodsCategoryData = this.$options.data().goodsCategoryData;
          }
        },
        // 表单提交
        dataFormSubmit () {
          var url = !this.goodsCategoryData.id ? '/goods/insertGoodsCategory' : '/goods/updateGoodsCategory';
          this.$http({
                url: this.$http.adornUrl(url),
                method: 'post',
                data:this.goodsCategoryData
              }).then(({data}) => {
                if(data==='成功'){
                  this.visible = false;
                  this.$message({message: data,type: 'success'});
                  this.$emit('getGoodsCategoryList')
                }else{
                  this.$message.error(data)
                }
              })
        }
      }
    }
  </script>
  