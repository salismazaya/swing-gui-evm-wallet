package dev.salism3.lambdawallet;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import dev.salism3.lambdawallet.lib.Memory;
import dev.salism3.lambdawallet.lib.Screen;

public class SendScreen2 extends Screen {
    JPanel mainPanel;
    JTextField amountField;
    JButton nextButton;

    public SendScreen2() {
        mainPanel = new BasePanel();
        mainPanel.setLayout(new GridLayout(5,1));

        JPanel labelPanel = new BasePanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel label = new JLabel("AMOUN TO SEND");
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        label.setForeground(Color.white);

        labelPanel.add(label);

        mainPanel.add(labelPanel);

        this.add(mainPanel);

        JPanel panel = new BasePanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        amountField = new JTextField();
        amountField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        amountField.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        nextButton = new JButton("NEXT");
        nextButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        nextButton.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        panel.add(amountField);
        panel.add(nextButton);
        mainPanel.add(panel);

        mainPanel.add(new BlankPanel());
        mainPanel.add(new BlankPanel());
        mainPanel.add(new BlankPanel());

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nextButton.setVisible(false);
                String address = (String)Memory.get("receipent");
                Web3j web3j = Web3j.build(new HttpService("https://bsc-testnet.publicnode.com"));
                Credentials credentials = (Credentials)Memory.get("CREDENTIALS");

                System.out.println(credentials.getAddress());

                double amountInEther = 0;

                try {
                    amountInEther = Double.parseDouble(amountField.getText());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Amount not valid");
                    return;
                }

                try {

                    BigInteger nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getTransactionCount();
                    BigInteger value = Convert.toWei(String.valueOf(amountInEther), Convert.Unit.ETHER).toBigInteger();
                    BigInteger maxPriority = web3j.ethMaxPriorityFeePerGas().send().getMaxPriorityFeePerGas();
                    BigInteger baseFeePerGas = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getResult().getBaseFeePerGas();
                    BigInteger maxFeePerGas = baseFeePerGas.multiply(BigInteger.valueOf(2)).add(maxPriority);

                    RawTransaction trx = RawTransaction.createEtherTransaction(97, nonce, BigInteger.valueOf(21000), address, value, maxPriority, maxFeePerGas);
                    byte[] signedTrx = TransactionEncoder.signMessage(trx, 97, credentials);

                    String hexValue = Numeric.toHexString(signedTrx);

                    String hash = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();

                    JOptionPane.showMessageDialog(null, hash);


                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Send failed :(");
                }

                nextButton.setVisible(true);
            }
        });
    }
}
