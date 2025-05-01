package dragonkk.rs2rsps.util;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.model.World;

import java.sql.*;

//import dragonkk.rs2rsps.model.player.Player;

public class MYSQL {

    /**
     * Define MySQL connection info.
     */

    //left my information here so it's easy for new people to understand the mysql stuffs easier :3

    private static final String host = "www.spawnscape.org";   //website ip or domain
    private static final String db = "spawnsca_voting";        //website voting databse
    private static final String user = "spawnsca_vote";         //website voting user
    private static final String pass = "voting";                //website voting user's password
    private static final String port = "3306";                   //keep this at 3306 (mysql port)

    //if your server doesn't connect to the webhost and you know it is right your webhost might not allow mysql!

    //private Player player;

    /**
     * The database connection in use
     */
    private static Connection con;
    /**
     * A statement for running queries on
     */
    private static Statement statement;
    /**
     * The last query being executed
     */
    private String lastQuery;

    static {
        testForDriver();
    }

    /**
     * Tests we have a mysql Driver
     */
    private static void testForDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found exception");
        }
    }

    /**
     * Instantiates a new database connection
     */
    public MYSQL() {
        if (!createConnection()) {
            System.out.println("Unable to connect to MySQL");
            Server.voteDisabled = true;
            System.out.println("Loading the server without MYSQL.");
        }
    }

    private boolean createConnection() {
        try {
            if (Server.voteDisabled) {
                System.out.println("Aborted conncetion.");
                return true;
            }
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
            statement = con.createStatement();
            statement.setEscapeProcessing(true);
            System.out.println("Connected to voting databse");
            return isConnected();
        } catch (SQLException e) {
            Server.voteDisabled = true;
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean isConnected() {
        try {
            statement.executeQuery("SELECT CURRENT_DATE");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Runs a select query on the current database connection
     * <p>
     * <p>
     * The query to be ran
     */
    public ResultSet getQuery(String q) throws SQLException {
        try {
            lastQuery = q;
            return statement.executeQuery(q);
        } catch (SQLException e) {
            if (!isConnected() && createConnection()) return getQuery(q);
            throw new SQLException(e.getMessage() + ": '" + lastQuery + "'", e
                    .getSQLState(), e.getErrorCode());
        }
    }

    /**
     * Runs a update/insert/replace query on the current database connection
     * <p>
     * <p>
     * The query to be ran
     *
     * @param q
     * @return
     * @throws java.sql.SQLException
     */
    public int updateQuery(String q) throws SQLException {
        try {
            lastQuery = q;
            return statement.executeUpdate(q);
        } catch (SQLException e) {
            if (!isConnected() && createConnection()) return updateQuery(q);
            throw new SQLException(e.getMessage() + ": '" + lastQuery + "'", e.getSQLState(), e.getErrorCode());
        }
    }

    public static ResultSet query(String s) throws SQLException {
        try {
            if (Server.voteDisabled) {
                System.out.println("Aborted conncetion.");
                return null;
            }
            if (s.toLowerCase().startsWith("select")) return statement.executeQuery(s);
            else statement.executeUpdate(s);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            con = null;
            statement = null;
        }
        return null;
    }

    public static void updatePlayers() {
        try {
            if (Server.voteDisabled) {
                System.out.println("Aborted conncetion.");
                return;
            }
            String query = "DELETE FROM online";
            String query2 = "INSERT INTO online (total) VALUES ('" + getPlayersOnline() + "')";
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getPlayersOnline() {
        return World.getPlayers().size();
    }

    /**
     * Closes the database conection.
     *
     * @throws SQLException if there was an error when closing the connection
     */
    public void close() throws SQLException {
        con.close();
        con = null;
    }

}