package com.cst8334.cst833;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HelloController {

    @FXML private ImageView stockPile;
    @FXML private StackPane talonPile;
    private Deck deck = new Deck();

    //K: Initialize is executed on application startup
    @FXML
    private void initialize() {
        setupGame();
    }
    //K: startNewGame is executed when the GUI button made by Charles is clicked.
    @FXML
    private void startNewGame() {
        setupGame();
    }

    private void setupGame() {
        deck = new Deck();
        deck.shuffle();
        stockPile.setImage(new Image(Deck.class.getResourceAsStream("/card_images/back_card.gif")));
        dealCardsToTableau();
    }

    private void dealCardsToTableau() {
        for (int pileIndex = 0; pileIndex < 7; pileIndex++) {
            StackPane tableauPane = getTableauPane(pileIndex);
            tableauPane.getChildren().clear();
            for (int cardIndex = 0; cardIndex <= pileIndex; cardIndex++) {
                Card card = deck.drawCard();
                // The ImageView for the card is now directly accessed from the Card object
                ImageView cardView = card.getImageView();
                if (cardIndex == pileIndex) {
                    card.flip(); //Automatically updates the ImageView inside the Card class
                }
                setupDragAndDrop(cardView, card); //Setup drag-and-drop event handlers
                tableauPane.getChildren().add(cardView);
                cardView.setTranslateY(cardIndex * 30); // Position cards with a vertical offset
            }
        }
    }


    //Charles Talon pile must maintain order in which stockpile cards were discarded GROUP-26 e Ө Ө

    @FXML
    private void onStockClicked(MouseEvent event) {
        if (!deck.isEmpty()) {
            Card card = deck.drawCard();
            ImageView cardView = card.getImageView();

            // Ensure the card is face up when moved to the talon pile, if not already.
            if (!card.isFaceUp()) {
                card.flip(); // This should automatically update the card's ImageView to show the front image.
            }

            // Apply drag-and-drop event handlers to this card's ImageView.
            setupDragAndDrop(cardView, card);

            // Add the card to the talon pile, preserving order.
            talonPile.getChildren().add(cardView);
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
    


    private boolean isAlternateSuit(Card card1, Card card2) {
        //If both cards are face down, no stack
        if (!card1.isFaceUp() || !card2.isFaceUp()) {
            return false;
        }

        //Checks if suit is alt
        return (card1.getSuit() == Card.Suit.HEARTS || card1.getSuit() == Card.Suit.DIAMONDS) !=
               (card2.getSuit() == Card.Suit.HEARTS || card2.getSuit() == Card.Suit.DIAMONDS);
    }
    private void setupDragAndDrop(ImageView cardView, Card card) {
        final double[] mouseAnchorX = new double[1];
        final double[] mouseAnchorY = new double[1];

        cardView.setOnMousePressed(event -> {
            //K: Record the mouse position at the start of the drag.
            //K: This is relative to the scene to ensure we get an absolute position.
            mouseAnchorX[0] = event.getSceneX();
            mouseAnchorY[0] = event.getSceneY();

            //K: Card opacity
            cardView.setOpacity(0.4);
            event.consume();
        });

        cardView.setOnMouseDragged(event -> {
            //K: Calculate the new translation based on the current mouse position
            //K: inus the initial mouse anchor position. This requires adjusting
            //K: for the current translation to ensure the card follows the cursor smoothly.
            double translateX = event.getSceneX() - mouseAnchorX[0] + cardView.getTranslateX();
            double translateY = event.getSceneY() - mouseAnchorY[0] + cardView.getTranslateY();

            // L:Update the translation to move the card with the cursor.
            cardView.setTranslateX(translateX);
            cardView.setTranslateY(translateY);

            // K: Updates  anchor position so card stays with cursor while moving.
            mouseAnchorX[0] = event.getSceneX();
            mouseAnchorY[0] = event.getSceneY();
            event.consume();
        });

        cardView.setOnMouseReleased(event -> {
            // Reset visual cues and apply any necessary adjustments to finalize the drop.
            cardView.setOpacity(1.0);
            event.consume();
        });
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


}
