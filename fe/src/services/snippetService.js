import Api from '@/services/api'
import store from '@/store/index'

export default {
  getAllTitles() {
    return Api().get('/titles/' + store.state.userData.user.userId)
  },
  getTitleNamesList(params) {
    return Api().get('/titleNamesList/' + store.state.userData.user.userId + '/' + params.name)
  },
  getLatestSnippets() {
    return Api().get('/latestSnippets/' + store.state.userData.user.userId)
  },
  getSnippets(params) {
    return Api().get('/snippets/' + store.state.userData.user.userId + '/' + params.titleId)
  },
  addSnippet(snippet) {
    return Api().post('/snippet', snippet)
  }
}
