package li.aron.shuffler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sequences {
    ArrayList<Sequence> sequences = new ArrayList<>();
    
    /**
     * Writes the statistics from the sequences to a file given.
     * 
     * @param methods   Amount of methods in a sequence.
     * @param decks     Amount of decks it should shuffle.
     * @param file      File it should write to.
     * @param pile      How expensive is the pile shuffle (a normal method is 1).
     * @throws java.io.IOException
     */
    public void writeSequencesToFile(int methods, int decks, String file, int pile) throws IOException {
        for (int i = 1; i <= methods; i++) {
            getSequences("", i, pile);
        }
        
        int t = 0;
        for (Sequence sequence : sequences) {
            t++;
            System.out.println(t + "/" + sequences.size());
            Shuffler shuffler = new Shuffler();
            shuffler.newDeck(decks);
            for (char c : sequence.sequence.toCharArray()) {
                if (c == 'p') {
                    shuffler.PileShuffle();
                } else if (c == 'r') {
                    shuffler.RiffleShuffle();
                } else if (c == 'o') {
                    shuffler.OverhandShuffle();
                } else if (c == 'b') {
                    shuffler.Box();
                } else if (c == 'c') {
                    shuffler.Cut();
                }
            }
            shuffler.generateStatistics();
            sequence.interquartileRange = shuffler.interquartileRange;
            sequence.standardDeviation = shuffler.standardDeviation;
            sequence.maxChance = shuffler.maxChance;
            sequence.minChance = shuffler.minChance;
        }
        
        for (int i = 0; i < 4; i++) {
            Collections.sort(sequences, new SequenceComparator(i));
            
            double temp = Double.MIN_VALUE;
            if (i == 3) temp = Double.MAX_VALUE;
            int tempRank = 1;
            int currentRank = 1;
            for (Sequence sequence : sequences) {
                if (i == 0) {
                    if (sequence.interquartileRange > temp) {
                        temp = sequence.interquartileRange;
                        tempRank = currentRank;
                    }

                    sequence.interquartileRangeRank = tempRank;
                } else if (i == 1) {
                    if (sequence.standardDeviation > temp) {
                        temp = sequence.standardDeviation;
                        tempRank = currentRank;
                    }

                    sequence.standardDeviationRank = tempRank;
                } else if (i == 2) {
                    if (sequence.maxChance > temp) {
                        temp = sequence.maxChance;
                        tempRank = currentRank;
                    }

                    sequence.maxChanceRank = tempRank;
                } else if (i == 3) {
                    if (sequence.minChance < temp) {
                        temp = sequence.minChance;
                        tempRank = currentRank;
                    }

                    sequence.minChanceRank = tempRank;
                }
                sequence.rank += tempRank;
                
                currentRank++;
            }
        }
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println("Seq;# IQR;IQR;# SD;SD;# Max;Max;# Min;Min;# Total");
            
            for (Sequence sequence : sequences) {
                out.println("'" + sequence.sequence
                    + ";'" + sequence.interquartileRangeRank
                    + ";'" + sequence.interquartileRange
                    + ";'" + sequence.standardDeviationRank
                    + ";'" + sequence.standardDeviation
                    + ";'" + sequence.maxChanceRank
                    + ";'" + sequence.maxChance
                    + ";'" + sequence.minChanceRank
                    + ";'" + sequence.minChance
                    + ";'" + sequence.rank);
            }
        }
    }
    
    private void getSequences(String sequence, int methods, int pile) {
        if (methods < 1) {
            sequences.add(new Sequence(sequence));
        } else {
            if (methods >= pile) {
                getSequences(sequence + "p", methods - pile, pile);
            }
            getSequences(sequence + "r", methods - 1, pile);
            getSequences(sequence + "o", methods - 1, pile);
            getSequences(sequence + "b", methods - 1, pile);
            getSequences(sequence + "c", methods - 1, pile);
        }
    }
}

class SequenceComparator implements Comparator<Sequence> {
    int num;
    
    public SequenceComparator(int num) {
        this.num = num;
    }

    @Override
    public int compare(Sequence c1, Sequence c2) {
        if (num == 0) {
            if (c1.interquartileRange < c2.interquartileRange) return -1;
            if (c1.interquartileRange > c2.interquartileRange) return 1;
            return 0;
        } else if (num == 1) {
            if (c1.standardDeviation < c2.standardDeviation) return -1;
            if (c1.standardDeviation > c2.standardDeviation) return 1;
            return 0;
        } else if (num == 2) {
            if (c1.maxChance < c2.maxChance) return -1;
            if (c1.maxChance > c2.maxChance) return 1;
            return 0;
        } else if (num == 3) {
            if (c1.minChance > c2.minChance) return -1;
            if (c1.minChance < c2.minChance) return 1;
            return 0;
        }
        
        return 0;
    }
}

class Sequence {
    public String sequence;
    public double interquartileRange;
    public int interquartileRangeRank;
    public double standardDeviation;
    public int standardDeviationRank;
    public double maxChance;
    public int maxChanceRank;
    public double minChance;
    public int minChanceRank;
    public int rank = 0;
    
    public Sequence(String sequence) {
        this.sequence = sequence;
    }
}