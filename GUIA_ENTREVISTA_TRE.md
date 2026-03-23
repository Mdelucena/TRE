# Guia de Entrevista - Projeto TRE (LogiTrack Pro)

## 1) Pitch rapido (30-60s)

"Eu desenvolvi um MVP de gestao de frota chamado LogiTrack Pro para o desafio TRE/LogAp. A solucao tem backend em Java 17 com Spring Boot e frontend em React. Entreguei autenticacao JWT, CRUD de manutencao e dashboard analitico com metricas SQL de negocio. A arquitetura foi separada em camadas no backend e SPA no frontend, com comunicacao via API REST."

## 2) Problema de negocio que o projeto resolve

- Organiza o ciclo de manutencao de veiculos (agendar, acompanhar, concluir).
- Mostra indicadores operacionais para decisao rapida.
- Reduz visao fragmentada entre operacao e manutencao.

## 3) Arquitetura (como explicar)

### Backend (Java + Spring Boot)

- `controller`: endpoints REST (e controllers MVC legados com Thymeleaf).
- `service`: regras de negocio e orquestracao.
- `repository`: acesso a dados com Spring Data JPA + queries nativas SQL.
- `domain`: entidades e enums.
- `service/dto` + `mapper`: contrato da API desacoplado das entidades.

### Frontend (React + Vite)

- SPA com React Router.
- `context/AuthContext.jsx` para estado global de autenticacao.
- `services/api.js` e `services/authService.js` para integracao HTTP com Axios.
- `pages` para telas e `components` para blocos reutilizaveis.

### Banco e migracoes

- Flyway para versionamento de schema e dados iniciais.
- Modo local com H2 em memoria (rapido para avaliacao).
- Estrutura de migration separada por ambiente (`common`, `h2`, `postgresql`).

## 4) Java/Spring: pontos tecnicos para defender

### 4.1 Seguranca e autenticacao

- Spring Security em modo stateless (`SessionCreationPolicy.STATELESS`).
- Login e cadastro em `/api/auth/**` liberados.
- Demais `/api/**` protegidos por JWT.
- Senhas com hash BCrypt.
- `JwtAuthenticationFilter` processa token antes do fluxo padrao de autenticacao.

Resumo do fluxo:

1. Front envia usuario/senha para `/api/auth/login`.
2. `AuthService` valida credenciais e gera JWT.
3. Front salva token em `localStorage`.
4. Axios envia `Authorization: Bearer <token>` em todas as chamadas.
5. Backend valida token e autoriza endpoints protegidos.

### 4.2 API e padrao de resposta

- Endpoints REST para dashboard, manutencao e veiculos.
- Uso de DTO para evitar expor entidade diretamente.
- Mapper dedicado para transformar entidade <-> DTO.
- Retornos HTTP coerentes (`200`, `201`, `204`, `404`).

### 4.3 Dashboard com SQL (requisito do desafio)

Metricas implementadas por queries nativas no `DashboardRepository`:

- Total de veiculos.
- Contagem de manutencoes por status.
- Distribuicao por categoria de veiculo.
- Ultimas 5 manutencoes (timeline).
- Ranking de utilizacao (top 1 por quilometragem).
- (Tambem existe projecao financeira mensal no repositorio.)

Argumento forte na entrevista:

"Eu usei SQL nativo no dashboard para garantir aderencia direta aos requisitos de negocio e controle total das agregacoes."

### 4.4 Validacao e consistencia

- `@Valid` nos requests de login/cadastro e manutencao.
- Verificacao de existencia antes de update/delete.
- Regras de negocio em service e nao no controller.

## 5) Integracao backend + frontend (ponta a ponta)

## Fluxo de login

1. Usuario autentica na tela de Login.
2. Front chama `authService.login`.
3. Resposta retorna token + dados do usuario.
4. `AuthContext` persiste token e user no `localStorage`.
5. `App.jsx` sincroniza token no Axios (`setAuthToken` e `setApiToken`).
6. Rotas privadas ficam acessiveis.

## Fluxo de manutencao

1. Front busca veiculos (`GET /api/vehicles`) para formulario.
2. Cria ou edita manutencao (`POST/PUT /api/maintenance`).
3. Backend valida e persiste via repository.
4. Front atualiza lista com `GET /api/maintenance`.

## Fluxo de dashboard

1. Front chama `GET /api/dashboard`.
2. Backend agrega dados SQL e monta DTO consolidado.
3. Front renderiza cards, grafico e timeline.

## 6) Decisoes tecnicas (como justificar)

- Java + Spring Boot: produtividade, padrao enterprise e ecossistema maduro.
- JWT: autenticacao stateless, simples para SPA.
- React (Vite): melhor UX e separacao de responsabilidades.
- Flyway: historico confiavel de schema e reproducao de ambiente.
- H2 em dev: setup rapido para avaliacao local.

## 7) Trade-offs e limites do MVP (importante falar)

- Sem deploy em nuvem e sem Docker neste escopo.
- Cobertura de testes ainda basica (espaco para ampliar unitario/integracao/E2E).
- Token em `localStorage` (pratico para MVP, mas em producao avaliaria cookie HttpOnly + hardening).
- Parte MVC/Thymeleaf ainda existe em paralelo ao React (migracao incremental).

## 8) Perguntas comuns de entrevista + resposta sugerida

## "Por que separar DTO de entidade?"

"Para evitar acoplamento da API ao modelo de persistencia, controlar campos expostos e facilitar evolucao sem quebrar clientes."

## "Como voce protegeu as rotas?"

"No backend, `/api/**` exige autenticacao e o JWT e validado por filtro. No frontend, o estado de auth controla acesso as rotas privadas."

## "Por que SQL nativo no dashboard?"

"As metricas eram analiticas e agregadas. SQL nativo trouxe clareza, performance previsivel e aderencia direta ao requisito."

## "Como garantiria escalabilidade?"

"Separaria melhor leitura analitica de escrita transacional, criaria cache para dashboard, revisaria indices e evoluiria para observabilidade e deploy containerizado."

## "O que voce melhoraria primeiro?"

"Testes automatizados ponta a ponta, refresh token/expiracao robusta, e pipeline CI/CD com validacoes de qualidade."

## 9) Roteiro de demonstracao (5 minutos)

1. Mostrar login e explicar JWT.
2. Criar manutencao e listar itens.
3. Editar e excluir manutencao.
4. Abrir dashboard e explicar cada metrica.
5. Fechar com arquitetura e proximos passos.

## 10) Checklist final antes da entrevista

- Revisar endpoints principais e seus metodos HTTP.
- Decorar 3 decisoes tecnicas e 3 trade-offs.
- Treinar pitch de 60 segundos.
- Ter exemplos reais de melhoria futura (testes, seguranca, deploy).
- Saber explicar claramente o fluxo completo: UI -> API -> service -> repository -> banco.

---

## Frases curtas que passam maturidade

- "Eu priorizei entregar valor de negocio com base tecnica limpa para evolucao."
- "As escolhas foram orientadas por escopo de MVP, com plano claro de hardening para producao."
- "Separei responsabilidades para facilitar manutencao, teste e escalabilidade futura."
