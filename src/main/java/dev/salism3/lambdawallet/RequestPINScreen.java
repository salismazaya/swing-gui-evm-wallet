package dev.salism3.lambdawallet;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.web3j.crypto.Credentials;

import dev.salism3.lambdawallet.lib.AESUtil;
import dev.salism3.lambdawallet.lib.Memory;
import dev.salism3.lambdawallet.lib.SQLiteConnection;
import dev.salism3.lambdawallet.lib.Screen;

public class RequestPINScreen extends Screen {
    JPanel mainPanel;

    RequestPINScreen() {
        super();
        mainPanel = new BasePanel();
        mainPanel.setLayout(new GridLayout(3,1));

        mainPanel.add(new BlankPanel());
        JPanel panel = new BasePanel();
    
        JPasswordField passwordInput = new JPasswordField(20);
        passwordInput.setAlignmentX(JPasswordField.CENTER_ALIGNMENT);
        panel.add(passwordInput);
    
        JButton button = new JButton();
        button.setText("LOGIN");
        button.setAlignmentX(JPasswordField.CENTER_ALIGNMENT);
        
        panel.add(button);
    
        mainPanel.add(panel, 1);
        mainPanel.add(new BlankPanel());

        this.add(mainPanel);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String encryptedPrivateKey = SQLiteConnection.getEncryptedPrivateKey();
                try {
                    String privateKey = AESUtil.decrypt(encryptedPrivateKey, String.valueOf(passwordInput.getPassword()));
                    Memory.set("CREDENTIALS", Credentials.create(privateKey));
                    navigateTo("DASHBOARD_SCREEN");
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Wrong password");
                }
            }
        });
    }
}
