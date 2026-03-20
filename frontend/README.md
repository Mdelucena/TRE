# LogiTrack Pro - Frontend (React + Vite)

Frontend em React com Vite para o sistema de gestão de frota e manutenção LogiTrack Pro.

## 🚀 Início Rápido

### Pré-requisitos
- Node.js 16+ 
- npm ou yarn

### Instalação

```bash
npm install
```

### Desenvolvimento

Inicie o servidor de desenvolvimento (será aberto em `http://localhost:3000`):

```bash
npm run dev
```

O frontend fará proxy automaticamente para o backend em `http://localhost:8080`.

### Build para Produção

```bash
npm run build
```

Isso gerará os arquivos otimizados na pasta `dist/`.

### Preview da Build

```bash
npm run preview
```

## 📁 Estrutura do Projeto

```
src/
├── components/          # Componentes reutilizáveis
│   ├── Navigation.jsx   # Barra de navegação
│   ├── MetricCard.jsx   # Card de métrica
│   ├── MaintenanceTimeline.jsx
│   ├── VehicleChart.jsx
│   └── *.module.css     # Estilos CSS Modules
├── pages/               # Páginas da aplicação
│   ├── Dashboard.jsx    # Dashboard com métricas
│   ├── MaintenanceList.jsx  # Lista de manutenções
│   ├── MaintenanceForm.jsx  # Formulário de manutenção
│   └── *.module.css
├── services/
│   └── api.js          # Cliente Axios com endpoints
├── App.jsx             # Componente raiz com rotas
├── main.jsx            # Entrada da aplicação
└── index.css           # Estilos globais
```

## 🛠️ Tecnologias

- **React 18** - Frontend framework
- **Vite** - Build tool
- **React Router v6** - Roteamento
- **Axios** - Cliente HTTP
- **CSS Modules** - Estilos encapsulados

## 🔌 API Integration

O frontend consome APIs REST do backend Spring Boot:

- `GET /api/dashboard` - Dados do dashboard
- `GET /api/vehicles` - Lista de veículos
- `GET /api/maintenance` - Lista de manutenções
- `POST /api/maintenance` - Criar manutenção
- `PUT /api/maintenance/:id` - Atualizar manutenção
- `DELETE /api/maintenance/:id` - Deletar manutenção

**Base URL**: `http://localhost:8080` (configurado em `vite.config.js`)

## 📋 Funcionalidades

### Dashboard
- Métricas de veículos e manutenções
- Gráfico de distribuição por categoria
- Timeline de histórico de manutenções
- Top veículos mais utilizados

### Gestão de Manutenção
- Listar manutenções com filtro por veículo
- Criar nova manutenção
- Editar manutenção existente
- Deletar manutenção
- Validação de formulário

## 🎨 Estilo

- **CSS Modules** para escopo de estilos
- **Variáveis CSS** para cores e temas
- Design responsivo
- Cores personalizadas:
  - Primary: `#0066cc`
  - Secondary: `#f39c12`
  - Success: `#27ae60`
  - Danger: `#e74c3c`

## 🚧 Próximos Passos

- [ ] Autenticação e autorização
- [ ] Testes unitários e E2E
- [ ] Mais gráficos/visualizações
- [ ] Export de dados (PDF, CSV)
- [ ] Notificações em tempo real
- [ ] Dark mode

## 📝 Notas de Migração

Este é um refactor do frontend original em Thymeleaf. 

**Antes**: Server-side rendering com Spring MVC + Thymeleaf
**Agora**: SPA em React com chamadas AJAX aos endpoints Spring Boot

O backend continua inalterado. A API já está pronta para consumo do frontend React.
