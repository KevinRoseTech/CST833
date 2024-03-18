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
            System.out.println("Card move not valid, if its supposed to be bring it up to the team!");
            // Alternatively, throw an exception or handle this case as needed
        }
    }

    private boolean isOppositeColor(Card card1, Card card2) {
        //Suits are ordered as HEARTS, DIAMONDS, CLUBS, SPADES in the enum (check card class).
        boolean card1IsRed = card1.getSuit() == Card.Suit.HEARTS || card1.getSuit() == Card.Suit.DIAMONDS;
        boolean card2IsRed = card2.getSuit() == Card.Suit.HEARTS || card2.getSuit() == Card.Suit.DIAMONDS;

        return card1IsRed != card2IsRed; //False if both are the same colour.
    }

}