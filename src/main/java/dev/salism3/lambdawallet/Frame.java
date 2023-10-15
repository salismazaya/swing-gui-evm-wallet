package dev.salism3.lambdawallet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatLightLaf;

// import com.alee.laf.WebLookAndFeel;

public class Frame extends JFrame {
    private static Frame frame;

    public static Frame getFrame() {
        return frame;
    }

    public Frame() {
        super();
        Frame.frame = this;
        FlatLightLaf.setup();

        this.setTitle("LAMBDA WALLET");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setMinimumSize(new Dimension(600, 600));

        this.setLayout(new BorderLayout());
        
        JPanel keren1 = new JPanel();
        JPanel keren2 = new JPanel();
        JPanel keren3 = new JPanel();
        JPanel keren4 = new JPanel();

        keren1.setBackground(Color.darkGray);
        keren2.setBackground(Color.darkGray);
        keren2.setBackground(Color.darkGray);
        keren3.setBackground(Color.darkGray);
        keren4.setBackground(Color.darkGray);

        this.add(keren1, BorderLayout.EAST);
        this.add(keren2, BorderLayout.WEST);
        this.add(keren3, BorderLayout.NORTH);
        this.add(keren4, BorderLayout.SOUTH);

        JPanel manager = new ManagerPanel();

        this.add(manager, BorderLayout.CENTER);

    }
}
