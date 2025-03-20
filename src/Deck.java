import java.util.*;

public class Deck {
    private List<Card> cards;
    private List<String> typeOrder;
    private List<Card> sortedCards;
    private Controller controller;
    private int difficulty;

    public Deck(Controller controller, int difficulty) {
        this.controller = controller;
        this.difficulty = difficulty;
        initialize();
    }

    private void initialize() {
        cards = new ArrayList<Card>();
        sortedCards = new ArrayList<Card>();
        typeOrder = Arrays.asList("Potion", "Weapon", "Monster");

        if (difficulty == 0) {
            addCards(cards, "Potion", 2, 5);
            addCards(cards, "Weapon", 2, 5);
            addCards(cards, "Monster", 2, 5);
        } else if (difficulty == 1) {
            addCards(cards, "Potion", 2, 10);
            addCards(cards, "Weapon", 2, 10);
            addCards(cards, "Monster", 2, 14);
        } else if (difficulty == 2) {
            addCards(cards, "Potion", 2, 10);
            addCards(cards, "Weapon", 2, 10);
            addCards(cards, "Monster", 2, 14);
            addCards(cards, "Monster", 2, 14);
        }
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
                cards.add(new Card(type, val, controller));
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
        controller.makeCardPanels();
        System.out.println("Hand filled");
    }

    public void newHand(List<Card> currentHand) {
        while (!currentHand.isEmpty()) {
            cards.add(currentHand.removeFirst());
        }
        while (currentHand.size() < 4 && !cards.isEmpty()) {
            currentHand.add(this.drawCard());
        }
        System.out.println("New hand is dealt");
        controller.makeCardPanels();
    }

    public List<Card> sortedRemainingCards() {
        sortedCards = cards;
        sortedCards.sort(Comparator
                .comparing((Card card) -> typeOrder.indexOf(card.type)) // Sort by suit order
                .thenComparing(card -> card.value));
        return sortedCards;
    }
}
