# LogiTrack Pro

Aplicacao web desenvolvida como MVP para o desafio tecnico da LogAp/TRE, com foco em gestao de manutencao de frota e consolidacao de metricas operacionais.

## Objetivo

Implementar a Opcao 2 do desafio: modulo de agendamento de manutencao com CRUD completo, integrado a um dashboard com indicadores de negocio.

## Funcionalidades

- Cadastro, listagem, edicao e exclusao de manutencoes.
- Dashboard com 5 metricas obrigatorias, calculadas por SQL nativo:
  - Total de quilometragem percorrida (frota inteira ou por veiculo).
  - Volume de viagens por categoria (LEVE/PESADO).
  - Proximas 5 manutencoes agendadas.
  - Ranking de utilizacao (veiculo com maior quilometragem acumulada).
  - Projecao financeira de manutencao no mes atual.

## Stack Tecnica

- Java 17
- Spring Boot 3.3.5
- Spring MVC
- Thymeleaf
- Spring Data JPA
- H2 Database (ambiente local/demo)
- Maven

## Arquitetura

Estrutura em camadas para separacao de responsabilidades:

- controller: roteamento web e fluxo de navegacao.
- service: composicao de regras e agregacao de dados para a camada de apresentacao.
- repository: acesso a dados e consultas SQL nativas.
- domain: entidades JPA e enums de negocio.
- templates/static: views Thymeleaf e recursos estaticos (CSS).

## Banco de Dados

Inicializacao automatica via scripts:

- src/main/resources/schema.sql
- src/main/resources/data.sql

Observacoes:

- O desafio original apresenta nomenclatura em portugues para tabelas.
- Neste projeto, o schema foi padronizado em ingles para alinhamento com entidades JPA e melhor manutencao.
- A carga seed preserva o cenario funcional necessario para demonstrar as metricas.

## Execucao Local

Prerequisitos:

- Java 17
- Maven 3.9+

Comando:

```bash
cd "C:\Users\mateu\Desktop\projeto tre"
mvn clean spring-boot:run
```

## Endpoints e Acessos

- Aplicacao: http://localhost:8080
- Dashboard: http://localhost:8080/dashboard
- Manutencoes (CRUD): http://localhost:8080/manutencoes
- Console H2: http://localhost:8080/h2-console

Configuracao H2:

- JDBC URL: jdbc:h2:mem:logitrack;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
- User: sa
- Password: (vazio)

## Decisoes Tecnicas Relevantes

- Uso de SQL nativo no dashboard para aderencia ao requisito e maior controle das consultas.
- Uso de H2 em memoria para facilitar execucao imediata em contexto de avaliacao tecnica.
- Filtro opcional por veiculo na quilometragem total para comparacao entre visao agregada e individual.

## Limites do MVP

- Sem autenticacao/autorizacao.
- Sem suite de testes automatizados de integracao.
- Sem pipeline de containerizacao/deploy.

## Proximos Passos

- Adicionar autenticacao e autorizacao com Spring Security.
- Cobrir consultas criticas com testes de integracao.
- Containerizar aplicacao com Docker Compose.
- Publicar ambiente de demonstracao em nuvem.

## Material de Estudo

A colinha de apresentacao com racional de arquitetura e trade-offs esta em:

- COLINHA_ESTUDO.md
