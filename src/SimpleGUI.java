import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI {

    public SimpleGUI() {
        JFrame frame = new JFrame("GAME TITLE");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField(15);

        JButton button = new JButton("Text on button");
        JButton button2 = new JButton("button 2");
        JLabel label = new JLabel("Label text");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                JOptionPane.showMessageDialog(frame, "input text: "+ name);
                //button.setText("Button text updated");
                //label.setText("Button label updated"); //changes text
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(button);

        frame.add(panel);

//        frame.setLayout(new java.awt.FlowLayout());
//        frame.add(button2);
//        frame.add(label);
//        frame.add(button);

        frame.setVisible(true);
    }

}
