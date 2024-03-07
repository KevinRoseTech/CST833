package com.cst8334.cst833;

import java.util.ArrayList;
import java.util.List;

public class Foundation {
    private List<Card> heartsPile;
    private List<Card> diamondsPile;
    private List<Card> clubsPile;
    private List<Card> spadesPile;

    public Foundation() {
        heartsPile = new ArrayList<>();
        diamondsPile = new ArrayList<>();
        clubsPile = new ArrayList<>();
        spadesPile = new ArrayList<>();
    }

    public boolean canPlaceCard(Card card) {
        List<Card> targetPile = getPile(card.getSuit());
        //K: For an empty pile, only aces may be placed.
        if (targetPile.isEmpty() && card.getValue() == 1) {
            return true;
        } else if (!targetPile.isEmpty()) {
            Card topCard = targetPile.get(targetPile.size() - 1);
            return card.getSuit() == topCard.getSuit() && card.getValue() == topCard.getValue() + 1;
        }
        return false;
    }

    public void placeCard(Card card) {
        if (canPlaceCard(card)) {
            List<Card> targetPile = getPile(card.getSuit());
            targetPile.add(card);
        } else {
            //K: Drawing a blank on error exception, will come back to this
        }
    }

    private List<Card> getPile(Card.Suit suit) {
        return switch (suit) {
            case HEARTS -> heartsPile;
            case DIAMONDS -> diamondsPile;
            case CLUBS -> clubsPile;
            case SPADES -> spadesPile;
        };
    }
}
