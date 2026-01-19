package br.com.carlos.migration;

import br.com.carlos.ddl.CreateTableGenerator;
import br.com.carlos.model.Banco;
import br.com.carlos.model.Tabela;

import java.sql.Connection;
import java.sql.Statement;

public class SchemaMigrator {

    private final CreateTableGenerator ddlGenerator;

    public SchemaMigrator(CreateTableGenerator ddlGenerator) {
        this.ddlGenerator = ddlGenerator;
    }

    public void migrarSchema(Connection conexaoDestino, Banco bancoOrigem) {

        try (Statement stmt = conexaoDestino.createStatement()) {

            for (Tabela tabela : bancoOrigem.getTabelas()) {

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
}
