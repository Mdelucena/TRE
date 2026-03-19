# LogiTrack Pro

MVP web para o desafio LogAp/TRE, com foco em gestao de manutencao de frota e dashboard analitico com metricas SQL.

## Objetivo do desafio

Implementar a Opcao 2:

- Modulo de Agendamento de Manutencao (CRUD).
- Dashboard de analise com consultas SQL para indicadores de negocio.

## Funcionalidades entregues

- Login com autenticacao JWT.
- CRUD completo de manutencoes (criar, listar, editar, excluir).
- Dashboard com as metricas obrigatorias do desafio.
- Filtro e visualizacao por tipo de dado operacional.
- Logout e protecao de rotas no frontend.

## Mapeamento dos requisitos do dashboard

As 5 metricas obrigatorias estao implementadas via SQL no backend:

- Total de KM percorrido: soma da quilometragem (frota ou por veiculo).
- Volume por Categoria: quantidade de viagens por tipo de veiculo (LEVE/PESADO).
- Cronograma de Manutencao: proximas 5 manutencoes ordenadas por data.
- Ranking de Utilizacao: veiculo com maior quilometragem acumulada.
- Projecao Financeira: soma dos custos estimados de manutencao no mes atual.

## Stack tecnica

Backend:

- Java 17
- Spring Boot 3.3.5
- Spring Data JPA
- Spring Security
- JWT (JJWT)
- Flyway
- OpenAPI (SpringDoc)
- Maven
- H2 Database (memoria)

Frontend:

- React (Vite)
- React Router
- Axios

## Arquitetura

Backend em camadas:

- controller: endpoints REST.
- service: regras de negocio e orquestracao.
- repository: acesso a dados e queries SQL.
- domain: entidades e enums.
- service/dto: contratos de resposta da API.

Frontend SPA:

- pages: telas principais (login, dashboard, manutencoes).
- components: navegacao e componentes reutilizaveis.
- services: integracao HTTP com backend.
- context: estado global de autenticacao.

## Banco de dados

Versionamento por Flyway:

- src/main/resources/db/migration/common
- src/main/resources/db/migration/h2
- src/main/resources/db/migration/postgresql

Configuracao local (H2):

- JDBC URL: jdbc:h2:mem:logitrack;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
- User: sa
- Password: vazio

## Como rodar localmente

Prerequisitos:

- Java 17+
- Maven 3.9+
- Node.js 18+
- npm

1) Subir backend (dev)

```bash
cd "C:\Users\mateu\Desktop\projeto tre"
mvn -Pdev spring-boot:run
```

Para rodar em prod:

```bash
mvn -Pprod spring-boot:run
```

2) Subir frontend

```bash
cd "C:\Users\mateu\Desktop\projeto tre\frontend"
npm install
npm run dev
```

3) Acessar aplicacao

- Frontend: http://localhost:3000
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console

## Credenciais de teste

- Usuario: admin
- Senha: admin

## Endpoints principais

- POST /api/auth/login
- POST /api/auth/register
- GET /api/dashboard
- GET /api/maintenance
- POST /api/maintenance
- PUT /api/maintenance/{id}
- DELETE /api/maintenance/{id}
- GET /api/vehicles

## Decisoes tecnicas

- SQL no dashboard para aderencia direta ao requisito do desafio.
- React no frontend para melhor experiencia e separacao da UI.
- JWT para autenticacao stateless simples e objetiva para MVP.
- H2 em memoria para facilitar avaliacao local sem dependencias externas.

## Diferenciais implementados

- Frontend moderno com React.
- Seguranca com login e autenticacao.

## Limites do MVP

- Sem deploy em nuvem.
- Sem Docker.
- Sem suite completa de testes automatizados.

## Demonstracao

Os prints da entrega estao na pasta:

- DOC/prints

Conteudo:

- Prints das funcionalidades principais do sistema.
- Prints do terminal com subida da aplicacao e execucao de testes.

## Troubleshooting rapido

Se der erro de porta 8080 ocupada:

```powershell
taskkill /F /IM java.exe
```

Se houver varias instancias de Vite:

```powershell
taskkill /F /IM node.exe
```

Depois suba novamente backend e frontend.

