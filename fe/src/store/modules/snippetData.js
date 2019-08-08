import snippetService from '@/services/snippetService'
import userDataState from '@/store/modules/userData'
import router from '@/router/router'
import Vue from "vue";

const snippetDataState = {
  state: {
    titles: [],
    titleList: [], // for searching titles
    tempTitleStore: [],
    isTitlesLoading: false,
    selectedTitle: '', // currently visible snippets title
    snippets: [],
    isSnippetsLoading: false,
    addNewComponentHeight: 0,
    snackbar: {},
    snippetTemplate: {
      snippetId: null,
      titleName: '',
      privateSnippet: false,
      description: '',
      snippet: ''
    },
    editModeOn: false
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

      console.log('index: ', index)

      // not found
      if (index === -1) {
        state.snippets.push(responseSnippet)
      } else {
        // if old snippet gets edited
        Vue.set(state.snippets, index, responseSnippet)
      }
    },
    DELETE_SNIPPET_LOCALLY(state, snippetId) {
      state.snippets = state.snippets.filter(snippet => snippet.snippetId !== snippetId)
    },
    UPDATE_SNIPPET_TEMPLATE(state, snippet) {
      console.log('snippet: ', snippet)
      state.snippetTemplate = {
        snippetId: snippet.snippetId,
        titleName: snippet.title.title,
        privateSnippet: false,
        description: snippet.description,
        snippet: snippet.snippet
      }
      state.editModeOn = true
    },
    EDIT_MODE_OFF(state) {
      state.editModeOn = false
    },
    CLEAR_SNIPPET_TEMPLATE(state) {
      state.snippetTemplate = {
        snippetId: null,
        titleName: '',
        privateSnippet: false,
        description: '',
        snippet: ''
      }

      state.editModeOn = false
    },
    GET_ADD_SNIPPET_CONTAINER_HEIGHT(state, height) {
      if (height > 0) {
        console.log('height: ', height)
        state.addNewComponentHeight = height
      }
    },
    UPDATE_SNACKBAR_MSG(state, msgObj) {
      state.snackbar = msgObj
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
      context.dispatch('createSnippetObject', item)
        .then(snippet => {
          console.log('FINAL snippet: ', snippet)

          snippetService.addSnippet(snippet)
            .then(snippetRes => {
              context.dispatch('updateTitlesAndSnippets', snippetRes.data)
              context.commit('CLEAR_SNIPPET_TEMPLATE')

              context.commit('UPDATE_SNACKBAR_MSG', {
                text: 'Snippet added successfully!',
                visible: true,
                color: 'primary'
              })
            })
            .catch(err => {
              console.log('err', err)
              context.commit('UPDATE_SNACKBAR_MSG', {
                text: 'Snippet add failed',
                visible: true,
                color: 'red'
              })
            })
        })

    },
    editSnippet(context, item) {
      context.dispatch('createSnippetObject', item)
        .then(snippet => {
          console.log('FINAL edited snippet: ', snippet)

          snippetService.editSnippet(snippet)
            .then(snippetRes => {
              context.dispatch('updateTitlesAndSnippets', snippetRes.data)
              context.commit('CLEAR_SNIPPET_TEMPLATE')
              context.commit('EDIT_MODE_OFF')

              context.commit('UPDATE_SNACKBAR_MSG', {
                text: 'Snippet edited successfully!',
                visible: true,
                color: 'primary'
              })
            })
            .catch(err => {
              console.log('err', err)
              context.commit('UPDATE_SNACKBAR_MSG', {
                text: 'Snippet edit failed!',
                visible: true,
                color: 'red'
              })
            })
        })
    },
    deleteSnippet(context, id) {
      snippetService.deleteSnippet({snippetId: id})
        .then(res => {
          // todo delete snackbar flag
          context.dispatch('getTitles')
          context.commit('DELETE_SNIPPET_LOCALLY', id)

          context.commit('UPDATE_SNACKBAR_MSG', {
            text: 'Snippet deleted successfully!',
            visible: true,
            color: 'primary'
          })
        })
        .catch(err => {
          console.log('err', err)

          context.commit('UPDATE_SNACKBAR_MSG', {
            text: 'delete failed',
            visible: true,
            color: 'red'
          })
        })
    },
    createSnippetObject(context, item) {
      return {
        owner: userDataState.state.user,
        title: {
          title: item.titleName,
        },
        isPrivateSnippet: userDataState.state.user.privateSnippets,
        description: item.description,
        snippet: item.snippet,
        snippetId: item.snippetId ? item.snippetId : null
      }
    },
    updateTitlesAndSnippets(context, snippetRes) {
      if (snippetRes.title.title === context.state.selectedTitle) {
        // prevents unnecessary db-query
        context.commit('UPDATE_SNIPPETS_LOCALLY', snippetRes)
      } else {
        context.dispatch('getSnippets', snippetRes.title)
      }

      context.dispatch('getTitles')

      // prevents unnecessary db-query
      // context.commit('UPDATE_TITLES_LOCALLY', res.data.title)
    },
  },
};

export default snippetDataState;
