<template>
  <div>
    <el-card style="width: 50%">
      <div slot="header" class="clearfix">
        <span>商家数据</span>
      </div>
      <div>
        <el-table :data="business_users">
          <el-table-column label="店铺id" prop="storeId"></el-table-column>
          <el-table-column label="手机号" prop="mobile"></el-table-column>
          <el-table-column label="姓名" prop="nickName"></el-table-column>
          <el-table-column label="资金" prop="balance"></el-table-column>

        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Business",
  data(){
    return{
      business_users:[]
    }
  },
  mounted() {
    axios.get('http://172.18.39.191:9090/manager/selectBusinessByPagination',{
      params: {
        page: '',
        pageSize: '',
      }
    }).then(res => {
      console.log(res.data)
      this.business_users = res.data.data.rows;
    })
  },
  methods:{

  }
}
</script>

<style scoped>

</style>