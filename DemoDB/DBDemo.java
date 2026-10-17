
package DemoDB;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDemo {
    public static void main(String[] args)
            throws ClassNotFoundException, SQLException {

        // 1. Load driver
        String driverName = "net.ucanaccess.jdbc.UcanaccessDriver";
        Class.forName(driverName);

        // 2. Connect
        String path = "D:\\CC.accdb";
        String url = "jdbc:ucanaccess://" + path;
        Connection conn = DriverManager.getConnection(url);

        // 3. Statement
        Statement stat = conn.createStatement();
        String sql = "select * from book";
        ResultSet rs = stat.executeQuery(sql);

        // 4. ResultSet
        while (rs.next()) {
            System.out.println(
                rs.getInt("bid") + "\t"
                + rs.getString("name") + "\t"
                + rs.getDouble("price")
            );
        }

        // 5. Close
        rs.close();
        stat.close();
        conn.close();
    }
}
