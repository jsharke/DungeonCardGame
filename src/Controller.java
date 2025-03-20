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
        runGame();
    }

    public void runGame() {
        // could add main menu, instructions, settings and then call a runGame method afterwards
        System.out.println("Game running");
        if (gui == null) {
            gui = new GUI(this);
        }
    }

    public void newGame() {
        System.out.println("New Game");
        setupGame();
        gui.newGame(deck, currentHand, player);
    }

    public void setupGame() {
        this.player = new Player();
        this.deck = new Deck(this);
        this.currentHand = new ArrayList<Card>();
        deck.fillHand(currentHand);
        System.out.println("Game set up");
    }

    public void runAway() {
        if (player.isSkipAvail() && currentHand.size() == 4) {
            deck.newHand(currentHand);
            System.out.println("New hand is dealt");
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

    } //different from using weapon, see useCard for that implementation

    public void sheatheWeapon(){
        isAttacking = false;
        gui.sheatheWeapon();
        gui.refreshCurrentHand(currentHand, isAttacking);
    }

    public void useCard(Card card) {
        if (card.type.equals("Potion")) {
            player.heal(card.value);
            System.out.println(player.getHealth() + " healed for " + card.value);
        }
        if (card.type.equals("Weapon")) {
            player.equip(card.value);
            player.setLastKill(101);
            gui.updateWeapon();
            gui.enableFight();
            System.out.println("equipped " + card.value);
        }
        if (card.type.equals("Monster")) {
            if (isAttacking) {
                System.out.println("Attacking " + card.value + " with " + player.getWeapon() + " weapon and " + player.getHealth() + " health");
                player.fight(card.value);
                player.setLastKill(card.value);
                System.out.println("just killed " + player.getLastKill() + ", health now " + player.getHealth());
                gui.updateWeapon();
                sheatheWeapon();
            } else {
                player.takeDamage(card.value);
                System.out.println(player.getHealth() + " took damage " + card.value);
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
//        System.out.println(currentHand.size());

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
