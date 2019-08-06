import snippetService from '@/services/snippetService'
import router from '@/router/router'

const snippetDataState = {
  state: {
    titles: [],
    tempTitleStore: [],
    isTitlesLoading: true,
    selectedTitle: '',
    snippets: [],
    isSnippetsLoading: true
  },
  getters: {},
  mutations: {
    SET_TITLES(state, titles) {
      state.titles = titles
      state.tempTitleStore = titles
      state.isTitlesLoading = false
    },
    FILTER_TITLES(state, query) {
      state.titles = state.tempTitleStore
      state.titles = state.titles.filter(item => item.title.title.toLowerCase().startsWith(query.toLowerCase()))
    },
    RETURN_ORIGINAL_TITLES_STATE(state) {
      state.titles = state.tempTitleStore
    },
    SET_SELECTED_TITLE(state, value) {
      state.selectedTitle = value.title
    },
    SET_SNIPPETS(state, snippets) {
      state.snippets = snippets
      state.isSnippetsLoading = false
    }
  },
  actions: {
    getTitles(context) {
      snippetService.getAllTitles()
        .then(titles => {
          setTimeout(() => {
            context.commit('SET_TITLES', titles.data)
          }, 500)

        })
        .catch(err => {
          console.log('err', err)
        })
    },
    filterTitles(context, query) {
      if (query) {
        context.commit('FILTER_TITLES', query)
      } else {
        context.commit('RETURN_ORIGINAL_TITLES_STATE')
      }
    },
    getLatestSnippetsAdded(context) {
      snippetService.getLatestSnippets()
        .then(snippets => {
          setTimeout(() => {
            context.commit('SET_SNIPPETS', snippets.data)
            context.commit('SET_SELECTED_TITLE', snippets.data[0].title)
          }, 500)

        })
        .catch(err => {
          console.log('err', err)
        })
    }
  },
};

export default snippetDataState;
