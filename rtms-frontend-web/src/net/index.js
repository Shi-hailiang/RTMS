import axios from "axios";
import { message } from 'ant-design-vue';
const [messageApi] = message.useMessage();

const defaultError = () => {
    messageApi.error('发生了一些错误，请联系管理员');
}

const defaultFailure = (message) => {
    messageApi.warning(message);
}

function post(url, data, success, failure = defaultFailure, error = defaultError) {
    axios.post(url, data, {
        headers: {
            'Content-Type': 'application/json'
        },
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.message, data.status)
        } else {
            failure(data.message, data.status)
        }
    }).catch(error)
}

function get(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        withCredentials: true
    }).then(({data}) => {
        if (data.code === 200) {
            success(data.message, data.status)
        } else {
            failure(data.message, data.status)
        }
    }).catch(error)
}

export { get, post }