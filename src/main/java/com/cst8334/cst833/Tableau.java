package com.cst8334.cst833;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tableau {

    private List<Stack<Card>> piles;

    public Tableau(int numberOfPiles) {
        piles = new ArrayList<>(numberOfPiles);
        for (int i = 0; i < numberOfPiles; i++) {
            piles.add(new Stack<>());
        }
    }

    public boolean canPlaceCard(Card card, int pileIndex) {
        if (pileIndex < 0 || pileIndex >= piles.size()) {
            return false; // Pile index out of bounds
        }

        Stack<Card> targetPile = piles.get(pileIndex);

        if (targetPile.isEmpty()) {
            // Rule: Spare tableau spots can only be filled with kings
            return card.getValue() == 13; // Assuming 13 represents a King
        } else {
            Card topCard = targetPile.peek();
            // Rule: Tableau cards can only be stacked in alternating colors
            return isOppositeColor(card, topCard) && card.getValue() == topCard.getValue() - 1;
        }
    }

    public void placeCard(Card card, int pileIndex) {
        if (canPlaceCard(card, pileIndex)) {
            piles.get(pileIndex).push(card);
        } else {
            System.out.println("Cannot place card here.");
            // Alternatively, throw an exception or handle this case as needed
        }
    }

    private boolean isOppositeColor(Card card1, Card card2) {
        // Suits are ordered as HEARTS, DIAMONDS, CLUBS, SPADES in the enum.
        // HEARTS and DIAMONDS (ordinal values 0 and 1) are red; CLUBS and SPADES (ordinal values 2 and 3) are black.
        // This checks if one card is red and the other is black by comparing their ordinal values.
        boolean card1IsRed = card1.getSuit() == Card.Suit.HEARTS || card1.getSuit() == Card.Suit.DIAMONDS;
        boolean card2IsRed = card2.getSuit() == Card.Suit.HEARTS || card2.getSuit() == Card.Suit.DIAMONDS;

        return card1IsRed != card2IsRed; // True if one is red and the other is black, false if both are the same color.
    }

    // Additional helper methods if needed...
}