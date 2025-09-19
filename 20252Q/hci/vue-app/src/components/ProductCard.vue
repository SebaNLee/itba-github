<template>
    <div class="product">
        <div class="product-image">
            <img :src="image" alt="A pair of socks"/>
        </div>
        <div class="product-detail">
            <h2>{{ title }}</h2>
            <p v-show="stock > 10">In Stock</p>
            <p v-show="stock <= 10 && stock > 0">Almost Sold Out!</p>
            <p v-show="stock === 0">Out of Stock</p>

            <p>Shipping: {{ shipping }}</p>

            <ul>
                <li v-for="detail in details" :key="detail">{{ detail }}</li>
            </ul>

            <div class="colors">
                <div v-for="(variant, index) in variants"
                     :key="variant.color"
                     class="color-box"
                     :class="{ 'selected-box': !isVariantSelected(index) }"
                     :style="{ backgroundColor: variant.color }"
                     @mouseover="updateVariant(index)">
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps(['brand', 'premium'])
const emits = defineEmits(['variantUpdated'])

const currentVariantIndex = ref(0)
const details = ref([
    '80% cotton', '20% polyester', 'Gender-neutral'
    ])
const variants = ref([
    { id: 23, color: 'green', image: 'socks-green.png', stock: 100 },
    { id: 25, color: 'darkblue', image: 'socks-blue.png', stock: 8 }
])

const title = computed(() => {
    return `${props.brand} - Socks`
})
const image = computed(() => {
    return currentVariant.value.image
})
const stock = computed(() => {
    return currentVariant.value.stock
})
const shipping = computed(() => {
    return (props.premium) ? "Free" : "2.99"
})
const currentVariant = computed(() => {
    return variants.value[currentVariantIndex.value]
})

function updateVariant(index) {
    if (index !== currentVariantIndex.value) {
        currentVariantIndex.value = index;
        emits('variantUpdated', currentVariant.value.color)
    }
}
function isVariantSelected(index) {
    return currentVariantIndex.value === index
}
</script>

<style scoped>
img {
    height: 300px;
}

.product {
    width: 600px;
    display: flex;
    margin: 10px 0;
    justify-content: center;
    align-items: center;
    border-radius: 25px;
    border: 2px solid;
}

.product-image {
    padding-left: 20px;
}

.product-image, .product-detail {
    width: 320px;
}

.colors {
    display: flex;
}
.color-box {
    height: 28px;
    width: 28px;
    margin: 3px;
    border: 3px solid transparent;
}
.selected-box {
    border-color: white;
}
</style>