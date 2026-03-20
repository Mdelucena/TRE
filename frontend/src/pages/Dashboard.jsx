import { useEffect, useState } from 'react'
import { getDashboardData } from '../services/api'
import MetricCard from '../components/MetricCard'
import MaintenanceTimeline from '../components/MaintenanceTimeline'
import VehicleChart from '../components/VehicleChart'
import styles from './Dashboard.module.css'

export default function Dashboard() {
  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchDashboardData()
  }, [])

  const fetchDashboardData = async () => {
    try {
      setLoading(true)
      const response = await getDashboardData()
      setData(response.data)
      setError(null)
    } catch (err) {
      setError('Erro ao carregar dados do dashboard')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <div className="loading">Carregando...</div>
  if (error) return <div className="alert alert-danger">{error}</div>
  if (!data) return <div className="alert alert-warning">Nenhum dado disponível</div>

  return (
    <div className={styles.dashboard}>
      <h1>Dashboard</h1>

      <div className={styles.metrics}>
        <MetricCard
          title="Total de Veículos"
          value={data.totalVehicles || 0}
          color="primary"
        />
        <MetricCard
          title="Manutenções Agendadas"
          value={data.totalScheduledMaintenances || 0}
          color="warning"
        />
        <MetricCard
          title="Manutenções em Andamento"
          value={data.totalInProgressMaintenances || 0}
          color="secondary"
        />
        <MetricCard
          title="Manutenções Concluídas"
          value={data.totalCompletedMaintenances || 0}
          color="success"
        />
      </div>

      <div className={styles.chartsSection}>
        {data.vehiclesByCategory && data.vehiclesByCategory.length > 0 && (
          <div className="card">
            <h2>Distribuição de Veículos por Categoria</h2>
            <VehicleChart data={data.vehiclesByCategory} />
          </div>
        )}
      </div>

      <div className={styles.timelineSection}>
        {data.maintenanceTimeline && data.maintenanceTimeline.length > 0 && (
          <div className="card">
            <h2>Histórico de Manutenções</h2>
            <MaintenanceTimeline items={data.maintenanceTimeline} />
          </div>
        )}
      </div>

      {data.topVehicleUsage && data.topVehicleUsage.length > 0 && (
        <div className="card">
          <h2>Veículos Mais Utilizados</h2>
          <table>
            <thead>
              <tr>
                <th>Veículo</th>
                <th>Quilometragem</th>
              </tr>
            </thead>
            <tbody>
              {data.topVehicleUsage.map(v => (
                <tr key={v.vehicleId}>
                  <td>{v.vehicleDescription}</td>
                  <td>{v.totalMileage?.toFixed(2)} km</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  )
}
