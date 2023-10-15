package dev.salism3.lambdawallet;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dev.salism3.lambdawallet.lib.SQLiteConnection;
import dev.salism3.lambdawallet.lib.ScreenManager;

public class ManagerPanel extends ScreenManager {
    ManagerPanel() {
        super();
        this.setBackground(Color.darkGray);

        Connection conn = SQLiteConnection.connect();
        PreparedStatement pstmt;
        try {
            
            pstmt = conn.prepareStatement("SELECT * FROM data LIMIT 1");
            ResultSet rs = pstmt.executeQuery();
    
            if (!rs.next()) {
                this.addScreen(new CreateLoadScreen(), "CREATE_LOAD_SCREEN");
                this.addScreen(new CreateScreen(), "CREATE_SCREEN");
                this.addScreen(new LoadScreen(), "LOAD_SCREEN");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.addScreen(new RequestPINScreen(), "REQUEST_PIN_SCREEN");
        this.addScreen(new Dashboard(), "DASHBOARD_SCREEN");
    }
}
