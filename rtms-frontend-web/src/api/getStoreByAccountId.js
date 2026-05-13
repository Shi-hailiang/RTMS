import axios from "axios";
import {ref} from "vue";

const getStoreByAccountId = (accountId) => {
    const store = ref({})

    const load = async () => {
        try {
            const {data} = await axios("/api/select_store_by_account_id/" + accountId)
            store.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {store, load}
}

export default getStoreByAccountId
