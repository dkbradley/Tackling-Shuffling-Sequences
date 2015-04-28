package li.aron.shuffler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import li.aron.shuffler.methods.Cut;
import li.aron.shuffler.methods.Box;
import li.aron.shuffler.methods.OverhandShuffle;
import li.aron.shuffler.methods.PileShuffle;
import li.aron.shuffler.methods.RiffleShuffle;

public class Shuffler {
    private ArrayList<ArrayList<Card>> decks;
    
    private final PileShuffle PileShuffle;
    private final OverhandShuffle OverhandShuffle;
    private final RiffleShuffle RiffleShuffle;
    private final Cut Cut;
    private final Box Box;
    
    public double interquartileRange = Double.NaN;
    public double standardDeviation  = Double.NaN;
    public double maxChance          = Double.NaN;
    public double minChance          = Double.NaN;
    public double median             = Double.NaN;
    
    /**
     * Creates a shuffler instance.
     */
    public Shuffler() {
        PileShuffle = new PileShuffle();
        OverhandShuffle = new OverhandShuffle();
        RiffleShuffle = new RiffleShuffle();
        Cut = new Cut();
        Box = new Box();
        
        decks = new ArrayList<>();
    }
    
    /**
     * Adds a new deck using a set order.
     */
    public void newDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                Card c = new Card(i, j);
                deck.add(c);
            }
        }
        
        decks.add(deck);
    }
    
    /**
     * Adds a new deck using a set order.
     * 
     * @param amount    Amount of decks to be created.
     */
    public void newDeck(int amount) {
        for (int i = 0; i < amount; i++) {
            newDeck();
        }
    }
    
    /**
     * Adds a new deck in given order.
     * 
     * @param deck  The arrangement of the cards
     */
    public void newDeck(ArrayList<Card> deck) {
        decks.add(deck);
    }
    
    /**
     * Sets current decks to given arraylist.
     * 
     * @param decks The decks it should use.
     */
    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }
    
    /**
     * Returns all the decks.
     * 
     * @return      All decks.
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }
    
    /**
     * Return total amount of decks.
     * 
     * @return      Amount of decks.
     */
    public int getTotalDecks() {
        return decks.size();
    }
    
    /**
     * Shuffles a deck using the Riffle Shuffle method.
     */
    public void RiffleShuffle() {
        decks = RiffleShuffle.shuffleDeck(decks);
    }
    
    /**
     * Shuffles a deck using the Overhand Shuffle method.
     */
    public void OverhandShuffle() {
        decks = OverhandShuffle.shuffleDeck(decks);
    }
    
    /**
     * Shuffles a deck using the Pile Shuffle method.
     */
    public void PileShuffle() {
        decks = PileShuffle.shuffleDeck(decks);
    }
    
    /**
     * Takes a cut from a deck and puts it on the top.
     */
    public void Cut() {
        decks = Cut.shuffleDeck(decks);
    }
    
    /**
     * Takes 4 cuts from a deck essentially flipping them.
     */
    public void Box() {
        decks = Box.shuffleDeck(decks);
    }
    
    /**
     * Returns a heatmap from all current decks.
     * 
     * @param showPercentages   Should the heatmap show the percentages?
     * @return Heatmap instance with decks initialized.
     */
    public Heatmap getHeatmap(boolean showPercentages) {
        Object[][] o = new Object[52][52];
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 52; j++) {
                o[i][j] = 0;
            }
        }
        
        for (ArrayList<Card> deck : decks) {
            for (int i = 0; i < deck.size(); i++) {
                Card c = deck.get(i);
                o[c.getNumber()-1][i] = (int) o[c.getNumber()-1][i] + 1;
            }
        }
        
        return new Heatmap(52, o, showPercentages);
    }
    
    /**
     * Returns a heatmap from data from a file.
     * 
     * @param showPercentages   Should the heatmap show the percentages?
     * @param file              File it should read from.
     * @return Heatmap instance with decks initialized.
     * @throws java.io.FileNotFoundException
     */
    public Heatmap getHeatmapFromFile(boolean showPercentages, String file) throws FileNotFoundException {
        Scanner in = new Scanner(new FileReader(file));
        
        Object[][] o = new Object[52][52];
        
        int i = 0;
        while (in.hasNext()) {
            int num = in.nextInt();
            o[i / 52][i % 52] = num;
            
            i++;
        }
        
        return new Heatmap(52, o, showPercentages);
    }
    
    /**
     * Returns a heatmap from all current decks.
     * 
     * @param file  File it should save decks data to.
     * @throws java.io.IOException
     */
    public void saveHeatmapToFile(String file) throws IOException {
        Object[][] o = new Object[52][52];
        
        if (new File(file).exists()) {
            Scanner in = new Scanner(new FileReader(file));

            int i = 0;
            while (in.hasNext()) {
                int num = in.nextInt();
                o[i / 52][i % 52] = num;

                i++;
            }
        } else {
            for (int i = 0; i < 52; i++) {
                for (int j = 0; j < 52; j++) {
                    o[i][j] = 0;
                }
            }
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        
        for (ArrayList<Card> deck : decks) {
            for (int i = 0; i < deck.size(); i++) {
                Card c = deck.get(i);
                o[c.getNumber()-1][i] = (int) o[c.getNumber()-1][i] + 1;
            }
        }
        
        for (Object[] row : o) {
            for (Object card : row) {
                writer.write("" + card);
                writer.newLine();
                writer.flush();
            }
        }
    }
    
    /**
     * Generates statistics and sets the variables:
     * interquartileRange, standardDeviation, maxChance, minChance, median
     */
    public void generateStatistics() {
        int[] rowOccurrance = new int[52];
        int[][] occurrance = new int[52][52];
        double[] chance = new double[52 * 52];
        
        for (ArrayList<Card> deck : decks) {
            for (int i = 0; i < 52; i++) {
                rowOccurrance[i]++;
                occurrance[i][deck.get(i).getNumber() - 1]++;
            }
        }
        
        double powerSum = 0;
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 52; j++) {
                double rowPercent = (occurrance[i][j] * 1.0) / (rowOccurrance[i] * 1.0) * 100.0;
                chance[i + 52 * j] = rowPercent / 100.0 * 52;
                powerSum += Math.pow(chance[i + 52 * j] - 1.0, 2);
            }
        }
        
        Arrays.sort(chance);
        
        interquartileRange = chance[52 * 39 - 1] - chance[52 * 13 - 1];
        standardDeviation = Math.sqrt(powerSum / (52 * 52));
        maxChance = chance[52 * 52 - 1];
        minChance = chance[0];
        median = (chance[52 * 26 - 1] + chance[52 * 26]) / 2;
    }
}
