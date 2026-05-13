import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import { setGlobalOptions } from "vue-request"
import { createPinia } from "pinia"
import piniaPluginPersist from "pinia-plugin-persist"
import axios from "axios"
import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
import Antd from "ant-design-vue"
import "ant-design-vue/dist/reset.css"
import "@/assets/main.css"

const store = createPinia()
store.use(piniaPluginPersist)

const app = createApp(App)
axios.defaults.baseURL = 'http://localhost:8090'
app.use(setGlobalOptions)
app.use(store)
app.use(router)
app.use(ElementPlus)
app.use(Antd)
app.mount('#app')
