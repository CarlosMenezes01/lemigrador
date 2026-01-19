package br.com.carlos.dao;

import br.com.carlos.model.Banco;
import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;
import br.com.carlos.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetadataReader {

    public void lerEstruturaBanco(Banco banco) throws SQLException {

        // Abre conexão (try-with-resources garante fechamento)
        try (Connection connection = ConnectionFactory.getConnection(banco)) {

            DatabaseMetaData metaData = connection.getMetaData();

            // =========================
            // 1. LER TABELAS
            // =========================
            try (ResultSet tabelasRs = metaData.getTables(
                    null,
                    banco.getSchema(),
                    "%",
                    new String[]{"TABLE"})) {

                while (tabelasRs.next()) {

                    String nomeTabela = tabelasRs.getString("TABLE_NAME");
                    Tabela tabela = new Tabela(nomeTabela);

                    // =========================
                    // 2. LER COLUNAS DA TABELA
                    // =========================
                    try (ResultSet colunasRs = metaData.getColumns(
                            null,
                            banco.getSchema(),
                            nomeTabela,
                            "%")) {

                        while (colunasRs.next()) {

                            Coluna coluna = new Coluna();
                            coluna.setNome(colunasRs.getString("COLUMN_NAME"));
                            coluna.setTipo(colunasRs.getString("TYPE_NAME"));
                            coluna.setTamanho(colunasRs.getInt("COLUMN_SIZE"));
                            coluna.setNullable(
                                    colunasRs.getInt("NULLABLE")
                                            == DatabaseMetaData.columnNullable
                            );

                            tabela.addColuna(coluna);
                        }
                    }

                    // =========================
                    // 3. LER CHAVES PRIMÁRIAS
                    // =========================
                    try (ResultSet pkRs = metaData.getPrimaryKeys(
                            null,
                            banco.getSchema(),
                            nomeTabela)) {

                        while (pkRs.next()) {
                            String colunaPk = pkRs.getString("COLUMN_NAME");

                            tabela.getColunas().forEach(coluna -> {
                                if (coluna.getNome().equalsIgnoreCase(colunaPk)) {
                                    coluna.setChavePrimaria(true);
                                }
                            });
                        }
                    }

                    // =========================
                    // 4. LER CHAVES ESTRANGEIRAS
                    // =========================
                    try (ResultSet fkRs = metaData.getImportedKeys(
                            null,
                            banco.getSchema(),
                            nomeTabela)) {

                        while (fkRs.next()) {

                            String colunaFk = fkRs.getString("FKCOLUMN_NAME");
                            String tabelaPk = fkRs.getString("PKTABLE_NAME");
                            String colunaPk = fkRs.getString("PKCOLUMN_NAME");

                            tabela.getColunas().forEach(coluna -> {
                                if (coluna.getNome().equalsIgnoreCase(colunaFk)) {
                                    coluna.setChaveEstrangeira(true);
                                    coluna.setTabelaReferenciada(tabelaPk);
                                    coluna.setColunaReferenciada(colunaPk);
                                }
                            });
                        }
                    }

                    // =========================
                    // 5. ADICIONAR TABELA AO BANCO
                    // =========================
                    banco.addTabela(tabela);
                }
            }
        }
    }
}

