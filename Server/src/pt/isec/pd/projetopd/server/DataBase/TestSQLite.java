package pt.isec.pd.projetopd.server.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestSQLite {
    public TestSQLite() {
        String url = "jdbc:sqlite:dd.db";
        Connection con;

        try {
            con = DriverManager.getConnection(url);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
        } catch (SQLException e) {
            System.err.println("Erro ao interagir com o banco de dados SQLite: " + e.getMessage());
        }
    }
}

