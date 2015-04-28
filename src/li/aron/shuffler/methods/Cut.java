package li.aron.shuffler.methods;

import li.aron.shuffler.Card;
import java.util.ArrayList;

public class Cut {
    double[] chance = {
        3.3457556441221355E-5,
        8.814892059186139E-5,
        2.1817067376144012E-4,
        5.072620143249421E-4,
        0.001107962102984502,
        0.0022733906253977637,
        0.004382075123392136,
        0.007934912958916854,
        0.013497741628297016,
        0.021569329706627883,
        0.032379398916472936,
        0.04566227134725548,
        0.06049268112978584,
        0.07528435803870111,
        0.08801633169107488,
        0.09666702920071231,
        0.09973557010035818,
        0.09666702920071231,
        0.08801633169107488,
        0.07528435803870111,
        0.06049268112978584,
        0.04566227134725548,
        0.032379398916472936,
        0.021569329706627883,
        0.013497741628297016,
        0.007934912958916854,
        0.004382075123392136,
        0.0022733906253977637,
        0.001107962102984502,
        5.072620143249421E-4,
        2.1817067376144012E-4,
        8.814892059186139E-5,
        3.3457556441221355E-5
    };
    
    /**
     * Takes a cut from a deck and puts it on the top.
     * 
     * @param decks     Decks to be cut.
     * @return          Cut decks.
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        for (ArrayList<Card> deck : decks) {
            int cutSize = packetSize(Math.random());
            
            for (int i = 0; i < cutSize; i++) {
                deck.add(0, deck.remove(deck.size() - 1));
            }
        }
        
        return decks;
    }
    
    private int packetSize(double r) {
        double c = 0.0;
        for (int i = 0; i <= 32; i++) {
            c += chance[i];
            if (r <= c) {
                return i + 10;
            }
        }
        return 0;
    }
}
