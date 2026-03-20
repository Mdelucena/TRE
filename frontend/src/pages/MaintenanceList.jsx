import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getMaintenances, getVehicles, deleteMaintenance } from '../services/api'
import styles from './MaintenanceList.module.css'

export default function MaintenanceList() {
  const [maintenances, setMaintenances] = useState([])
  const [vehicles, setVehicles] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [filterVehicleId, setFilterVehicleId] = useState('')

  useEffect(() => {
    fetchData()
  }, [filterVehicleId])

  const fetchData = async () => {
    try {
      setLoading(true)
      const [mainRes, vehiclesRes] = await Promise.all([
        getMaintenances(filterVehicleId || null),
        getVehicles()
      ])
      setMaintenances(mainRes.data)
      setVehicles(vehiclesRes.data)
      setError(null)
    } catch (err) {
      setError('Erro ao carregar dados')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (id) => {
    if (window.confirm('Tem certeza que deseja deletar?')) {
      try {
        await deleteMaintenance(id)
        setMaintenances(maintenances.filter(m => m.id !== id))
      } catch (err) {
        setError('Erro ao deletar manutenção')
        console.error(err)
      }
    }
  }

  const getStatusBadgeClass = (status) => {
    switch(status) {
      case 'PENDENTE': return 'badge-warning'
      case 'EM_REALIZACAO': return 'badge-info'
      case 'CONCLUIDA': return 'badge-success'
      default: return 'badge-default'
    }
  }

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('pt-BR')
  }

  if (loading) return <div className="loading">Carregando...</div>

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h1>Gestão de Manutenção</h1>
        <Link to="/maintenance/new" className={`${styles.btnNew} btn-primary`}>
          + Nova Manutenção
        </Link>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}

      <div className="card">
        <div className={styles.filterSection}>
          <label htmlFor="vehicleFilter">Filtrar por veículo:</label>
          <select
            id="vehicleFilter"
            value={filterVehicleId}
            onChange={(e) => setFilterVehicleId(e.target.value)}
            className={styles.select}
          >
            <option value="">Todos os veículos</option>
            {vehicles.map(v => (
              <option key={v.id} value={v.id}>
                {v.description}
              </option>
            ))}
          </select>
        </div>

        {maintenances.length === 0 ? (
          <p className={styles.empty}>Nenhuma manutenção encontrada</p>
        ) : (
          <table className={styles.table}>
            <thead>
              <tr>
                <th>Veículo</th>
                <th>Serviço</th>
                <th>Data Agendada</th>
                <th>Status</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {maintenances.map(m => (
                <tr key={m.id}>
                  <td>{m.vehicleDescription || 'N/A'}</td>
                  <td>{m.serviceType}</td>
                  <td>{formatDate(m.scheduledDate)}</td>
                  <td>
                    <span className={`${styles.badge} ${styles[getStatusBadgeClass(m.status)]}`}>
                      {m.status}
                    </span>
                  </td>
                  <td className={styles.actions}>
                    <Link to={`/maintenance/${m.id}`} className={styles.btnEdit}>
                      Editar
                    </Link>
                    <button
                      className={styles.btnDelete}
                      onClick={() => handleDelete(m.id)}
                    >
                      Deletar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  )
}
