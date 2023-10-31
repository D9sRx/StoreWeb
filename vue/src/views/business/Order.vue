<template>
  <div>
    <el-card style="width: 90%">
      <div slot="header" class="clearfix">
        <span>管理用户订单</span>
      </div>
      <div>
        <el-table :data="orders" >
          <el-table-column label="用户id" prop="consumerId"></el-table-column>
          <el-table-column label="订单id" prop="orderId"></el-table-column>
          <el-table-column label="订单价格" prop="orderPrice"></el-table-column>
          <el-table-column label="商品数量" prop="goodsNum"></el-table-column>
          <el-table-column label="商家id" prop="storeId"></el-table-column>
          <el-table-column label="支付时间" prop="payTime"></el-table-column>
          <el-table-column label="发货状态" prop="statusText"></el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="primary" icon="el-icon-edit" @click="handleRemove(scope.row.orderId)">删除</el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleOrder(scope.row.orderId)">发货</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Order",
  data(){
    return{
      orders:[],
      orderId: ''
    }
  },
  mounted() {
    // this.storeId = localStorage.getItem('StoreId');
    axios.get('http://172.18.39.191:9090/business/selectOrderInfo',{
      params:{
        statusText: '',
        createTimeStart: '',
        createTimeEnd: '',
        storeId: localStorage.getItem('StoreId')
      }
    }).then(res => {

      console.log(res.data)
      this.orders = res.data.data;
    })
  },
  methods:{
    handleRemove(orderId){
      axios.get('http://172.18.39.191:9090/business/removeOrder',{
        params:{
          orderId: orderId
        }
      }).then(res => {
        this.$message.success("删除成功")
        location.reload();
      })
    },
    handleOrder(orderId){
      axios.get('http://172.18.39.191:9090/business/sendOrder',{
        params:{
          orderId: orderId
        }
      }).then(res => {
        this.$message.success("发货成功")
        location.reload();
      })
    }
  }
}

</script>

<style scoped>

</style>