<template>
<!-- 这是一个封装弹窗组件 -->
 <div id="Dialog">
        <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <span>{{text}}</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="openNo()">取 消</el-button>
            <el-button type="primary" @click="openYes">确 定</el-button>
        </span>
        </el-dialog>
        </div>

</template>

<script>
  export default {
    name:"Dialog",
    data () {
      return {
        dialogVisible : false,
        text:'',
        openNo:function () {
          
        },
        openYes:function(){

        }
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      showDialogVisible(text,openNo,openYes){
        this.dialogVisible = true;
        this.text = text;
        this.openYes = openYes;
        this.openNo = openNo;
      },
      closeDialogVisible(){
        this.dialogVisible = false
      },
      open(text,type) {
        switch(type){
          case '':this.$message(text);break;
          case 'success':
            this.$message({
                message: text,
                type: 'success'
              });
              break;
          case 'warning':
                this.$message({
                    message: text,
                    type: 'warning'
                  });
                break;
          case 'erro':this.$message.error(text);break;
        }
      }
    }
  };
</script>