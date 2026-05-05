package com.socops.data;

import java.util.List;

/**
 * Central catalogue of every icebreaker prompt that can appear on a board.
 * Exactly 24 entries — one fewer than the 25-cell grid, because the
 * centre cell is always the free space.
 */
public final class IcebreakerPrompts {

    public static final String FREE_CELL_LABEL = "FREE SPACE";

    public static final List<String> ALL_PROMPTS = List.of(
            "já derrubou produção numa sexta à noite",
            "tem mais de 20 abas abertas agora mesmo",
            "já commitou com a mensagem 'fix' ou 'teste'",
            "usa o Caps Lock como tecla de atalho",
            "já culpou o cache antes de debugar de verdade",
            "tem um script que 'só roda na minha máquina'",
            "já respondeu e-mail pelo terminal",
            "conhece alguém chamado João que 'mexe com TI'",
            "já fez deploy às 23h e foi dormir na fé",
            "tem um HD externo cheio de 'projetos do futuro'",
            "já usou IA pra explicar código que você mesmo escreveu",
            "tem um repositório chamado 'teste2-final-v3'",
            "já reiniciou o computador como solução oficial",
            "já escreveu um TODO que virou legacy",
            "sabe o que é um 'erro 418 – Sou um bule de chá'",
            "já abriu uma issue pra reportar o próprio bug",
            "tem Vim instalado e nunca soube sair",
            "já usou Stack Overflow offline de tanta memória",
            "já nomeou variável como 'coisa', 'x2' ou 'teste123'",
            "já fez pair programming e quis desistir da profissão",
            "participa de grupo de WhatsApp chamado 'Dev [cidade]'",
            "já explicou recursão usando a própria recursão",
            "pedra-papel-tesoura: quem perder faz um 'git blame' ao vivo",
            "já escreveu documentação falando que o código é auto-explicativo"
    );

    private IcebreakerPrompts() {
        /* catalogue only — no instances */
    }
}
