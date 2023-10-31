<template>

  <div class="product-details-container" style="display:flex; align-items: center;justify-content: center">
    <!-- 图片放在左边 -->
    <div class="product-image" style="margin-right: 300px">
      <img :src="getImageUrl(product.id)" alt="Product Image"/>
    </div>

    <!-- 详情放在右边 -->
    <div class="product-details" style="align-items: center;justify-content: center;width: 50%">
      <div class="product-name" style="margin-top: 120px; font-size: 500%">{{ product.name }}</div>
      <div class="product-price" style="margin-top: 200px; font-size: 500% ">{{ product.price + '¥' }}</div>

      <!-- 下面一个下单的按钮 -->
      <el-input-number v-model="orderQuantity" :min="1" label="购买数量:" style="margin-bottom: 20px;"></el-input-number>
      <el-button type="primary" className="order-button" style="font-size: xx-large;margin-left:35px;width: 200px;height: 100px;border-radius: 15px" @click="submitOrder">下单</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      product: {},
      orderQuantity:"",
      nickName:'',
      storeId:'',
    };
  },
  mounted() {
    const productId = Number(this.$route.params.id);
    const mockProducts = [
      { id: 1, name: 'Product 1', price: 1000.00 },
      { id: 2, name: 'Product 2', price: 1500.00 },
      { id: 3, name: 'Product 3', price: 2000.00 },
      { id: 4, name: 'Product 4', price: 5000.00 },
      { id: 5, name: 'Product 5', price: 4588.00 },
      { id: 6, name: 'Product 7', price: 2800.00 },
      { id: 7, name: 'Product 8', price: 1700.00 },
      { id: 8, name: 'Product 9', price: 654.00 },
      { id: 9, name: 'Product 10', price: 999.00 },
    ];

    this.product = mockProducts.find(p => p.id === productId) || {};
  },
  methods: {
    getImageUrl(productId) {
      // 如果图片在assets目录
      return require(`@/assets/product${productId}.jpg`);
    },
    async submitOrder() {
      this.nickName = localStorage.getItem('managerName');

      try {
        // 等待这个请求完成并获取storeId
        const storeIdResponse = await axios.get('http://172.18.39.191:9090/consumer/selectStoreIdByGoodsId', {
          params: {
            goodsId: 1000 + this.product.id,
          }
        });

        this.storeId = storeIdResponse.data.data;

        // 既然storeId已经获取，现在你可以提交订单了
        const orderResponse = await axios.get('http://172.18.39.191:9090/consumer/addConsumerOrders', {
          params: {
            consumerId: this.nickName,
            goodsSellPrice: this.product.price,
            goodsId: 1000 + this.product.id,
            goodsNum: this.orderQuantity,
            storeId: this.storeId,
          }
        });
        if (orderResponse.data.code === 200) {
          this.$message.success('订单提交成功！');
          await axios.get('http://172.18.39.191:9090/consumer/addBusinessBalance', {
            params: {
              goodsSellPrice: this.product.price,
              goodsNum: this.orderQuantity,
              storeId: this.storeId,
            }
          })
        } else {
          this.$message.error(orderResponse.data.msg);
        }
      } catch (error) {
        this.$message.error('发生错误，请稍后重试！');
      }
    }

  }
}
</script>

<style scoped>
.product-details-container {
  display: flex;
  align-items: center;
  width: 100%;
}

.product-image {
  margin-right: 30px;
  width: 70%;
  height: 70%;
}

.product-name {
  font-size: 24px;
  margin-bottom: 20px;
}

.product-price {
  font-size: 20px;
  color: #FF5733 !important;
  margin-bottom: 30px;
}

.order-button {
  display: block;
}
.product-image img {
  max-width: 100%;
  max-height: 100%;
}
.product-details-container {
  display: flex;
  align-items: center;
  padding: 20px;
  border: 1px solid #ffffff; /* 添加边框 */
  /*box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); !* 添加阴影 *!*/
  background-color: #ffffff; /* 设置背景颜色，使阴影更加明显 */

}

.product-image {
  margin-right: 30px;
  width: 80%;
  /*height: 100%;  !* 固定高度 *!*/
  border: 1px solid #e4e4e4;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  overflow: hidden;  /* 隐藏超出部分 */
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-name {
  font-size: 24px;
  margin-bottom: 20px;
  border-bottom: 1px solid #e4e4e4; /* 为商品名称添加下边框 */
  padding-bottom: 10px; /* 商品名称与其下边框之间的间距 */
}

.product-price {
  font-size: 20px;
  color: #FF5733;
  margin-bottom: 30px;
}

.order-button {
  display: block;
  border: 1px solid #e4e4e4; /* 按钮边框 */
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); /* 按钮阴影 */
}

.product-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
}

</style>
