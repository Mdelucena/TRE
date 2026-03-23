import styles from './VehicleChart.module.css'

export default function VehicleChart({ data = [] }) {
  if (!data || data.length === 0) {
    return <p>Nenhum dado de categoria disponível</p>
  }

  const normalizedData = data.map((item) => ({
    category: item.category ?? item.categoryName ?? 'Sem categoria',
    totalTrips: Number(item.totalTrips ?? item.quantity ?? 0)
  }))

  // Encontra o maior valor para normalizar
  const maxValue = Math.max(...normalizedData.map(item => item.totalTrips), 1)
  const colors = ['#0066cc', '#f39c12', '#27ae60', '#e74c3c', '#9b59b6', '#1abc9c']

  return (
    <div className={styles.chart}>
      {normalizedData.map((item, index) => (
        <div key={index} className={styles.barContainer}>
          <div className={styles.barLabel}>
            <strong>{item.category}</strong>
            <span className={styles.value}>{item.totalTrips}</span>
          </div>
          <div className={styles.barTrack}>
            <div
              className={styles.bar}
              style={{
                width: `${(item.totalTrips / maxValue) * 100}%`,
                backgroundColor: colors[index % colors.length]
              }}
            />
          </div>
        </div>
      ))}
    </div>
  )
}
