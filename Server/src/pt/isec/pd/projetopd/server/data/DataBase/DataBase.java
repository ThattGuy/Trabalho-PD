package pt.isec.pd.projetopd.server.data.DataBase;

import pt.isec.pd.projetopd.communication.classes.Admin;
import pt.isec.pd.projetopd.communication.classes.RESPONSE;
import pt.isec.pd.projetopd.communication.classes.User;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    Connection con;
    public DataBase(String path) {
        String url = path;

        try {
            con = DriverManager.getConnection(url);
            createTables(con);
            System.out.println("Connection to the SQLite database established.");
            register("admin@isec.pt","123","Admin",0,123,"21312","awdfawdaw",true);
        } catch (SQLException e) {
            System.err.println("Error interacting with SQLite database:" + e.getMessage());
        }

    }

    public void createTables(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS User ( " +
                    "username TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "studentNumber INTEGER NOT NULL, " +
                    "nif INTEGER, " +
                    "id INTEGER PRIMARY KEY, "+
                    "address TEXT, " +
                    "admin BOOLEAN NOT NULL " +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS Event ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "Designacao TEXT NOT NULL, " +
                    "Local TEXT NOT NULL, " +
                    "Data DATE NOT NULL, " +
                    "HoraInicio TIME NOT NULL, " +
                    "HoraFim TIME NOT NULL, " +
                    "user_id INTEGER, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id) " +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS CodigoRegisto ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "codigo TEXT NOT NULL, " +
                    "event_id INTEGER, " +
                    "FOREIGN KEY (event_id) REFERENCES Event(id) " +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS UserEvent ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "user_id INTEGER, " +
                    "event_id INTEGER, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id), " +
                    "FOREIGN KEY (event_id) REFERENCES Event(id) " +
                    ");");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public boolean verifyAdmin(int userId) {
        String query = "SELECT admin FROM User WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Obtém o valor da coluna "admin" (true se for admin, false se não for)
                    boolean isAdmin = resultSet.getBoolean("admin");
                    return isAdmin;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking admin status: " + e.getMessage());
        }
        return false;
    }



    public Serializable CheckLogin(String user, String pass) {

        String query = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if(resultSet.getString("username") != null) //todo retornar declined caso password estaja errada

                if (resultSet.next()) {

                    // Check if the user is an admin
                    boolean isAdmin = resultSet.getBoolean("admin");

                    if (isAdmin) {
                        // User is an admin
                        return new Admin(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));
                    } else {
                        // User is not an admin
                        return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));
                    }

                    // User credentials are correct

                    //return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));

                } else {
                    // No matching username and password
                    return RESPONSE.DECLINED;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error verifying login: " + e.getMessage());
            return RESPONSE.DECLINED;
        }
        return RESPONSE.DECLINED;
    }



    public boolean register(String username, String psswd, String name, int studentnumber, int nif, String id, String address, boolean admin){
        String checkQuery = "SELECT COUNT(*) FROM User WHERE id = ?";
        int existingIDCount = 0;
        try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
            checkStatement.setString(1, id);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    existingIDCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error verifying ID:" + e.getMessage());
            return false;
        }
        if (existingIDCount > 0 && admin!=true) {
            System.err.println("ID already exists.");
            return false;
        }else{
            if(admin==true){
                return false;
            }
        }
        String query = "INSERT INTO User (username, password, name, studentNumber, nif, id, address, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, psswd);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, studentnumber);
            preparedStatement.setInt(5, nif);
            preparedStatement.setString(6, id);
            preparedStatement.setString(7, address);
            preparedStatement.setBoolean(8, admin);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public boolean editDataUser(String username, String name, int studentnumber, int nif, String id, String address) {
        String query = "UPDATE User SET username = ?, name = ?, studentNumber = ?, nif = ?, address = ?, admin = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, studentnumber);
            preparedStatement.setInt(4, nif);
            preparedStatement.setString(5, address);
            preparedStatement.setString(7, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error editing user data: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String id) {
        String query = "DELETE FROM User WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error removing user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEvent(int eventId) {
        String query = "DELETE FROM Event WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, eventId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
            return false;
        }
    }

    public boolean registerEvent(String designacao, String local, String data, String horaInicio, String horaFim, String userId) {
        String query = "INSERT INTO Event (Designacao, Local, Data, HoraInicio, HoraFim, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, designacao);
            preparedStatement.setString(2, local);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, horaInicio);
            preparedStatement.setString(5, horaFim);
            preparedStatement.setString(6, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting event: " + e.getMessage());
            return false;
        }
    }

    public boolean editEvent(int eventId, String designacao, String local, String data, String horaInicio, String horaFim, int userId) {
        String query = "UPDATE Event SET Designacao = ?, Local = ?, Data = ?, HoraInicio = ?, HoraFim = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, designacao);
            preparedStatement.setString(2, local);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, horaInicio);
            preparedStatement.setString(5, horaFim);
            preparedStatement.setInt(6, userId);
            preparedStatement.setInt(7, eventId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error editing event: " + e.getMessage());
            return false;
        }
    }

    public boolean addCodeRegister(String codigo, int eventId) {
        String query = "INSERT INTO CodigoRegisto (codigo, event_id) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, codigo);
            preparedStatement.setInt(2, eventId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding registration code:" + e.getMessage());
            return false;
        }
    }

    public boolean registerPresence(int code, String clientMail) {
        String query = "INSERT INTO UserEvent (user_id, event_id) VALUES ((SELECT id FROM User WHERE username = ?), ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, clientMail);
            preparedStatement.setInt(2, code);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registering presence: " + e.getMessage());
            return false;
        }
    }

    public Serializable getPresence() {
        return "presence";
    }

    //TODO: xico mudei aqui para string mas faz mais mudancas que sejam precisas! so mudei onde tava a dar erro (ja te mando o mail do user)
    public Serializable getCSV(String mail) {
        List<String> csvLines = new ArrayList<>();

        String query = "SELECT User.name AS Nome, User.studentNumber AS \"Número identificação\", UserEvent.user_id, Event.Designacao, Event.Local, Event.Data, Event.HoraInicio " +
                "FROM UserEvent " +
                "JOIN Event ON UserEvent.event_id = Event.id " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE UserEvent.user_id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, mail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                csvLines.add("\"Nome\";\"Número identificação\";\"Email\"");
                csvLines.add("\"" + resultSet.getString("Nome") + "\";\"" + resultSet.getInt("Número identificação") + "\";\"" + resultSet.getString("user_id") + "\"");

                csvLines.add("\"Designação\";\"Local\";\"Data\";\"Horainício\"");

                while (resultSet.next()) {
                    String designacao = resultSet.getString("Designacao");
                    String local = resultSet.getString("Local");
                    String data = resultSet.getString("Data");
                    String horaInicio = resultSet.getString("HoraInicio");

                    csvLines.add("\"" + designacao + "\";\"" + local + "\";\"" + data + "\";\"" + horaInicio + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence CSV: " + e.getMessage());
        }

        return String.join("\n", csvLines);
    }

    public Serializable getUserData() {

        return "user";
    }
}

