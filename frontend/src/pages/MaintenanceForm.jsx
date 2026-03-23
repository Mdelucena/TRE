import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { getVehicles, getMaintenanceById, createMaintenance, updateMaintenance } from '../services/api'
import styles from './MaintenanceForm.module.css'

const SERVICE_TYPES = ['TROCA_DE_PNEUS', 'MOTOR', 'OLEO', 'FREIOS', 'REVISAO_GERAL']

const STATUSES = ['PENDENTE', 'EM_REALIZACAO', 'CONCLUIDA']

export default function MaintenanceForm() {
  const { id } = useParams()
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    vehicleId: '',
    serviceType: 'TROCA_DE_PNEUS',
    scheduledDate: '',
    expectedEndDate: '',
    estimatedCost: '',
    status: 'PENDENTE',
    description: ''
  })

  const [vehicles, setVehicles] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(null)
  const [errors, setErrors] = useState({})

  useEffect(() => {
    fetchVehicles()
    if (id) {
      fetchMaintenance()
    } else {
      setLoading(false)
    }
  }, [id])

  const fetchVehicles = async () => {
    try {
      const response = await getVehicles()
      setVehicles(response.data)
    } catch (err) {
      setError('Erro ao carregar veículos')
      console.error(err)
    }
  }

  const fetchMaintenance = async () => {
    try {
      const response = await getMaintenanceById(id)
      setFormData({
        vehicleId: response.data.vehicleId || '',
        serviceType: response.data.serviceType,
        scheduledDate: response.data.scheduledDate || '',
        expectedEndDate: response.data.expectedEndDate || response.data.scheduledDate || '',
        estimatedCost: response.data.estimatedCost ?? '',
        status: response.data.status,
        description: response.data.description || ''
      })
    } catch (err) {
      setError('Erro ao carregar manutenção')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value
    }))
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: null
      }))
    }
  }

  const validateForm = () => {
    const newErrors = {}

    if (!formData.vehicleId) {
      newErrors.vehicleId = 'Veículo é obrigatório'
    }

    if (!formData.scheduledDate) {
      newErrors.scheduledDate = 'Data é obrigatória'
    }

    if (!formData.expectedEndDate) {
      newErrors.expectedEndDate = 'Data de finalização prevista é obrigatória'
    } else if (formData.scheduledDate && formData.expectedEndDate < formData.scheduledDate) {
      newErrors.expectedEndDate = 'Data de finalização prevista não pode ser anterior à data agendada'
    }

    if (formData.estimatedCost === '') {
      newErrors.estimatedCost = 'Custo estimado é obrigatório'
    } else if (Number(formData.estimatedCost) < 0) {
      newErrors.estimatedCost = 'Custo estimado não pode ser negativo'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!validateForm()) {
      return
    }

    try {
      const payload = {
        vehicleId: parseInt(formData.vehicleId),
        serviceType: formData.serviceType,
        scheduledDate: formData.scheduledDate,
        expectedEndDate: formData.expectedEndDate,
        estimatedCost: parseFloat(formData.estimatedCost),
        status: formData.status,
        description: formData.description
      }

      if (id) {
        await updateMaintenance(id, payload)
        setSuccess('Manutenção atualizada com sucesso!')
      } else {
        await createMaintenance(payload)
        setSuccess('Manutenção criada com sucesso!')
      }

      setTimeout(() => {
        navigate('/maintenance')
      }, 1500)
    } catch (err) {
      setError('Erro ao salvar manutenção')
      console.error(err)
    }
  }

  if (loading) return <div className="loading">Carregando...</div>

  const pageTitle = id ? 'Editar Manutenção' : 'Nova Manutenção'

  return (
    <div className={styles.container}>
      <h1>{pageTitle}</h1>

      {error && <div className="alert alert-danger">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="vehicleId">Veículo *</label>
            <select
              id="vehicleId"
              name="vehicleId"
              value={formData.vehicleId}
              onChange={handleChange}
              className={errors.vehicleId ? styles.inputError : ''}
            >
              <option value="">Selecione um veículo</option>
              {vehicles.map(v => (
                <option key={v.id} value={v.id}>
                  {v.description}
                </option>
              ))}
            </select>
            {errors.vehicleId && <div className="error-message">{errors.vehicleId}</div>}
          </div>

          <div className="form-group">
            <label htmlFor="serviceType">Tipo de Serviço *</label>
            <select
              id="serviceType"
              name="serviceType"
              value={formData.serviceType}
              onChange={handleChange}
            >
              {SERVICE_TYPES.map(type => (
                <option key={type} value={type}>
                  {type.replace(/_/g, ' ')}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="scheduledDate">Data Agendada *</label>
            <input
              type="date"
              id="scheduledDate"
              name="scheduledDate"
              value={formData.scheduledDate}
              onChange={handleChange}
              className={errors.scheduledDate ? styles.inputError : ''}
            />
            {errors.scheduledDate && <div className="error-message">{errors.scheduledDate}</div>}
          </div>

          <div className="form-group">
            <label htmlFor="estimatedCost">Custo Estimado *</label>
            <input
              type="number"
              id="estimatedCost"
              name="estimatedCost"
              min="0"
              step="0.01"
              value={formData.estimatedCost}
              onChange={handleChange}
              className={errors.estimatedCost ? styles.inputError : ''}
            />
            {errors.estimatedCost && <div className="error-message">{errors.estimatedCost}</div>}
          </div>

          <div className="form-group">
            <label htmlFor="expectedEndDate">Data de Finalização Prevista *</label>
            <input
              type="date"
              id="expectedEndDate"
              name="expectedEndDate"
              value={formData.expectedEndDate}
              onChange={handleChange}
              className={errors.expectedEndDate ? styles.inputError : ''}
            />
            {errors.expectedEndDate && <div className="error-message">{errors.expectedEndDate}</div>}
          </div>

          <div className="form-group">
            <label htmlFor="status">Status</label>
            <select
              id="status"
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              {STATUSES.map(status => (
                <option key={status} value={status}>
                  {status.replace(/_/g, ' ')}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="description">Descrição</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="4"
              placeholder="Descrição da manutenção (opcional)"
            />
          </div>

          <div className={styles.actions}>
            <button type="submit" className="btn-primary">
              {id ? 'Atualizar' : 'Criar'}
            </button>
            <button
              type="button"
              className="btn-secondary"
              onClick={() => navigate('/maintenance')}
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
