package li.aron.shuffler.methods;

import java.util.ArrayList;
import li.aron.shuffler.Card;

public class OverhandShuffle {
    double[] chance = {
        0.10675483945301002,
        0.11862889531911201,
        0.11717659620232311,
        0.10850819688018143,
        0.09646181921058905,
        0.08337078548319514,
        0.07058582509542609,
        0.05882767352161258,
        0.04842290269458448,
        0.03945982547589064,
        0.03189006294268411,
        0.02559346799384616,
        0.020418577221835262,
        0.016206915049641706,
        0.012806803167803013,
        0.010080482706354869,
        0.007907087924613152,
        0.0061831434074794646,
        0.0048216687340514024,
        0.0037505790548904632,
        0.002910806697301981,
        0.0022543958490729397,
        0.0017427104573451819,
        0.0013448246460144577,
        0.0010361214254449261,
        7.970998473778661E-4,
        6.12376686589774E-4,
        4.6986194332306004E-4,
        3.600851263641138E-4,
        2.756495433605817E-4,
        2.1079350115521452E-4,
        1.6103965603385915E-4
    };
    
    /**
     * Shuffles a deck using the Overhand Shuffle method.
     * 
     * @param decks Decks to be shuffled.
     * @return      Shuffled decks
     */
    public ArrayList<ArrayList<Card>> shuffleDeck(ArrayList<ArrayList<Card>> decks) {
        ArrayList<ArrayList<Card>> shuffledDecks = new ArrayList<>();
        
        for (ArrayList<Card> deck : decks) {
            ArrayList<Card> shuffledDeck = new ArrayList<>();
            
            while (!deck.isEmpty()) {
                int packet = packetSize(Math.random());
                packet = Math.min(packet, deck.size());
                
                for (int i = 0; i < packet; i++) {
                    shuffledDeck.add(i, deck.remove(0));
                }
            }
            
            shuffledDecks.add(shuffledDeck);
        }
        
        return shuffledDecks;
    }
    
    private int packetSize(double r) {
        double c = 0.0;
        for (int i = 0; i <= 31; i++) {
            c += chance[i];
            if (r <= c) {
                return i + 1;
            }
        }
        return 0;
    }
}
