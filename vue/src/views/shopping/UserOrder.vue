<template>
  <div>
    <el-card style="width: 100%">
      <div slot="header" class="clearfix">
        <span>用户订单</span>
      </div>
      <div>
        <el-table :data="UserOrder">
          <el-table-column label="用户" prop="consumerId"></el-table-column>
          <el-table-column label="总花费" prop="orderPrice"></el-table-column>
          <el-table-column label="商品数量" prop="goodsNum"></el-table-column>
          <el-table-column label="商家id" prop="storeId"></el-table-column>
          <el-table-column label="支付时间" prop="payTime"></el-table-column>

        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "UserOrder",
  data(){
    return{
      UserOrder:[],
      managerName: ''
    }
  },
  mounted() {
    this.managerName = localStorage.getItem('managerName');
    axios.get('http://172.18.39.191:9090/consumer/selectMyOrders',{
      params: {
        consumerId: this.managerName
      }
    }).then(res => {
      console.log(res.data)
      this.UserOrder = res.data.data;
    })
  },
  methods:{

  }
}
</script>

<style scoped>

</style>