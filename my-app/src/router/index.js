import {createRouter, createWebHistory} from "vue-router";

import HomePage from "../views/HomePage.vue";
import NotesPage from "../views/NotesPage.vue";
import LoginPage from "../views/LoginPage.vue";
import RegisterPage from "../views/RegisterPage.vue";
import SettingsPage from "../views/SettingsPage.vue"; // ← ДОБАВИЛИ
import CalendarPage from "@/components/CalendarPage.vue";

const routes = [
    {path: "/", component: HomePage},

    {
        path: "/notes",
        component: NotesPage,
        meta: {requiresAuth: true} // 🔐
    },

    {path: "/login", component: LoginPage},
    {path: "/register", component: RegisterPage},

    {
        path: "/profile",
        component: () => import("@/views/UserProfile.vue"),
        meta: {requiresAuth: true}
    },

    {
        path: "/settings",                 // ← НОВАЯ СТРАНИЦА
        component: SettingsPage,
        meta: {requiresAuth: true}        // 🔐 логично защищена
    },

    {
        path: "/payment",
        component: () => import("@/views/PaymentPage.vue")
    },
    {
        path: "/calendar",
        name: "Calendar",
        component: CalendarPage
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

/* 🔐 AUTH GUARD */
router.beforeEach((to, from, next) => {
    const isAuth = localStorage.getItem("auth") === "true";

    if (to.meta.requiresAuth && !isAuth) {
        next("/login");
    } else {
        next();
    }
});

export default router;
