package br.com.carlos.migration;

import br.com.carlos.ddl.CreateTableGenerator;
import br.com.carlos.model.Banco;
import br.com.carlos.model.Tabela;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SchemaMigrator {

    private final CreateTableGenerator ddlGenerator;

    public SchemaMigrator(CreateTableGenerator ddlGenerator) {
        this.ddlGenerator = ddlGenerator;
    }

    public void migrarSchema(Connection conexaoDestino, Banco bancoOrigem) {

        try (Statement stmt = conexaoDestino.createStatement()) {

            for (Tabela tabela : bancoOrigem.getTabelas()) {

                if (tabelaExiste(conexaoDestino, tabela.getNome())) {
                    System.out.println(
                        "Tabela '" + tabela.getNome() + "' já existe. Pulando criação."
                    );
                    continue;
                }

                String sql = ddlGenerator.gerarCreateTable(tabela);
                stmt.execute(sql);

                System.out.println(
                    "Tabela '" + tabela.getNome() + "' criada com sucesso."
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "Erro ao migrar esquema: " + e.getMessage(), e
            );
        }
    }

    private boolean tabelaExiste(Connection conn, String nomeTabela)
            throws Exception {

        String sql =
            "SELECT 1 FROM information_schema.tables " +
            "WHERE table_name = '" + nomeTabela + "'";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next();
        }
    }
}

