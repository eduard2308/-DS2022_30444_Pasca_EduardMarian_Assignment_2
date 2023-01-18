import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import ItemList from "../views/ItemList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import ItemListAdmin from "../views/ItemListAdmin";
import Energy from "@/views/Energy";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin(store.state)) {
        next();
      } else {
        next({ name: "Devices" });
      }
    },
  },
  {
    path: "/energy",
    name: "Energy",
    component: Energy,
    beforeEnter: (to, from, next) => {
      if (store.getters.isCustomer(store.state)) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
  {
    path: "/devices",
    name: "ItemList",
    component: ItemList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },

  {
    path: "/devices/admin",
    name: "DevicesAdmin",
    component: ItemListAdmin,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin(store.state)) {
        next();
      } else {
        next({ name: "Devices" });
      }
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
