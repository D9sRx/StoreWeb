<template>
  <div style="height: 100vh; display: flex; align-items: center;justify-content: center; background-color: teal">
    <div style="display: flex; background-color: white; width: 60%; border-radius: 10px ; overflow: hidden">
      <div style="flex:1">
        <div style="font-size: 30px; font-weight: bold;text-align: center; margin-top: 20px">
          吉吉国商城
        </div>
        <div>
          <img src="@/assets/register.png" alt="" style="width: 100%;height: 100%">
        </div>
      </div>
      <div style="flex:1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">

          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 30px">欢迎注册</div>

          <el-form-item prop="managerName">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.managerName"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>

          <el-form-item prop="confirmPass">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>

          <el-radio-group v-model="role" style="margin-bottom: 20px; display: flex; justify-content: center;">
            <el-radio label="user">用户</el-radio>
            <el-radio label="business">商家</el-radio>
          </el-radio-group>

          <el-form-item>
            <el-button size="medium" type="primary" style="width:100%" @click="register">注 册</el-button>
          </el-form-item>

          <div style="display: flex">
            <div style="flex:1">
              <span style="color: lightseagreen;cursor: pointer" @click="$router.push('/login')">返回登录</span>
            </div>
          </div>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script>



export default {
  name: "Register",

  data(){
    const validatePassword = (rule, confirmPass, callback) => {
          if (confirmPass === '') {
            callback(new Error('请确认密码'))
          } else if (confirmPass !== this.user.password) {
            callback(new Error('密码不相同'))
          } else {
            callback()
          }
        }
    return{
      role:'user',//默认为user
      user:{
        managerName:'',
        password:'',
        confirmPass:'',

      },
      rules: {
        managerName: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPass: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          {validator: validatePassword, trigger:'blur'}
        ],
        // code:[
        //   {validator: validatePassword, trigger:'blur'}
        // ]
      }
    }
  },
  created() {

  },
  methods:{

    register(){
      this.$refs['registerRef'].validate((valid) => {
        if(valid){
          //验证通过，发送请求
          if (this.role==="user"){
            const config = {
              params: {
                managerName: this.user.managerName,
                password: this.user.password,
                role: this.role
              },
            };
            const config2 = {
              params: {
                nickName: this.user .managerName,
                password: this.user .password
              },
            };
            this.$request.get(  '/manager/register',config).then(res1 =>{
              this.$request.get(  '/consumer/register',config2).then(res2 =>{
                if(res1.code===200){
                  this.$router.push('/login')
                  this.$message.success( '注册成功')
                }else{
                  this.$message.error(res1.data)
                }
              })
            })
          }else if (this.role==="business"){
            const config = {
              params: {
                managerName: this.user.managerName,
                password: this.user.password,
                role: this.role
              },
            };
            const config2 = {
              params: {
                nickName: this.user .managerName,
                password: this.user .password
              },
            };
            this.$request.get(  '/manager/register',config).then(res1 =>{
              this.$request.get(  '/business/register',config2).then(res2 =>{
                if(res1.code===200 && res2.code === 200){
                  this.$router.push('/login')
                  this.$message.success( '注册成功')
                }else{
                  this.$message.error(res1.data)
                }
              })
            })
          }
        }else{
          this.$message.error("密码不一致")

        }
      })

    }

  }
}
</script>

<style scoped>


</style>