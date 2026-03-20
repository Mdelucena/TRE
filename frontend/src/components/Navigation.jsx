import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import styles from './Navigation.module.css'

export default function Navigation() {
  const { logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
    <nav className={styles.navbar}>
      <div className="container">
        <div className={styles.navContent}>
          <Link to="/" className={styles.brand}>
            <h1>LogiTrack Pro</h1>
          </Link>
          <ul className={styles.navLinks}>
            <li><Link to="/">Dashboard</Link></li>
            <li><Link to="/maintenance">Manutenção</Link></li>
            <li><Link to="/maintenance/new" className={styles.btnNew}>+ Nova Manutenção</Link></li>
            <li>
              <button type="button" onClick={handleLogout} className={styles.btnLogout}>
                Sair
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  )
}
