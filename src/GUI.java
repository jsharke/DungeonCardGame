import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI {
    private JFrame frame;
    private Controller controller;
    private JPanel gamePanel;
    private GridBagConstraints gbc;
    private JLabel remainingCardsLabel;
    private JButton runButton;
    private JLabel playerLabel;

    private JPanel card0Panel;
    private JPanel card1Panel;
    private JPanel card2Panel;
    private JPanel card3Panel;
    private JLabel card0JLabel;
    private JLabel card1JLabel;
    private JLabel card2JLabel;
    private JLabel card3JLabel;
    private JButton card0PrimaryButton;
    private JButton card1PrimaryButton;
    private JButton card2PrimaryButton;
    private JButton card3PrimaryButton;
    private JButton card0SecondaryButton;
    private JButton card1SecondaryButton;
    private JButton card2SecondaryButton;
    private JButton card3SecondaryButton;

    private Player player;

    public GUI(Controller controller) {
        this.controller = controller;

        frame = new JFrame("GAME TITLE");
        frame.setSize(1000,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        card0Panel = new JPanel();
        card1Panel = new JPanel();
        card2Panel = new JPanel();
        card3Panel = new JPanel();
        card0JLabel = new JLabel();
        card1JLabel = new JLabel();
        card2JLabel = new JLabel();
        card3JLabel = new JLabel();
        card0PrimaryButton = new JButton();
        card1PrimaryButton = new JButton();
        card2PrimaryButton = new JButton();
        card3PrimaryButton = new JButton();
        card0SecondaryButton = new JButton();
        card1SecondaryButton = new JButton();
        card2SecondaryButton = new JButton();
        card3SecondaryButton = new JButton();

        card0Panel.add(card0JLabel);
        card0Panel.add(card0PrimaryButton);
        card0Panel.add(card0SecondaryButton);

        remainingCardsLabel = new JLabel();
        playerLabel = new JLabel();
        runButton = new JButton();

        addToUI(remainingCardsLabel, 0, 1);
        addToUI(playerLabel, 0, 0);
        addToUI(runButton, 0, 2);
        addToUI(card0JLabel, 1, 1);
        addToUI(card0PrimaryButton, 1, 2);
        addToUI(card0SecondaryButton, 1, 3);
        addToUI(card1JLabel, 2, 1);
        addToUI(card1PrimaryButton, 2, 2);
        addToUI(card1SecondaryButton, 2, 3);
        addToUI(card2JLabel, 3, 1);
        addToUI(card2PrimaryButton, 3, 2);
        addToUI(card2SecondaryButton, 3, 3);
        addToUI(card3JLabel, 4, 1);
        addToUI(card3PrimaryButton, 4, 2);
        addToUI(card3SecondaryButton, 4, 3);

        setupGeneralActionListener(runButton, controller::runAway);
        setupUseCardActionListener(card0PrimaryButton, 0, true);
        setupUseCardActionListener(card0SecondaryButton, 0, false);
        setupUseCardActionListener(card1PrimaryButton, 1, true);
        setupUseCardActionListener(card1SecondaryButton, 1, false);
        setupUseCardActionListener(card2PrimaryButton, 2, true);
        setupUseCardActionListener(card2SecondaryButton, 2, false);
        setupUseCardActionListener(card3PrimaryButton, 3, true);
        setupUseCardActionListener(card3SecondaryButton, 3, false);

        frame.add(gamePanel);
        frame.setVisible(true);
    }

    public void initialize(Deck deck, List<Card> currentHand, Player player) {
        this.player = player;
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(deck.getCards().size()));
        runButton.setText("Run");
        playerLabel.setText("Health: " + player.getHealth() + " | Equipped Weapon: " + player.getWeapon() + " (Last Weapon Kill: " + "None" + " )");
    }

    private void setupGeneralActionListener(JButton button, Runnable action) {
        button.addActionListener(e -> action.run());
    }

    public void setupUseCardActionListener(JButton button, int index, boolean isPrimary) {
        // Use lambda expression to pass the integer to the controller method
        button.addActionListener(e -> controller.useCard(index, isPrimary));  // Passing the integer to the method
    }
//    private void setupActionListener(JButton button, ActionListener actionListener) {
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                method; //controller.method();
//            }
//        });
//    }

    private void addToUI(JComponent jcomp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        gamePanel.add(jcomp, gbc);
    }

    public void updateRemainingDeck(int size) {
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(size));
    }

    public void updateRun(String str) {
        runButton.setText(str);
    }

    public void refreshHand() {
        setupCard(0, card0JLabel, card0PrimaryButton, card0SecondaryButton, card0Panel);
        setupCard(1, card1JLabel, card1PrimaryButton, card1SecondaryButton, card1Panel);
        setupCard(2, card2JLabel, card2PrimaryButton, card2SecondaryButton, card2Panel);
        setupCard(3, card3JLabel, card3PrimaryButton, card3SecondaryButton, card3Panel);
    }

    private void setupCard(int index, JLabel jlabel, JButton jPrimaryButton, JButton jSecondaryButton, JPanel jpanel) {
        List<Card> currentHand = controller.getCurrentHand();
        int size = currentHand.size();
        if (size > index) {

            jlabel.setText(currentHand.get(index).type + ": " + currentHand.get(index).value);
//            jpanel.add(jlabel);
//            jpanel.add(jPrimaryButton);
//            jpanel.add(jSecondaryButton);
//            jpanel.setVisible(true);
            jlabel.setVisible(true);
            jPrimaryButton.setVisible(true);
            jSecondaryButton.setVisible(false);


            jPrimaryButton.setEnabled(true);
            jSecondaryButton.setEnabled(true);

            switch (currentHand.get(index).type) {
                case "Potion" -> {
                    jPrimaryButton.setText("Heal");
                }
                case "Weapon" -> {
                    jPrimaryButton.setText("Equip");
                }
                case "Monster" -> {
                    if (player.getWeapon() == 0) {
                        jPrimaryButton.setText("No equipped weapon!");
                        jPrimaryButton.setEnabled(false);
                    } else if (player.getLastKill() > currentHand.get(index).value) {
                        jPrimaryButton.setText("Fight");
                    } else {
                        jPrimaryButton.setText("Stronger than last kill!");
                        jPrimaryButton.setEnabled(false);
                    }
                    jSecondaryButton.setText("Take damage");
                    jSecondaryButton.setVisible(true);
                }
            }
        } else {
            jlabel.setVisible(false);
            jPrimaryButton.setVisible(false);
            jSecondaryButton.setVisible(false);
        }
    }

    public void refreshPlayer() {
        if (player.getLastKill() == 100) {
            playerLabel.setText("Health: " + player.getHealth() + " | Equipped Weapon: " + player.getWeapon() + " (Last Weapon Kill: " + "None" + " )");
        } else {
            playerLabel.setText("Health: " + player.getHealth() + " | Equipped Weapon: " + player.getWeapon() + " (Last Weapon Kill: " + player.getLastKill() + " )");
        }
    }
    public void gameOver(String text){
//        JOptionPane gameOver = new JOptionPane();
//        gameOver.showMessageDialog(frame, text);

        int option = JOptionPane.showOptionDialog(null, text, "Game Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"Restart", "Quit"}, "Restart");

        // Handle the user's choice
        if (option == 0) {
            // Option 0 is "Restart", call startGame() again

            controller.runGame();
        } else if (option == 1) {
            // Option 1 is "Quit", exit the program
            System.exit(0);
        }
    }
}
