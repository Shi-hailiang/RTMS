import axios from "axios";
import {ref} from "vue";

const getOrderProductsByOrderId = (orderId) => {
    const orderProducts = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_order_products_by_order_id/" + orderId)
            orderProducts.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {orderProducts, load}
}

export default getOrderProductsByOrderId