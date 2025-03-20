import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI {
    //CONSTRUCTOR
    private Controller controller;
    private JFrame gameWindow;

    //Main Menu
    private JPanel mainMenuPanel;
    private JPanel titleNamePanel;
    private JLabel titleNameLabel;
    private Font titleFont = new Font("Dialog", Font.PLAIN, 56);
    private JPanel startButtonPanel;
    private JButton startButton;
    private Font normalFont = new Font("Dialog", Font.PLAIN, 20);

    //NEW GAME
    private Deck deck;
    private Player player;
    private List<Card> currentHand;

    private JPanel gamePanel;
    private GridBagConstraints gbc;
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

    public GUI(Controller controller) {
        this.controller = controller;
        gameWindow = new JFrame("GAME TITLE");
        gameWindow.setLayout(new BorderLayout());

        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(800, 600);
        gameWindow.getContentPane().setBackground(Color.black);
        gameWindow.setResizable(false);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        gameWindow.setLayout(null);
    }

    public void mainMenu(){
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        //gbc.weighty = 1;

        mainMenuPanel.setSize(new Dimension(800, 600));
        mainMenuPanel.setVisible(true);
        mainMenuPanel.setBackground(Color.black);

        titleNamePanel = new JPanel();
        //titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setOpaque(true);
        titleNamePanel.setBackground(Color.black);

        titleNameLabel = new JLabel("Dungeon Card Game");
        titleNameLabel.setBackground(Color.black);
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        titleNamePanel.add(titleNameLabel);
        //mainMenuPanel.add(titleNamePanel);

        startButtonPanel = new JPanel();
        startButtonPanel.setLayout(new GridBagLayout());
        //startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.black);

        JButton easyButton = new JButton("Easy");
        easyButton.setFont(normalFont);
        easyButton.setFocusPainted(false);
        easyButton.setBorderPainted(true);
        easyButton.setPreferredSize(new Dimension(100, 50));
        easyButton.setBorder(new LineBorder(Color.white, 3));
        easyButton.setBackground(Color.black);
        easyButton.setForeground(Color.white);
        easyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                easyButton.setBackground(Color.gray);
            }
            //
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                easyButton.setBackground(Color.black);
            }
        });
        easyButton.addActionListener(e -> controller.newGame(0));

        JButton mediumButton = new JButton("Medium");
        mediumButton.setFont(normalFont);
        mediumButton.setFocusPainted(false);
        mediumButton.setBorderPainted(true);
        mediumButton.setPreferredSize(new Dimension(100, 50));
        mediumButton.setBorder(new LineBorder(Color.white, 3));
        mediumButton.setBackground(Color.black);
        mediumButton.setForeground(Color.white);
        mediumButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                mediumButton.setBackground(Color.gray);
            }
//
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                mediumButton.setBackground(Color.black);
            }
        });
        mediumButton.addActionListener(e -> controller.newGame(1));

        JButton hardButton = new JButton("Difficult");
        hardButton.setFont(normalFont);
        hardButton.setFocusPainted(false);
        hardButton.setBorderPainted(true);
        hardButton.setPreferredSize(new Dimension(100, 50));
        hardButton.setBorder(new LineBorder(Color.white, 3));
        hardButton.setBackground(Color.black);
        hardButton.setForeground(Color.white);
        hardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                hardButton.setBackground(Color.gray);
            }
            //
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                hardButton.setBackground(Color.black);
            }
        });
        hardButton.addActionListener(e -> controller.newGame(2));

        addToParentPanel(startButtonPanel, gbc, 0, 0, easyButton, 1, 1);
        addToParentPanel(startButtonPanel, gbc, 0, 1, mediumButton, 1, 1);
        addToParentPanel(startButtonPanel, gbc, 0, 2, hardButton, 1, 1);

        addToParentPanel(mainMenuPanel, gbc, 0, 0, titleNamePanel, 3, 1);
        addToParentPanel(mainMenuPanel, gbc, 1, 1, startButtonPanel, 3, 1);

        gameWindow.add(mainMenuPanel);
        gameWindow.revalidate();
        gameWindow.repaint();
    }

    public void newGame(Deck deck, List<Card> currentHand, Player player) {
        this.deck = deck;
        this.player = player;
        this.currentHand = currentHand;

        mainMenuPanel.setVisible(false);

        gamePanel = new JPanel();//(new FlowLayout(FlowLayout.CENTER, 20, 20));
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setSize(new Dimension(800, 600));
        gamePanel.setVisible(true);
        gamePanel.setBackground(Color.black);
        gameWindow.add(gamePanel);

        //Remaining cards
        remainingCardsPanel = new JPanel();
        remainingCardsPanel.setBackground(Color.black);
        remainingCardsPanel.setForeground(Color.white);

        remainingCardsLabel = new JLabel();
        remainingCardsLabel.setForeground(Color.white);
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(deck.getCards().size()));
        remainingCardsLabel.setFont(normalFont);
        remainingCardsButton = new JButton();
        remainingCardsButton.setText("View deck");
        remainingCardsButton.setFont(normalFont);
        //remainingCardsButton.addActionListener(e -> controller.showSortedRemainingCards());

        remainingCardsPanel.add(remainingCardsLabel);
        //remainingCardsPanel.add(remainingCardsButton);
        remainingCardsPanel.setVisible(true);


        //Run
        runPanel = new JPanel();
        runPanel.setBackground(Color.black);
        runButton = new JButton("RUN");
        runButton.setFont(normalFont);
        runButton.setFocusPainted(false);
        runButton.setPreferredSize(new Dimension(75, 35));
        runButton.setBorder(new LineBorder(Color.white, 3));
        runButton.setBackground(Color.black);
        runButton.setForeground(Color.white);
        runButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                runButton.setBackground(Color.gray);
            }
            //
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                runButton.setBackground(Color.black);
            }
        });
        runButton.addActionListener(e -> controller.runAway());

        runPanel.add(runButton);
        runPanel.setVisible(true);

        //Current hand
        currentHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25,25));
        currentHandPanel.setLayout(new BoxLayout(currentHandPanel, BoxLayout.X_AXIS));
        currentHandPanel.setBackground(Color.black);

        //Actions
        fightPanel = new JPanel();
        fightPanel.setBackground(Color.black);
        fightButton = new JButton("No weapon equipped");
        fightButton.setEnabled(false);
        fightButton.setFocusPainted(false);
        fightButton.setFont(normalFont);
        fightButton.setPreferredSize(new Dimension(450, 35));
        fightButton.setBorder(new LineBorder(Color.white, 3));
        fightButton.setBackground(Color.black);
        fightButton.setForeground(Color.white);

        fightButton.addActionListener(e -> controller.equipWeapon());


        sheatheButton = new JButton();
        sheatheButton.setFont(normalFont);
        sheatheButton.setFocusPainted(false);
        sheatheButton.setPreferredSize(new Dimension(450, 35));
        sheatheButton.setBorder(new LineBorder(Color.white, 3));
        sheatheButton.setBackground(Color.black);
        sheatheButton.setForeground(Color.white);
        sheatheButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                sheatheButton.setBackground(Color.gray);
            }
            //
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                sheatheButton.setBackground(Color.black);
            }
        });
        sheatheButton.addActionListener(e -> controller.sheatheWeapon());

        fightPanel.add(fightButton);
        fightPanel.setVisible(true);


        //Player
        playerPanel = new JPanel();
        playerPanel.setBackground(Color.black);
        playerLabel = new JLabel("Health: " + player.getHealth());
        playerLabel.setForeground(Color.white);
        playerLabel.setFont(normalFont);

        playerPanel.add(playerLabel);
        playerPanel.setVisible(true);

        addToParentPanel(gamePanel, gbc, 0,0, remainingCardsPanel, 1, 1);
        addToParentPanel(gamePanel, gbc, 2,0, runPanel, 1, 1);
        refreshCurrentHand(currentHand, false);
        addToParentPanel(gamePanel, gbc, 0,2, fightPanel, 3, 1);
        addToParentPanel(gamePanel, gbc, 0,3, playerPanel, 3, 1);

//        gameWindow.revalidate();
//        gameWindow.repaint();

    }
    private void addToParentPanel(JPanel parentPanel, GridBagConstraints gbc, int x, int y, JComponent jcomp, int gridwidth, int gridheight) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        parentPanel.add(jcomp, gbc);
    }

    public void refreshCurrentHand(List<Card> newCurrentHand, boolean isAttacking) {
        currentHandPanel.removeAll();
        currentHandPanel.add(Box.createHorizontalGlue());
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
        }
        currentHandPanel.setVisible(true);
        currentHandPanel.revalidate();
        currentHandPanel.repaint();

        addToParentPanel(gamePanel, gbc, 0,1, currentHandPanel, 3, 1);

        //gamePanel.add(currentHandPanel);
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
        fightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Change color when mouse enters
                fightButton.setBackground(Color.gray);
            }
            //
            @Override
            public void mouseExited(MouseEvent e) {
                // Change color back when mouse exits
                fightButton.setBackground(Color.black);
            }
        });
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

    public void updateRemainingDeck(int size) {
        remainingCardsLabel.setText("Remaining cards: " + String.valueOf(size));
    }

    private void addToUI(JPanel panel, JComponent jcomp, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(jcomp, gbc);
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
            gamePanel.setVisible(false);
            mainMenuPanel.setVisible(true);

            // Option 0 is "Restart", call startGame() again
        } else if (option == 1) {
            // Option 1 is "Quit", exit the program
            System.exit(0);
        }
    }
}
