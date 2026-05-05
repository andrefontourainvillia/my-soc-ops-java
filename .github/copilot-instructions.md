# Soc Ops — Workspace Instructions

## Development Checklist

Before completing any code change, run all three steps from `socops/`:

```bash
./mvnw clean package          # lint (compiler errors = lint failures)
./mvnw test                   # unit tests — must all pass
./mvnw spring-boot:run        # smoke-check: verify app starts on :8080
```

All three must succeed before a change is considered done.

## Architecture

Spring Boot 3.4.2 · Java 21 · Social Bingo icebreaker game (5×5 board, win by row/column/diagonal).

```
com.socops
├── data/     IcebreakerPrompts   — 24 prompts + FREE_CELL_LABEL (must stay exactly 24)
├── model/    BingoCell, PlayPhase (LOBBY|ACTIVE|VICTORY), WinningStreak
├── service/  BoardAssembler      — static utility: assemble, flip, detect win
└── web/      BingoRestController — GET / → "game" template · GET /api/bingo/fresh-board → JSON
```

Frontend: `game.html` (Thymeleaf shell) + vanilla JS + utility CSS (`app.css`). No frameworks.

## Conventions

- **Java records** for domain objects (`BingoCell`, `WinningStreak`). `BoardAssembler` is static — no instantiation, no bean.
- Free cell: always index 12, always pre-selected, immune to flipping.
- **CSS**: utility classes from `app.css` only — see `css-utilities.instructions.md`. No external frameworks.
- **JS**: vanilla, inline in `game.html`. JS drives transitions between `#lobbyView`, `#gameView`, `#victoryView`.
- **No persistence** unless explicitly requested. All state is client-side.

## Docs
- File-scoped instructions: `.github/instructions/`
- Workshop guide: `workshop/GUIDE.md`
