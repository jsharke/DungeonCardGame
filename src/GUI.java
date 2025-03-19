import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUI {
    //CONSTRUCTOR
    private Controller controller;
    private JFrame gameWindow;
    private Container con;
    private JPanel titleNamePanel;
    private JLabel titleNameLabel;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 56);
    private JPanel startButtonPanel;
    private JButton startButton;
    private Font normalFont = new Font("Times New Roman", Font.PLAIN, 20);

    //NEW GAME
    private Deck deck;
    private Player player;
    private List<Card> currentHand;

    private JPanel remainingCardsPanel;
    private JLabel remainingCardsLabel;
    private JButton remainingCardsButton;
    private JPanel currentHandPanel;
    private JPanel runPanel;
    private JButton runButton;
    private JPanel fightPanel;
    private JButton fightButton;
    private JButton sheatheButton;
    private JPanel playerPanel;
    private JLabel playerLabel;
    private ActionListener equipListener;

    //REMAINING CARDS
    private JDialog remainingCardsWindow;
    private GridBagConstraints gbc;

    public GUI(Controller controller) {
        this.controller = controller;
        mainMenu();
    }
    public void mainMenu(){
        gameWindow = new JFrame("GAME TITLE");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(800, 600);
        //gameWindow.getContentPane().setBackground(Color.black);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameWindow.setLayout(null);

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setOpaque(true);

        titleNameLabel = new JLabel("Dungeon Card Game");
        //titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        titleNamePanel.add(titleNameLabel);
        //con.add(titleNamePanel);
        gameWindow.add(titleNamePanel);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        //startButtonPanel.setBackground(Color.black);

        JButton startButton = new JButton("NEW GAME");
        //startButton.setBackground(Color.black);
        //startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(true);
        startButton.addActionListener(e -> controller.newGame());

        startButtonPanel.add(startButton);

//        con.add(startButtonPanel);
        gameWindow.add(startButtonPanel);
    }

    public void newGame(Deck deck, List<Card> currentHand, Player player) {
        this.deck = deck;
        this.player = player;
        this.currentHand = currentHand;

        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        //Remaining cards
        remainingCardsPanel = new JPanel();
        remainingCardsPanel.setBounds(25, 25, 300, 50);
        remainingCardsPanel.setBackground(Color.blue);

        remainingCardsLabel = new JLabel();
        remainingCardsLabel.setForeground(Color.white);
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(deck.getCards().size()));
        remainingCardsLabel.setFont(normalFont);
        remainingCardsButton = new JButton();
        remainingCardsButton.setText("View deck");
        remainingCardsButton.setFont(normalFont);
        //remainingCardsButton.addActionListener(e -> controller.showSortedRemainingCards());

        remainingCardsPanel.add(remainingCardsLabel);
        remainingCardsPanel.add(remainingCardsButton);
        remainingCardsPanel.setVisible(true);
        gameWindow.add(remainingCardsPanel);
        //con.add(mainTextPanel);

        //Run
        runPanel = new JPanel();
        runPanel.setBackground(Color.blue);
        runPanel.setBounds(575, 25, 100, 50);
        runButton = new JButton("RUN");
        runButton.setFont(normalFont);
        runButton.addActionListener(e -> controller.runAway());

        runPanel.add(runButton);
        runPanel.setVisible(true);
        gameWindow.add(runPanel);

        //Current hand
        currentHandPanel = new JPanel();
        currentHandPanel.setLayout(new BoxLayout(currentHandPanel, BoxLayout.X_AXIS));
        currentHandPanel.setBackground(Color.blue);
        currentHandPanel.setBounds(25, 100, 700, 200);
        refreshCurrentHand(currentHand, false);

        //Actions
        fightPanel = new JPanel();
        fightPanel.setBackground(Color.blue);
        fightPanel.setBounds(25, 325, 650, 50);
        fightButton = new JButton("No weapon equipped");
        fightButton.setEnabled(false);
        fightButton.setFont(normalFont);
        fightButton.addActionListener(e -> controller.equipWeapon());
        sheatheButton = new JButton();
        sheatheButton.setFont(normalFont);
        sheatheButton.addActionListener(e -> controller.sheatheWeapon());

        fightPanel.add(fightButton);
        fightPanel.setVisible(true);
        gameWindow.add(fightPanel);

        //Player
        playerPanel = new JPanel();
        playerPanel.setBackground(Color.blue);
        playerPanel.setBounds(25, 400, 500, 50);
        playerLabel = new JLabel("Health: " + player.getHealth());
        playerLabel.setForeground(Color.white);
        playerLabel.setFont(normalFont);

        playerPanel.add(playerLabel);
        playerPanel.setVisible(true);
        gameWindow.add(playerPanel);

        gameWindow.revalidate();
        gameWindow.repaint();

    }

    public void refreshCurrentHand(List<Card> newCurrentHand, boolean isAttacking) {
        currentHandPanel.removeAll();
        for (Card card : newCurrentHand) {
            if (isAttacking) {
                if (card.type.equals("Potion") || card.type.equals("Weapon")) {
                    card.cardButton.setEnabled(false);
                }
                if (card.value > player.getLastKill()) {
                    card.cardButton.setEnabled(false);
                }
            } else {
                card.cardButton.setEnabled(true);
            }
            currentHandPanel.add(card.cardButton);
            currentHandPanel.add(Box.createHorizontalGlue());
        }
        currentHandPanel.setVisible(true);
        currentHandPanel.revalidate();
        currentHandPanel.repaint();
        gameWindow.add(currentHandPanel);
    }

    public void updateWeapon() {
//        System.out.println("gui sees lastkill as " + player.getLastKill());
        //fightButton.setEnabled(true);

        if (player.getLastKill() == 101) {
            fightButton.setText("Click to EQUIP weapon " + player.getWeapon() + " (Last kill: None)");
            sheatheButton.setText("Click to SHEATHE weapon " + player.getWeapon() + " (Last kill: None)");
        } else {
            fightButton.setText("Click to EQUIP weapon " + player.getWeapon() + " (Last kill: " + player.getLastKill() + ")");
            sheatheButton.setText("Click to SHEATHE weapon " + player.getWeapon() + " (Last kill: " + player.getLastKill() + ")");
        }

        fightPanel.revalidate();
        fightPanel.repaint();
    }

    public void enableFight(){
        fightButton.setEnabled(true);
    }

    public void usingWeapon(){
        fightPanel.remove(fightButton);
        fightButton.setVisible(false);
        fightPanel.add(sheatheButton);
        sheatheButton.setVisible(true);

        fightPanel.revalidate();
        fightPanel.repaint();
        updateWeapon();
    }

    public void sheatheWeapon() {
        fightPanel.remove(sheatheButton);
        sheatheButton.setVisible(false);
        fightPanel.add(fightButton);
        fightButton.setVisible(true);

        fightPanel.revalidate();
        fightPanel.repaint();
        updateWeapon();
    }

    private void addToUI(JPanel panel, JComponent jcomp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(jcomp, gbc);
    }

    public void updateRemainingDeck(int size) {
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(size));
    }

    public void showRemainingDeck(List<Card> remainingDeck) {
        System.out.println("remaining cards menu");

        remainingCardsWindow = new JDialog(gameWindow, "Remaining Deck: " + String.valueOf(remainingDeck.size()), true);
        //remainingCardsWindow.setSize(800,400);
        remainingCardsWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Add some content to the dialog
//        remainingCardsWindow.add(new JLabel("Remaining cards: " + String.valueOf(remainingDeck.size())));
        JButton exitButton = new JButton("Back");
        remainingCardsWindow.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingCardsWindow.dispose();
            }
        });

        remainingCardsWindow.setLayout(new GridBagLayout());
        addToUI(remainingCardsPanel, exitButton, 0, 0);

        int pCounter = 0;
        int wCounter = 0;
        int mCounter = 0;
        for (Card card : remainingDeck) {
            if (card.type.equals("Potion")) {
                addToUI(remainingCardsPanel, new JLabel(card.type + " " + card.value), pCounter, 1);
                pCounter += 1;
            }
            if (card.type.equals("Weapon")) {
                addToUI(remainingCardsPanel, new JLabel(card.type + " " + card.value), wCounter, 2);
                wCounter += 1;
            }
            if (card.type.equals("Monster")) {
                addToUI(remainingCardsPanel, new JLabel(card.type + " " + card.value), mCounter, 3);
                mCounter += 1;
            }
        }

        remainingCardsWindow.add(remainingCardsPanel);

        remainingCardsWindow.pack();
        remainingCardsWindow.setLocationRelativeTo(null);
        remainingCardsPanel.setVisible(true);
        remainingCardsWindow.setVisible(true);

    }

    public void resetRun() {
        runButton.setEnabled(true);
//        runButton.setText(str);
    }

    public void refreshRun() {
        if (player.isSkipAvail()) {
            runButton.setEnabled(true);
        } else {
            runButton.setEnabled(false);
        }

    }

    public void refreshPlayer() {
       playerLabel.setText("Health: " + player.getHealth());
    }

    public void gameOver(String text){
//        JOptionPane gameOver = new JOptionPane();
//        gameOver.showMessageDialog(frame, text);

        int option = JOptionPane.showOptionDialog(null, text, "Game Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new Object[]{"Back to Menu", "Quit"}, "Back to Menu");

        // Handle the user's choice
        if (option == 0) {
            System.out.println("back to main menu");
            controller.runGame();
            // Option 0 is "Restart", call startGame() again
        } else if (option == 1) {
            // Option 1 is "Quit", exit the program
            System.exit(0);
        }
    }
}
