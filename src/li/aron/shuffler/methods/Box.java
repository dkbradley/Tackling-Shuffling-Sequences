package li.aron.shuffler.methods;

import java.util.ArrayList;
import java.util.Random;
import li.aron.shuffler.Card;

public class Box {
    double[] chance = {
        4.46100752549618E-5,
        1.600902172069403E-4,
        5.140929987637019E-4,
        0.0014772828039793363,
        0.003798662007932482,
        0.008740629697903164,
        0.017996988837729353,
        0.03315904626424956,
        0.05467002489199788,
        0.0806569081730478,
        0.10648266850745075,
        0.12579440923099774,
        0.1329807601338109,
        0.12579440923099774,
        0.10648266850745075,
        0.0806569081730478,
        0.05467002489199788,
        0.03315904626424956,
        0.017996988837729353,
        0.008740629697903164,
        0.003798662007932482,
        0.0014772828039793363,
        5.140929987637019E-4,
        1.600902172069403E-4,
        4.46100752549618E-5
    };
    
    /**
     * Takes 4 cuts from a deck essentially flipping them.
     * 
     * @param decks     Decks to be cut.
     * @return          Cut decks.
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        ArrayList<ArrayList<Card>> newDecks = new ArrayList<>();
        
        for (ArrayList<Card> deck : decks) {
            ArrayList<Card> newDeck = new ArrayList<>();
            
            Random rand = new Random();
            boolean linked = rand.nextBoolean();
            
            int p1 = packetSize(Math.random());
            int p2 = packetSize(Math.random());
            int p3 = linked ? 26 - p1 : 26 - p2;
            
            for (int i = p1 + p2 + p3; i < 52; i++) {
                newDeck.add(deck.get(i));
            }
            
            for (int i = p1 + p2; i < p1 + p2 + p3; i++) {
                newDeck.add(deck.get(i));
            }
            
            for (int i = p1; i < p1 + p2; i++) {
                newDeck.add(deck.get(i));
            }
            
            for (int i = 0; i < p1; i++) {
                newDeck.add(deck.get(i));
            }
            
            newDecks.add(newDeck);
        }
        
        return newDecks;
    }
    
    private int packetSize(double r) {
        double c = 0.0;
        for (int i = 0; i <= 24; i++) {
            c += chance[i];
            if (r <= c) {
                return i + 1;
            }
        }
        return 0;
    }
}