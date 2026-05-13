import axios from "axios";
import {ref} from "vue";

const getHomeDataByStoreId = (storeId) => {
    const homeData = ref({})

    const load = async () => {
        try {
            const {data} = await axios("/api/get_home_data_by_store_id/" + storeId)
            homeData.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {homeData, load}
}

export default getHomeDataByStoreId