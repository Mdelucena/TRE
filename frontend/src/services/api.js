

import axios from 'axios'

const API_BASE = '/api'

const apiClient = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json'
  }
})

const persistedToken = localStorage.getItem('token')
if (persistedToken) {
  apiClient.defaults.headers.common['Authorization'] = `Bearer ${persistedToken}`
}

// Dashboard APIs
export const getDashboardData = () => apiClient.get('/dashboard')

// Vehicle APIs
export const getVehicles = () => apiClient.get('/vehicles')
export const getVehicleById = (id) => apiClient.get(`/vehicles/${id}`)

// Maintenance APIs
export const getMaintenances = (vehicleId = null) => {
  const params = vehicleId ? { vehicleId } : {}
  return apiClient.get('/maintenance', { params })
}

export const getMaintenanceById = (id) => apiClient.get(`/maintenance/${id}`)

export const createMaintenance = (data) => apiClient.post('/maintenance', data)

export const updateMaintenance = (id, data) => apiClient.put(`/maintenance/${id}`, data)

export const deleteMaintenance = (id) => apiClient.delete(`/maintenance/${id}`)

// Error handler
apiClient.interceptors.response.use(
  response => response,
  error => {
    console.error('API Error:', error.response?.data || error.message)
    return Promise.reject(error)
  }
)

// Adicionar token ao header automaticamente
export const setApiToken = (token) => {
  if (token) {
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`
  } else {
    delete apiClient.defaults.headers.common['Authorization']
  }
}

export default apiClient
