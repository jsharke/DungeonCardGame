import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards = new ArrayList<Card>();

    public Deck() {
        initialize();
    }

    private void initialize() {
        addCards(cards, "Potion", 2, 10);
        addCards(cards, "Weapon", 2, 10);
//        addCards(cards, "Monster", 2, 10);
//        addCards(cards, "Monster", 2, 10);
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCards(List<Card> cards, String type, int minVal, int maxVal) {
        if (cards != null && minVal < maxVal) {
            int newSize = cards.size() + (maxVal - minVal + 1);
            int val = minVal;
            for (int i = cards.size(); i < newSize ; i++) {
                cards.add(new Card(type, val));
                //System.out.println("Added " + cards.get(i).type);
                val ++;
            }
        } else {
            System.out.println("addCards failed");
        }
    }

    public Card drawCard() {
        return cards.removeFirst();
    }

    public void fillHand(List<Card> currentHand) {
        while (currentHand.size() < 4 && !getCards().isEmpty()) {
            currentHand.add(drawCard());
        }
    }

    public void newHand(List<Card> currentHand) {
        while (!currentHand.isEmpty()) {
            cards.add(currentHand.removeFirst());
        }
        while (currentHand.size() < 4 && !cards.isEmpty()) {
            currentHand.add(this.drawCard());
        }
    }
}
