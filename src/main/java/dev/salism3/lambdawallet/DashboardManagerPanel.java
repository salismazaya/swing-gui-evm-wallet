package dev.salism3.lambdawallet;

import java.awt.Color;

import dev.salism3.lambdawallet.lib.ScreenManager;

public class DashboardManagerPanel extends ScreenManager {
    DashboardManagerPanel() {
        super();
        this.setBackground(Color.darkGray);

        this.addScreen(new SendScreen(), "SEND_SCREEN");
        this.addScreen(new SendScreen2(), "SEND_2_SCREEN");

    }
    
}
