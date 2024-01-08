<template>
  <div>
    <el-dialog
        title="详细"
        :visible.sync="visible"
        width="60%"
        :before-close="handleClose">

         <el-descriptions class="margin-top" labelStyle="labelStyle" direction="horizontal" :column="2" :size="medium" border>
            <el-descriptions-item label="渠道编号">{{data.id}}</el-descriptions-item>
            <el-descriptions-item label="渠道名称">{{data.title}}</el-descriptions-item>
            <el-descriptions-item label="用户名">{{data.userName}}</el-descriptions-item>
            <el-descriptions-item label="费率">{{data.lv}}</el-descriptions-item>

            <el-descriptions-item label="渠道类型">
                <template>
                <span>{{data.type === 0 ? '快充' : data.type === 1 ? '慢充': '-'}}</span>
            </template>
            </el-descriptions-item>

            <el-descriptions-item label="支付类型">
                <template>
                <span>{{data.phoneType === 0 ? 'Android' : data.phoneType === 1 ? 'IOS': '双端'}}</span>
            </template>
            </el-descriptions-item>

            <el-descriptions-item label="支付类型">
                <template>
                <span>{{data.zfType === 0 ? '微信' : data.zfType === 1 ? '支付宝': '-'}}</span>
            </template>
            </el-descriptions-item>
            <el-descriptions-item label="状态">
                <template>
                    <el-tag
                    :type="data.status===0?'danger':'success'"
                    disable-transitions>{{data.status===0?'禁用':'启用'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="通信地址">{{data.url}}</el-descriptions-item>
        
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