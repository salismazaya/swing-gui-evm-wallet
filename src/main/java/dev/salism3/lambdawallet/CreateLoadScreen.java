package dev.salism3.lambdawallet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dev.salism3.lambdawallet.lib.Screen;

public class CreateLoadScreen extends Screen {
    CreateLoadScreen() {
        this.setLayout(new GridLayout(3,1));
        this.setBackground(Color.darkGray);

        JPanel grid1Panel = new BasePanel();

        JLabel nameAppLabel = new JLabel("LAMBDA WALLET");
        nameAppLabel.setForeground(Color.red);
        nameAppLabel.setBackground(Color.blue);
        nameAppLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        nameAppLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        grid1Panel.add(nameAppLabel);
        
        JPanel grid2Panel = new BasePanel();
        grid2Panel.setLayout(new BoxLayout(grid2Panel, BoxLayout.Y_AXIS));

        JButton createButton = new JButton();
        createButton.setText("Create new wallet");
        createButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        createButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				navigateTo("CREATE_SCREEN");
			}
        });

        grid2Panel.add(createButton);

        JButton loadWallet = new JButton();
        loadWallet.setText("Using existing wallet");
        loadWallet.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        loadWallet.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        
        loadWallet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				navigateTo("LOAD_SCREEN");
            }
        });

        grid2Panel.add(loadWallet);

        this.add(grid1Panel);
        this.add(grid2Panel);
        this.add(new BlankPanel());
    }
}
