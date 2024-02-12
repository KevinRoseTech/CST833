package com.cst8334.cst833;
/*
    K: To start us off this is what I imagine the Card class should compromise of, please add to this.
       We have their suit and value, as well as the status of card being flipped up or down
 */
public class Card {
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    private final int value;
    private final Suit suit;
    private boolean faceUp;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.faceUp = false; //K: I put the default value of cards to be face down for now
    }

    //K: getters and setters
    public int getValue() {
        return value;
    }
    public Suit getSuit() {
        return suit;
    }
    public boolean isFaceUp() {
        return faceUp;
    }
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
}
