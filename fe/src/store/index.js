import Vue from 'vue';
import Vuex from 'vuex';

import userDataModule from './modules/userData';
import snippetDataModule from './modules/snippetData'

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    userData: userDataModule,
    snippetData: snippetDataModule,
  },
});
