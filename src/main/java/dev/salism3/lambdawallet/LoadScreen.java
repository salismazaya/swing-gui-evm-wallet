package dev.salism3.lambdawallet;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dev.salism3.lambdawallet.lib.AESUtil;
import dev.salism3.lambdawallet.lib.Interactive;
import dev.salism3.lambdawallet.lib.SQLiteConnection;
import dev.salism3.lambdawallet.lib.Screen;
import sas.swing.MultiLineLabel;

import org.web3j.crypto.WalletUtils;

public class LoadScreen extends Screen {
    JPanel mainPanel;
    JTextField[] mnemonic = new JTextField[24];


    public LoadScreen() {
        mainPanel = new BasePanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
    }
    
    public void load() {
        MultiLineLabel dangerText = new MultiLineLabel("INPUT YOUR MNEMONIC");
        dangerText.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        dangerText.setForeground(Color.red);
        dangerText.setHorizontalTextAlignment(JLabel.CENTER);
        dangerText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainPanel.add(dangerText);

        JPanel gridPanel = new BasePanel();
        gridPanel.setLayout(new GridLayout(6,4));

        for (int i = 0; i < 24; i++) {
            JTextField input = new JTextField();
            input.setForeground(Color.black);
            input.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
            gridPanel.add(input);

            mnemonic[i] = input;
        }

        mainPanel.add(gridPanel);

        JButton nextButton = new JButton();
        nextButton.setText("LOAD MY WALLET");
        nextButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        nextButton.setAlignmentX(JButton.CENTER_ALIGNMENT);

        JPanel mantap1 = new BasePanel();
        mantap1.setLayout(new FlowLayout());

        JLabel keren1 = new JLabel("CREATE PASSWORD");
        keren1.setForeground(Color.red);
        mantap1.add(keren1);

        JPasswordField passwordInput = new JPasswordField();
        new Interactive("createPasswordInput", passwordInput);
        passwordInput.setColumns(30);
        passwordInput.setAlignmentX(JButton.CENTER_ALIGNMENT);
        mantap1.add(passwordInput);

        JPanel mantap2 = new BasePanel();
        mantap2.setLayout(new FlowLayout());

        JLabel keren2 = new JLabel("CONFIRM PASSWORD");
        keren2.setForeground(Color.red);
        mantap2.add(keren2);

        JPasswordField confirmPasswordInput = new JPasswordField();
        new Interactive("confirmPasswordInput", confirmPasswordInput);
        confirmPasswordInput.setColumns(30);
        confirmPasswordInput.setAlignmentX(JButton.CENTER_ALIGNMENT);

        mantap2.add(confirmPasswordInput);

        mainPanel.add(mantap1);
        mainPanel.add(mantap2);
        mainPanel.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] preMnemonicString = new String[24];

                int i = 0;
                for (JTextField input : mnemonic) {
                    if (input.getText().equals("")) {
                        JOptionPane.showMessageDialog(Frame.getFrame(), "Mnemonic not complete");
                        return;
                    }
                    
                    preMnemonicString[i] = input.getText();
                    i++;
                }

                JPasswordField createPasswordInput = (JPasswordField)Interactive.getComponent("createPasswordInput");
                JPasswordField confirmPasswordInput = (JPasswordField)Interactive.getComponent("confirmPasswordInput");

                if (createPasswordInput.getPassword().length < 1 || confirmPasswordInput.getPassword().length < 1) {
                    JOptionPane.showMessageDialog(Frame.getFrame(), "Invalid input");
                    return;
                }

                if (!String.valueOf(createPasswordInput.getPassword()).equals(String.valueOf(confirmPasswordInput.getPassword()))) {
                    JOptionPane.showMessageDialog(Frame.getFrame(), "Password and confirm password not same");
                    return;
                }

                String password = String.valueOf(createPasswordInput.getPassword());
                String mnemonicString = String.join(" ", preMnemonicString);
                String privateKey = WalletUtils.loadBip39Credentials("salisganteng", mnemonicString).getEcKeyPair().getPrivateKey().toString(16);
                try {
                    String encryptedPrivateKey = AESUtil.encrypt(privateKey, password);
                    Connection conn = SQLiteConnection.connect();
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO data (encryptedPrivateKey) VALUES (?)");
                    pstmt.setString(1, encryptedPrivateKey);
                    pstmt.executeUpdate();
                    navigateTo("REQUEST_PIN_SCREEN");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
