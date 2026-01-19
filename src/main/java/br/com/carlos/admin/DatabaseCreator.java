package br.com.carlos.admin;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseCreator {

    public static void criarBanco(Connection conexaoAdmin, String nomeBanco) {

        String sql = "CREATE DATABASE " + nomeBanco;

        try (Statement stmt = conexaoAdmin.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados '" + nomeBanco + "' criado com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao criar banco de dados '" + nomeBanco + "': " + e.getMessage(), e
            );
        }
    }
}
