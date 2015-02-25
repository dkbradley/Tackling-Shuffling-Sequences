package li.aron.shuffler.methods;

import java.util.ArrayList;
import li.aron.shuffler.Card;

public class RiffleShuffle {
    double[] chance = {
        1.4102953523041252E-4,
        3.9286799099900625E-4,
        9.952655771974825E-4,
        0.002301551647269178,
        0.004873874076570025,
        0.009476977371108381,
        0.016958801611457103,
        0.02798202265890422,
        0.042639272623092146,
        0.06008261142344802,
        0.07836862359580177,
        0.09469542017826048,
        0.10605887059965173,
        0.11013805793040757,
        0.10605887059965173,
        0.09469542017826048,
        0.07836862359580177,
        0.06008261142344802,
        0.042639272623092146,
        0.02798202265890422,
        0.016958801611457103,
        0.009476977371108381,
        0.004873874076570025,
        0.002301551647269178,
        9.952655771974825E-4,
        3.9286799099900625E-4,
        1.4102953523041252E-4
    };
    
    /**
     * Shuffles a deck using the Riffle Shuffle method.
     * 
     * @param decks Decks to be shuffled.
     * @return      Shuffled decks
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        ArrayList<ArrayList<Card>> shuffledDecks = new ArrayList<>();
        
        for (ArrayList<Card> deck : decks) {
            ArrayList<Card> shuffledDeck = new ArrayList<>();
            
            int leftDeck = packetSize(Math.random());
            int rightDeck = 52 - leftDeck;
            int piled = 0;
            int chosenDeck = 0;

            while (leftDeck > 0 || rightDeck > 0) {
                double prob = leftDeck * 1.0 / (leftDeck + rightDeck);

                if (prob > Math.random()) {
                    if (chosenDeck != 1 && chosenDeck != 0) {
                        for (int i = 0; i < piled; i++) {
                            shuffledDeck.add(deck.remove(leftDeck));
                        }
                        piled = 0;
                    }

                    piled++;
                    leftDeck--;
                    chosenDeck = 1;
                } else {
                    if (chosenDeck != 2 && chosenDeck != 0) {
                        for (int i = 0; i < piled; i++) {
                            shuffledDeck.add(deck.remove(0));
                        }
                        piled = 0;
                    }

                    piled++;
                    rightDeck--;
                    chosenDeck = 2;
                }
            }
            if (piled != 0) {
                for (int i = 0; i < piled; i++) {
                    if (chosenDeck == 2) {
                        shuffledDeck.add(deck.remove(0));
                    } else {
                        shuffledDeck.add(deck.remove(leftDeck));
                    }
                }
            }
            shuffledDecks.add(shuffledDeck);
        }
        return shuffledDecks;
    }
    
    private int packetSize(double r) {
        double c = 0.0;
        for (int i = 0; i <= 26; i++) {
            c += chance[i];
            if (r <= c) {
                return i + 13;
            }
        }
        return 0;
    }
}
