package br.com.carlos.migration;

import br.com.carlos.model.Banco;
import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataMigrator {

    public void migrarDados(
            Connection origem,
            Connection destino,
            Banco bancoOrigem) {

        try {
            for (Tabela tabela : bancoOrigem.getTabelas()) {
                migrarTabela(origem, destino, tabela);
            }
        } catch (Exception e) {
            throw new RuntimeException(
                "Erro na migração de dados: " + e.getMessage(), e
            );
        }
    }

    private void migrarTabela(
            Connection origem,
            Connection destino,
            Tabela tabela) throws Exception {

        String selectSQL = "SELECT * FROM " + tabela.getNome();

        try (
            Statement stmtOrigem = origem.createStatement();
            ResultSet rs = stmtOrigem.executeQuery(selectSQL)
        ) {

            while (rs.next()) {

                if (registroExiste(destino, tabela, rs)) {
                    continue; // ignora duplicado
                }

                String insertSQL = gerarInsert(tabela);
                try (PreparedStatement ps = destino.prepareStatement(insertSQL)) {

                    int idx = 1;
                    for (Coluna col : tabela.getColunas()) {
                        ps.setObject(idx++, rs.getObject(col.getNome()));
                    }

                    ps.executeUpdate();
                }
            }
        }
    }

    private boolean registroExiste(
            Connection destino,
            Tabela tabela,
            ResultSet rsOrigem) throws Exception {

        Coluna pk = tabela.getColunas().stream()
                .filter(Coluna::isChavePrimaria)
                .findFirst()
                .orElse(null);

        if (pk == null) return false;

        String sql =
            "SELECT 1 FROM " + tabela.getNome() +
            " WHERE " + pk.getNome() + " = ?";

        try (PreparedStatement ps = destino.prepareStatement(sql)) {
            ps.setObject(1, rsOrigem.getObject(pk.getNome()));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private String gerarInsert(Tabela tabela) {

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
          .append(tabela.getNome())
          .append(" (");

        for (int i = 0; i < tabela.getColunas().size(); i++) {
            sb.append(tabela.getColunas().get(i).getNome());
            if (i < tabela.getColunas().size() - 1) sb.append(", ");
        }

        sb.append(") VALUES (");

        for (int i = 0; i < tabela.getColunas().size(); i++) {
            sb.append("?");
            if (i < tabela.getColunas().size() - 1) sb.append(", ");
        }

        sb.append(")");

        return sb.toString();
    }
}


