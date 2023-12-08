<template>
  <div>
       <el-switch
        v-model="tabbarStyle"
        active-text="充电页面"
        inactive-text="话费页面"
        :change="switchChange()"
        >
      </el-switch>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        tabbarStyle:true
      };
    },
    created(){
       this.$http({
              url: this.$http.adornUrl('/text/getTabBarStyle'),
              method: 'post'
            }).then(({data}) => {
              this.tabbarStyle = data === 1?true:false;
            })
    },
    methods:{
      switchChange(){
          this.$http({
              url: this.$http.adornUrl('/cd/setTabBarStyle'),
              method: 'post',
              data: this.tabbarStyle?1:0
            })
      }
    }
  };
</script>

<style lang="scss">
.like {
    cursor: pointer;
    font-size: 25px;
    display: inline-block;
  }
</style>

