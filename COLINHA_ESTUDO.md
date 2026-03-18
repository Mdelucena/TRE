# Colinha de estudo - LogiTrack Pro

Guia rapido para revisar escolhas tecnicas, justificativas e trade-offs do projeto.

## 1) Escopo e recorte

- Escolha: Opcao 2 do desafio (CRUD de manutencao + dashboard).
- Motivo: entregar fluxo completo de negocio com valor visivel na interface.
- Impacto: MVP funcional, cobrindo operacao e indicadores.

## 2) Arquitetura em camadas

- Escolha: controller, service, repository, domain e view.
- Motivo: separar responsabilidades e facilitar evolucao.
- Impacto: manutencao mais simples e melhor organizacao do codigo.

## 3) Renderizacao server-side (Spring MVC + Thymeleaf)

- Escolha: evitar SPA no MVP.
- Motivo: reduzir complexidade e acelerar entrega.
- Impacto: menos sobrecarga de frontend, com menor interatividade em tempo real.

## 4) SQL nativo no dashboard

- Escolha: consultas nativas para as metricas obrigatorias.
- Motivo: aderencia explicita ao requisito e controle fino das queries.
- Impacto: consultas objetivas e performaticas para o cenario, com menor portabilidade entre bancos.

## 5) DTOs para camada de apresentacao

- Escolha: mapear retorno de consultas para DTOs especificos.
- Motivo: desacoplar persistencia da view e centralizar transformacoes.
- Impacto: mais codigo de mapeamento, porem com melhor clareza de contrato na UI.

## 6) H2 em memoria para ambiente local

- Escolha: H2 com compatibilidade PostgreSQL (MODE=PostgreSQL).
- Motivo: subir o projeto rapidamente sem infraestrutura externa.
- Impacto: avaliacao facil; em producao, o destino natural e PostgreSQL real.

## 7) Modelo de dominio com enums

- Escolha: enums para status, tipo de servico e categoria.
- Motivo: restringir valores de negocio validos.
- Impacto: reduz erro semantico; novos valores exigem alteracao de codigo.

## 8) Validacao no backend

- Escolha: @Valid + BindingResult nos fluxos de create/update.
- Motivo: garantir integridade antes da persistencia.
- Impacto: feedback de erro no formulario e menor risco de dado invalido.

## 9) Robustez das consultas

- Escolha: COALESCE, LEFT JOIN e filtro opcional por vehicleId.
- Motivo: evitar null e manter dashboard resiliente com dados incompletos.
- Impacto: comportamento estavel mesmo com base parcial ou sem historico completo.

## 10) Trade-offs assumidos no MVP

- Sem autenticacao/autorizacao.
- Sem testes automatizados de integracao.
- Sem esteira de deploy/container pronta.

## Pitch de 60 segundos

"Priorizei um MVP funcional e de facil avaliacao: backend em Spring Boot com Thymeleaf, CRUD completo de manutencao e dashboard com 5 metricas calculadas por SQL nativo. Estruturei o projeto em camadas para manter clareza e facilitar evolucao. Usei DTOs para separar persistencia de apresentacao e H2 em memoria para execucao imediata. O foco foi entregar valor de negocio com simplicidade tecnica, deixando seguranca, testes e deploy como evolucao natural."