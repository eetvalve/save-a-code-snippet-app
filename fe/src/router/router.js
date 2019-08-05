import Vue from 'vue';
import Router from 'vue-router';
import Home from '../views/Dashboard.vue';
import AuthPage from '../views/AuthPage.vue';
import SnippetPage from '../views/SnippetPage.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: SnippetPage,
    },
    {
      path: '/auth',
      name: 'auth',
      component: AuthPage,
    },
  ],
})
;
