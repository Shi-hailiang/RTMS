import axios from "axios";
import {ref} from "vue";

const storeAcceptOrderById = (orderId) => {
    const load = async () => {
        try {
            await axios("/api/store_accept_order_by_id/" + orderId)
        } catch (error) {
            console.log(error)
        }
    }
    return {load}
}

export default storeAcceptOrderById