import snippetService from '@/services/snippetService'
import userDataState from '@/store/modules/userData'
import router from '@/router/router'

const snippetDataState = {
  state: {
    titles: [],
    titleList: [], // for searching titles
    tempTitleStore: [],
    isTitlesLoading: false,
    selectedTitle: '', // currently visible snippets title
    snippets: [],
    isSnippetsLoading: false,
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
    TITLES_SEARCH_LIST(state, titles) {
      state.titleList = titles
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
    },
    SET_SNIPPETS_LOADING(state) {
      state.isSnippetsLoading = true
    },
    SET_TITLES_LOADING(state) {
      state.isTitlesLoading = true
    },
    UPDATE_TITLES_LOCALLY(state, responseTitle) {
      const matchingTitle = state.titles.some(title => title.title === responseTitle.title)
      if (!matchingTitle) {
        state.titles.push(responseTitle)
      }

    },
    UPDATE_SNIPPETS_LOCALLY(state, responseSnippet) {
      // replace if same index
      const index = state.snippets.findIndex(item => item.snippetId === responseSnippet.snippetId)
      if (index > 0) {
        // if old snippet gets edited
        state.snippets[index] = responseSnippet
      } else {
        // totally new snippet
        state.snippets.push(responseSnippet)
      }

    }

  },
  actions: {
    getTitles(context) {
      context.commit('SET_TITLES_LOADING')
      snippetService.getAllTitles()
        .then(titles => {
          setTimeout(() => {
            context.commit('SET_TITLES', titles.data)
          }, 200)

        })
        .catch(err => {
          console.log('err', err)
        })
    },
    filterTitles(context, query) { // filter navbar titles
      if (query) {
        context.commit('FILTER_TITLES', query)
      } else {
        context.commit('RETURN_ORIGINAL_TITLES_STATE')
      }
    },
    filterTitlesList(context, name) {  // add new snippet query

      if (name) {
        snippetService.getTitleNamesList({name: name})
          .then(titles => {
            context.commit('TITLES_SEARCH_LIST', titles.data)
          })
          .catch(err => {
            console.log('err', err)
          })
      } else {
        context.commit('TITLES_SEARCH_LIST', [])
      }


    },
    getLatestSnippetsAdded(context) {
      context.commit('SET_SNIPPETS_LOADING')
      snippetService.getLatestSnippets()
        .then(snippets => {
          setTimeout(() => {
            context.commit('SET_SNIPPETS', snippets.data)
            context.commit('SET_SELECTED_TITLE', snippets.data[0].title)
          }, 200)

        })
        .catch(err => {
          console.log('err', err)
        })
    },
    getSnippets(context, title) {
      context.commit('SET_SNIPPETS_LOADING')
      snippetService.getSnippets({titleId: title.titleId})
        .then(snippets => {
          setTimeout(() => {
            context.commit('SET_SNIPPETS', snippets.data)
            context.commit('SET_SELECTED_TITLE', snippets.data[0].title)
          }, 200)

        })
        .catch(err => {
          console.log('err', err)
        })
        .finally(() => {
          context.commit('RETURN_ORIGINAL_TITLES_STATE')
        })
    },
    addNewSnippet(context, item) {

      // create snippet
      const snippet = {
        owner: userDataState.state.user,
        title: {
          title: item.titleName,
        },
        isPrivateSnippet: userDataState.state.user.privateSnippets,
        description: item.description,
        snippet: item.snippet
      }

      console.log('FINAL snippet: ', snippet)

      snippetService.addSnippet(snippet)
        .then(snippetRes => {
          context.dispatch('updateTitlesAndSnippets', snippetRes.data)
        })
        .catch(err => {
          console.log('err', err)
        })
    },
    editSnippet(context, item) {

    },
    updateTitlesAndSnippets(context, snippetRes) {
      console.log('snippetRes: ', snippetRes)
      if (snippetRes.title.title === context.state.selectedTitle) {
        // prevents unnecessary db-query
        context.commit('UPDATE_SNIPPETS_LOCALLY', snippetRes)
      } else {
        context.dispatch('getSnippets', snippetRes.title)
      }

      context.dispatch('getTitles')

      // prevents unnecessary db-query
      // context.commit('UPDATE_TITLES_LOCALLY', res.data.title)
    }
  },
};

export default snippetDataState;
