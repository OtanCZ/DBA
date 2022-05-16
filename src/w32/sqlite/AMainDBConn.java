package w32.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Download correct jar file from https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/ and add it to lib
 * or add maven artefact
 * File -> Project Structure -> libraries ->add X:\stemberk\verejne_zaci\javafx-sdk-17.0.1\lib
 * */
public class AMainDBConn {
    /**
     * Connect to a sample database
     */
    public static Connection connect() {

        Connection conn = null;
        try {
            // db parameters
            // absolute path
            //String url = "jdbc:sqlite:C:/w32.sqlite/db/chinook.db";
            //relative path
            String url = "jdbc:sqlite:moje.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

//            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } /*finally {
             */
        return conn;
    }
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection c = AMainDBConn.connect();
        AMainDBConn.closeConnection(c);
    }
}
