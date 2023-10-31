<template>

  <div>
    <div>
      <el-input style="width: 200px;margin-right: 10px;" placeholder="查询商品名称" v-model="goodsName"></el-input>
      <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
      <el-button type="info" @click="resetForm">重置</el-button>
    </div>
    <div style="margin: 30px 0">
      <el-button type="primary" plain @click="handleAdd" style="margin-right: 20px" >上架商品</el-button>
      <el-dialog :visible.sync="fromVisible">
        <el-form :model="form" ref="formRef">
          <el-form-item  label="商品id" label-width="120px">
            <el-input v-model="form.goodsId" style=""></el-input>
          </el-form-item>
          <el-form-item label="商品名称" label-width="120px">
            <el-input v-model="form.goodsName" style=""></el-input>
          </el-form-item>
          <el-form-item label="商品价格" label-width="120px">
            <el-input v-model="form.goodsSellPrice"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" label-width="120px">
            <el-input v-model="form.rqSkuInfos[0].currentStorage" ></el-input>
          </el-form-item>
          <!--          <el-form-item label="商品规格" label-width="120px">-->
          <!--            <el-input v-model="form.rqSkuInfos" ></el-input>-->
          <!--          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="fromVisible = false">取消</el-button>
          <el-button type="primary" @click="save">上架</el-button>
        </div>
      </el-dialog>
      <el-button type="danger" plain >批量删除</el-button>
    </div>
    <el-card style="width: 60%">
      <div slot="header" class="clearfix">
        <span>管理商品信息</span>
      </div>
      <div>
        <el-table :data="store">
          <el-table-column label="id" prop="goodsId"></el-table-column>
          <el-table-column label="商品名称" prop="goodsName"></el-table-column>
          <el-table-column label="商品售价" prop="goodsSellPrice"></el-table-column>
          <el-table-column label="当前库存" prop="currentStorage"></el-table-column>
          <el-table-column label="操作">
            <template v-slot="scope">
              <el-button type="primary" icon="el-icon-edit">编辑</el-button>
              <el-button type="primary" icon="el-icon-delete" @click="handleRemove(scope.row.goodsId)">下架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import ValidCode from "@/components/ValidCode";

export default {
  name: "Store",
  data(){
    return{
      fromVisible: false,

      store:[],
      storeIdd: '',
      goodsName : '',
      form: {
        goodsName: '',
        goodsSellPrice: '',
        rqSkuInfos: [
          {
            currentStorage: '',
          }
        ],
        goodsId: '',
        storeId: '',
      },
      components: {
        ValidCode
      }
    }
  },
  mounted() {
    this.storeIdd = localStorage.getItem('StoreId');
    this.form.storeId = this.storeIdd;
    axios.get('http://172.18.39.191:9090/business/selectShelvesGoodsInfo',{
      params:{
        storeId: localStorage.getItem('StoreId')
      }
    }).then(res => {
      console.log(this.storeIdd)
      console.log(res.data)
      this.store = res.data.data;
    })
  },


  methods:{

    resetForm(){
      this.storeName=''
    },
    search() {
      axios.get('http://172.18.39.191:9090/business/selectShelvesGoodsInfo', {
        params: {
          storeId: localStorage.getItem('StoreId'),
          page: '',
          pageSize: '',
          goodsName: this.goodsName
        }
      })
          .then(res => {
            this.$message.success("查询成功")
            console.log(res.data);
            this.store = res.data.data;
          })
          .catch(error => {
            console.error(error);
          });
    },

    handleAdd(){
      this.fromVisible = true
    },
    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: '/business/shelvesGoods',
            method: 'POST',
            data: this.form
          }).then(res => {
            if (res.code === 200) {  // 表示成功保存
              this.$message.success('上架成功')
              console.log(this.form)
              location.reload();
              this.fromVisible = false
            } else {
              this.$message.error(res.error())  // 弹出错误的信息
            }
          })
        }
      })
    },
    remove(storeId,goodsId) {
      this.$confirm('您确认删除吗？', '确认删除', {type: "warning"}).then(response => {
        this.$request.delete(`/business/offGoods`,{
          params: {
            storeId: localStorage.getItem('StoreId'),
            goodsId: goodsId
          }
        }).then(res => {
          if (res.code === 200) {   // 表示操作成功
            this.$message.success('操作成功')
            location.reload();
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => {})
    },
    handleRemove(goodsId) {
      this.remove(10, goodsId);
    },
  }
}
</script>

<style scoped>
.el-form-item .el-input {
  width: 200px;
}
</style>