import javax.swing.*;
import java.awt.*;

public class Card {
    private Controller controller;
    public String type;
    public int value;


    public JPanel cardPanel;
    public JButton cardButton;
    private Font valueFont = new Font("Times New Roman", Font.PLAIN, 20);

    Card(String type, int value, Controller controller) {
        this.type = type;
        this.value = value;
        this.controller = controller;
    }

    public void createCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setSize(100, 140);
        cardPanel.setLayout(new FlowLayout());
        cardPanel.setBackground(Color.red);
        //cardPanel.setBounds(25, 25, 100, 200);

        ImageIcon icon = new ImageIcon();
        if (type.equals("Potion")) {
            icon = new ImageIcon("C:\\Users\\jakes\\IdeaProjects\\Java\\imgs\\card\\potion.png");
        }
        if (type.equals("Weapon")) {
            icon = new ImageIcon("C:\\Users\\jakes\\IdeaProjects\\Java\\imgs\\card\\sword.png");
        }
        if (type.equals("Monster")) {
            icon = new ImageIcon("C:\\Users\\jakes\\IdeaProjects\\Java\\imgs\\card\\monster.png");
        }

        Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledIcon);

        cardButton = new JButton(String.valueOf(value), resizedIcon);
        cardButton.setFont(valueFont);
        cardButton.setPreferredSize(new Dimension(100, 140));
        cardButton.setFocusPainted(false);

//        if (type.equals("Potion")) {
//            cardButton.setBackground(Color.GREEN);
//            cardButton.setForeground(Color.black);
//        }
//        if (type.equals("Weapon")) {
//            cardButton.setBackground(Color.YELLOW);
//            cardButton.setForeground(Color.black);
//        }
//        if (type.equals("Monster")) {
//            cardButton.setBackground(Color.red);
//            cardButton.setForeground(Color.black);
//        }
        cardButton.setBackground(Color.white);
        cardButton.setForeground(Color.black);


        cardButton.setHorizontalTextPosition(SwingConstants.CENTER);
        cardButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        cardButton.setIconTextGap(30);

        cardPanel.add(cardButton);
        cardPanel.setVisible(true);

        cardButton.addActionListener(e -> controller.useCard(this));
    }

//    public JPanel makeCardPanel() {
//
//    }


}
