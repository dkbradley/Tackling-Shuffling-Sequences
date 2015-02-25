package li.aron.shuffler.methods;

import li.aron.shuffler.Card;
import java.util.ArrayList;

public class PerfectRiffleShuffle {
    
    /**
     * Shuffles a deck using the Perfect Riffle Shuffle method.
     * 
     * @param decks     Decks to be shuffled.
     * @param topFirst  Should it shuffle from the top deck first.
     * @return          Shuffled decks
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks, boolean topFirst) {
        ArrayList<ArrayList<Card>> shuffledDecks = new ArrayList<>();
        
        for (ArrayList<Card> deck : decks) {
            int half = deck.size() / 2;
            
            ArrayList<Card> firstHalf = new ArrayList<>();
            ArrayList<Card> secondHalf = new ArrayList<>();
            
            for (int i = 0; i < half; i++) {
                firstHalf.add(deck.get(i));
            }
            
            for (int i = half; i < deck.size(); i++) {
                secondHalf.add(deck.get(i));
            }
            
            ArrayList<Card> shuffledDeck = new ArrayList<>();
            
            for (int i = 0; i < deck.size(); i++) {
                if (topFirst) {
                    if (i % 2 == 0) {
                        shuffledDeck.add(secondHalf.get(i / 2));
                    } else {
                        shuffledDeck.add(firstHalf.get(i / 2));
                    }
                } else {
                    if (i % 2 != 0) {
                        shuffledDeck.add(secondHalf.get(i / 2));
                    } else {
                        shuffledDeck.add(firstHalf.get(i / 2));
                    }
                }
            }
            
            shuffledDecks.add(shuffledDeck);
        }
        
        return shuffledDecks;
    }
}
