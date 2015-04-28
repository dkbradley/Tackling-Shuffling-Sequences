# Card Shuffler
A java library able to simulate card shuffling as if it was an actual person. This program is able to shuffle using the methods listed below and can output various statistics and results. This program was used in the paper 'Tackling shuffling sequences'.

## Features
* Shuffle with five different shuffling methods.
* Get standard deviation
* Get interquartile range
* Get maximum and minimum chance
* Show a heatmap of the card spread

## Shuffling methods
A detailed explanation of the shuffling methods is explained in the paper. The shuffling methods currently supported by this program are as follow:
* Riffle shuffle
* Overhand shuffle
* Pile shuffle
* Cut
* Box

## Basic Usage
```
// Create a new instance with 10.000 sorted decks.
Shuffler shuffler = new Shuffler();
shuffler.newDeck(10000);

// Shuffle all 10.000 decks using the riffle shuffle.
shuffler.RiffleShuffle();

// Shuffle the 10.000 decks again, now using the overhand shuffle.
shuffler.OverhandShuffle();

// Gets a heatmap from the previously shuffled decks.
Heatmap map = shuffler.getHeatmap(true);
map.setVisible(true);

// Generates and outputs statistics from the previously shuffled decks.
shuffler.generateStatistics();
System.out.println(shuffler.interquartileRange);
System.out.println(shuffler.standardDeviation);
System.out.println(shuffler.maxChance);
System.out.println(shuffler.minChance);
System.out.println(shuffler.median);

// Generate results from sequences of methods.
// This example is the one used in the paper 'Tackling shuffling sequences'.
// Please note that this sample size will run most computers out of ram, for a small test keep it around 100000.
Sequences sequences = new Sequences(new File("results.csv"), 4, 3);
sequences.shuffleAndSave(25000000);
```