package com.cst8334.cst833;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.List;

public class HelloController {

    @FXML
    private ImageView stockPile; //K: ImageView for the stock pile.

    @FXML
    private StackPane talonPile; //K: StackPane for displaying the talon pile.

    private Deck deck = new Deck();

    @FXML
    private void initialize() {
        deck.shuffle();
        //K: Optionally set an image on the stockPile to indicate it's clickable or holds cards.
        stockPile.setImage(new Image(Deck.class.getResourceAsStream("/card_images/back_card.gif")));

        //K: Deal cards to each tableau pile
        for (int pileIndex = 0; pileIndex < 7; pileIndex++) {
            StackPane tableauPane = getTableauPane(pileIndex);
            for (int cardIndex = 0; cardIndex <= pileIndex; cardIndex++) {
                Card card = deck.drawCard();
                ImageView cardView = new ImageView(card.getBackImage());
                if (cardIndex == pileIndex) {
                    card.flip(); //K: Flip the last card to be face up
                    cardView.setImage(card.getFrontImage());
                }
                tableauPane.getChildren().add(cardView);
                //K: Adjusting layout
                cardView.setTranslateY(cardIndex * 30);
            }
        }
    }

    //Charles Talon pile must maintain order in which stockpile cards were discarded GROUP-26 e Ө Ө
    @FXML
    private void onStockClicked(MouseEvent event) {
        if (!deck.isEmpty()) {
            Card card = deck.drawCard();
            ImageView cardView = new ImageView(card.getFrontImage());

            // Add the card to the talon pile, preserving order
            talonPile.getChildren().add(cardView);
        }
    }
 
    //Charles User must be able to start a new game, thus receiving a freshly shuffled deck of cards (button, starts game again)
    @FXML
    private void startNewGame() {
        deck = new Deck();
        deck.shuffle();

        stockPile.setImage(new Image(Deck.class.getResourceAsStream("/card_images/back_card.gif")));

        for (int pileIndex = 0; pileIndex < 7; pileIndex++) {
            StackPane tableauPane = getTableauPane(pileIndex);
            for (int cardIndex = 0; cardIndex <= pileIndex; cardIndex++) {
                Card card = deck.drawCard();
                ImageView cardView = new ImageView(card.getBackImage());
                if (cardIndex == pileIndex) {
                    card.flip();
                    cardView.setImage(card.getFrontImage());
                }
                tableauPane.getChildren().add(cardView);
                cardView.setTranslateY(cardIndex * 30);
            }
        }
    }
    private int getTableauIndex(StackPane tableauPane) {
        if (tableauPane == tableau1) return 0;
        else if (tableauPane == tableau2) return 1;
        else if (tableauPane == tableau3) return 2;
        else if (tableauPane == tableau4) return 3;
        else if (tableauPane == tableau5) return 4;
        else if (tableauPane == tableau6) return 5;
        else if (tableauPane == tableau7) return 6;
        else throw new IllegalArgumentException("Invalid tableau pane");
    }

    

    
    //Tableau
    @FXML
    private StackPane tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7;
    private StackPane getTableauPane(int pileIndex) {
        switch (pileIndex) {
            case 0: return tableau1;
            case 1: return tableau2;
            case 2: return tableau3;
            case 3: return tableau4;
            case 4: return tableau5;
            case 5: return tableau6;
            case 6: return tableau7;
            default: throw new IllegalArgumentException("Invalid pile index: " + pileIndex);
        }
    }
    
    //Charles  Tableau Stacks of cards can only be moved when appropriately stacked by alternating suits
    @FXML
    private void onCardClicked(MouseEvent event) {
        ImageView clickedCard = (ImageView) event.getSource();

        // Find the parent stack pane of the clicked card
        StackPane parentStackPane = (StackPane) clickedCard.getParent();

        // Check if the clicked card is the top card in its stack pane
        if (parentStackPane.getChildren().get(parentStackPane.getChildren().size() - 1) == clickedCard) {
            // Get the index of the tableau pane where the card is located
            int pileIndex = getTableauIndex(parentStackPane);

            // Get the card being moved
            Card movingCard = deck.getCardAtTopOfDeck();

            // Check if there is an appropriate spot to stack the clicked card
            if (canStackCard(movingCard, pileIndex)) {
                // Move the clicked card to the appropriate tableau pane
                parentStackPane.getChildren().remove(clickedCard);
                getTableauPane(pileIndex).getChildren().add(clickedCard);
            }
        }
    }

    private boolean canStackCard(Card movingCard, int pileIndex) {
        if (movingCard == null) {
            return false; // No card to move
        }

        // Get the destination tableau stack pane
        StackPane destinationTableauPane = getTableauPane(pileIndex);

        // If the destination tableau stack pane is empty, any card can be moved
        if (destinationTableauPane.getChildren().isEmpty()) {
            return true;
        }

        // Get the top card of the destination tableau stack pane
        ImageView topCardImageView = (ImageView) destinationTableauPane.getChildren().get(destinationTableauPane.getChildren().size() - 1);
        Card topCard = deck.getCardAtTopOfDeck();

        // Check if the suits alternate
        return isAlternateSuit(movingCard, topCard);
    }

    private boolean isAlternateSuit(Card card1, Card card2) {
        // Cards cannot be stacked if both are face down
        if (!card1.isFaceUp() || !card2.isFaceUp()) {
            return false;
        }

        // Check if the suits alternate
        return (card1.getSuit() == Card.Suit.HEARTS || card1.getSuit() == Card.Suit.DIAMONDS) !=
               (card2.getSuit() == Card.Suit.HEARTS || card2.getSuit() == Card.Suit.DIAMONDS);
    }



















    private void setupDragAndDrop() {
        List<StackPane> tableaus = Arrays.asList(tableau1, tableau2, tableau3, tableau4, tableau5, tableau6, tableau7);
        for (StackPane tableau : tableaus) {
            setupTableauDropTarget(tableau);
        }
    }

    private void makeCardDraggable(ImageView cardView, Card card) {
        cardView.setOnDragDetected(event -> {
            Dragboard db = cardView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            // Generate a unique identifier for the card
            String identifier = card.getSuit().name() + ":" + card.getValue();
            content.putString(identifier);
            db.setContent(content);

            cardView.setOpacity(0.5);
            event.consume();
        });

        cardView.setOnDragDone(event -> {
            cardView.setOpacity(1.0);
            event.consume();
        });
    }

    private void setupTableauDropTarget(StackPane tableau) {
        tableau.setOnDragOver(event -> {
            if (event.getGestureSource() != tableau && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        tableau.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                String cardData = db.getString();
                String[] parts = cardData.split(":");
                Card.Suit suit = Card.Suit.valueOf(parts[0]);
                int value = Integer.parseInt(parts[1]);

                // Now you have the suit and value, you can find the corresponding card
                // Implement the logic to check if the move is valid and then move the card

                success = true; // Set to true if the move is valid
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }





}
