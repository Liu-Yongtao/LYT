<template>
  <div>
    <el-dialog
        title="详细"
        :visible.sync="visible"
        width="60%"
        :before-close="handleClose">

         <el-descriptions class="margin-top" labelStyle="labelStyle" direction="horizontal" :column="2" :size="medium" border>
            <el-descriptions-item label="话商ID">{{data.cashierId}}</el-descriptions-item>
            <el-descriptions-item label="支付订单号">{{data.payLogno}}</el-descriptions-item>
            <el-descriptions-item label="平台订单号">{{data.logno}}</el-descriptions-item>
            <el-descriptions-item label="账号">{{data.account}}</el-descriptions-item>
            <el-descriptions-item label="金额">{{data.money}}</el-descriptions-item>
            <el-descriptions-item label="支付类型">
                <template>
                <span>{{data.zfType === 0 ? '微信' : data.zfType === 1 ? '支付宝':'-'}}</span>
            </template>
            </el-descriptions-item>
            <el-descriptions-item label="运营商类型">
                <template>
                    <span>{{data.telephoneType === 0 ? '移动' : data.telephoneType === 1?'联通':'电信'}}</span>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="端类型">
                <template>
                    <span>{{data.phoneType === 0  ? '安卓' : data.telephoneType === 1?'苹果':'-'}}</span>
                </template>
            </el-descriptions-item>
            
            <el-descriptions-item label="产码状态">
                <template>
                    <el-tag
                    :type="data.status === 0 ? 'primary' : data.status===1?'danger':'success'"
                    disable-transitions>{{data.status === 0 ? '待产码' : data.status===1?'产码失败':'产码成功'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="ip">{{data.ipaddr}}</el-descriptions-item>
            <el-descriptions-item label="当前渠道">{{data.location}}</el-descriptions-item>
            <el-descriptions-item label="支付链接">{{data.payUrl}}</el-descriptions-item>
            <el-descriptions-item label="查单地址">{{data.queryUrl}}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{data.statTime}}</el-descriptions-item>
            <el-descriptions-item label="结束时间">{{data.endTime}}</el-descriptions-item>
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