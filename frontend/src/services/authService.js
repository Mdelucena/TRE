import axios from 'axios'

const API_BASE = '/api'

const authClient = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json'
  }
})

const persistedToken = localStorage.getItem('token')
if (persistedToken) {
  authClient.defaults.headers.common['Authorization'] = `Bearer ${persistedToken}`
}

// Auth APIs
export const login = (username, password) => 
  authClient.post('/auth/login', { username, password })

export const register = (username, email, password) => 
  authClient.post('/auth/register', { username, email, password })

// Adicionar token ao header automaticamente
export const setAuthToken = (token) => {
  if (token) {
    authClient.defaults.headers.common['Authorization'] = `Bearer ${token}`
  } else {
    delete authClient.defaults.headers.common['Authorization']
  }
}

export default authClient
