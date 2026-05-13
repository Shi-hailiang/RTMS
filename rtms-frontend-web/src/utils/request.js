import axios from "axios"

async function post(url, data) {
    try {
        const response = await axios.post(url, data, {
            headers: {
                "Content-Type": "application/json",
            },
            withCredentials: true,
        })
        return response.data
    } catch (error) {
        // 进行错误处理，可以根据需要进行日志记录或其他操作
        console.error("Error while making POST request:", error)
        throw error // 可以选择将错误抛出以在调用函数的地方处理
    }
}

async function get(url) {
    try {
        const response = await axios.get(url, {
            withCredentials: true,
        })
        return response.data
    } catch (error) {
        // 进行错误处理，可以根据需要进行日志记录或其他操作
        console.error("Error while making GET request:", error)
        throw error // 可以选择将错误抛出以在调用函数的地方处理
    }
}

async function del(url, data) {
    try {
        const response = await axios.delete(url, data, {
            headers: {
                "Content-Type": "application/json",
            },
            withCredentials: true,
        })
        return response.data
    } catch (error) {
        // 进行错误处理，可以根据需要进行日志记录或其他操作
        console.error("Error while making POST request:", error)
        throw error // 可以选择将错误抛出以在调用函数的地方处理
    }
}

export { get, post, del }
