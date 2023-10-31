<template>
  <div class="products-container">
    <product-card v-for="product in products" :key="product.id" :product="product"></product-card>
  </div>
</template>

<script>
import ProductCard from "@/views/ProductCard.vue";

export default {
  components: {
    ProductCard
  },
  name: "Product",
  data() {
    return {
      products: [
        {id: 1, name: 'Product 1', price: '1000.00'},
        {id: 2, name: 'Product 2', price: '1500.00'},
        {id: 3, name: 'Product 3', price: '2000.00'},
        {id: 4, name: 'Product 4', price: '5000.00'},
        {id: 5, name: 'Product 5', price: '4588.00'},
        {id: 6, name: 'Product 7', price: '2800.00'},
        {id: 7, name: 'Product 8', price: '1700.00'},
        {id: 8, name: 'Product 9', price: '654.00'},
        {id: 9, name: 'Product 10', price: '999.00'},
        // ... add more mock products as required
      ]
    };
  },
}
</script>

<style scoped>

.products-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.product-card {
  margin: 10px;
}
</style>