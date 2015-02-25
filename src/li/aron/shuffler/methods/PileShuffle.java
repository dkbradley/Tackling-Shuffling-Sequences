package li.aron.shuffler.methods;

import li.aron.shuffler.Card;
import java.util.ArrayList;
import java.util.Random;

public class PileShuffle {
    
    /**
     * Shuffles a deck using the Pile Shuffle method.
     * 
     * @param decks Decks to be shuffled.
     * @return      Shuffled decks
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        ArrayList<ArrayList<Card>> shuffledDecks = new ArrayList<>();
        for (ArrayList<Card> deck : decks) {
            Random rand = new Random();
            int way = rand.nextInt(10);

            ArrayList<Card> shuffledDeck = new ArrayList<>();
        
            ArrayList<ArrayList<Card>> piles = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ArrayList<Card> pile = new ArrayList<>();
                for (int j = i; j < deck.size(); j += 5) {
                    pile.add(deck.get(j));
                }
                piles.add(pile);
            }

            if (way >= 5) {
                int j = (3 + way) % 5;
                while (true) {
                    for (int k = 0; k < piles.get(j).size(); k++) {
                        shuffledDeck.add(piles.get(j).get(piles.get(j).size() - k - 1));
                    }

                    if (j == way % 5) break;
                    else j = (j - 2 < 0) ? j + 3 : j - 2;
                }
            } else {
                int j = (2 + way) % 5;
                while (true) {
                    for (int k = 0; k < piles.get(j).size(); k++) {
                        shuffledDeck.add(piles.get(j).get(piles.get(j).size() - k - 1));
                    }

                    if (j == way) break;
                    else j = (j + 2) % 5;
                }
            }

            shuffledDecks.add(shuffledDeck);
        }

        return shuffledDecks;
    }
}