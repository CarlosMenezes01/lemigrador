package br.com.carlos.util;

import br.com.carlos.model.Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

    private ConnectionFactory() {
        // Evita instanciação
    }

    public static Connection getConnection(Banco banco) throws SQLException {

        String url = "jdbc:" + banco.stringConexao();

        // Driver é carregado automaticamente (JDBC 4+)
        return DriverManager.getConnection(
                url,
                banco.getUsuario(),
                banco.getSenha()
        );
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Erro ao fechar conexão com o banco", e
                );
            }
        }
    }

    public static void close(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Erro ao fechar PreparedStatement", e
                );
            }
        }
    }

    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Erro ao fechar ResultSet", e
                );
            }
        }
    }
    public static Connection getAdminConnection(
        String sgbd,
        String host,
        String porta,
        String usuario,
        String senha
) {
    try {
        String url;
        String driver;

        if (sgbd.equalsIgnoreCase("postgresql")) {
            driver = "org.postgresql.Driver";
            url = "jdbc:postgresql://" + host + ":" + porta + "/postgres";
        } else if (sgbd.equalsIgnoreCase("mysql") || sgbd.equalsIgnoreCase("mariadb")) {
            driver = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://" + host + ":" + porta + "/";
        } else {
            throw new RuntimeException("SGBD não suportado para conexão administrativa");
        }

        Class.forName(driver);
        return java.sql.DriverManager.getConnection(url, usuario, senha);

    } catch (Exception e) {
        throw new RuntimeException("Erro ao obter conexão administrativa: " + e.getMessage(), e);
    }
}

}
