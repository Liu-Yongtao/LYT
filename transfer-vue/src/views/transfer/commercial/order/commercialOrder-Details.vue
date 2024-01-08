<template>
  <div>
    <el-dialog
        title="详细"
        :visible.sync="visible"
        width="60%"
        :before-close="handleClose">

         <el-descriptions class="margin-top" labelStyle="labelStyle" direction="horizontal" :column="2" :size="medium" border>
            <el-descriptions-item label="商户ID">{{data.commercialId}}</el-descriptions-item>
            <el-descriptions-item label="话商ID">{{data.cashierId}}</el-descriptions-item>
            <el-descriptions-item label="商户订单号">{{data.commercialNo}}</el-descriptions-item>
            <el-descriptions-item label="平台订单号">{{data.logno}}</el-descriptions-item>
            <el-descriptions-item label="充值号码">{{data.account}}</el-descriptions-item>
            <el-descriptions-item label="支付类型">
                <template>
                <span>{{data.zfType === 0 ? '微信' : data.zfType === 1?'支付宝':'-'}}</span>
            </template>
            </el-descriptions-item>
            <el-descriptions-item label="渠道类型">
                <template>
                <span>{{data.type === 0 ? '快充' : data.type === 1?'慢充':'-'}}</span>
            </template>
            </el-descriptions-item>
            <el-descriptions-item label="运营商">
                <template>
                <span>{{data.telephoneType === 0 ? '移动' : data.telephoneType === 1?'联通':'电信'}}</span>
            </template>
            </el-descriptions-item>
            
            <el-descriptions-item label="金额">{{data.money}}</el-descriptions-item>
            <el-descriptions-item label="归属地">{{data.area}}</el-descriptions-item>
            <el-descriptions-item label="客户端IP">{{data.ipaddr}}</el-descriptions-item>
            <el-descriptions-item label="客户端类型">{{data.phoneType === 0 ? 'Android' : data.phoneType === 1 ? 'IOS':'-'}}</el-descriptions-item>
            <el-descriptions-item label="通道编号">{{data.passage}}</el-descriptions-item>
            <el-descriptions-item label="支付状态">
                <template>
                    <el-tag
                    :type="data.payStatus === 0 ? 'primary' : data.payStatus===1?'danger':'success'"
                    disable-transitions>{{data.payStatus === 0 ? '待支付' : data.payStatus===1?'支付失败':'支付成功'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="通知状态">
                <template>
                    <el-tag
                    :type="data.notifyStatus === 0 ? 'primary' : data.notifyStatus===1?'danger':'success'"
                    disable-transitions>{{data.notifyStatus === 0 ? '待通知' : data.notifyStatus===1?'通知失败':'通知成功'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="产码状态">
                <template>
                    <el-tag
                    :type="data.urlStatus === 0 ? 'primary' : data.urlStatus===1?'danger':'success'"
                    disable-transitions>{{data.urlStatus === 0 ? '产码中' : data.urlStatus===1?'产码失败':'产码成功'}}</el-tag>
                </template>
            </el-descriptions-item>
            <el-descriptions-item label="匹配状态">
                <template>
                    <el-tag
                    :type="data.normalStatus === 0 ? 'primary' : data.normalStatus===1?'danger':'success'"
                    disable-transitions>{{data.normalStatus === 0 ? '待匹配' : data.normalStatus===1?'匹配失败':'匹配成功'}}</el-tag>
                </template>
            </el-descriptions-item>
            
            
            <el-descriptions-item label="渠道索引">{{data.locationIndex}}</el-descriptions-item>
            <el-descriptions-item label="通知地址">{{data.notifyUrl}}</el-descriptions-item>
            <el-descriptions-item label="创建时间">{{data.statTime}}</el-descriptions-item>
            <el-descriptions-item label="结束时间">{{data.endTime}}</el-descriptions-item>

        
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