package pt.isec.projetopd.serverbackup;

import pt.isec.pd.projetopd.communication.classes.User;

import java.io.Serializable;
import java.sql.*;

public class UpdateDatabase {
    Connection con;
    public UpdateDatabase(String path) {
        String url = "jdbc:sqlite:" + path ;

        try {
            con = DriverManager.getConnection(url);
            System.out.println("Connection to the SQLite database established.");
        } catch (SQLException e) {
            System.err.println("Error interacting with SQLite database:" + e.getMessage());
        }

    }

    public void update(Serializable clientInfo) {
        //TODO update database
        //if(clientInfo instanceof User)
            //register((clientInfo.getUsername(), clientInfo.getPassword(), clientInfo.getName(), clientInfo.getStudentNumber(), clientInfo.getNIF(), clientInfo.getId(), clientInfo.getAddress(), false);
    }

    /*
    private boolean register(String username, String psswd, String name, int studentnumber, int nif, String id, String address, boolean admin){

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

     */


}
