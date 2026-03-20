import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../services/authService'
import { useAuth } from '../context/AuthContext'
import styles from './Login.module.css'

export default function Login() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState(null)
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()
  const { login: authLogin } = useAuth()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    setLoading(true)

    try {
      const response = await login(username, password)
      authLogin(response.data)
      navigate('/')
    } catch (err) {
      setError('Usuário ou senha inválidos')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.loginBox}>
        <h1>LogiTrack Pro</h1>
        <p className={styles.subtitle}>Sistema de Gestão de Frota</p>

        {error && <div className="alert alert-danger">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="username">Usuário</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              placeholder="Digite seu usuário"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Senha</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Digite sua senha"
            />
          </div>

          <button
            type="submit"
            className={styles.btnLogin}
            disabled={loading}
          >
            {loading ? 'Entrando...' : 'Entrar'}
          </button>
        </form>

        <p className={styles.footer}>
          Todas as credenciais de teste:<br />
          <strong>Usuário:</strong> admin<br />
          <strong>Senha:</strong> admin
        </p>
      </div>
    </div>
  )
}
