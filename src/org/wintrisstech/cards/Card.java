package org.wintrisstech.cards;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * There are 4 suits (clubs, diamonds, hearts, and spades) and 13 cards in each
 * suit (ace, 2 through 10, jack, queen, king). The number of a Card is an
 * integer between 1 and 13, where 1 denotes an ace, 11 a jack, 12 a queen, and
 * 13 a king. Cards are immutable; they cannot change after they are created.
 * There may be multiple instances of Card with equal suit and number.
 * <p>
 * This class has no public constructor. Use the <tt>getCard()</tt> methods of
 * class Deck, to get an instance of Card.
 * 
 * @see Deck#getCard()
 * @see Deck#getCard(org.wintrisstech.cards.Card.Suit, int)
 * 
 * @author Erik Colban &copy; 2013
 * 
 */
public final class Card {

	/*
	 * Static variables
	 */
	private static final Image[][] allFaceImages = new Image[4][13];

	/*
	 * Instance variables
	 */
	private final Suit suit;
	private final int number;
	private final Image faceImage;
	private final Image backImage;

	/**
	 * The suits
	 */
	public static enum Suit {

		CLUBS, DIAMONDS, HEARTS, SPADES;
	}


	/**
	 * Creates an instance of a Card. Note that this constructor is package-
	 * private, i.e., cannot be invoked outside the package. Use
	 * {@link Deck#getCard()} or
	 * {@link Deck#getCard(org.wintrisstech.cards.Card.Suit, int) } to get an
	 * instance of Card.
	 * 
	 * @param suit
	 *            the Suit of the Card.
	 * @param number
	 *            an integer between 1 and 13, where 1 denotes an ace, 11 a
	 *            jack, 12 a queen, 13 a king.
	 * @param backImage
	 *            the image of the back of the Card
	 */
	Card(Suit suit, int number, Image backImage) {
		this.suit = suit;
		this.number = number;
		int suitIndex = -1;
		for (int i = 0; i < Suit.values().length; i++) {
			if (Suit.values()[i] == suit) {
				suitIndex = i;
				break;
			}
		}
		Image image = allFaceImages[suitIndex][number - 1];
		if (image == null) { // load image
			String fileName;
			switch (number) {
			case 1:
				fileName = suit.toString().toLowerCase() + "-a-150.png";
				break;
			case 11:
				fileName = suit.toString().toLowerCase() + "-j-150.png";
				break;
			case 12:
				fileName = suit.toString().toLowerCase() + "-q-150.png";
				break;
			case 13:
				fileName = suit.toString().toLowerCase() + "-k-150.png";
				break;
			default:
				fileName = suit.toString().toLowerCase() + "-" + number + "-150.png";
				break;
			}
			// Instantiate the Card
			InputStream imageStream = getClass().getResourceAsStream(
					"images/" + fileName);
			try {
				image = ImageIO.read(imageStream);
			} catch (IOException e) {
			}
			allFaceImages[suitIndex][number - 1] = image;
		}

		faceImage = image;
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
	 * @param that
	 *            any Object instance or null
	 * @return true if this Card equals obj
	 */
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		final Card other = (Card) that;
		if (this.suit != other.suit || this.number != other.number) {
			return false;
		}
		return true;
	}
}
