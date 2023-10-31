<template>
<div style="height: 100vh; display: flex; align-items: center;justify-content: center; background-color: cornflowerblue">
  <div style="display: flex; background-color: white; width: 60%; border-radius: 10px ; overflow: hidden">
    <div style="flex:1">
      <div style="font-size: 30px; font-weight: bold;text-align: center; margin-top: 20px">
        吉吉国商城
      </div>
      <div>
        <img src="@/assets/login02.png" alt="" style="width: 100%;height: 100%">
      </div>
    </div>
    <div style="flex:1; display: flex; align-items: center; justify-content: center">
      <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">

        <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 30px">欢迎登录</div>

        <el-form-item prop="managerName">
          <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.managerName"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码" v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item prop="code">
          <div style="display: flex">
            <el-input placeholder="请输入验证码" prefix-icon="el-icon-position" size="medium" style="flex: 1" v-model="user.code">

            </el-input>

            <div style="flex:1;height: 36px">
              <valid-code @update:value="getCode" />
            </div>

          </div>
        </el-form-item>

        <el-form-item>
          <el-button size="medium" type="primary" style="width:100%" @click="Login">登 录</el-button>
        </el-form-item>

        <div style="display: flex">
          <div style="flex:1">
            <span style="color: lightseagreen; cursor: pointer" @click="$router.push('/register')">立即注册</span>
          </div>
<!--          <div style="flex: 1; text-align: right">-->
<!--            忘记密码-->
<!--          </div>-->
        </div>
      </el-form>
    </div>
  </div>
</div>

</template>

<script>
import ValidCode from "@/components/ValidCode.vue";

export default {
  name: "Login",
  components:{
    ValidCode
  },
  data(){
    // 验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }

    return{
      code:'',//验证码传过来的code
      user:{
        code:'',//用户输入的code验证码
        managerName:'',
        password:''
      },
      rules: {
        managerName: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        code:[
          {validator: validateCode, trigger:'blur'}
        ]
      }
    }
  },
  created() {

  },
  methods:{

    getCode(code){
      this.code = code.toLowerCase()
    },
    Login(){
      this.$refs['loginRef'].validate((valid) => {
        if(valid){
          //验证通过，发送请求
          const config = {
            params: {
              managerName: this.user.managerName,
              password: this.user.password
            }
          };
          this.$request.get('/manager/login',config).then(res =>{
            if(res.code ===200 && res.data.role ==='manager'){
              this.$router.push('/manager')
              this.$message.success('登录成功')
              localStorage.setItem("honey-user",JSON.stringify(res.data))
            }else if(res.code ===200 && res.data.role ==='user'){
              this.$router.push('/shopping')
              this.$message.success('登录成功')
              localStorage.setItem('managerName', res.data.managerName);
            }else if(res.code ===200 && res.data.role === 'business'){
              this.$router.push('/business_manager')
              this.$message.success('登录成功')
              localStorage.setItem('StoreId', res.data.storeId);
            }else(
                this.$message.error(res.msg)
            )
          })
        }
      })

    }

  }
}
</script>

<style scoped>


</style>