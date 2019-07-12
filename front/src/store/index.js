import Vue from 'vue';
import Vuex from 'vuex';

import timelineDataModule from './modules/timelineData';
import personalDataModule from './modules/personalData';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    timelineData: timelineDataModule,
    personalData: personalDataModule,
  },
});
