<!-- l10n-sync: source-file="README.md" -->
<div align="center">

# 🎲 Soc Ops

### Social Bingo para encuentros presenciales

**Encuentra personas que coincidan con los cuadros. Consigue 5 en línea. Rompe el hielo.**

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)

[🎮 **Demo en vivo**](https://copilot-dev-days.github.io/agent-lab-java/) &nbsp;·&nbsp;
[📚 **Guía del Lab**](workshop/es/GUIDE.md) &nbsp;·&nbsp;
[🌐 **English**](README.md) &nbsp;·&nbsp;
[🌐 **Português (BR)**](README.pt_BR.md)

</div>

---

## ¿Qué es Soc Ops?

Soc Ops es un **juego de Social Bingo** diseñado para eventos presenciales, talleres y encuentros de equipo. Cada jugador recibe un tablero aleatorio de 5×5 con preguntas como *"Ha vivido en otro país"* o *"Tiene una planta en casa"*. Recorre la sala, encuentra personas que coincidan con los cuadros y sé el primero en completar una fila, columna o diagonal.

Este repositorio también es un **lab práctico de GitHub Copilot Agent** — construyes el juego mientras dominas el Modo Agente de VS Code, los agentes personalizados y la ingeniería de contexto.

```
┌─────────────────────────────────────────────────────────────────┐
│  🎲 Soc Ops — Tablero de Social Bingo 5×5                       │
│                                                                  │
│  ┌──────┬──────┬──────┬──────┬──────┐                           │
│  │Tiene │Habla │Corre │Codea │Ama el│                           │
│  │masc. │2 idio│ 5K+  │ Java │ café │                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Vivió │Horneó│ ★★★  │Juega │ Té   │                           │
│  │fuera │ pan  │LIBRE │deport│verde │                           │
│  ├──────┼──────┼──────┼──────┼──────┤                           │
│  │Noc-  │Planta│Trab. │Madru-│Juegos│                           │
│  │turno │inter.│remoto│gador │ mesa │                           │
│  └──────┴──────┴──────┴──────┴──────┘                           │
│                                                                  │
│  Ganar por: fila · columna · diagonal                            │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Inicio rápido

**Requisitos:** [Java 21 JDK](https://adoptium.net/) · [Maven 3.9+](https://maven.apache.org/) (o usa el wrapper incluido)

```bash
# Clonar y ejecutar
git clone <url-de-tu-repositorio>
cd my-soc-ops-java/socops
./mvnw spring-boot:run
```

Abre [http://localhost:8080](http://localhost:8080) — ¡tu tablero de bingo está listo para jugar!

```bash
# Compilar y probar
./mvnw clean package   # compilar + empaquetar
./mvnw test            # ejecutar todas las pruebas
```

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| **Runtime** | Java 21 · Spring Boot 3.4.2 |
| **Frontend** | Thymeleaf · Vanilla JS · Utilidades CSS personalizadas |
| **Lógica del juego** | `BoardAssembler` estático — sin persistencia necesaria |
| **Despliegue** | GitHub Pages (auto-deploy en push a `main`) |

```
com.socops
├── data/     IcebreakerPrompts   — 24 preguntas + celda central FREE
├── model/    BingoCell · PlayPhase (LOBBY|ACTIVE|VICTORY) · WinningStreak
├── service/  BoardAssembler      — ensamblar · voltear · detectar victoria
└── web/      BingoRestController — GET / → UI del juego · GET /api/bingo/fresh-board → JSON
```

---

## 📚 Guía del Lab

Este proyecto es un **taller de GitHub Copilot de 60 minutos**. Cada parte se construye sobre la anterior.

| Parte | Título | Enfoque |
|:----:|-------|-------|
| [**00**](workshop/es/00-overview.md) | Descripción General y Lista | Objetivos y checklist de configuración |
| [**01**](workshop/es/01-setup.md) | Configuración e Ingeniería de Contexto | Instrucciones del workspace, agentes en segundo plano |
| [**02**](workshop/es/02-design.md) | Frontend Orientado al Diseño | Rediseño completo de UI con Modo Plan |
| [**03**](workshop/es/03-quiz-master.md) | Quiz Master Personalizado | Crea un agente que genera preguntas personalizadas |
| [**04**](workshop/es/04-multi-agent.md) | Desarrollo Multi-Agente | TDD Red→Green→Refactor, Pixel Jam |

> 📝 Las guías están disponibles en la carpeta [`workshop/es/`](workshop/es/) para lectura sin conexión.

---

## 🎭 Qué Construirás

Al finalizar el lab tendrás:

- ✅ Un juego de bingo con tema completo y responsivo en el navegador
- ✅ Instrucciones del workspace que hacen que Copilot code exactamente como quieres
- ✅ Un agente Quiz Master que genera preguntas personalizadas
- ✅ Nuevos modos de juego añadidos con TDD, guiados por flujos multi-agente

---

## 🔗 Recursos

- [Documentación del Modo Agente de VS Code](https://code.visualstudio.com/docs/copilot/overview)
- [Documentación de GitHub Copilot](https://docs.github.com/copilot)
- [Awesome Copilot](https://github.com/github/awesome-copilot)
- [VS Code en YouTube](https://www.youtube.com/code)
