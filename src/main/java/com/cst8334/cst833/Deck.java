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
    public Card getCardAtTopOfDeck() {
        if (!isEmpty()) {
            return cards.peek(); // Peek at the top card without removing it
        }
        return null; // Return null if the deck is empty
    }


    // K: My vision for the Deck constructor, feel free to edit
    public Deck() {
        cards = new Stack<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int value = 1; value <= 13; value++) {
                String imagePathSuffix = switch (suit) {
                    case HEARTS -> "h";
                    case DIAMONDS -> "d";
                    case CLUBS -> "c";
                    case SPADES -> "s";
                };
                String frontImagePath = "/card_images/" + value + imagePathSuffix + ".gif";
                Image frontImage = new Image(Deck.class.getResourceAsStream(frontImagePath));
                Image backImage = new Image(Deck.class.getResourceAsStream("/card_images/back_card.gif"));
                cards.push(new Card(value, suit, frontImage, backImage));
            }
        }
        Collections.shuffle(cards);
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
    public void shuffle() {
        Collections.shuffle(cards);
    }
}
