import styles from './MetricCard.module.css'

export default function MetricCard({ title, value, color = 'primary' }) {
  return (
    <div className={`${styles.card} ${styles[color]}`}>
      <div className={styles.value}>{value}</div>
      <div className={styles.title}>{title}</div>
    </div>
  )
}
