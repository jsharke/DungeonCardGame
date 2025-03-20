import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Deck deck;
    private List<Card> currentHand;
    private Player player;
    private GUI gui;
    private List<Card> remainingDeck;
    private Boolean isAttacking = false;

    public Controller() {
        mainMenu();
    }

    public void mainMenu() {
        // could add main menu, instructions, settings and then call a runGame method afterwards
        System.out.println("Game running");
        if (gui == null) {
            gui = new GUI(this);
        }
        gui.mainMenu();
    }

    public void newGame(int difficulty) {
        System.out.println("New Game");
        setupGame(difficulty);
        gui.newGame(deck, currentHand, player);
    }

    public void setupGame(int difficulty) {
        this.player = new Player();
        this.deck = new Deck(this, difficulty);
        this.currentHand = new ArrayList<Card>();
        deck.fillHand(currentHand);
        System.out.println("Game set up");
    }

    public void showRules() {
        gui.showRules();
    }

    public void runAway() {
        if (player.isSkipAvail() && currentHand.size() == 4) {
            System.out.println("Run selected");
            deck.newHand(currentHand);
            player.runUnavavailable();
            gui.refreshRun();
            gui.updateRemainingDeck(deck.getCards().size());
            gui.refreshCurrentHand(currentHand, false);
        }
    }

    public void equipWeapon() {
        isAttacking = true;
        gui.usingWeapon();
        gui.refreshCurrentHand(currentHand, isAttacking);
        System.out.println("Weapon " + player.getWeapon() + " attacking");

    } //different from using weapon, see useCard for that implementation

    public void sheatheWeapon(){
        isAttacking = false;
        gui.sheatheWeapon();
        gui.refreshCurrentHand(currentHand, isAttacking);
        System.out.println("Weapon sheathed");
    }

    public void useCard(Card card) {
        if (card.type.equals("Potion")) {
            System.out.println("Player (health " + player.getHealth() + ") healing for " + card.value);
            player.heal(card.value);
            System.out.println("Player health now " + player.getHealth());
        }
        if (card.type.equals("Weapon")) {
            player.equip(card.value);
            player.setLastKill(101);
            gui.updateWeapon();
            gui.enableFight();
            System.out.println("Equipped weapon " + card.value);
        }
        if (card.type.equals("Monster")) {
            if (isAttacking) {
                System.out.println("Attacking monster" + card.value + " with " + player.getWeapon() + " weapon and " + player.getHealth() + " health");
                player.fight(card.value);
                player.setLastKill(card.value);
                System.out.println("Just killed monster" + player.getLastKill() + " - health now " + player.getHealth());
                gui.updateWeapon();
                sheatheWeapon();
            } else {
                System.out.println("Player (health " + player.getHealth() + ") taking damage " + card.value);
                player.takeDamage(card.value);
                System.out.println("Player health now " + player.getHealth());
            }
        }

        player.runUnavavailable();
        currentHand.remove(card);
        //gui.currentHandRemove(card);

        if (currentHand.size() == 1){
            deck.fillHand(currentHand);
            player.skipReset();
            gui.updateRemainingDeck(deck.getCards().size());
        }

        if (player.isDead()) {
            gui.gameOver("YOU DIED");
        }
        if (!containsMonster(currentHand) && deck.getCards().isEmpty()) {
            gui.gameOver("YOU WIN");
        }

        gui.refreshRun();
        gui.refreshCurrentHand(currentHand, false);
        gui.refreshPlayer();
    }

    public void makeCardPanels() {
        for (Card card : currentHand){
            card.createCardPanel();
            System.out.println("Panel made for " + card.type + " " + card.value);
        }

    }

    public void showSortedRemainingCards() {
          gui.showRemainingDeck(deck.sortedRemainingCards());
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

    private void gameOver() {

    }
}
