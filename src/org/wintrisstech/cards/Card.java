package org.wintrisstech.cards;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * There are 4 suits (clubs, diamonds, hearts, and spades) and 13 cards in each
 * suit (ace, 2 through 10, jack, queen, king). The number of a Card is an
 * integer between 1 and 13, where 1 denotes an ace, 11 a jack, 12 a queen, and
 * 13 a king. Cards are immutable; they cannot change after they are created.
 * There may be multiple instances of Card with equal suit and number. <p> This
 * class has no public constructor. Use the <tt>getCard()</tt> methods of class
 * Deck, to get an instance of Card.
 *
 * @see Deck#getCard()
 * @see Deck#getCard(org.wintrisstech.cards.Card.Suit, int)
 *
 * @author Erik Colban
 *
 */
public final class Card {

    /*
     * Static variables
     */
    private static final Image[][] allFaceImages = new Image[4][13];

    /*
     * Instance varaibles
     */
    private final Suit suit;
    private final int number;
    private final Image faceImage;
    private final Image backImage;

    /**
     * The suits
     */
    public static enum Suit {

        clubs, diamonds, hearts, spades;
    }

    /*
     * Read all the image files once.
     */
    static {
        try {
            for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
                Card.Suit suit = Card.Suit.values()[suitIndex];
                for (int number = 1; number <= 13; number++) {
                    // Find the file name of the file containing the card image.
                    String fileName;
                    switch (number) {
                        case 1:
                            fileName = suit + "-a-150.png";
                            break;
                        case 11:
                            fileName = suit + "-j-150.png";
                            break;
                        case 12:
                            fileName = suit + "-q-150.png";
                            break;
                        case 13:
                            fileName = suit + "-k-150.png";
                            break;
                        default:
                            fileName = suit + "-" + number + "-150.png";
                            break;
                    }
                    //fileName found.
                    //Instantiate the Card
                    Image image = ImageIO.read(Card.class.getResource("Cards/" + fileName));
                    allFaceImages[suitIndex][number - 1] = image;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates an instance of a Card. Note that this constructor is package-
     * private, i.e., cannot be invoked outside the package. Use
     * {@link Deck#getCard()} or
     * {@link Deck#getCard(org.wintrisstech.cards.Card.Suit, int) } to get an
     * instance of Card.
     *
     * @param suit the Suit of the Card.
     * @param number an integer between 1 and 13, where 1 denotes an ace, 11 a
     * jack, 12 a queen, 13 a king.
     * @param backImage the image of the back of the Card
     */
    Card(Suit suit, int number, Image backImage) {
        this.suit = suit;
        this.number = number;
        Image im = null;
        for (int i = 0; i < Suit.values().length; i++) {
            if (Suit.values()[i] == suit) {
                im = allFaceImages[i][number - 1];
                break;
            }
        }
        assert im != null;
        faceImage = im;
        this.backImage = backImage;
    }

    /**
     * Gets the image of the face.
     *
     * @return the image of the face of this Card
     */
    public Image getFaceImage() {
        // Apply defensive copying
        return faceImage.getScaledInstance(-1, -1, Image.SCALE_REPLICATE);
    }

    /**
     * Gets the image of the back.
     *
     * @return the image of the back of this Card
     */
    public Image getBackImage() {
        // Apply defensive copying
        return backImage.getScaledInstance(-1, -1, Image.SCALE_REPLICATE);
    }

    /**
     * Gets the suit
     *
     * @return the Suit of this Card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Gets the number of this card. The number is between 1 and 13, where 1
     * denotes an ace, 11 a jack, 12 a queen, 13 a king.
     *
     * @return the number of this Card
     */
    public int getNumber() {
        return number;
    }

    /**
     * The String representation of a Card instance. Overridden for a more
     * readable form.
     *
     * @return the String representation of this Card
     */
    @Override
    public String toString() {
        switch (number) {
            case 1:
                return "ace of " + suit;
            case 11:
                return "jack of " + suit;
            case 12:
                return "queen of " + suit;
            case 13:
                return "king of " + suit;
            default:
                return number + " of " + suit;
        }
    }

    /**
     * The hash code of the card. Overridden so that cards that have the same
     * suit and number have same hash code.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.suit.hashCode();
        hash = 59 * hash + this.number;
        return hash;
    }

    /**
     * Test if this Card equals obj. Overridden so that cards that have the same
     * suit and number are equal.
     *
     * @param obj any Object instance or null
     * @return true if this Card equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.suit != other.suit) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        return true;
    }
}
