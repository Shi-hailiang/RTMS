import { createRouter, createWebHistory } from "vue-router"
import Index from "@/views/Index.vue"
import Register from "@/views/Register.vue"
import Login from "@/views/Login.vue"
import Store from "@/views/Store.vue"
import Admin from "@/views/Admin.vue"
import Customer from "@/views/Customer.vue"
import StoreHome from "@/components/store/Home.vue"
import StoreCategory from "@/components/store/Category.vue"
import StoreProduct from "@/components/store/Product.vue"
import StoreOrder from "@/components/store/Order.vue"
import StoreSetting from "@/components/store/Setting.vue"
import StoreReview from "@/components/store/Review.vue"
import AdminHome from "@/components/admin/Home.vue"
import AdminAccount from "@/components/admin/Account.vue"
import AdminStore from "@/components/admin/Store.vue"
import AdminRider from "@/components/admin/Rider.vue"
import AdminAddress from "@/components/admin/Address.vue"
import AdminSetting from "@/components/admin/Setting.vue"
import AdminComplaint from "@/components/admin/Complaint.vue"
import CustomerHome from "@/components/customer/Home.vue"
import CustomerOpenStore from "@/components/customer/OpenStore.vue"
import { useAccountStore } from "@/stores/account.js"

const routes = [
    {
        path: "/",
        name: "Index",
        component: Index,
    },
    {
        path: "/register",
        name: "Register",
        component: Register,
    },
    {
        path: "/login",
        name: "Login",
        component: Login,
    },
    {
        path: "/store",
        name: "Store",
        component: Store,
        children: [
            {
                path: "/store/home",
                name: "StoreHome",
                component: StoreHome,
            },
            {
                path: "/store/category",
                name: "StoreCategory",
                component: StoreCategory,
            },
            {
                path: "/store/product",
                name: "StoreProduct",
                component: StoreProduct,
            },
            {
                path: "/store/order",
                name: "StoreOrder",
                component: StoreOrder,
            },
            {
                path: "/store/review",
                name: "StoreReview",
                component: StoreReview,
            },
            {
                path: "/store/setting",
                name: "StoreSetting",
                component: StoreSetting,
            },
        ],
    },
    {
        path: "/admin",
        name: "Admin",
        component: Admin,
        children: [
            {
                path: "/admin/home",
                name: "AdminHome",
                component: AdminHome,
            },
            {
                path: "/admin/account",
                name: "AdminAccount",
                component: AdminAccount,
            },
            {
                path: "/admin/store",
                name: "AdminStore",
                component: AdminStore,
            },
            {
                path: "/admin/rider",
                name: "AdminRider",
                component: AdminRider,
            },
            {
                path: "/admin/address",
                name: "AdminAddress",
                component: AdminAddress,
            },
            {
                path: "/admin/complaint",
                name: "AdminComplaint",
                component: AdminComplaint,
            },
            {
                path: "/admin/setting",
                name: "AdminSetting",
                component: AdminSetting,
            },
        ],
    },
    {
        path: "/customer",
        name: "Customer",
        component: Customer,
        children: [
            {
                path: "/customer/home",
                name: "CustomerHome",
                component: CustomerHome,
            },
            {
                path: "/customer/open_store",
                name: "CustomerOpenStore",
                component: CustomerOpenStore,
            },
        ],
    },
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    linkActiveClass: "active-router",
    routes,
})

router.beforeEach((to, from, next) => {
    const accountStore = useAccountStore()
    if (
        to.path === "/" ||
        to.path === "/login" ||
        to.path === "/register" ||
        accountStore.id !== ""
    ) {
        next()
    } else {
        alert("您还没有登录，请先登录")
        next("/login")
    }
})

export default router
