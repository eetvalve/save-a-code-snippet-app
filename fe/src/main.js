import Vue from 'vue';
import './plugins/vuetify'
import App from './App.vue';
import router from './router/router';
import store from './store/index';
// import BootstrapVue from 'bootstrap-vue';
//import 'bootstrap/dist/css/bootstrap.css';
//import 'bootstrap-vue/dist/bootstrap-vue.css';
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css' // Ensure you are using css-loader

Vue.use(Vuetify);
Vue.config.productionTip = false;


new Vue({
  router,
  store,
  render: h => h(App),
}).
$mount('#app');
