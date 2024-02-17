package com.cst8334.cst833;
import java.util.Collections;
import java.util.Stack;

import javafx.scene.image.Image;
/*
    K: To start us off this is what I imagine the Deck class should compromise of, please add to this.
       We have their suit and value, as well as the status of card being flipped up or down
 */
public class Deck {
    private Stack<Card> cards;


    // K: My vision for the Deck constructor, feel free to edit
    public Deck() {
        cards = new Stack<>(); // K: Creates a Stack object. Stack objects are Last in First Out from what I see.
        for (Card.Suit suit : Card.Suit.values()) { // K: This loop iterates through each suit in the card class
            for (int value = 1; value <= 13; value++) { // K: This nested loop creates 13 cards of each suit, ace-king = 13
                String frontImagePath = "path_to_front_image";
                String backImagePath = "/card_images/back_card.gif";
                System.out.println(backImagePath);
                Image frontImage = new Image(frontImagePath);
                Image backImage = new Image(backImagePath);
                cards.push(new Card(value, suit, frontImage, backImage)); // K: Card object is created with a value and a suit and pushed to the TOP of the card stack
            }
        }
        Collections.shuffle(cards); // K: Shuffles the deck
    }

    // K: Method to remove the top card from the cards Stack and returns it.
    //    pop() removes the object at the top of the stack, returns it as the method value
    //    if deck is out of Card objects (empty), this method SHOULD throw and EmptyStackException
    public Card drawCard() {
        return cards.pop();
    }

    // K: This deals with the Exception noted above, by calling this method it prevents drawing a card from an empty deck
    //    Avoiding the EmptyStackException
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
