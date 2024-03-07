package com.cst8334.cst833;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    @FXML
    private void onStockClicked(MouseEvent event) {
        if (!deck.isEmpty()) {
            Card card = deck.drawCard();
            //K: Assuming card.getFrontImage() returns an Image.
            ImageView cardView = new ImageView(card.getFrontImage());
            talonPile.getChildren().clear();
            talonPile.getChildren().add(cardView);
        }
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


}
