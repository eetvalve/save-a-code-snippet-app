import Api from '@/services/api'

export default {
  validateSecureCode(params) {
    return Api().post('/validateSecureCode/' + params.secureCode)
  },
  initUser(params) {
    return Api().post('/initUser/' + params.userName)
  },
  togglePrivacy(params) {
    return Api().put('/togglePrivacy/' + params.userId)
  }

}
