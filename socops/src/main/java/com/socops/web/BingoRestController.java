package com.socops.web;

import com.socops.model.BingoCell;
import com.socops.service.BoardAssembler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/** Serves the game page and the board-generation REST endpoint. */
@Controller
public class BingoRestController {

    @GetMapping("/")
    public String serveLobbyPage() {
        return "game";
    }

    @GetMapping("/api/bingo/fresh-board")
    @ResponseBody
    public List<BingoCell> dispenseFreshBoard() {
        return BoardAssembler.assembleNewBoard();
    }

    @GetMapping("/api/scavenger-hunt/fresh-board")
    @ResponseBody
    public List<BingoCell> dispenseFreshScavengerHunt() {
        return BoardAssembler.assembleNewScavengerHunt();
    }

    @GetMapping("/api/card-deck/fresh-card")
    @ResponseBody
    public List<BingoCell> dispenseFreshCardDeckPrompt() {
        return BoardAssembler.assembleRandomPromptCard();
    }
}
