package com.cst8334.cst833;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card {
    enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    private final int value;
    private final Suit suit;
    private boolean faceUp;
    private final Image frontImage;
    private final Image backImage;
    private ImageView imageView;

    public Card(int value, Suit suit, Image frontImage, Image backImage) {
        this.value = value;
        this.suit = suit;
        this.faceUp = false; // Default value of cards to be face down
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.imageView = new ImageView(faceUp ? frontImage : backImage); // Initialize ImageView with the back image
    }

    // Getters and setters
    public void flip() {
        faceUp = !faceUp;
        imageView.setImage(faceUp ? frontImage : backImage); // Update ImageView when card is flipped
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
        imageView.setImage(faceUp ? frontImage : backImage); // Update ImageView when faceUp state changes
    }
    public Image getFrontImage() {
        return frontImage;
    }
    public Image getBackImage() {
        return backImage;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                ", faceUp=" + faceUp +
                '}';
    }
}




