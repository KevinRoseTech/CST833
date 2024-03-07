package com.cst8334.cst833;
/*
    K: To start us off this is what I imagine the Card class should compromise of, please add to this.
       We have their suit and value, as well as the status of card being flipped up or down.
 */
import javafx.scene.image.Image;



//K: I put
public class Card {
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    private final int value;
    private final Suit suit;
    private boolean faceUp;

    private Image frontImage;
    private Image backImage;

    public Card(int value, Suit suit, Image frontImage, Image backImage) {
        this.value = value;
        this.suit = suit;
        this.faceUp = false; //K: I put the default value of cards to be face down for now
        this.frontImage = frontImage;
        this.backImage = backImage;
    }

    //K: getters and setters
    public void flip() {
        faceUp = !faceUp;
    }
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
    public Image getFrontImage() {
        return frontImage;
    }
    public Image getBackImage() {
        return backImage;
    }

    //K: This is a debug method I created, I don't have much use for it anymore but maybe you all would?
    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                ", faceUp=" + faceUp +
                '}';
    }
}



