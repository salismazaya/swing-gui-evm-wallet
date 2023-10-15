package dev.salism3.lambdawallet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import dev.salism3.lambdawallet.lib.Memory;
import dev.salism3.lambdawallet.lib.Screen;

public class Dashboard extends Screen {
    JPanel mainPanel;
    Credentials credentials;
    Web3j web3j;
    JLabel balanceLabel;
    
    Dashboard() {
        web3j = Web3j.build(new HttpService("https://bsc-testnet.publicnode.com"));
        mainPanel = new BasePanel();
        mainPanel.setLayout(new GridLayout(3,1));

        mainPanel.add(new BlankPanel());

        this.add(mainPanel);
    }
    
    public void load() {
        credentials = (Credentials)Memory.get("CREDENTIALS");
        JPanel panel = new BasePanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel2 = new BasePanel();
        panel2.setLayout(new GridBagLayout());


        Font font = new Font(Font.MONOSPACED, Font.BOLD, 20);

        JTextField addressLabel = new JTextField();
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.VERTICAL;
        gbc1.gridx = 0;
        gbc1.gridy = 0;

        addressLabel.setText(credentials.getAddress());
        addressLabel.setEditable(false);
        addressLabel.setFont(font);
        panel2.add(addressLabel, gbc1);
        
        EthGetBalance ethGetBalance;
        try {
            ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger wei = ethGetBalance.getBalance();
            BigDecimal ether = Convert.fromWei(wei.toString(), Unit.ETHER);

            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.fill = GridBagConstraints.VERTICAL;
            gbc2.gridx = 0;
            gbc2.gridy = 1;

            balanceLabel = new JLabel();
            balanceLabel.setText(ether.toString() + " BNB");
            balanceLabel.setFont(font);
            balanceLabel.setForeground(Color.white);

            panel2.add(balanceLabel, gbc2);

            panel.add(panel2);
            mainPanel.add(panel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mainPanel.add(new BlankPanel());

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridx = 0;
        gbc3.gridy = 2;

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setFont(font);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
                    BigInteger wei = ethGetBalance.getBalance();
                    BigDecimal ether = Convert.fromWei(wei.toString(), Unit.ETHER);

                    balanceLabel.setText(ether.toString() + " BNB");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
        });

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.VERTICAL;
        gbc4.gridx = 0;
        gbc4.gridy = 3;

        JButton sendButton = new JButton("SEND");
        sendButton.setFont(font);
        
        panel2.add(refreshButton, gbc3);
        panel2.add(sendButton, gbc4);
    }
}
