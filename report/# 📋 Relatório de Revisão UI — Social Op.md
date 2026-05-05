# 📋 Relatório de Revisão UI — Social Ops Bingo

**Data**: Maio 5, 2026  
**Projeto**: Social Ops — Social Bingo Icebreaker Game  
**Escopo**: Análise completa de UX/UI — Lobby, Game Board, Victory Screen, Design Responsivo  
**Status**: ✅ Revisão Concluída  

---

## 📊 Resumo Executivo

| Categoria | Avaliação | Status |
|-----------|-----------|--------|
| **Design Visual & Estética** | ✅ **Excelente** | Tema coerente, tipografia forte, gradientes bem executados |
| **Clareza de Interação** | 🟡 **Marginal** | Mudanças de estado muito sutis; feedback dependente de animação |
| **Responsividade Mobile** | 🟡 **Incompleta** | Breakpoint único em 640px; lacunas tablet (768px) e escala tipografia |
| **Acessibilidade** | 🔴 **Crítica** | Falta outlines focus, suporte teclado, semântica modal |
| **Celebração & Feedback** | 🟡 **Contida** | Elegante mas sub-celebrando vs. Wordle/Duolingo |

---

## 🔴 CRÍTICO — Implementar Imediatamente

### 1️⃣ Falta Acessibilidade Teclado & Keyboard Navigation

**Problema**:
- Sem outlines `:focus-visible` em botões/tiles
- Modal victory sem suporte Escape key
- Sem focus trap (usuário pode tabular pra fora do modal)
- Victory modal faltam atributos ARIA (`role`, `aria-modal`, `aria-labelledby`)

**Impacto**: 🔴 **Crítico**
- Usuários navegando por teclado não veem onde estão (WCAG 2.1 Failure)
- Leitores de tela não reconhecem modal como dialog
- Tempo de implementação: 15 min

**Como Reproduzir**:
1. Abrir app em `http://localhost:8080`
2. Pressionar `Tab` 3-5 vezes para navegar entre botões
3. ❌ **Esperado**: Outline visível (ex: `2px solid #00ff88`)
4. ❌ **Real**: Nenhuma indicação visual qual elemento tem foco

**Localização no Código**:
- Template: `socops/src/main/resources/templates/game.html` (linhas 800-825)
- CSS: `socops/src/main/resources/static/css/app.css` (linhas 150-170)

**Evidência Visual**:
```html
<!-- game.html linhas ~800-820 ❌ Atual -->
<div id="victoryOverlay" class="victory-backdrop fixed inset-0...">
    <div class="victory-card text-center">
        <button onclick="dismissVictoryModal()" class="start-btn">
            Continue  <!-- Sem suporte Enter/Space/Escape -->
        </button>
    </div>
</div>
```

**Correção Esperada**:
```html
<!-- ✅ Correto -->
<div id="victoryOverlay" 
     class="victory-backdrop..."
     role="dialog"
     aria-modal="true"
     aria-labelledby="victoryTitle">
    <div class="victory-card">
        <div class="victory-glyph" aria-hidden="true">★</div>
        <h2 id="victoryTitle">BINGO!</h2>
        <button>Continue</button>
    </div>
</div>
```

```css
/* ✅ Adicionar em app.css */
button:focus-visible,
.tile:focus-visible,
.hunt-item:focus-visible {
    outline: 2px solid var(--accent);
    outline-offset: 2px;
}
```

---

### 2️⃣ Mudanças de Estado de Tile Imperceptíveis

**Problema**:
- Tile marcado: `rgba(0,255,136,0.06)` = 6% opacidade verde = **quase invisível**
- Tile vencedor: `rgba(255,200,50,0.09)` = 9% opacidade ouro = **fácil confundir**
- Único feedback visual é animação pop de 0.22s

**Impacto**: 🔴 **Crítico**
- Se animação for desabilitada ou perdida, usuário não vê mudança de estado
- Acessibilidade: usuários com problemas visuais não detectam marcação
- Confiança na interface diminui

**Como Reproduzir**:
1. Iniciar jogo em modo Bingo
2. Clicar em um tile (quadrado do grid)
3. ❌ **Esperado**: Mudança óbvia de cor/borda/fundo
4. ❌ **Real**: Apenas animação pop visível; cor imperceptível

**Localização no Código**:
- CSS: app.css (linhas 305-340)

**Comparação de Opacidades**:
```
Idle (padrão):
  Background: var(--bg-2) = #0d0f1a

Marked (atual):
  Background: rgba(0,255,136, 0.06)
  ≈ #0d1515 em hex
  Diferença: Imperceptível! ❌

Marked (recomendado):
  Background: rgba(0,255,136, 0.15)
  ≈ #0d1f1d em hex
  Diferença: Notável! ✅

Winner (atual):
  Background: rgba(255,200,50, 0.09)
  ≈ #0e0e0a em hex
  Confunde com marked! ❌

Winner (recomendado):
  Background: rgba(255,200,50, 0.20)
  ≈ #0f0f07 em hex
  Claramente diferente! ✅
```

**Evidência no Código**:
```css
/* app.css linhas ~305-340 ❌ Atual */
.tile-idle {
    background: var(--bg-2);           /* #0d0f1a */
    border-color: var(--border);       /* #1d2235 */
}

.tile-marked {
    background: rgba(0,255,136,0.06); /* ❌ 6% = imperceptível */
    border-color: rgba(0,255,136,0.4);
    animation: tileSelect 0.22s ease both;
}

.tile-winner {
    background: rgba(255,200,50,0.09); /* ❌ 9% = confunde */
    border-color: rgba(255,200,50,0.52);
}
```

**Correção**:
```css
/* ✅ Aumentar opacidade */
.tile-marked {
    background: rgba(0,255,136,0.15);  /* ✅ 6% → 15% */
}

.tile-winner {
    background: rgba(255,200,50,0.20); /* ✅ 9% → 20% */
}
```

---

### 3️⃣ Modal Victory Sem Semântica ARIA

**Problema**:
- Faltam atributos ARIA: `role="dialog"`, `aria-modal="true"`, `aria-labelledby`
- Ícone decorativo (★) sem `aria-hidden="true"`
- Sem focus trap (foco pode sair do modal)

**Impacto**: 🔴 **Crítico (Acessibilidade)**
- Leitores de tela não reconhecem como dialog
- Navegação via teclado quebrada
- Falha em conformidade WCAG 2.1

**Localização no Código**:
- game.html (linhas 800-825)

**HTML Atual** ❌:
```html
<div id="victoryOverlay" class="victory-backdrop...">
    <div class="victory-card text-center">
        <div class="victory-glyph">★</div>
        <h2 id="victoryTitle" class="victory-title">BINGO!</h2>
        <p id="victorySubtitle" class="victory-subtitle">Line Completed</p>
        <button onclick="dismissVictoryModal()">Continue</button>
    </div>
</div>
```

**HTML Correto** ✅:
```html
<div id="victoryOverlay"
     class="victory-backdrop..."
     role="dialog"
     aria-modal="true"
     aria-labelledby="victoryTitle"
     aria-describedby="victorySubtitle">
    <div class="victory-card text-center">
        <div class="victory-glyph" aria-hidden="true">★</div>
        <h2 id="victoryTitle">BINGO!</h2>
        <p id="victorySubtitle">Line Completed</p>
        <button onclick="dismissVictoryModal()">Continue</button>
    </div>
</div>
```

---

## 🟡 ALTA PRIORIDADE — Esta Semana

### 4️⃣ Tipografia Não Escala Responsivamente

**Problema**:
- Título: `font-size: 3.8rem` (constante em todos viewports)
- Eyebrows: `letter-spacing: 0.38em` (cria "rios" em mobile)
- Subtítulo: `letter-spacing: 0.44em` (pior ainda)
- Sem breakpoints para 320px, 375px, 480px

**Impacto**: 🟡 **Alto**
- 375px iPhone: Título 3.8rem ocupa ~60px altura = engole espaço
- Eyebrow spacing torna texto ilegível ("W e l c o m e")
- Mobile 320px: Texto transborda ou quebra fora de proporção

**Como Reproduzir**:
1. Abrir DevTools (F12)
2. Clicar ícone "device toggle" ou Ctrl+Shift+M
3. Redimensionar para 375px width
4. ❌ **Real**: Título muito grande, espaçamento cria linhas ruins

**Localização no Código**:
- game.html — CSS inline (linhas 20-50)

**Evidência Visual - Tipografia Atual**:
```
Desktop (≥1024px):
┌─────────────────────────┐
│   Welcome to            │  (0.6rem, ok)
│   SOC OPS               │  (3.8rem, dominante)
│ Choose your mission     │  (0.65rem, ok)
└─────────────────────────┘

Mobile (375px):
┌──────────────────┐
│ W e l c o m e   │  ← letter-spacing 0.38em = visível gap
│ SOC OPS (huge!)  │  ← 3.8rem overflow
│ C h o o s e ..   │  ← letter-spacing 0.44em
└──────────────────┘
```

**Breakpoint Faltante**:
```css
/* ❌ Não existe em app.css */

/* Recomendado: adicionar */
@media (max-width: 480px) {
    .lobby-title {
        font-size: 2.4rem;    /* ✅ de 3.8rem */
    }
    
    .lobby-eyebrow,
    .lobby-subtitle {
        letter-spacing: 0.26em;  /* ✅ de 0.38-0.44em */
    }
    
    .scavenger-text {
        font-size: 0.78rem;   /* ✅ de 0.86rem */
    }
}
```

---

### 5️⃣ Touch Targets Undersized (< 44px WCAG)

**Problema**:
- Scavenger Hunt checkbox: `width: 1.05rem` = **16.8px** (mínimo 44px)
- Back button: `padding: 0.35rem 0.7rem` = **~20px altura** (mínimo 44px)

**Impacto**: 🟡 **Alto**
- Usuários móveis erram cliques em ~30-40% das tentativas (estudos UX)
- Especialmente problema para users com artrite, tremor, crianças
- Touch target = (largura mínimo) × (altura mínimo) ≥ 44×44px (WCAG 2.1)

**Localização no Código**:
- app.css (linhas 360-375, 240-260)

**Comparação**:
```
Checkbox (atual):
  1.05rem × 1.05rem = 16.8px × 16.8px
  Tamanho mínimo: 44px × 44px
  Déficit: -27.2px ❌

Back Button (atual):
  Altura: 0.35rem (top) + fonte + 0.35rem (bottom) = ~20px
  Mínimo: 44px
  Déficit: -24px ❌

Grid Tiles (OK):
  60px × 60px ✅ (acima de 44px)

Hunt Item:
  Altura: 0.9rem padding + texto = ~40px
  Borda: ~40px ❌ (abaixo de 44px ideal)
```

**Evidência no Código**:
```css
/* app.css linhas ~360-375 ❌ */
.hunt-item__checkbox {
    width: 1.05rem;    /* ❌ 16.8px */
    height: 1.05rem;   /* ❌ 16.8px */
}

/* app.css linhas ~240-260 ❌ */
.back-btn {
    padding: 0.35rem 0.7rem;  /* ❌ ~5px top/bottom */
    font-size: 0.58rem;
    min-width: 4rem;
}
```

**Recomendação**:
```css
/* ✅ Aumentar tamanho tap */
.hunt-item {
    padding: 1rem 1.25rem;    /* ✅ de 0.9rem 1rem */
    min-height: 44px;          /* ✅ explícito */
}

.hunt-item__checkbox {
    width: 1.5rem;    /* ✅ de 1.05rem = 24px */
    height: 1.5rem;   /* ✅ de 1.05rem = 24px */
}

.back-btn {
    padding: 0.5rem 1rem;     /* ✅ de 0.35rem 0.7rem */
    min-height: 44px;          /* ✅ explícito */
}
```

---

### 6️⃣ Animação de Celebração Sub-energizada

**Problema**:
- Victory pulse: 2 segundos (lento)
- Sem escala do título (fica fixo)
- Glow drop-shadow: opacidade 0.5 (suave demais)
- Falta "explosão" visual vs. Wordle/Duolingo

**Impacto**: 🟡 **Alto**
- Celebração não desperta dopamina
- Momento de vitória sente tímido vs. competidores
- Usuários não sentem vitória "massuda"

**Como Reproduzir**:
1. Vencer um jogo Bingo (completar 1 linha)
2. ❌ **Esperado**: Explosão visual! Glow brilhante, escala título, talvez partículas
3. ❌ **Real**: Pulsação lenta, glow suave

**Localização no Código**:
- CSS keyframe: app.css (linhas 220-235)
- CSS aplicado: `.victory-title`, `.victory-glyph` (linhas 665-680)

**Comparação com Competidores**:
```
Wordle (referência):
  Animation: 0.6s burst
  - Confetti: 1.2s scatter
  - Scale: 1.0 → 1.2 → 1.0
  - Glow: 0 → bright → 0
  Resultado: Explosão de energia! 🎉

Soc Ops (atual):
  Animation: 2s pulsação lenta
  - Scale: none
  - Glow: 20px → 45px → 20px (lento)
  - Drop-shadow: opacidade 0.5
  Resultado: Suave, gentil 😊 (não suficiente)
```

**Evidência no Código**:
```css
/* app.css linhas ~220-235 ❌ */
@keyframes victoryPulse {
    0%, 100% {
        text-shadow: 0 0 20px rgba(0,255,136,0.35);  /* Suave */
    }
    50% {
        text-shadow: 0 0 45px rgba(0,255,136,0.75), 
                     0 0 90px rgba(0,255,136,0.25);
    }
}

/* app.css linhas ~665-680 ❌ */
.victory-title {
    animation: victoryPulse 2s ease-in-out infinite;  /* 2s = lento */
    /* ❌ Sem transform scale */
}

.victory-glyph {
    filter: drop-shadow(0 0 10px rgba(255,200,50,0.5));  /* 0.5 = suave */
}
```

**Recomendação**:
```css
/* ✅ Keyframe mais energético */
@keyframes victoryExplode {
    0% {
        transform: scale(1);
        text-shadow: 0 0 20px rgba(0,255,136,0.35);
    }
    50% {
        transform: scale(1.15);  /* ✅ Escala título */
        text-shadow: 0 0 60px rgba(0,255,136,0.9),  /* ✅ Mais brilho */
                     0 0 120px rgba(0,255,136,0.4);
    }
    100% {
        transform: scale(1);
        text-shadow: 0 0 20px rgba(0,255,136,0.35);
    }
}

.victory-title {
    animation: victoryExplode 1.5s ease-in-out infinite;  /* ✅ 1.5s */
}

.victory-glyph {
    filter: drop-shadow(0 0 15px rgba(255,200,50,0.9)); /* ✅ 0.9 opacidade */
}
```

---

### 7️⃣ Texto Botão Ambíguo ("Continue")

**Problema**:
- Botão mostra "Continue"
- Usuário não sabe: volta ao lobby? Joga novamente? Próximo nível?

**Impacto**: 🟡 **Alto**
- UX confusion: usuário deve experimentar para saber
- Tempo para ação aumenta
- Taxa de clique pode diminuir

**Localização no Código**:
- game.html (linhas 815-820)

**HTML Atual** ❌:
```html
<button onclick="dismissVictoryModal()" class="start-btn">
    Continue  <!-- Ambíguo -->
</button>
```

**Fluxo Real**:
```
Usuário clica "Continue"
↓
Modal desaparece
↓
Board ainda visível com linha vencedora destacada
↓
❌ Usuário pensa: "O que agora?"
↓
Usuário deve clicar "Back" manualmente para voltar ao lobby
```

**Opção 1 - Clareza Imediata** ✅:
```html
<button onclick="returnToLobby()" class="start-btn">
    Voltar ao Lobby
</button>
```

**Opção 2 - Permitir Novo Jogo** ✅:
```html
<button onclick="playAgain()" class="start-btn">
    Jogar Novamente
</button>
```

---

## 🟠 MÉDIA PRIORIDADE — Próximo Sprint

### 8️⃣ Breakpoint Tablet 768px Faltando

**Problema**:
- Único breakpoint em `@media (max-width: 640px)`
- Salta direto para desktop
- Tablet (768px) usa estilos mobile = undersized/inadequate

**Impacto**: 🟠 **Médio**
- iPad em modo portrait: 768px width usa mobile styles
- Shuffle card: 18.5rem height (mobile) em 768px fica pequeno
- Hunt list: max-w-md deixa ~160px de gutter vazio em tablet

**Localização no Código**:
- app.css (linhas 630-665)

**Breakpoint Faltante**:
```css
/* ❌ Não existe */

/* ✅ Recomendado adicionar */
@media (min-width: 641px) and (max-width: 1023px) {
    .shuffle-card {
        min-height: 22rem;  /* ✅ volta para desktop height */
    }
    
    .hunt-shell,
    .hunt-list {
        max-width: 32rem;   /* ✅ expande um pouco */
    }
}
```

---

### 9️⃣ Scavenger Hunt Sem Celebração Escalada

**Problema**:
- Vencer Bingo (1 linha): modal victory
- Vencer Scavenger (24/24 prompts): **mesmo modal**
- Semanticamente Scavenger é conquista **maior** (100% complete vs. 1 linha)

**Impacto**: 🟠 **Médio**
- Scavenger Hunt sente não celebrado proporcionalmente
- Usuário não sente diferença de dificuldade

**Localização no Código**:
- game.html (linhas 1050-1070)
- Victory logic: `victoryTitle` mesmo para ambos

**Recomendação**:
```javascript
// Escalar animação para Scavenger
if (isScavengerComplete(liveTiles)) {
    currentPhase = "VICTORY";
    modalShown = true;
    isMajorVictory = true;  // Flag para celebração escalada
}

// CSS condicional
if (isMajorVictory) {
    victoryTitle.classList.add("victory-title--major");
}
```

```css
/* ✅ Versão escalada */
.victory-title--major {
    animation: victoryExplode 1.2s ease-in-out infinite;  /* Mais rápido */
    font-size: 3rem;  /* Maior */
}
```

---

### 🔟 Victory Subtitle Legibilidade Mobile

**Problema**:
- Subtitle: `font-size: 0.68rem` (constante)
- 320px viewport: 0.68rem ≈ 5px visual = invisível
- `letter-spacing: 0.25em` em 10.88px = "rio" ilegível

**Impacto**: 🟠 **Médio**
- Mobile users não leem subtitle
- Proporção vs. título: 2.75rem / 0.68rem = 4:1 (título domina)

**Localização no Código**:
- game.html (linhas 670-680)

**Recomendação**:
```css
/* Adicionar breakpoint */
@media (max-width: 480px) {
    .victory-subtitle {
        font-size: 0.82rem;    /* ✅ de 0.68rem */
        letter-spacing: 0.16em;  /* ✅ de 0.25em */
        margin-top: 0.75rem;   /* ✅ adiciona espaço */
    }
}
```

---

---

# 📊 MATRIZ DE PRIORIDADE (Priority Matrix)

| # | Problema | Complexidade | Impacto | Timeline | Esforço | Status |
|---|----------|-------------|---------|----------|---------|--------|
| **1️⃣** | Acessibilidade Teclado | ⚡ 15 min | 🔴 Crítico | Agora | Baixo | 🔴 Não Iniciado |
| **2️⃣** | Opacity Tiles Imperceptível | ⚡ 5 min | 🔴 Crítico | Agora | Baixo | 🔴 Não Iniciado |
| **3️⃣** | Modal sem ARIA Attributes | ⚡ 10 min | 🔴 Crítico | Agora | Baixo | 🔴 Não Iniciado |
| **4️⃣** | Tipografia Não Responsiva | ⚙️ 20 min | 🟡 Alto | Esta Semana | Médio | 🟡 Pendente |
| **5️⃣** | Touch Targets < 44px | ⚙️ 20 min | 🟡 Alto | Esta Semana | Médio | 🟡 Pendente |
| **6️⃣** | Animação Sub-energizada | ⚙️ 15 min | 🟡 Alto | Esta Semana | Médio | 🟡 Pendente |
| **7️⃣** | Botão Texto Ambíguo | ⚡ 5 min | 🟡 Alto | Esta Semana | Baixo | 🟡 Pendente |
| **8️⃣** | Breakpoint Tablet 768px | ⚙️ 20 min | 🟠 Médio | Próximo Sprint | Médio | 🟠 Backlog |
| **9️⃣** | Scavenger Celebração | ⚙️ 15 min | 🟠 Médio | Próximo Sprint | Médio | 🟠 Backlog |
| **🔟** | Subtitle Legibilidade Mobile | ⚙️ 10 min | 🟠 Médio | Próximo Sprint | Baixo | 🟠 Backlog |

**Legenda**:
- ⚡ = Muito Rápido (< 15 min)
- ⚙️ = Médio (15-30 min)
- 🔴 = Crítico (impede uso)
- 🟡 = Alto (degrada UX)
- 🟠 = Médio (melhoria)

---

## ✅ O Que Está Funcionando Bem

| Aspecto | Avaliação | Detalhes |
|---------|-----------|----------|
| **Design Visual** | ✅ Forte | Tema cyberpunk coerente, palette distinta verde + ouro |
| **Hierarquia Tipográfica** | ✅ Excelente | Razão 6:1 título/eyebrow = dominância visual clara |
| **Contraste WCAG** | ✅ AAA | #00ff88 sobre dark (#07080f) = 14:1 (muito acima de 4.5:1 AA) |
| **Narrativa Animação** | ✅ Intencional | Entrances escalonadas (brand → how-to → cards) criam fluxo natural |
| **Feedback de Tile** | ✅ Claro | Pop animation 0.22s + glow = confirmação rápida e visível |
| **Modal Layering** | ✅ Bem-pensado | Backdrop semi-transparente + blur preserva contexto linha vencedora |
| **Layout Grid** | ✅ Equilibrado | 5×5 com gaps proporcionais, tiles 60px = bom balance tamanho/readability |
| **Card Shuffle UX** | ✅ Intuitivo | Face-down → reveal mechanic claro, redraw button aparece sensatamente |

---

## 📋 Próximos Passos Recomendados

### 🔥 Imediato (Hoje ou Amanhã)
```
- [ ] Adicionar :focus-visible outlines a todos botões/tiles
- [ ] Aumentar opacity: tile-marked 6% → 15%, tile-winner 9% → 20%
- [ ] Adicionar role="dialog", aria-modal="true" ao modal victory
- [ ] Implementar suporte teclado: Escape para fechar, Enter para confirmar
- [ ] Implementar focus trap em modal
```

### 📅 Esta Semana
```
- [ ] Adicionar breakpoints responsivos (320px, 375px, 480px)
- [ ] Aumentar touch targets: checkbox → 24px, items → 44px altura
- [ ] Melhorar animação victory: scale(1→1.15) + glow mais brilhante
- [ ] Clarificar texto botão: "Continue" → "Voltar ao Lobby" ou "Jogar Novamente"
- [ ] Testar contraste e legibilidade em dispositivos reais
```

### 🎯 Próximo Sprint
```
- [ ] Adicionar breakpoint tablet (641px-1023px)
- [ ] Escalar celebração Scavenger Hunt (animação mais rápida/brilhante)
- [ ] Melhorar legibilidade subtitle mobile (0.82rem, menos spacing)
- [ ] Testar keyboard navigation completa com screen readers
- [ ] Validar WCAG 2.1 AA conformance
```

---

## 📞 Contato & Questões

Para questões sobre este relatório ou pedidos de clarificação:
- Consulte as referências de código (linhas específicas) listadas
- Teste as "Como Reproduzir" steps para validar cada problema
- Verifique as correções propostas contra seu design system

---

**Documento Completo**  
**Data Geração**: Maio 5, 2026  
**Revisor**: GitHub Copilot — UI Review Agent  
**Status Final**: ✅ Pronto para Implementação

---

**Notas Adicionais**:
- Todos os problemas críticos têm impacto em acessibilidade (WCAG)
- Implementação de críticos = ~30 min total
- Alta prioridade = ~1h total
- Recomenda-se implementar críticos antes do próximo release
