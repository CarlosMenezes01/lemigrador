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
}
