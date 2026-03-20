import { createContext, useState, useContext } from 'react'

const AuthContext = createContext()

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem('user')
    if (!storedUser) return null

    try {
      const parsed = JSON.parse(storedUser)
      return {
        id: parsed.userId,
        username: parsed.username,
        email: parsed.email
      }
    } catch {
      localStorage.removeItem('user')
      return null
    }
  })
  const [token, setToken] = useState(localStorage.getItem('token'))

  const login = (authResponse) => {
    setUser({
      id: authResponse.userId,
      username: authResponse.username,
      email: authResponse.email
    })
    setToken(authResponse.token)
    localStorage.setItem('token', authResponse.token)
    localStorage.setItem('user', JSON.stringify(authResponse))
  }

  const logout = () => {
    setUser(null)
    setToken(null)
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  const isAuthenticated = !!token

  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}
