# Soc Ops Design Spec

## Card Deck Shuffle

- Goal: add a fast-start mode where each player opens the game, taps once, and receives one random icebreaker question.
- Entry point: a third lobby card beside Bingo and Scavenger Hunt so the new mode feels native to the existing mission selector.
- Interaction: opening the mode loads a facedown card, the first tap reveals the question, and a follow-up action draws a fresh card without leaving the mode.
- Visual direction: warm gold accents distinguish the deck from the green Bingo path while staying inside the established cyber-social palette.
- State decision: the current dealt card is stored separately from the other modes so switching modes does not overwrite Bingo or Scavenger progress.