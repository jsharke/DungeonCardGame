import java.util.List;

public class CLIController {

    public CLIController () {

    }

    void clearScreen() {
        System.out.println("Clearing Screen");
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void printRemaining(Deck deck) {
        System.out.println("Remaining cards: " + deck.getCards().size() + "\n");
    }

    void printSkipStatus(List<Card> currentHand, boolean skipAvail) {
        if (skipAvail && currentHand.size() == 4) {
            System.out.println("'r' to run (re-deal hand) - can only use if 4 cards and must clear 1 hand before being able to run again. Original cards are added to the bottom of the deck");
        } else {
            System.out.println("Running not possible this hand (skipped last turn or already performed an action)");
        }
    }

    void printHand(List<Card> currentHand, int selection) {
        int i = 1;

        for (Card card : currentHand) {
            if (i == selection) {
                System.out.println(i + " : " + card.type + " ( " + card.value + " ) <--");
            } else {
                System.out.println(i + " : " + card.type + " ( " + card.value + " )");
            }
            i++;
        }
    }

    void printOptions(List<Card> currentHand, Player player, int selection) {
         Card currentCard = currentHand.get(selection - 1);
        System.out.println("\nActions given selection:");
        if (currentCard.type .equals("Potion")) {
            System.out.println("'q' to use - Heal value, increase health by value to a max of 20");
        }
        if (currentCard.type .equals("Weapon")) {
            System.out.println("'q' to equip - Doing so will replace current weapon and reset Last weapon kill");
        }
        if (currentCard.type .equals("Monster")) {
            if (player.getWeapon() == 0) {
                System.out.println("CANNOT kill with equipped weapon - No weapon equipped");
            } else if (player.getLastKill() > currentCard.value) {
               System.out.println("'q' to kill with equipped weapon - Must be less than last weapon kill, will take Monster value minus Weapon value amount of damage to Health");
           } else {
               System.out.println("CANNOT kill with equipped weapon - Last Weapon Kill is not greater than Monster value");
           }
           System.out.println("'w' to kill without weapon - Will take full Monster value to Health");
        }
    }

    void printPlayer(Player player) {
        if (player.getLastKill() == 100) {
            System.out.println("\nHealth: " + player.getHealth() + " | Equipped Weapon: " + player.getWeapon() + " (Last Weapon Kill: " + "None" + " )");
        } else {
            System.out.println("\nHealth: " + player.getHealth() + " | Equipped Weapon: " + player.getWeapon() + " (Last Weapon Kill: " + player.getLastKill() + " )");
        }
    }

    void updateCLI(Deck deck, List<Card> currentHand, Player player, boolean skipAvail, int selection) {
        this.printRemaining(deck);
        this.printSkipStatus(currentHand, player.isSkipAvail());
        this.printHand(currentHand, selection);
        this.printOptions(currentHand, player, selection);
        this.printPlayer(player);
    }

    void printGameOver(int playerHealth, int remainingCards) {
        if (playerHealth <= 0) {
            System.out.print("YOU DIED");
        } else {
            System.out.println("You have cvleared the dungeon!");
        }
    }
}
