package li.aron.shuffler.methods;

import li.aron.shuffler.Card;
import java.util.ArrayList;

public class Cut {
    double[] chance = {
        0.001296222731805947,
        0.002103977170145009,
        0.0033100207032411948,
        0.005047178852531671,
        0.00745924680114996,
        0.010684877714656685,
        0.014834483608308776,
        0.019961979596064997,
        0.026035329540363528,
        0.03291174036531431,
        0.04032430722703931,
        0.047886299671675625,
        0.05511679306946605,
        0.06148722588955736,
        0.06648354305146661,
        0.06967415490012244,
        0.07077136318751316,
        0.06967415490012244,
        0.06648354305146661,
        0.06148722588955736,
        0.05511679306946605,
        0.047886299671675625,
        0.04032430722703931,
        0.03291174036531431,
        0.026035329540363528,
        0.019961979596064997,
        0.014834483608308776,
        0.010684877714656685,
        0.00745924680114996,
        0.005047178852531671,
        0.0033100207032411948,
        0.002103977170145009,
        0.001296222731805947
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
