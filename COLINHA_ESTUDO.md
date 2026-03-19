# Colinha de estudo - LogiTrack Pro

Guia rapido para entrevista tecnica sobre o projeto.

## 1) Escopo escolhido

- Opcao 2 do desafio: CRUD de manutencao + dashboard analitico.
- Objetivo: centralizar operacao e fornecer visibilidade para decisao.

## 2) O que foi entregue

- Backend Spring Boot em camadas.
- Frontend React (SPA) com React Router.
- Login com JWT e rotas protegidas.
- Dashboard com as 5 metricas obrigatorias via SQL.
- CRUD completo de manutencao.

## 3) Arquitetura (explicacao curta)

- Controller: expoe endpoints REST.
- Service: concentra regras de negocio.
- Repository: consultas SQL e acesso a dados.
- Domain: entidades e enums.
- DTO: contrato de resposta entre backend e frontend.
- Frontend: pages, components, services e auth context.

## 4) Decisoes tecnicas e justificativas

- React no frontend: melhor experiencia e separacao clara da API.
- JWT no backend: autenticacao stateless simples para MVP.
- SQL no dashboard: requisito pedia extracao por SQL; uso explicito para controle das metricas.
- H2 em memoria: avaliacao local rapida sem setup extra.

## 5) Metricas do dashboard (como falar)

- Total de KM percorrido: soma de mileage por frota/veiculo.
- Volume por categoria: viagens por tipo de veiculo.
- Cronograma de manutencao: proximas 5 manutencoes ordenadas.
- Ranking de utilizacao: veiculo com maior km acumulado.
- Projecao financeira: soma de custos estimados no mes atual.

## 6) Seguranca (resposta pronta)

- Login em /api/auth/login.
- Token JWT retornado pelo backend.
- Token salvo no frontend e enviado no header Authorization.
- Rotas /api/** protegidas, exceto /api/auth/**.
- Logout limpa token e sessao local.

## 7) Problemas reais que resolvemos

- Conflito de porta 8080 ocupada por processos antigos.
- Multiplas instancias de Vite consumindo portas diferentes.
- CORS limitado a localhost:3000, ajustado para localhost em qualquer porta.
- Persistencia de sessao no F5 (token e usuario reidratados).

## 8) Trade-offs do MVP

- Sem Docker e sem deploy em cloud (diferencial, nao obrigatorio).
- Sem suite completa de testes automatizados.
- Foco em entregar requisito funcional com boa organizacao.

## 9) Perguntas comuns e respostas curtas

Por que nao Docker?
- Priorizamos requisitos obrigatorios e diferenciais de maior impacto funcional (React + autenticacao).

Por que H2 e nao PostgreSQL?
- H2 acelera setup da banca; estrutura esta preparada para migracao posterior.

Como garantir manutencao do codigo?
- Camadas bem definidas, DTOs para contratos e separacao frontend/backend.

## 10) Pitch de 60 segundos

"Entregamos um MVP completo para gestao de manutencao de frota com dashboard de inteligencia. O backend foi construido em Spring Boot com arquitetura em camadas e consultas SQL para as metricas exigidas. O frontend foi modernizado com React, incluindo autenticacao JWT, rotas protegidas e logout. Priorizamos valor funcional e clareza tecnica para avaliacao, mantendo o projeto pronto para evoluir com testes, Docker e deploy em uma segunda etapa." 