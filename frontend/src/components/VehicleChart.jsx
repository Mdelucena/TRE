import styles from './VehicleChart.module.css'

export default function VehicleChart({ data = [] }) {
  if (!data || data.length === 0) {
    return <p>Nenhum dado de categoria disponível</p>
  }

  // Encontra o maior valor para normalizar
  const maxValue = Math.max(...data.map(item => item.quantity || 0))
  const colors = ['#0066cc', '#f39c12', '#27ae60', '#e74c3c', '#9b59b6', '#1abc9c']

  return (
    <div className={styles.chart}>
      {data.map((item, index) => (
        <div key={index} className={styles.barContainer}>
          <div className={styles.barLabel}>
            <strong>{item.categoryName}</strong>
            <span className={styles.value}>{item.quantity}</span>
          </div>
          <div className={styles.barTrack}>
            <div
              className={styles.bar}
              style={{
                width: `${(item.quantity / maxValue) * 100}%`,
                backgroundColor: colors[index % colors.length]
              }}
            />
          </div>
        </div>
      ))}
    </div>
  )
}
