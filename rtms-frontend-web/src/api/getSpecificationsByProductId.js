import axios from "axios";
import {ref} from "vue";

const getSpecificationsByProductId = (productId) => {
    const specifications = ref([])

    const load = async () => {
        try {
            const {data} = await axios("/api/select_specification_by_product_id/" + productId)
            specifications.value = data.data
        } catch (error) {
            console.log(error)
        }
    }

    return {specifications, load}
}

export default getSpecificationsByProductId