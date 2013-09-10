package org.wintrisstech.cards;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A Deck consists of 52 Cards.
 *
 *
 * @author Erik Colban &copy; 2013
 */
public class Deck {

    /**
     * A 2-dim array organized by suit and number of all the cards in the deck.
     */
    private final Card[][] allCards = new Card[4][13];
    /**
     * An array containing the 52 cards in a deck in random order
     */
    private final Card[] shuffledCards = new Card[4 * 13];
    /**
     * The indext to the next card to be dealt from the deck
     */
    private int nextCard;
    /**
     * A random number generator used for shuffling the deck
     */
    private final Random random = new Random();
    /**
     * The width of the cards in this deck
     */
    private static int cardWidth = 75;
    /**
     * The height of the cards in this deck
     */
    private static int cardHeight = 107;

    /**
     * Creates an instance of Deck containing exactly one instance of each of 
     * the 52 Cards.
     * 
     * @param color color of the back of the cards.
     */
    public Deck(Color color) {
        Image backImage = null;
        try {
            if (color == null) {
                throw new IllegalArgumentException("Argument must be non-null.");
            } else if (color.equals(Color.RED)) {
                backImage = ImageIO.read(Card.class.getResource("images_small/back-red-75-1.png"));
            } else if (color.equals(Color.BLUE)) {
                backImage = ImageIO.read(Card.class.getResource("images_small/back-blue-75-1.png"));
            } else {
                throw new IllegalArgumentException("Argument must either Color.RED or Color.BLUE.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        }
        assert backImage != null;
        initialize(backImage);
    }

    /**
     * Creates an instance of Deck containing exactly one instance of each of
     * the 52 Cards. This constructor allows the caller to specify the image of
     * the back of the cards in this deck by providing a URL to the image file.
     * <p>
     * If the url is null or the file at the url is not found or cannot be read,
     * the back of the cards is left blank.
     *
     * @param url The url of the image file containing the back image of the
     * cards. This image should preferably have dimensions width = 75 and
     * height = 107; otherwise it is scaled to those dimensions, and the quality
     * may be poor.
     */
    public Deck(URL url) {
        Image backImage = null;
        if (url != null) {
            try {
                backImage = ImageIO.read(url);
            } catch (IOException ex) {
                Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (backImage != null
                && Math.abs(backImage.getHeight(null) - cardHeight) <= 1
                && Math.abs(backImage.getWidth(null) - cardWidth) <= 1) {
            initialize(backImage);
        } else {
            if (backImage == null) {
                initialize(new BufferedImage(cardWidth, cardHeight, BufferedImage.TYPE_INT_ARGB));
            } else {
                initialize(backImage.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));
            }
        }
    }

    /**
     * Creates an instance of each of the 52 Cards, adds them to the Deck and
     * shuffles the Deck
     */
    private void initialize(Image backImage) {

        nextCard = 0;
        for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
            Card.Suit suit = Card.Suit.values()[suitIndex];
            for (int number = 1; number <= 13; number++) {
                //Instantiate the Card
                Card card = new Card(suit, number, backImage);
                allCards[suitIndex][number - 1] = card;
                // The inside-out version of Fisher-Yates shuffle:
                int randomIndex = random.nextInt(nextCard + 1);
                shuffledCards[nextCard++] = shuffledCards[randomIndex];
                shuffledCards[randomIndex] = card;
            }
        }
        assert nextCard == 52;
        nextCard = 0;
    }

    /**
     * Gets the next Card from this Deck of cards. The Card that is returned
     * is random but different from any previously returned Card since the Deck
     * was last shuffled.
     * @return a Card instance, or null if no Cards are left
     */
    public Card getCard() {
        if (nextCard >= shuffledCards.length) {
            return null;
        } else {
            return shuffledCards[nextCard++];
        }
    }

    /**
     * Gets a specific Card instance from the deck of cards.
     * @param suit the suit of the card
     * @param number the number of the card
     * @return a Card instance
     * @throws IllegalArgumentException if number is out of range
     */
    public Card getCard(Card.Suit suit, int number) {
        if (number < 1 || 13 < number) {
            throw new IllegalArgumentException("number must be between 1 and 13.");
        }
        int index = 0;
        for (index = 0; index < 4; index++) {
            if (Card.Suit.values()[index] == suit) {
                break;
            }
        }
        return allCards[index][number - 1];
    }

    /**
     * Gets the number of cards that are left in the Deck.
     * @return the number of Cards left in the Deck
     */
    public int getCount() {
        return shuffledCards.length - nextCard;
    }

    /**
     * Shuffles the Deck. 
     */
    public void shuffle() {
        /* Apply the Fisher-Yates shuffle algorithm, a.k.a. Knuth shuffle */
        for (int i = shuffledCards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card card = shuffledCards[j];
            shuffledCards[j] = shuffledCards[i];
            shuffledCards[i] = card;
        }
        nextCard = 0;
    }
}
