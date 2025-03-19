import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.List;

public class CardPanel extends JPanel {
    private Card card;
    private JLabel valueLabel;
    private JLabel typeLabel;
    private JButton PrimaryButton;
    private JButton SecondaryButton;
    private JPanel panel;

    CardPanel(Controller controller, Player player, Card card) {
        this.card = card;
        panel = new JPanel();
        typeLabel = new JLabel(card.type);
        valueLabel = new JLabel(String.valueOf(card.value));
        PrimaryButton = new JButton();
        SecondaryButton = new JButton();

        panel.add(typeLabel);
        panel.add(valueLabel);
        panel.add(PrimaryButton);
        panel.add(SecondaryButton);

        typeLabel.setVisible(true);
        valueLabel.setVisible(true);

        PrimaryButton.setVisible(true);
        SecondaryButton.setVisible(true);
        PrimaryButton.setEnabled(true);
        SecondaryButton.setEnabled(true);

        switch (card.type) {
            case "Potion" -> {
                PrimaryButton.setText("Heal");
                SecondaryButton.setVisible(false);
            }
            case "Weapon" -> {
                PrimaryButton.setText("Equip");
                SecondaryButton.setVisible(false);
            }
            case "Monster" -> {
                if (player.getWeapon() == 0) {
                    PrimaryButton.setText("No equipped weapon!");
                    PrimaryButton.setEnabled(false);
                } else if (player.getLastKill() > card.value) {
                    PrimaryButton.setText("Fight");
                } else {
                    PrimaryButton.setText("Stronger than last kill!");
                    PrimaryButton.setEnabled(false);
                }
                SecondaryButton.setText("Take damage");
                SecondaryButton.setVisible(true);
            }
        }

//        PrimaryButton.addActionListener(e -> controller.useCard(card));
//        SecondaryButton.addActionListener(e -> controller.useCard(card));

        panel.setSize(100, 400);
        panel.setVisible(true);
    }

}
