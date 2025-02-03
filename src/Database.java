import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database implements AutoCloseable {
    private String url, password, username;

    private Connection connection;

    private Statement createStmt;

    private PreparedStatement addAutoStmt;
    private PreparedStatement addAutohausStmt;
    private PreparedStatement removeAutohausStmt;
    private PreparedStatement removeAutoStmt;
    private PreparedStatement updateAutoStmt;
    private PreparedStatement updateAutohausStmt;
    private PreparedStatement transferAutoStmt;
    private PreparedStatement removeAutoFromAutohausStmt;


    public Database() throws SQLException, IOException {
        //create connection
        createConnection();

        createStmt = connection.createStatement();

        try {
            prepareStatements();
        } catch (SQLException e) {
            setupDatabase();
        }
    }

    public int createAutohaus(Autohaus autohaus) throws SQLException {
        addAutohausStmt.setString(1, autohaus.getName());

        return addAutohausStmt.executeUpdate();
    }

    public void removeAutohaus(int id) throws SQLException {
        try {
            connection.setAutoCommit(false);

            removeAutoFromAutohausStmt.setInt(1, id);
            if (removeAutoFromAutohausStmt.executeUpdate() == 0) {
                throw new SQLException("Autos nicht gefunden!");
            }

            removeAutohausStmt.setInt(1, id);
            if (removeAutohausStmt.executeUpdate() == 0) {
                throw new SQLException("Autohaus nicht gefunden!");
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public int updateAutohaus(Autohaus autohaus) throws SQLException {
        updateAutohausStmt.setString(1, autohaus.getName());
        updateAutohausStmt.setInt(2, autohaus.getId());

        return updateAutohausStmt.executeUpdate();
    }

    public int createAuto(Auto auto) throws SQLException {
        addAutoStmt.setString(1, auto.getMarke());
        addAutoStmt.setDouble(2, auto.getPreis());

        return addAutoStmt.executeUpdate();
    }

    public int removeAuto(int id) throws SQLException {
        removeAutoStmt.setInt(1, id);

        return removeAutoStmt.executeUpdate();
    }

    public int updateAuto(Auto auto) throws SQLException {
        updateAutoStmt.setString(1, auto.getMarke());
        updateAutoStmt.setDouble(2, auto.getPreis());
        updateAutoStmt.setInt(3, auto.getId());

        return updateAutoStmt.executeUpdate();
    }

    public int transferAuto(int autoId, int autohausId) throws SQLException {
        transferAutoStmt.setInt(1, autohausId);
        transferAutoStmt.setInt(2, autoId);

        return transferAutoStmt.executeUpdate();
    }

    public void prepareStatements() throws SQLException {
        addAutohausStmt = connection.prepareStatement("INSERT INTO Autohaus (name) VALUES(?)");
        addAutoStmt = connection.prepareStatement("INSERT INTO Auto(marke, preis) VALUES(?, ?)");
        removeAutoStmt = connection.prepareStatement("DELETE FROM Auto WHERE auto_id = ?");
        removeAutohausStmt = connection.prepareStatement("DELETE FROM Autohaus WHERE autohaus_id = ?");
        updateAutoStmt = connection.prepareStatement("UPDATE Auto SET marke = ?, preis = ? WHERE auto_id = ?");
        updateAutohausStmt = connection.prepareStatement("UPDATE Autohaus SET name = ? WHERE autohaus_id = ?");
        transferAutoStmt = connection.prepareStatement("UPDATE Auto SET autohaus_id = ? WHERE auto_id = ?");
        removeAutoFromAutohausStmt = connection.prepareStatement("UPDATE Auto Set autohaus_id = null WHERE autohaus_id = ?");
    }

    public void setupDatabase() throws SQLException {
        createStmt.execute("DROP TABLE IF EXISTS Auto");
        createStmt.execute("DROP TABLE IF EXISTS Autohaus");

        createStmt.execute("create table Autohaus\n" +
                "(\n" +
                "    autohaus_id INTEGER identity\n" +
                "        constraint Autohaus_pk\n" +
                "            primary key,\n" +
                "    name        varchar(255) not null\n" +
                ")\n" +
                "\n");

        createStmt.execute("create table Auto\n" +
                "(\n" +
                "    auto_id     INTEGER identity\n" +
                "        constraint Auto_pk\n" +
                "            primary key,\n" +
                "    marke       varchar(255) not null,\n" +
                "    preis       INTEGER      not null,\n" +
                "    autohaus_id INTEGER\n" +
                "        constraint Auto_Autohaus_autohaus_id_fk\n" +
                "            references Autohaus (autohaus_id)\n" +
                ")");

        //statements preparen
        prepareStatements();
    }

    public void createConnection() throws IOException, SQLException {
        readCredentials();

        connection = DriverManager.getConnection(url, username, password);
    }

    public void readCredentials() throws IOException {
        try (FileReader fileReader = new FileReader("dbconnect.properties")) {
            Properties props = new Properties();
            props.load(fileReader);

            url = props.getProperty("url");
            password = props.getProperty("password");
            username = props.getProperty("username");
        }
    }

    @Override
    public void close() throws Exception {
        addAutohausStmt.close();
        addAutoStmt.close();
        removeAutoStmt.close();
        removeAutohausStmt.close();
        updateAutoStmt.close();
        updateAutohausStmt.close();
        transferAutoStmt.close();
        removeAutoFromAutohausStmt.close();

        connection.close();
    }


}
