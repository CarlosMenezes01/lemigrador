package br.com.carlos.migration;

import br.com.carlos.model.Banco;
import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataMigrator {

    public void migrarDados(Connection origem, Connection destino, Banco bancoOrigem) {

        try {
            destino.setAutoCommit(false); // inicia transação

            for (Tabela tabela : bancoOrigem.getTabelas()) {
                migrarTabela(tabela, origem, destino);
            }

            destino.commit();
            System.out.println("Migração de dados concluída com sucesso.");

        } catch (Exception e) {
            try {
                destino.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro na migração de dados: " + e.getMessage(), e);
        }
    }

    private void migrarTabela(Tabela tabela, Connection origem, Connection destino) throws Exception {

        String sqlSelect = "SELECT * FROM " + tabela.getNome();

        try (
            Statement stmt = origem.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelect)
        ) {
            int totalColunas = tabela.getColunas().size();

            StringBuilder sqlInsert = new StringBuilder();
            sqlInsert.append("INSERT INTO ")
                     .append(tabela.getNome())
                     .append(" VALUES (");

            for (int i = 0; i < totalColunas; i++) {
                sqlInsert.append("?");
                if (i < totalColunas - 1) sqlInsert.append(", ");
            }
            sqlInsert.append(")");

            PreparedStatement ps = destino.prepareStatement(sqlInsert.toString());

            while (rs.next()) {
                int index = 1;
                for (Coluna coluna : tabela.getColunas()) {
                    ps.setObject(index++, rs.getObject(coluna.getNome()));
                }
                ps.executeUpdate();
            }
        }
    }
}

