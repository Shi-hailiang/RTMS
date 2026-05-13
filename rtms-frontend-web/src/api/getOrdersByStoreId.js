import axios from "axios";
import {ref} from "vue";

const getOrdersByStoreId = (storeId) => {
    const orders = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_orders_by_store_id/" + storeId)
            orders.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {orders, load}
}

export default getOrdersByStoreId