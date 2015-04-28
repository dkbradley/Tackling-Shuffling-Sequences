package li.aron.shuffler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Sequences {
    private ArrayList<Sequence> sequences;
    private final File file;
    
    /**
     * Initializes sequences from a previously generated file.
     * 
     * @param file      File it should initialize from.
     * @throws java.io.FileNotFoundException
     */
    public Sequences(File file) throws FileNotFoundException {
        this.file = file;
        this.sequences = new ArrayList<>();
        
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNext()) {
                String line = in.nextLine();
                if (line.startsWith("'")) {
                    String[] sequence = line.replaceAll("'", "").split(";");
                    
                    Sequence seq = new Sequence(sequence[0]);
                    seq.sampleSize = Integer.parseInt(sequence[1]);
                    seq.interquartileRange = Double.parseDouble(sequence[3]);
                    seq.standardDeviation = Double.parseDouble(sequence[5]);
                    seq.maxChance = Double.parseDouble(sequence[7]);
                    seq.minChance = Double.parseDouble(sequence[9]);
                    
                    sequences.add(seq);
                }
            }
        }
    }
    
    
    /**
     * Initializes sequences and file from given parameters.
     * 
     * @param file      File it should write to.
     * @param methods   Amount of methods in a sequence.
     * @param pile      How expensive is the pile shuffle (a normal method is 1).
     */
    public Sequences(File file, int methods, int pile) {
        this.file = file;
        this.sequences = new ArrayList<>();
        
        for (int i = 1; i <= methods; i++) {
            getSequences("", i, pile);
        }
    }
    
    private void getSequences(String sequence, int methods, int pile) {
        if (methods < 1) {
            sequences.add(new Sequence(sequence));
        } else {
            if (methods >= pile) getSequences(sequence + "p", methods - pile, pile);
            getSequences(sequence + "r", methods - 1, pile);
            getSequences(sequence + "o", methods - 1, pile);
            getSequences(sequence + "b", methods - 1, pile);
            getSequences(sequence + "c", methods - 1, pile);
        }
    }
    
    /**
     * Writes the statistics from the sequences to a file given.
     * 
     * @param decks     Amount of decks it should shuffle.
     * @throws java.io.IOException
     */
    public void shuffleAndSave(int decks) throws IOException {
        for (int i = 0; i < sequences.size(); i++) {
            System.out.println((i + 1) + "/" + sequences.size());
            
            Sequence sequence = sequences.get(i);
            
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
            sequence.interquartileRange = ((sequence.sampleSize * sequence.interquartileRange) + (decks * shuffler.interquartileRange)) / ((sequence.sampleSize + decks) * 1.0);
            sequence.standardDeviation = ((sequence.sampleSize * sequence.standardDeviation) + (decks * shuffler.standardDeviation)) / ((sequence.sampleSize + decks) * 1.0);
            sequence.maxChance = ((sequence.sampleSize * sequence.maxChance) + (decks * shuffler.maxChance)) / ((sequence.sampleSize + decks) * 1.0);
            sequence.minChance = ((sequence.sampleSize * sequence.minChance) + (decks * shuffler.minChance)) / ((sequence.sampleSize + decks) * 1.0);
            sequence.sampleSize += decks;
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
        
        Collections.sort(sequences, new SequenceComparator(4));
        
        if (file.exists()) {
            file.delete();
        }
        
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println("Sequence;Sample Size;# IQR;IQR;# SD;SD;# Max;Max;# Min;Min;# Total");
            
            for (Sequence sequence : sequences) {
                out.println("'" + sequence.sequence
                    + ";'" + sequence.sampleSize
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
        } else if (num == 4) {
            if (c1.rank < c2.rank) return -1;
            if (c1.rank > c2.rank) return 1;
            return 0;
        }
        
        return 0;
    }
}

class Sequence {
    public String sequence;
    public int sampleSize = 0;
    public double interquartileRange = 0;
    public int interquartileRangeRank;
    public double standardDeviation = 0;
    public int standardDeviationRank;
    public double maxChance = 0;
    public int maxChanceRank;
    public double minChance = 0;
    public int minChanceRank;
    public int rank = 0;
    
    public Sequence(String sequence) {
        this.sequence = sequence;
    }
    
    public Sequence(String sequence, int sampleSize, double interquartileRange, double standardDeviation, double maxChance, double minChance) {
        this.sequence = sequence;
        this.sampleSize = sampleSize;
        this.interquartileRange = interquartileRange;
        this.standardDeviation = standardDeviation;
        this.maxChance = maxChance;
        this.minChance = minChance;
    }
}