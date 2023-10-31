<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      早安,祝你开心每一天！
    </div>

    <div style="display: flex">
      <el-card style="width: 50%;margin-right: 10px">
        <div slot="header" class="clearfix">
          <span>公告</span>
        </div>
        <div>
          这是主体区域
          <div style="margin-top: 20px">
            <div style="margin: 10px 0"><strong>主题色</strong></div>
            <el-button type="primary">按钮</el-button>
            <el-button type="success">按钮</el-button>
            <el-button type="warning">按钮</el-button>
            <el-button type="danger">按钮</el-button>
            <el-button type="info">按钮</el-button>


            <div class="el-dropdown-link" style="font-size: xx-large">
                    余额: ¥{{ businessBalance }}
                </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "BusinessHome",
  data() {
    return {
      businessBalance: '',
      storeId:''
    }
  },
  created() {

  },
  methods: {},
  mounted() {
    this.storeId = localStorage.getItem('StoreId')
    axios.get('http://172.18.39.191:9090/consumer/selectBusinessBalance', {
      params: {
        storeId: this.storeId
      }
    }).then(res => {
      this.businessBalance = res.data.data
      console.log(res.data.data)
    })
  }
}
</script>

<style scoped>
.el-dropdown-link{
  size: B4;
  margin-top: 60px;
}
</style>