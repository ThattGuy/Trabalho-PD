package pt.isec.pd.projetopd.server.data.DataBase;

import pt.isec.pd.projetopd.communication.classes.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
                    "nome TEXT PRIMARY KEY NOT NULL, " +
                    "Local TEXT NOT NULL, " +
                    "Data DATE NOT NULL, " +
                    "HoraInicio TIME NOT NULL, " +
                    "HoraFim TIME NOT NULL, " +
                    "user_id INTEGER, " +
                    "codigo UUID, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id) " +
                    ")");

            statement.execute("CREATE TABLE IF NOT EXISTS CodigoRegisto ( " +
                    "codigo UUID PRIMARY KEY, " +
                    "event_nome TEXT NOT NULL, " +
                    "hora_termino DATETIME NOT NULL, " +
                    "FOREIGN KEY (event_nome) REFERENCES Event(nome) " +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS UserEvent ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "user_id INTEGER, " +
                    "event_nome INTEGER, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id), " +
                    "FOREIGN KEY (event_nome) REFERENCES Event(nome) " +
                    ");");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> getDatabaseCopy() throws SQLException {

        List<Map<String, Object>> databaseCopy = new ArrayList<>();

        try  {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                databaseCopy.addAll(getDataFromTable(con, tableName));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting database copy: ");
            // Handle exceptions appropriately based on your application's needs
        }

        return databaseCopy;
    }

    private List<Map<String, Object>> getDataFromTable(Connection connection, String tableName) throws SQLException {
        List<Map<String, Object>> tableData = new ArrayList<>();

        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> record = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    record.put(columnName, value);
                }

                tableData.add(record);
            }
        }

        return tableData;
    }



    public Serializable verifyAdmin(int userId) {
        String query = "SELECT admin FROM User WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boolean isAdmin = resultSet.getBoolean("admin");
                    return isAdmin;
                }
            }
        } catch (SQLException e) {
            return "Error checking admin status: " + e.getMessage();
        }
        return false;
    }



    public Serializable CheckLogin(String user, String pass) {

        String query = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if(resultSet.getString("username") != null)

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (pass.equals(storedPassword)) {
                        // Check if the user is an admin
                        boolean isAdmin = resultSet.getBoolean("admin");

                        if (isAdmin) {
                            return new Admin(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));
                        } else {
                            return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));
                        }

                        // User credentials are correct

                        //return new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("studentNumber"), resultSet.getInt("nif"), resultSet.getString("id"), resultSet.getString("address"));
                    } else {
                        // Wrong password
                        return "Palavra passe incorreta";
                    }
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



    public Serializable register(String username, String psswd, String name, int studentnumber, int nif, String id, String address, boolean admin){
        String checkIDQuery = "SELECT COUNT(*) FROM User WHERE id = ?";
        String checkUsernameQuery = "SELECT COUNT(*) FROM User WHERE username = ?";
        int existingIDCount = 0;
        int existingUsernameCount = 0;
        try (PreparedStatement checkIDStatement = con.prepareStatement(checkIDQuery);
             PreparedStatement checkUsernameStatement = con.prepareStatement(checkUsernameQuery)) {
            checkIDStatement.setString(1, id);
            try (ResultSet idResultSet = checkIDStatement.executeQuery()) {
                if (idResultSet.next()) {
                    existingIDCount = idResultSet.getInt(1);
                }
            }

            checkUsernameStatement.setString(1, username);
            try (ResultSet usernameResultSet = checkUsernameStatement.executeQuery()) {
                if (usernameResultSet.next()) {
                    existingUsernameCount = usernameResultSet.getInt(1);
                }
            }

        } catch (SQLException e) {
            return "Error verifying ID or email: " + e.getMessage();

        }

        if (!username.endsWith("@isec.pt")) {
            return "Invalid email format. The email must end with @isec.pt.";
        }

        if (existingIDCount > 0 && admin != true) {
            return "ID already exists.";
        } else if (existingUsernameCount > 0) {
            return "Email already exists.";
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
            if (rowsAffected > 0) {
                return new User(username, psswd, name, studentnumber, nif, id, address);
            } else {
                return "Error registering user: No rows affected.";
            }
        } catch (SQLException e) {
            return "Error registering user: " + e.getMessage();
        }
    }

    public Serializable editDataUser(String username, String password , String name, int studentnumber, int nif, String id, String address) {
        String query = "UPDATE User SET username = ?, password = ?, name = ?, studentNumber = ?, nif = ?, address = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, studentnumber);
            preparedStatement.setInt(5, nif);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new User(username, password, name, studentnumber, nif, id, address);
            } else {
                return "No rows affected. User not found or data unchanged.";
            }
        } catch (SQLException e) {
            return "Error editing user data: " + e.getMessage();
        }
    }

    public Serializable deleteUser(String id) {
        String query = "DELETE FROM User WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
               return "User deleted successfully.";
            }
        } catch (SQLException e) {
            return "Error removing user: " + e.getMessage();
        }
        return false;
    }
    public Serializable deleteEvent(String nome) {
        String query = "DELETE FROM Event WHERE nome = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nome);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Event deleted successfully.";
            }
        } catch (SQLException e) {
           return "Error deleting event: " + e.getMessage();
        }
        return false;
    }

    public Serializable registerEvent(String nome, String local, String data, String horaInicio, String horaFim, String userId) {
        String checkQuery = "SELECT COUNT(*) FROM Event WHERE nome = ?";
        int existingEventCount = 0;

        try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
            checkStatement.setString(1, nome);

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    existingEventCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            return "Error verifying existing event: " + e.getMessage();
        }

        if (existingEventCount > 0) {
            return "Event with the same name already exists.";
        }

        String query = "INSERT INTO Event (nome, Local, Data, HoraInicio, HoraFim, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, local);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, horaInicio);
            preparedStatement.setString(5, horaFim);
            preparedStatement.setString(6, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return new Event(nome, local, data, horaInicio, horaFim);
            }
        } catch (SQLException e) {
            return "Error inserting event: " + e.getMessage();
        }

        return "Failed to insert event.";
    }

    public Serializable editEvent(String nome, String local, String data, String horaInicio, String horaFim, int userId) {
        String query = "UPDATE Event SET nome = ?, Local = ?, Data = ?, HoraInicio = ?, HoraFim = ?, user_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, local);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, horaInicio);
            preparedStatement.setString(5, horaFim);
            preparedStatement.setInt(6, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retorna o objeto Event após a edição
                return new Event(nome, local, data, horaInicio, horaFim);
            } else {
                return "No rows affected. Event not found or no changes made.";
            }
        } catch (SQLException e) {
            return "Error editing event: " + e.getMessage();
        }
    }
    public Serializable addCodeRegister(String codigo, String eventName) {
        String query = "INSERT INTO CodigoRegisto (codigo, event_nome) VALUES (?, (SELECT id FROM Event WHERE nome = ?))";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, codigo);
            preparedStatement.setString(2, eventName);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Retorna o objeto Event após a edição
                return true;
            }
        } catch (SQLException e) {
            return "Error adding registration code:" + e.getMessage();
        }
        return false;
    }
    public Serializable registerPresence(UUID code, String clientMail) {
        String checkQuery = "SELECT COUNT(*) FROM CodigoRegisto " +
                "WHERE codigo = ? AND hora_termino > datetime('now')";
        int validCodeCount = 0;

        try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
            checkStatement.setString(1, code.toString());

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    validCodeCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            return "Error checking valid code: " + e.getMessage();
        }

        if (validCodeCount == 0) {
            return "Invalid or expired registration code.";
        }

        String insertQuery = "INSERT INTO UserEvent (user_id, event_nome) VALUES ((SELECT id FROM User WHERE username = ?), " +
                "(SELECT event_nome FROM CodigoRegisto WHERE codigo = ?))";

        try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, clientMail);
            preparedStatement.setString(2, code.toString());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            return "Error registering presence: " + e.getMessage();
        }
    }
    public Serializable getPresence(String eventName) {
        List<String> presenceList = new ArrayList<>();

        String query = "SELECT User.username, User.name, User.studentNumber " +
                "FROM UserEvent " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "JOIN Event ON UserEvent.event_nome = Event.nome " +
                "WHERE Event.nome = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, eventName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                presenceList.add("\"Nome\";\"Número identificação\"");

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int studentNumber = resultSet.getInt("studentNumber");

                    presenceList.add("\"" + name + "\";\"" + studentNumber + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence: " + e.getMessage());
        }

        return String.join("\n", presenceList);
    }


    public List<String> getPresenceWithinTimeRange(String mail, String startTime, String endTime) {
        List<String> presenceList = new ArrayList<>();

        String query = "SELECT User.name AS Nome, User.studentNumber AS \"Número identificação\", Event.nome, Event.Local, Event.Data, Event.HoraInicio, Event.HoraFim " +
                "FROM UserEvent " +
                "JOIN Event ON UserEvent.event_nome = Event.nome " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE UserEvent.user_id = ? AND Event.HoraInicio >= ? AND Event.HoraFim <= ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, startTime);
            preparedStatement.setString(3, endTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                presenceList.add("\"Nome\";\"Número identificação\"");

                while (resultSet.next()) {
                    String nome = resultSet.getString("Nome");
                    String local = resultSet.getString("Local");
                    String data = resultSet.getString("Data");
                    String horaInicio = resultSet.getString("HoraInicio");
                    String horaFim = resultSet.getString("HoraFim");

                    presenceList.add("\"" + nome + "\";\"" + local + "\";\"" + data + "\";\"" + horaInicio + "\";\"" + horaFim + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence within time range: " + e.getMessage());
        }

        return presenceList;
    }

    public void generateCSV(String mail, String filePath) {
        List<String> csvLines = new ArrayList<>();

        String query = "SELECT User.name AS Nome, User.studentNumber AS \"Número identificação\", Event.nome, Event.Local, Event.Data, Event.HoraInicio " +
                "FROM UserEvent " +
                "JOIN Event ON UserEvent.event_nome = Event.nome " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE UserEvent.user_id = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, mail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                csvLines.add("\"Nome\";\"Número identificação\"");
                csvLines.add("\"" + resultSet.getString("Nome") + "\";\"" + resultSet.getInt("Número identificação") + "");

                csvLines.add("\"Nome\";\"Local\";\"Data\";\"Horainício\"");

                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String local = resultSet.getString("Local");
                    String data = resultSet.getString("Data");
                    String horaInicio = resultSet.getString("HoraInicio");

                    csvLines.add("\"" + nome + "\";\"" + local + "\";\"" + data + "\";\"" + horaInicio + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence CSV: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : csvLines) {
                writer.write(line + "\n");
            }
            System.out.println("CSV file generated successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    public User getUserData(String username) {
        String query = "SELECT * FROM User WHERE username = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String password = resultSet.getString("password");
                    String name = resultSet.getString("name");
                    int studentNumber = resultSet.getInt("studentNumber");
                    int nif = resultSet.getInt("nif");
                    String id = resultSet.getString("id");
                    String address = resultSet.getString("address");

                    return new User(username, password, name, studentNumber, nif, id, address);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user data: " + e.getMessage());
        }

        return null;
    }

    public List<String> getEventPresence(String eventName) {
        List<String> presenceList = new ArrayList<>();

        String query = "SELECT User.name AS Nome, User.studentNumber AS \"Número identificação\"" +
                "FROM UserEvent " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE UserEvent.event_name = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, eventName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                presenceList.add("\"Nome\";\"Número identificação\"");

                while (resultSet.next()) {
                    String name = resultSet.getString("Nome");
                    int studentNumber = resultSet.getInt("Número identificação");

                    presenceList.add("\"" + name + "\";\"" + studentNumber + "");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence for event: " + e.getMessage());
        }

        return presenceList;
    }


    public PresencesList getPresenceForUser(String userName) {
        List<String> presenceList = new ArrayList<>();

        String query = "SELECT Event.nome, Event.Local, Event.Data, Event.HoraInicio, Event.HoraFim " +
                "FROM UserEvent " +
                "JOIN Event ON UserEvent.event_nome = Event.nome " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE User.username = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                presenceList.add("\"Nome\";\"Local\";\"Data\";\"Horainício\";\"Horafim\"");

                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String local = resultSet.getString("Local");
                    String data = resultSet.getString("Data");
                    String horaInicio = resultSet.getString("HoraInicio");
                    String horaFim = resultSet.getString("HoraFim");

                    presenceList.add("\"" + nome + "\";\"" + local + "\";\"" + data + "\";\"" + horaInicio + "\";\"" + horaFim + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence for user: " + e.getMessage());
        }

        PresencesList pl = new PresencesList(presenceList.toString());

        return pl;
    }

    public EventList getAllEvents() {
        List<Event> eventList = new ArrayList<>();

        String query = "SELECT * FROM Event";

        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String local = resultSet.getString("Local");
                String data = resultSet.getString("Data");
                String horaInicio = resultSet.getString("HoraInicio");
                String horaFim = resultSet.getString("HoraFim");

                Event event = new Event(nome, local, data, horaInicio, horaFim);
                eventList.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all events: " + e.getMessage());
        }

        EventList events = new EventList(eventList);

        return events;
    }

    public List<String> getPresenceByEventName(String mail, String eventName) {
        List<String> presenceList = new ArrayList<>();

        String query = "SELECT User.name AS Nome, User.studentNumber AS \"Número identificação\", Event.nome, Event.Local, Event.Data, Event.HoraInicio, Event.HoraFim " +
                "FROM UserEvent " +
                "JOIN Event ON UserEvent.event_name = Event.name " +
                "JOIN User ON UserEvent.user_id = User.id " +
                "WHERE UserEvent.user_id = ? AND Event.nome = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, eventName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                presenceList.add("\"Nome\";\"Número identificação\"");

                while (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String local = resultSet.getString("Local");
                    String data = resultSet.getString("Data");
                    String horaInicio = resultSet.getString("HoraInicio");
                    String horaFim = resultSet.getString("HoraFim");

                    presenceList.add("\"" + nome + "\";\"" + local + "\";\"" + data + "\";\"" + horaInicio + "\";\"" + horaFim + "\"");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting presence by event name: " + e.getMessage());
        }

        return presenceList;
    }

    public Serializable createCode(String eventName, UUID code, Date expirationTime) {
        // Verifica se o evento existe
        String checkEventQuery = "SELECT COUNT(*) FROM Event WHERE nome = ?";
        int eventCount = 0;

        try (PreparedStatement checkEventStatement = con.prepareStatement(checkEventQuery)) {
            checkEventStatement.setString(1, eventName);

            try (ResultSet resultSet = checkEventStatement.executeQuery()) {
                if (resultSet.next()) {
                    eventCount = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            return "Error checking existing event: " + e.getMessage();
        }

        if (eventCount == 0) {
            return "Event does not exist. Cannot create a registration code.";
        }

        String insertCodeQuery = "INSERT INTO CodigoRegisto (codigo, event_nome, hora_termino) VALUES (?, ?, ?)";
        try (PreparedStatement insertCodeStatement = con.prepareStatement(insertCodeQuery)) {
            insertCodeStatement.setString(1, code.toString());
            insertCodeStatement.setString(2, eventName);
            insertCodeStatement.setTimestamp(3, new java.sql.Timestamp(expirationTime.getTime()));
            insertCodeStatement.executeUpdate();
        } catch (SQLException e) {
            return "Error inserting registration code: " + e.getMessage();
        }

        String updateEventCodeQuery = "UPDATE Event SET codigoregistoupdate = ? WHERE nome = ?";
        try (PreparedStatement updateEventCodeStatement = con.prepareStatement(updateEventCodeQuery)) {
            updateEventCodeStatement.setString(1, code.toString());
            updateEventCodeStatement.setString(2, eventName);
            updateEventCodeStatement.executeUpdate();
        } catch (SQLException e) {
            return "Error updating event registration code: " + e.getMessage();
        }

        return true;
    }
}

