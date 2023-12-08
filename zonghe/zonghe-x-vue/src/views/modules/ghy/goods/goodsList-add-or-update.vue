<template>
  <el-dialog
    :title="!goodsData.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="goodsData" :rules="dataRule" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="产品编号" prop="sn">
        <el-input v-model="goodsData.sn" placeholder="产品编号"></el-input>
      </el-form-item>
      <el-form-item label="产品名称" prop="name">
        <el-input v-model="goodsData.name" placeholder="产品名称"></el-input>
      </el-form-item>
      <el-form-item label="产品面值" prop="price">
        <el-input v-model="goodsData.price" placeholder="产品面值"></el-input>
      </el-form-item>
      <el-form-item label="业务类型" prop="categoryid">
        <el-select v-model="goodsData.categoryid" placeholder="业务类型">
                <el-option :label="item.name" :value="item.id" v-for="item in goodsCatetoryList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产品类型" prop="fromid">
        <el-select v-model="goodsData.fromid" placeholder="产品类型">
                <el-option :label="item.name" :value="item.id" v-for="item in cashierCatetoryList" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="是否上架" size="mini" prop="status">
        <el-radio-group v-model="goodsData.status">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
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
        goodsCatetoryList:[],
        cashierCatetoryList:[],
        goodsData:{
          id:0,
          sn:'',
          name:'',
          price:'',
          categoryid:'',
          fromid:'',
          status:1
        },
        dataRule: {
          sn: [
            { required: true, message: '产品编号为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '面值为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (data,goodsCatetoryList,cashierCatetoryList) {
        this.visible = true;
        if(data!==''){
            this.goodsData = data;
        }else{
          this.goodsData = this.$options.data().goodsData;
        }

        //业务类型列表和产品类型列表
        this.goodsCatetoryList = goodsCatetoryList;
        this.cashierCatetoryList = cashierCatetoryList;

      },
      // 表单提交
      dataFormSubmit () {
        var url = !this.goodsData.id ? '/goods/insterGoods' : '/goods/updateGoodsList';
        this.$http({
              url: this.$http.adornUrl(url),
              method: 'post',
              data:this.goodsData
            }).then(({data}) => {
              if(data==='成功'){
                this.visible = false;
                this.$message({message: data,type: 'success'});
                this.$emit('getGoodsList')
              }else{
                this.$message.error(data)
              }
            })
      }
    }
  }
</script>
