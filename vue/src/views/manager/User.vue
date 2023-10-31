<template>
  <div>
    <el-card style="width: 50%">
      <div slot="header" class="clearfix">
        <span>用户数据</span>
      </div>
      <div>
        <el-table :data="users">
          <el-table-column label="id" prop="consumerId"></el-table-column>
          <el-table-column label="性别" prop="gender"></el-table-column>
          <el-table-column label="姓名" prop="nickName"></el-table-column>
          <el-table-column label="手机号" prop="mobile"></el-table-column>

        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "User",
  data(){
    return{
      users:[]
    }
  },
  mounted() {
    axios.get('http://172.18.39.191:9090/manager/selectConsumerByPagination',{
      params: {
        page: '',
        pageSize: '',
        mobile: '',
        gender: ''
      }
    }).then(res => {
      console.log(res.data)
      this.users = res.data.data.rows;
    })
  },
  methods:{

  }
}
</script>

<style scoped>

</style>