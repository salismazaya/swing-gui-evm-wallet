package dev.salism3.lambdawallet;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.salism3.lambdawallet.lib.Memory;
import dev.salism3.lambdawallet.lib.Screen;
import dev.salism3.lambdawallet.lib.Utils;

public class SendScreen extends Screen {
    JPanel mainPanel;
    JTextField addressField;

    public SendScreen() {
        mainPanel = new BasePanel();
        mainPanel.setLayout(new GridLayout(5,1));

        JPanel labelPanel = new BasePanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("INPUT RECEIPENT ADDRESS");
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        label.setForeground(Color.white);

        labelPanel.add(label);

        mainPanel.add(labelPanel);

        this.add(mainPanel);

        JPanel panel = new BasePanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addressField = new JTextField();
        addressField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        addressField.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        JButton nextButton = new JButton("NEXT");
        nextButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        nextButton.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        panel.add(addressField);
        panel.add(nextButton);
        mainPanel.add(panel);

        mainPanel.add(new BlankPanel());
        mainPanel.add(new BlankPanel());
        mainPanel.add(new BlankPanel());

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String address = addressField.getText();

                boolean isValidAddress = Utils.isValidEVMAddress(address);

                if (!isValidAddress) {
                    JOptionPane.showMessageDialog(null, "not a valid evm address");
                    return;
                }
                
                Memory.set("receipent", address);
                navigateTo("SEND_2_SCREEN");
            }
        });
    }
}
