import styles from './MaintenanceTimeline.module.css'

export default function MaintenanceTimeline({ items = [] }) {
  const getStatusColor = (status) => {
    switch(status) {
      case 'PENDENTE': return 'warning'
      case 'EM_REALIZACAO': return 'info'
      case 'CONCLUIDA': return 'success'
      default: return 'default'
    }
  }

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('pt-BR')
  }

  return (
    <div className={styles.timeline}>
      {items.length === 0 ? (
        <p className={styles.empty}>Nenhuma manutenção registrada</p>
      ) : (
        items.map((item, index) => (
          <div key={index} className={styles.timelineItem}>
            <div className={`${styles.dot} ${styles[getStatusColor(item.status)]}`}></div>
            <div className={styles.content}>
              <div className={styles.header}>
                <strong>{item.vehicleDescription}</strong>
                <span className={`${styles.badge} ${styles[getStatusColor(item.status)]}`}>
                  {item.status}
                </span>
              </div>
              <div className={styles.details}>
                <p>{item.serviceType}</p>
                <small>{item.scheduledDate ? `Agendada para: ${formatDate(item.scheduledDate)}` : 'Sem data'}</small>
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  )
}
