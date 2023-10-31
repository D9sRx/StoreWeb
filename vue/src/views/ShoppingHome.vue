<template>
  <el-container direction="vertical" style="padding: 50px;">
    <el-header>
      <div class="search-container" style="display: flex; align-items: center; justify-content: center;height:100px;">
        <el-button class="el-button-home" type="text" icon="el-icon-s-home" size="medium" circle @click="goHome" style="margin-right: 20px;"></el-button>
        <el-button class="cart-button" type="text" icon="el-icon-s-goods" size="medium" circle @click="goToUserOrders" style="margin-right: 20px;"></el-button>
        <el-input
            v-model="searchQuery"
            placeholder="请输入关键词..."
            size="medium"
            clearable
            prefix-icon="el-icon-search"
            style="width: 600px;"
            @input="fetchSuggestions">
        </el-input>

        <!-- 添加余额的下拉菜单 -->
        <el-dropdown style="margin-left: 20px;">
                <span class="el-dropdown-link">
                    余额: ¥{{ userBalance }} <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="rechargeBalance">充值</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dialog :visible.sync="fromVisible">
          <el-form :model="form" ref="formRef">
            <el-form-item  label="输入你要充值的金额" label-width="150px">
              <el-input v-model="form.balance" style="width: 500px"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="fromVisible = false">取消</el-button>
            <el-button type="primary" @click="addBalance">确认充值</el-button>
          </div>
        </el-dialog>

      </div>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>
<script>
import ProductCard from './ProductCard.vue';
import axios from "axios";

export default {
  components: {
    ProductCard
  },
  data() {
    return {
      fromVisible: false,
      searchQuery: '',
      userBalance:'',
      nickName:'',
      form:{
        consumerId:'',
        balance:''
      },
      suggestions: []
    };
  },
  methods:{
    goToUserOrders() {
      if(this.$route.path !== '/shopping/user_order'){
        this.$router.push('/shopping/user_order');
      }
    },
    goHome(){
      if(this.$route.path !== '/shopping/')
      this.$router.push('/shopping/');
    },
    rechargeBalance(){
      this.fromVisible = true
    },
    addBalance(){
      axios.get('http://172.18.39.191:9090/consumer/addBalance',{
        params: {
          consumerId: this.nickName,
          balance: this.form.balance
        }
      }).then(res => {
        console.log(res.data)
        this.fromVisible=false;
        axios.get('http://172.18.39.191:9090/consumer/selectMyBalance',{
          params: {
            nickName:this.nickName
          }
        }).then(res => {
          console.log(res.data)
          this.userBalance = res.data.data;
        })
      })
    }

  },
  mounted() {
    this.nickName = localStorage.getItem('managerName')
    this.form.consumerId = localStorage.getItem('managerName')
    axios.get('http://172.18.39.191:9090/consumer/selectMyBalance',{
      params: {
        nickName:this.nickName
      }
    }).then(res => {
      console.log(res.data)
      this.userBalance = res.data.data;
    })

  }
}
</script>

<style scoped>
.products-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.custom-search-input .el-input__inner {
  height: 50px; /* 调整搜索框的高度 */
  font-size: 16px; /* 调整文字大小 */
  box-shadow: 0 4px 8px rgba(0,0,0,0.1); /* 添加阴影 */
  border-radius: 25px; /* 维持圆滑边框 */
}

.custom-search-input .el-input__icon {
  line-height: 50px; /* 使图标与搜索框对齐 */
}

.product-card {
  margin: 10px;
}
.cart-button {
  width: 100px;        /* 调整按钮宽度 */
  height: 100px;       /* 调整按钮高度 */
  margin-right: 10px; /* 与搜索框之间的间距 */
}
.el-button--medium{
  font-size: 40px;
}

.el-dropdown-link{
  font-size: 20px;
}
</style>
