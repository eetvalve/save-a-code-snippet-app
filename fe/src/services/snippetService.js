import Api from '@/services/api'
import store from '@/store/index'

export default {
  getAllTitles() {
    return Api().get('/titles/' + store.state.userData.user.userId)
  },
  getTitleNamesList(params) {
    return Api().get('/titleNamesList/' + params.userId + '/' + params.name)
  },
  getLatestSnippets() {
    return Api().get('/latestSnippets/' + store.state.userData.user.userId)
  }
}
