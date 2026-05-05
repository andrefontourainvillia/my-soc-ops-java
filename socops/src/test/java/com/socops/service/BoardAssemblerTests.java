package com.socops.service;

import com.socops.data.IcebreakerPrompts;
import com.socops.model.BingoCell;
import com.socops.model.WinningStreak;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates board assembly, cell flipping, and victory detection
 * provided by {@link BoardAssembler}.
 */
class BoardAssemblerTests {

    @SuppressWarnings("unchecked")
    private static List<BingoCell> assembleNewScavengerHunt() {
        try {
            return (List<BingoCell>) BoardAssembler.class
                    .getMethod("assembleNewScavengerHunt")
                    .invoke(null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("BoardAssembler must expose a scavenger hunt assembly path", exception);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<BingoCell> assembleRandomPromptCard() {
        try {
            return (List<BingoCell>) BoardAssembler.class
                    .getMethod("assembleRandomPromptCard")
                    .invoke(null);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("BoardAssembler must expose a card deck assembly path", exception);
        }
    }

    /* ── board creation ───────────────────────────────────────── */

    @Test
    @DisplayName("Assembled board contains exactly twenty-five cells")
    void assembledBoardHasTwentyFiveCells() {
        List<BingoCell> generatedBoard = BoardAssembler.assembleNewBoard();
        assertEquals(25, generatedBoard.size(),
                "A standard bingo grid must hold 25 tiles");
    }

    @Test
    @DisplayName("Centre slot (index 12) is always the free cell and pre-selected")
    void centerSlotIsAlwaysFreeCell() {
        List<BingoCell> generatedBoard = BoardAssembler.assembleNewBoard();
        BingoCell centreTile = generatedBoard.get(12);

        assertTrue(centreTile.freeCell(),  "Centre tile must be flagged as free");
        assertTrue(centreTile.selected(),  "Free cell must start already tapped");
    }

    @Test
    @DisplayName("Every non-free cell starts out unselected")
    void nonFreeCellsStartUnselected() {
        List<BingoCell> generatedBoard = BoardAssembler.assembleNewBoard();
        for (BingoCell tile : generatedBoard) {
            if (!tile.freeCell()) {
                assertFalse(tile.selected(),
                        "Tile id=" + tile.id() + " should begin unselected");
            }
        }
    }

        @Test
        @DisplayName("Scavenger Hunt assembly returns twenty-four unchecked prompt items with no free cell")
        void scavengerHuntAssemblyProducesUncheckedPromptItemsWithoutFreeCell() {
        List<BingoCell> generatedItems = assembleNewScavengerHunt();

        assertEquals(IcebreakerPrompts.ALL_PROMPTS.size(), generatedItems.size(),
            "Scavenger Hunt should expose one checklist item per prompt");
        assertEquals(Set.copyOf(IcebreakerPrompts.ALL_PROMPTS),
            generatedItems.stream().map(BingoCell::prompt).collect(Collectors.toSet()),
            "Scavenger Hunt should reuse the full prompt catalogue without depending on order");

        for (int expectedId = 0; expectedId < generatedItems.size(); expectedId++) {
            BingoCell item = generatedItems.get(expectedId);
            assertEquals(expectedId, item.id(),
                "Checklist items should use compact zero-based identifiers");
            assertFalse(item.selected(),
                "Checklist item id=" + item.id() + " should begin unchecked");
            assertFalse(item.freeCell(),
                "Scavenger Hunt should not include a free cell");
            assertFalse(IcebreakerPrompts.FREE_CELL_LABEL.equals(item.prompt()),
                "Scavenger Hunt prompts must all come from the regular prompt catalogue");
        }
        }

    @Test
    @DisplayName("Card Deck Shuffle assembly returns one hidden prompt card from the shared prompt catalogue")
    void cardDeckAssemblyProducesSinglePromptCard() {
        List<BingoCell> generatedCard = assembleRandomPromptCard();

        assertEquals(1, generatedCard.size(),
            "Card Deck Shuffle should return exactly one prompt card");

        BingoCell card = generatedCard.getFirst();
        assertEquals(0, card.id(),
            "Single-card mode should use a stable zero identifier");
        assertFalse(card.selected(),
            "Freshly dealt cards should stay face down until the player taps them");
        assertFalse(card.freeCell(),
            "Card Deck Shuffle should never create a free cell");
        assertTrue(IcebreakerPrompts.ALL_PROMPTS.contains(card.prompt()),
            "Card Deck Shuffle must draw from the shared prompt catalogue");
    }

    /* ── cell toggling ────────────────────────────────────────── */

    @Test
    @DisplayName("Flipping a regular cell toggles its selection on then off")
    void flippingCellTogglesSelection() {
        List<BingoCell> board = BoardAssembler.assembleNewBoard();
        int targetId = 0;

        List<BingoCell> afterFirstFlip = BoardAssembler.flipCell(board, targetId);
        assertTrue(afterFirstFlip.get(targetId).selected(),
                "First flip should mark the cell as selected");

        List<BingoCell> afterSecondFlip = BoardAssembler.flipCell(afterFirstFlip, targetId);
        assertFalse(afterSecondFlip.get(targetId).selected(),
                "Second flip should revert the cell to unselected");
    }

    @Test
    @DisplayName("Flipping the free cell has absolutely no effect")
    void flippingFreeCellHasNoEffect() {
        List<BingoCell> board = BoardAssembler.assembleNewBoard();
        int freeCellId = 12;

        List<BingoCell> afterAttemptedFlip = BoardAssembler.flipCell(board, freeCellId);
        assertTrue(afterAttemptedFlip.get(freeCellId).selected(),
                "Free cell must remain selected even after a flip attempt");
        assertTrue(afterAttemptedFlip.get(freeCellId).freeCell(),
                "Free cell flag must not change");
    }

    /* ── victory detection ────────────────────────────────────── */

    @Test
    @DisplayName("Completing the entire first row is detected as a row victory")
    void completeRowDetectedAsVictory() {
        List<BingoCell> board = BoardAssembler.assembleNewBoard();

        // Select every cell in row 0 (indices 0 through 4)
        for (int col = 0; col < 5; col++) {
            board = BoardAssembler.flipCell(board, col);
        }

        Optional<WinningStreak> result = BoardAssembler.detectWinningStreak(board);
        assertTrue(result.isPresent(), "A full row should trigger a victory");
        assertEquals("row", result.get().direction(),
                "The detected streak direction should be 'row'");
        assertEquals(0, result.get().index(),
                "The winning row index should be 0");
    }

    @Test
    @DisplayName("A freshly assembled board has no winning streak")
    void incompleteBoardHasNoVictory() {
        List<BingoCell> freshBoard = BoardAssembler.assembleNewBoard();
        Optional<WinningStreak> result = BoardAssembler.detectWinningStreak(freshBoard);
        assertTrue(result.isEmpty(),
                "No streak should be found on a brand-new board");
    }

    @Test
    @DisplayName("collectWinningCellIds returns the correct position set")
    void winningCellIdsMatchStreak() {
        List<Integer> expectedPositions = List.of(0, 1, 2, 3, 4);
        WinningStreak fakeStreak = new WinningStreak("row", 0, expectedPositions);

        Set<Integer> collectedIds = BoardAssembler.collectWinningCellIds(fakeStreak);
        assertEquals(Set.of(0, 1, 2, 3, 4), collectedIds,
                "Collected IDs must match the streak's cell positions");
    }
}
