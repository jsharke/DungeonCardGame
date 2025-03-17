import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Deck deck;
    private List<Card> currentHand;
    private Player player;
    private GUI gui;

    public Controller() {
        runGame();
    }

    public void setupGame() {
        System.out.println("Setting up game");
        this.player = new Player();
        this.deck = new Deck();
        this.currentHand = new ArrayList<Card>();
        deck.fillHand(currentHand);
        System.out.println("Game setup");
    }

    public void runGame() {
        // could add main menu, instructions, settings and then call a runGame method afterwards
        //SimpleGUI simpleGUI = new SimpleGUI();
        System.out.println("Game running");
        setupGame();
        if (gui == null) {
            gui = new GUI(this);
        }
        gui.initialize(deck, currentHand, player);
        gui.refreshHand();

    }

    public void runAway() {
        if (player.isSkipAvail() && currentHand.size() == 4) {
            deck.newHand(currentHand);
            System.out.println("New hand is dealt");
            player.skippedHand();
            gui.updateRemainingDeck(deck.getCards().size());
            gui.updateRun("Running not possible");
            gui.refreshHand();
        }
    }

    public void useCard(int index, boolean isPrimary) {
        if (isPrimary) {
            if (currentHand.get(index).type .equals("Potion")) {
                player.heal(currentHand.get(index).value);
                currentHand.remove(currentHand.get(index));
            } else if (currentHand.get(index).type .equals("Weapon")) {
                player.equip(currentHand.get(index).value);
                player.setLastKill(100);
                currentHand.remove(currentHand.get(index));
            } else if (currentHand.get(index).type .equals("Monster")) {
//                if (player.getLastKill() > currentHand.get(index).value) { //&& player.getWeapon() != 0) {
                player.fight(currentHand.get(index).value);
//                    if (player.getWeapon() != 0) {
                player.setLastKill(currentHand.get(index).value);
//                    }
                currentHand.remove(currentHand.get(index));
//                }
            }
        } else {
            if (currentHand.get(index).type .equals("Monster")) {
                player.takeDamage(currentHand.get(index).value);
                currentHand.remove(currentHand.get(index));
            }

        }

        if (currentHand.size() == 1){
            deck.fillHand(currentHand);
            player.skipReset();
            gui.updateRemainingDeck(deck.getCards().size());
        }
        System.out.println(currentHand.size());

        gui.refreshHand();
        gui.refreshPlayer();

        if (player.isDead()) {
            gui.gameOver("YOU DIED");
        }
        if (!containsMonster(currentHand) && deck.getCards().isEmpty()) {
            gui.gameOver("YOU WIN");
        }



    }

    private boolean containsMonster(List<Card> list) {
        for (Card card: list) {
            if (card.type .equals("Monster")) {
                return true;
            }
        }
        return false;
    }

    public List<Card> getCurrentHand() {
        return currentHand;
    }
}
