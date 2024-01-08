<template>
    <el-dialog
      title="配置渠道"
      :close-on-click-modal="false"
      width="70%"
      :visible.sync="visible">
      <el-form :inline="true">
        <el-form-item>
        <el-select v-model="value" placeholder="添加渠道" style="width:180px;margin-bottom:10px">
            <el-option label="--  --" value=""></el-option>
            <el-option  v-for="(item,index) in supplierUserList" :key="index" :label="item.title" 
            :value="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="addSupplierUserList()" style="margin-bottom:5px">增加</el-button>
      </el-form-item>
      </el-form>

    <el-table
        :data="userDataList"
        border
        style="width: 100%">
        <el-table-column prop="id" label="渠道ID" width="100" align="center"></el-table-column>
        <el-table-column prop="title" label="渠道名称" width="180" align="center"> </el-table-column>
        <el-table-column prop="type" label="渠道类型" width="130" align="center">
        <template slot-scope="scope">
            <span>{{scope.row.type === 0 ? '快充' : scope.row.type === 1?'慢充':'-'}}</span>
        </template>
        </el-table-column>
        <el-table-column prop="phoneType" label="操作系统" width="130" align="center">
        <template slot-scope="scope">
            <span>{{scope.row.phoneType === 0 ? 'Android' : scope.row.phoneType === 1?'IOS':'双端'}}</span>
        </template>
    </el-table-column>
        <el-table-column prop="zfType" label="支付类型" width="130" align="center">
        <template slot-scope="scope">
            <span>{{scope.row.zfType === 0 ? '微信' : scope.row.zfType === 1?'支付宝':'-'}}</span>
        </template>
    </el-table-column>
    <el-table-column prop="status" label="状态" width="130" align="center">
        <template slot-scope="scope">
            <el-switch
            v-model="scope.row.status"
            class="isShow"
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="禁用"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="changeSwitch(scope.row)">
            </el-switch>
        </template>
    </el-table-column>
    <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          
          <el-button @click="delSupplierUserIndex(scope.$index)" type="text" size="small">删除</el-button>
          <el-button @click="upSupplierUserIndex(scope.$index)" type="text" size="small">上移</el-button>
          <el-button @click="downSupplierUserIndex(scope.$index)" type="text" size="small">下移</el-button>
        </template>
    </el-table-column>
    </el-table>

      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
      </span>
    </el-dialog>
</template>

<script>
import request from '@/utils/request.js';
export default {
    data () {
        return {
          visible: false,
          id:'',
          userDataList:[],
          supplierUserList:[], //渠道列表
          value:'',
          type:'',
          zfType:''
        }
      },
      methods: {
        init (id,data,list,type,zfType,phoneType) {
           this.id = id;
           this.userDataList = JSON.parse(data);
           this.visible = true;
           // 筛选符合条件的渠道
           if(phoneType === 2){
              this.supplierUserList = list.filter((item)=>(item.type===type)&&
                            (item.zfType === zfType));
           }else{
            console.log(phoneType);
              this.supplierUserList = list.filter((item)=>(item.type===type)&&
                            (item.zfType === zfType) && (item.phoneType === phoneType));
           }
           
        },
        // 表单提交
        dataFormSubmit() {
            request({
              url: '/transfer/updateSupplierLocation',
              method: 'post',
              data:{
                id:this.id,
                locationList:JSON.stringify(this.userDataList)
              }
            }).then(data => {
                if(data === "未知错误" || data === '错误'){
                    this.$message.error(data);
                }else{
                    this.$message({
                        message: data,
                        type: 'success'
                    });
                    this.visible = false;
                    this.$emit('fromSubmit');
                }
        
            })
          
      },
    //   增加渠道
      addSupplierUserList(){
        if(this.value === '' || this.value === undefined){
             this.$message.error("添加失败，请查看是否选择正确");
            return;
        }
        var l = this.supplierUserList[this.value];
         var data = {
             id:l.id,
            title:l.title,
            type:l.type,
            zfType:l.zfType,
            phoneType:l.phoneType,
            status:1
         }
         this.userDataList.push(data);
      },
    //   删除一个渠道
    delSupplierUserIndex(i){
        this.$nextTick(()=>{
            this.userDataList.splice(i,1);
        })
    },
    // 上移
    upSupplierUserIndex(i){
        if(i==0){
            this.$message.error("已经到顶了！！");
            return;
        }
        var d = this.userDataList;
        let t = d[i];
        d[i] = d[i-1];
        d[i-1] = t;
        this.userDataList = d;
        //伪修改数组触发组件更新
        this.userDataList.splice(1,0);
    },
    // 下移
    downSupplierUserIndex(i){
        var d = this.userDataList;
        if(i==d.length-1){
            this.$message.error("已经到底了！！");
            return;
        }
        let t = d[i];
        d[i] = d[i+1];
        d[i+1] = t;
        this.userDataList = d;
        //伪修改数组触发组件更新
        this.userDataList.splice(1,0);
    }
    }
}
</script>

  <style>
  </style>