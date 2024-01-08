<template>
  <div>
    <el-dialog
        title="详细"
        :visible.sync="visible"
        width="50%"
        :before-close="handleClose">

         <el-descriptions class="margin-top" labelStyle="labelStyle" direction="horizontal" :column="2" :size="medium" border>
            <el-descriptions-item label="商户ID">{{data.id}}</el-descriptions-item>
            <el-descriptions-item label="商户名称">{{data.title}}</el-descriptions-item>
            <el-descriptions-item label="用户名">{{data.userName}}</el-descriptions-item>
        <el-descriptions-item label="apiKey">{{data.apiKey}}</el-descriptions-item>
            <el-descriptions-item label="状态">
                <template>
                    <el-tag
                    :type="data.status===0?'danger':'success'"
                    disable-transitions>{{data.status===0?'禁用':'启用'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="通道">{{data.passage}}</el-descriptions-item>
            <el-descriptions-item label="ip白名单">{{data.ipList}}</el-descriptions-item>
            <el-descriptions-item label="回调地址">{{data.notifyUrl}}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{data.time}}</el-descriptions-item>
            <el-descriptions-item label="优先级">{{data.sorts}}</el-descriptions-item>
            <el-descriptions-item label="备注">{{data.remake}}</el-descriptions-item>
        </el-descriptions>

    </el-dialog>
           
  </div>
</template>

<script>
export default {
    data(){
        return{
            data:{},
            visible:false
        }
    },
    methods:{
        init(data){
            this.data = data;
            this.visible = true;
        },
        handleClose(){
            this.visible = false;
        }
    }
}
</script>

<style>

</style>