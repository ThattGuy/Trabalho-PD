package pt.isec.pd.projetopd.server.DataBase;

import java.sql.*;

public class DataBase {
    Connection con;
    public DataBase() {
        String url = "jdbc:sqlite:database.db";

        try {
            con = DriverManager.getConnection(url);
            criarTabelas(con);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
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

    public boolean CheckLogin(String user, String pass){
        return true;
    }

    public boolean register(String username, String psswd, String name, int studentnumber, int nif, String id, String address, boolean admin){
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

            // Se a inserção for bem-sucedida (uma linha afetada), retorna true
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar o usuário: " + e.getMessage());
            return false;
        }
    }
}

