package dev.salism3.lambdawallet.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteConnection {
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getEncryptedPrivateKey() {
        Connection conn = SQLiteConnection.connect();
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM data LIMIT 1");
            ResultSet rs = pstmt.executeQuery();
    
            if (!rs.next()) {
                return "";
            }

            return rs.getString("encryptedPrivateKey");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }
}
