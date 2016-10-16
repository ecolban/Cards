//
// Property of The League of Amazing Programmers
// All rights reserved -- 2016
//
package org.jointheleague.cards;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestCard {
	
	Deck deck = new Deck(Color.BLUE);
	
	@Before
	public void setUp() {
		deck.shuffle();
	}

	@Test
	public void testSuitContains() {
		assertTrue(Card.Suit.CLUBS.contains(deck.getCard(Card.Suit.CLUBS, 2)));
		assertFalse(Card.Suit.CLUBS.contains(deck.getCard(Card.Suit.DIAMONDS, 2)));
		deck.shuffle();
		int count = 0;
		for(int i = 0; i < 52; i++) {
			if(Card.Suit.HEARTS.contains(deck.getCard())) {
				count++;
		 	}
		}
		assertEquals(0, deck.getCount());
		assertEquals(13, count);
	}
	
	@Test
	public void testCountSuits() {
		Set<Card.Suit> suits = new HashSet<>();
		while(deck.getCount() > 0) {
			suits.add(deck.getCard().getSuit());
		}
		assertEquals(4, suits.size());
		assertTrue(suits.contains(Card.Suit.CLUBS));
		assertTrue(suits.contains(Card.Suit.DIAMONDS));
		assertTrue(suits.contains(Card.Suit.HEARTS));
		assertTrue(suits.contains(Card.Suit.SPADES));
	}
	
	@Test
	public void testCardsPerSuit() {
		Map<Card.Suit, Set<Integer>> suits = new HashMap<>();
		for(Card.Suit s: Card.Suit.values()) {
			suits.put(s, new HashSet<Integer>());
		}
		while(deck.getCount() > 0) {
			Card card = deck.getCard();
			Set<Integer> numbers = suits.get(card.getSuit());
			numbers.add(card.getNumber());
		}
		for (Set<Integer> value : suits.values()) {
			assertEquals(13, value.size());
			for (int n = 1; n <= 13; n++) {
				assertTrue(value.contains(n));
			}
		}
	}
	
	@Test
	public void testEquals() {
		Deck deck2 = new Deck(Color.RED);
		for (Card.Suit suit : Card.Suit.values()) {
			for (int n = 1; n <= 13; n++) {
				assertEquals(deck.getCard(suit, n), deck2.getCard(suit, n));
			}
		}
	}
	
}
