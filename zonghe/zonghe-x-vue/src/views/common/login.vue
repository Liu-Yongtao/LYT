<template>
  <div class="login-body">
      <div class="login-main">
          <div class="login-top-bj">
              <h3 class="login-title">管理员登录</h3>
          </div>
          <span class="loginMessage">{{loginMessage}}</span>
          <div class="login">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
            <el-form-item prop="userName">
              <el-input v-model="dataForm.userName" placeholder="帐号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
            </el-form-item>
            <!-- 验证码功能 -->
            <!-- <el-form-item prop="captcha">
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-input v-model="dataForm.captcha" placeholder="验证码">
                  </el-input>
                </el-col>
                <el-col :span="10" class="login-captcha">
                  <img :src="captchaPath" @click="getCaptcha()" alt="">
                </el-col>
              </el-row>
            </el-form-item> -->
            <el-form-item>
              <el-button class="login-btn-submit" type="primary" @click="dataFormSubmit()">登录</el-button>
            </el-form-item>
          </el-form>
          </div>
        </div>
  </div>
</template>

<script>
  import { getUUID } from '@/utils'
  export default {
    data () {
      return {
        dataForm: {
          userName: '',
          password: '',
          uuid: '',
          captcha: ''
        },
        loginMessage:'',
        dataRule: {
          userName: [
            { required: true, message: '帐号不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          captcha: [
            { required: true, message: '验证码不能为空', trigger: 'blur' }
          ]
        },
        captchaPath: ''
      }
    },
    created () {
      this.getCaptcha()
    },
    methods: {
      // 提交表单
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl('/sys/login'),
              method: 'post',
              data: this.$http.adornData({
                'username': this.dataForm.userName,
                'password': this.dataForm.password,
                // 'uuid': this.dataForm.uuid,
                // 'captcha': this.dataForm.captcha
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$cookie.set('token', data.token)
                this.$router.replace({ name: 'home' })
              } else {
                // this.getCaptcha()
                this.loginMessage = data.msg;
                this.$message.error(data.msg)
              }
            }).catch(({error})=>{
                  this.$message.error("登录失败，服务器异常")
                  this.loginMessage = "登录失败，服务器异常...";
              })
          }
        })
      },
      // 获取验证码
      getCaptcha () {
        this.dataForm.uuid = getUUID()
        this.captchaPath = this.$http.adornUrl(`/captcha.jpg?uuid=${this.dataForm.uuid}`)
      }
    }
  }
</script>

<style lang="scss">
  .login-body{
    width: 100%;
    height: 100%;
    background-image: url(~@/assets/img/login-background.jpg);
    background-size: cover;
    display: flex;
    justify-content: center;
    align-content:center;
    position: fixed;
  }
  .login-body .login-main{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 400px;
    height: 600px;
    max-width: 94%;
    max-height: 70%;
    background: white;
    border-radius: 10px;

  }
  .login-top-bj{
    width: calc(100%);
    height: 25%;
    position: absolute;
    top: 0;
    left: 0;
    background-image: url(~@/assets/img/login-top.jpg);
    background-size: cover;
    border-radius: 10px 10px 0 0 ;
    display: flex;
    flex-direction: column;
    justify-content: center;
    
  }
  .login{
    width: 100%;
    height: 70%;
    position: absolute;
    top: 30%;
    padding: 30px;
    border-radius: 0 0 10px 10px;

  }
   .login-top-bj .login-title{
    font-size: 25px;
    color: white;
    text-align: center;

  }
   .login .login-btn-submit{
    position: absolute;
    left: 50%;
    margin-top: 5%;
    transform: translate(-50%,0);
  }
  .login .loginMessage{
    font-size: 14px;
    color: red;
    display: inline-block;
    margin-bottom: 5px;
  }
  .login-captcha img{
    width: 110px;
    height: 30px;
  }
</style>
