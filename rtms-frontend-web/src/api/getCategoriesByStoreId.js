import axios from "axios";
import {ref} from "vue";

const getCategoriesByStoreId = (storeId) => {
    const categories = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_categories_by_store_id/" + storeId)
            categories.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {categories, load}
}

export default getCategoriesByStoreId