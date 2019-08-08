import axios from 'axios'

export default () => {
  return axios.create({
    baseURL: process.env.VUE_APP_BASE_URL,
    withCredentials: false,
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'token': getToken()
    }
  })
}

const getToken = () => {
  return localStorage.getItem('token') ? localStorage.getItem('token') : ''
};
