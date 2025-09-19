<script setup>
import ProductCard from '@/components/ProductCard.vue'

function variantUpdated(color) {
    console.log(`Variant color updated to ${color}`)
}
</script>

<template>
  <main>
      <main>
          <h1>Products:</h1>
          <ProductCard brand="Vue 1" :premium="true" @variantUpdated="variantUpdated" />
          <ProductCard brand="Vue 2" :premium="false" />
      </main>
  </main>
</template>

<style scoped>

</style>
