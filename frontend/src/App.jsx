import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import { useEffect } from 'react'
import { AuthProvider, useAuth } from './context/AuthContext'
import { setAuthToken } from './services/authService'
import { setApiToken } from './services/api'
import Navigation from './components/Navigation'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import MaintenanceList from './pages/MaintenanceList'
import MaintenanceForm from './pages/MaintenanceForm'

function AppRoutes() {
  const { token, isAuthenticated } = useAuth()

  useEffect(() => {
    setAuthToken(token)
    setApiToken(token)
  }, [token])

  if (isAuthenticated) {
    return (
      <div className="app-container">
        <Navigation />
        <main className="container">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/maintenance" element={<MaintenanceList />} />
            <Route path="/maintenance/new" element={<MaintenanceForm />} />
            <Route path="/maintenance/:id" element={<MaintenanceForm />} />
            <Route path="/login" element={<Navigate to="/" />} />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </main>
      </div>
    )
  }

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  )
}

export default function App() {
  return (
    <Router>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </Router>
  )
}
