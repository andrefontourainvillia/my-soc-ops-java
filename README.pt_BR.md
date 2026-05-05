<!-- l10n-sync: source-file="README.md" -->
<div align="center">

# 🎲 Soc Ops

### Social Bingo para encontros presenciais

**Encontre pessoas que correspondam aos quadros. Faça 5 em linha. Quebre o gelo.**

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

[🎮 **Demo ao vivo**](https://copilot-dev-days.github.io/agent-lab-java/) &nbsp;·&nbsp;
[📚 **Guia do Lab**](workshop/pt_BR/GUIDE.md) &nbsp;·&nbsp;
[🌐 **English**](README.md) &nbsp;·&nbsp;
[🌐 **Español**](README.es.md)

</div>

---

## O que é o Soc Ops?

O Soc Ops é um **jogo de Social Bingo** projetado para eventos presenciais, workshops e encontros de equipe. Cada jogador recebe um tabuleiro aleatório de 5×5 com perguntas como *"Já morou em outro país"* ou *"Tem uma planta em casa"*. Circule pela sala, encontre pessoas que correspondam aos quadros e seja o primeiro a completar uma linha, coluna ou diagonal.

Este repositório também é um **lab prático de GitHub Copilot Agent** — você constrói o jogo enquanto domina o Modo Agente do VS Code, agentes personalizados e engenharia de contexto.

```
┌─────────────────────────────────────────────────────────────────┐
│  🎲 Soc Ops — Tabuleiro de Social Bingo 5×5                    │
│                                                                  │
│  ┌──────┬──────┬──────┬──────┬──────┐                           │
│  │Tem   │Fala  │Corre │Codifica│Ama  │                           │
│  │animal│2 idiom│ 5K+ │ Java │ café │                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Morou │Fez   │ ★★★  │Pratica│Chá  │                           │
│  │fora  │ pão  │LIVRE │esporte│verde│                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Coruja│Planta│Home  │Matut.│Jogos │                           │
│  │noturna│int. │office│ador  │mesa  │                           │
│  └──────┴──────┴──────┴──────┴──────┘                           │
│                                                                  │
│  Ganhar por: linha · coluna · diagonal                           │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Início Rápido

**Pré-requisitos:** [Java 21 JDK](https://adoptium.net/) · [Maven 3.9+](https://maven.apache.org/) (ou use o wrapper incluído)

```bash
# Clonar e executar
git clone <url-do-seu-repositório>
cd my-soc-ops-java/socops
./mvnw spring-boot:run
```

Abra [http://localhost:8080](http://localhost:8080) — seu tabuleiro de bingo está pronto para jogar!

```bash
# Build e testes
./mvnw clean package   # compilar + empacotar
./mvnw test            # executar todos os testes
```

---

## 🛠️ Stack Tecnológico

| Camada | Tecnologia |
|--------|-----------|
| **Runtime** | Java 21 · Spring Boot 3.4.2 |
| **Frontend** | Thymeleaf · Vanilla JS · Utilitários CSS personalizados |
| **Lógica do jogo** | `BoardAssembler` estático — sem persistência necessária |
| **Deploy** | GitHub Pages (auto-deploy no push para `main`) |

```
com.socops
├── data/     IcebreakerPrompts   — 24 perguntas + célula central FREE
├── model/    BingoCell · PlayPhase (LOBBY|ACTIVE|VICTORY) · WinningStreak
├── service/  BoardAssembler      — montar · virar · detectar vitória
└── web/      BingoRestController — GET / → UI do jogo · GET /api/bingo/fresh-board → JSON
```

---

## 📚 Guia do Lab

Este projeto é um **workshop de GitHub Copilot de 60 minutos**. Cada parte se apoia na anterior.

| Parte | Título | Foco |
|:----:|-------|-------|
| [**00**](workshop/pt_BR/00-overview.md) | Visão Geral & Lista Rápida | Objetivos e checklist de configuração |
| [**01**](workshop/pt_BR/01-setup.md) | Configuração & Engenharia de Contexto | Instruções do workspace, agentes em segundo plano |
| [**02**](workshop/pt_BR/02-design.md) | Frontend Design-First | Redesign completo da UI com Modo Plano |
| [**03**](workshop/pt_BR/03-quiz-master.md) | Quiz Master Personalizado | Crie um agente que gera perguntas personalizadas |
| [**04**](workshop/pt_BR/04-multi-agent.md) | Desenvolvimento Multi-Agente | TDD Red→Green→Refactor, Pixel Jam |

> 📝 Os guias estão disponíveis na pasta [`workshop/pt_BR/`](workshop/pt_BR/) para leitura offline.

---

## 🎭 O que Você Vai Construir

Ao final do lab você terá:

- ✅ Um jogo de bingo com tema completo e responsivo no navegador
- ✅ Instruções do workspace que fazem o Copilot codar exatamente do jeito que você quer
- ✅ Um agente Quiz Master que gera perguntas personalizadas
- ✅ Novos modos de jogo adicionados com TDD, guiados por fluxos multi-agente

---

## 🔗 Recursos

- [Documentação do Modo Agente do VS Code](https://code.visualstudio.com/docs/copilot/overview)
- [Documentação do GitHub Copilot](https://docs.github.com/copilot)
- [Awesome Copilot](https://github.com/github/awesome-copilot)
- [VS Code no YouTube](https://www.youtube.com/code)
