import axios from "axios";
import {ref} from "vue";

const getProductsByStoreId = (storeId) => {
    const products = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_products_by_store_id/" + storeId)
            products.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {products, load}
}

export default getProductsByStoreId