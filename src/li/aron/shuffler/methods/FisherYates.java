package li.aron.shuffler.methods;

import java.util.ArrayList;
import java.util.Collections;
import li.aron.shuffler.Card;

public class FisherYates {
    
    /**
     * Shuffles a deck using the Fisher Yates method.
     * 
     * @param decks     Decks to be shuffled.
     * @return          Shuffled decks
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        for (ArrayList<Card> deck : decks) {
            int n = deck.size(), i;
            while (n > 0) {
                i = (int) (Math.random() * n--);
                Collections.swap(deck, n, i);
            }
        }
        
        return decks;
    }
}
