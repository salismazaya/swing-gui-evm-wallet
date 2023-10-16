package dev.salism3.lambdawallet;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    Frame frame;

    void show() {
        frame.setVisible(true);
    }

    Main() {
        FlatLightLaf.setup();
        frame = new Frame();
    }

}
