package pt.isec.pd.projetopd.server.DataBase;

import java.sql.*;

public class DataBase {
    Connection con;
    public DataBase(String path) {
        String url = path;

        try {
            con = DriverManager.getConnection(url);
            criarTabelas(con);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
            register("admin@isec.pt","123","Admin",0,123,"21312","awdfawdaw",true);
        } catch (SQLException e) {
            System.err.println("Erro ao interagir com o banco de dados SQLite: " + e.getMessage());
        }

    }

    public void criarTabelas(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Cria a tabela "User" se não existir
            statement.execute("CREATE TABLE IF NOT EXISTS User ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "studentNumber INTEGER NOT NULL, " +
                    "nif INTEGER, " +
                    "address TEXT, " +
                    "admin BOOLEAN NOT NULL " +
                    ");");

            // Cria a tabela "Event" se não existir
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

            // Cria a tabela "CodigoRegisto" se não existir
            statement.execute("CREATE TABLE IF NOT EXISTS CodigoRegisto ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "codigo TEXT NOT NULL, " +
                    "event_id INTEGER, " +
                    "FOREIGN KEY (event_id) REFERENCES Event(id) " +
                    ");");

            // Cria a tabela "UserEvent" se não existir
            statement.execute("CREATE TABLE IF NOT EXISTS UserEvent ( " +
                    "id INTEGER PRIMARY KEY, " +
                    "user_id INTEGER, " +
                    "event_id INTEGER, " +
                    "FOREIGN KEY (user_id) REFERENCES User(id), " +
                    "FOREIGN KEY (event_id) REFERENCES Event(id) " +
                    ");");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
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
            System.err.println("Erro ao verificar o status de admin: " + e.getMessage());
        }
        return false;
    }

    public boolean CheckLogin(String user, String pass){
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o login: " + e.getMessage());
            return false;
        }
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
            System.err.println("Erro ao verificar o ID: " + e.getMessage());
            return false;
        }

        if (existingIDCount > 0) {
            System.err.println("ID já existe.");
            return false;
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
            System.err.println("Erro ao registrar o usuário: " + e.getMessage());
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
            System.err.println("Erro ao editar os dados do usuário: " + e.getMessage());
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
            System.err.println("Erro ao remover o usuário: " + e.getMessage());
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
            System.err.println("Erro ao excluir o evento: " + e.getMessage());
            return false;
        }
    }

    public boolean addEvent(String designacao, String local, String data, String horaInicio, String horaFim, int userId) {
        String query = "INSERT INTO Event (Designacao, Local, Data, HoraInicio, HoraFim, user_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, designacao);
            preparedStatement.setString(2, local);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, horaInicio);
            preparedStatement.setString(5, horaFim);
            preparedStatement.setInt(6, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o evento: " + e.getMessage());
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
            System.err.println("Erro ao editar o evento: " + e.getMessage());
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
            System.err.println("Erro ao adicionar o código de registro: " + e.getMessage());
            return false;
        }
    }

}

