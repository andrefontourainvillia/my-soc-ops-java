<div align="center">

# 🎲 Soc Ops

### Social Bingo for in-person mixers

**Find people who match the squares. Get 5 in a row. Break the ice.**

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

[🎮 **Live Demo**](https://copilot-dev-days.github.io/agent-lab-java/) &nbsp;·&nbsp;
[📚 **Lab Guide**](workshop/GUIDE.md) &nbsp;·&nbsp;
[🌐 **Português (BR)**](README.pt_BR.md) &nbsp;·&nbsp;
[🌐 **Español**](README.es.md)

</div>

---

## What is Soc Ops?

Soc Ops is a **Social Bingo icebreaker game** designed for in-person events, workshops, and team mixers. Each player gets a randomised 5×5 board filled with prompts like *"Has lived in another country"* or *"Owns a houseplant"*. Walk the room, find people who match the squares, and be the first to fill a row, column, or diagonal.

This repository is also a **hands-on GitHub Copilot Agent Lab** — you build the game while mastering VS Code's Agent Mode, custom agents, and context engineering.

```
┌─────────────────────────────────────────────────────────────────┐
│  🎲 Soc Ops — 5×5 Social Bingo Board                           │
│                                                                  │
│  ┌──────┬──────┬──────┬──────┬──────┐                           │
│  │ Owns │Speaks│ Runs │Codes │Loves │                           │
│  │a pet │2 lang│ 5K+  │ Java │coffee│                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Lived │ Baked│ ★★★  │Plays │Green │                           │
│  │abroad│bread │ FREE │sport │ tea  │                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Night │Indoor│ WFH  │Early │Board │                           │
│  │ owl  │plant │expert│ bird │games │                           │
│  └──────┴──────┴──────┴──────┴──────┘                           │
│                                                                  │
│  Win by: row · column · diagonal                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Quick Start

**Prerequisites:** [Java 21 JDK](https://adoptium.net/) · [Maven 3.9+](https://maven.apache.org/) (or use the included wrapper)

```bash
# Clone and run
git clone <your-repo-url>
cd my-soc-ops-java/socops
./mvnw spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) — your bingo board is ready to play!

```bash
# Build & test
./mvnw clean package   # compile + package
./mvnw test            # run all unit tests
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Runtime** | Java 21 · Spring Boot 3.4.2 |
| **Frontend** | Thymeleaf · Vanilla JS · Custom CSS utilities |
| **Game Logic** | Pure-static `BoardAssembler` — no persistence needed |
| **Deploy** | GitHub Pages (auto-deploy on push to `main`) |

```
com.socops
├── data/     IcebreakerPrompts   — 24 prompts + FREE center cell
├── model/    BingoCell · PlayPhase (LOBBY|ACTIVE|VICTORY) · WinningStreak
├── service/  BoardAssembler      — assemble · flip · detect win
└── web/      BingoRestController — GET / → game UI · GET /api/bingo/fresh-board → JSON
```

---

## 📚 Lab Guide

This project is structured as a **60-minute GitHub Copilot workshop**. Each part builds on the previous one.

| Part | Title | Focus |
|:----:|-------|-------|
| [**00**](workshop/00-overview.md) | Overview & Checklist | Goals & setup checklist |
| [**01**](workshop/01-setup.md) | Setup & Context Engineering | Workspace instructions, background agents |
| [**02**](workshop/02-design.md) | Design-First Frontend | Full UI redesign with Plan Mode |
| [**03**](workshop/03-quiz-master.md) | Custom Quiz Master | Build a custom agent that generates prompts |
| [**04**](workshop/04-multi-agent.md) | Multi-Agent Development | TDD Red→Green→Refactor, Pixel Jam |

> 📝 Guides are available in the [`workshop/`](workshop/) folder for offline reading, and in [Español](workshop/es/GUIDE.md) and [Português (BR)](workshop/pt_BR/GUIDE.md).

---

## 🎭 What You'll Build

By the end of the lab you'll have:

- ✅ A fully themed, responsive bingo game running in the browser
- ✅ Custom workspace instructions that make Copilot code exactly the way you want
- ✅ A Quiz Master custom agent that generates branded icebreaker prompts
- ✅ New game modes added via TDD, guided by multi-agent workflows

---

## 🔗 Resources

- [VS Code Agent Mode docs](https://code.visualstudio.com/docs/copilot/overview)
- [GitHub Copilot docs](https://docs.github.com/copilot)
- [Awesome Copilot](https://github.com/github/awesome-copilot)
- [VS Code on YouTube](https://www.youtube.com/code)
