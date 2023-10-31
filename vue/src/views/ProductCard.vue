<template>
  <el-card class="product-card" @click.native="goToProductDetails">
    <div class="product-image">
      <img :src="getImageUrl(product.id)" alt="Product Image" />
    </div>
    <div style="text-align: center; font-size: xx-large; font-weight: bold">
      <div>{{ product.name }}</div>
      <div>{{ product.price +'¥' }}</div>
    </div>
  </el-card>
</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      required: true
    }
  },
  methods: {
    getImageUrl(productId) {
      return require(`@/assets/product${productId}.jpg`);
    },
    goToProductDetails() {
      // Assuming you have Vue Router set up
      this.$router.push({ name: 'ProductDetails', params: { id: this.product.id } });
    }
  }
}
</script>

<style scoped>
.product-card {
  width: calc(20% - 20px);
  padding-top: calc(20% - 20px);
  position: relative;
}

.product-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 70%;
  height: 70%;
  margin: auto;
  right: 0;
  bottom: 0;
}

.product-image img {
  max-width: 100%;
  max-height: 100%;
}
</style>
