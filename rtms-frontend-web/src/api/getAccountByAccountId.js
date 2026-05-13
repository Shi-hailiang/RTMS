import axios from "axios";
import {ref} from "vue";

const getAccountByAccountId = (accountId) => {
    const account = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_account_by_account_id/" + accountId)
            account.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {account, load}
}

export default getAccountByAccountId