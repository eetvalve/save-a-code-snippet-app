import userService from '@/services/userService'
import router from '@/router/router'

const userDataState = {
  state: {
    user: localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : undefined
  },
  getters: {},
  mutations: {
    SET_USER(state, user) {
      state.user = user
      localStorage.setItem('user', JSON.stringify(user))
    },
    LOGOUT(state) {
      state.user = undefined
      localStorage.removeItem('user')
      localStorage.removeItem('token')
      localStorage.setItem('attemptsLeft', '3')
    }
  },
  actions: {
    getUser(context, userName) {
      userService.initUser({userName: userName})
        .then(res => {
          console.log('user: ', res)
          context.commit('SET_USER', res.data)
          router.push('/')
        })
        .catch(err => {
          console.log('err', err)
        })
    },
    togglePrivacy(context, userId) {
      userService.togglePrivacy({userId: userId})
        .then(user => {
          context.commit('SET_USER', user.data)
        })
        .catch(err => {
          console.log('err', err)
        })
    },
    logout(context) {
      context.commit('LOGOUT')
      router.push('/auth')
    }
  },
};

export default userDataState;
