package li.aron.shuffler.methods;

import java.util.ArrayList;
import java.util.Random;
import li.aron.shuffler.Card;

public class Box {
    double[] chance = {
        0.0017276880688458227,
        0.003272888823781654,
        0.005865022747385038,
        0.009942158384283343,
        0.0159427847577157,
        0.024183559124485776,
        0.03470154249855356,
        0.04710314134061936,
        0.06048163068719037,
        0.07346316604592332,
        0.084408915276511,
        0.09174439182973411,
        0.09432857239331743,
        0.09174439182973411,
        0.084408915276511,
        0.07346316604592332,
        0.06048163068719037,
        0.04710314134061936,
        0.03470154249855356,
        0.024183559124485776,
        0.0159427847577157,
        0.009942158384283343,
        0.005865022747385038,
        0.003272888823781654,
        0.0017276880688458227
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