package li.aron.shuffler;

public class Card {
    public int suit;
    public int card;
    
    private final String[] suits = {"\u2660","\u2666","\u2663","\u2665"};
    private final String[] cards = {"K","Q","J","10","9","8","7","6","5","4","3","2","A"};
    
    public Card(int suit, int card) {
        this.suit = suit;
        this.card = card;
    }
    
    public String getSuit() {
        return suits[suit];
    }
    
    public String getCard() {
        return cards[card];
    }
    
    public int getNumber() {
        return (card + 1) + (suit) * 13;
    }
    
    public String getCardByNumber(int num) {
        int cSuit = num / 13;
        int cCard = num % 13;
        return suits[cSuit] + cards[cCard];
    }
}
