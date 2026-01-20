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

        try (Connection connection = ConnectionFactory.getConnection(banco)) {

            DatabaseMetaData metaData = connection.getMetaData();

            // =========================
            // 1. LER TABELAS (PORTÁVEL)
            // =========================
            try (ResultSet tabelasRs = metaData.getTables(
                    connection.getCatalog(), // MySQL: banco | PostgreSQL: ok
                    banco.getSchema(),       // PostgreSQL: public | MySQL: null
                    "%",
                    new String[]{"TABLE"})) {

                while (tabelasRs.next()) {

                    String nomeTabela = tabelasRs.getString("TABLE_NAME");
                    Tabela tabela = new Tabela(nomeTabela);

                    // =========================
                    // 2. LER COLUNAS
                    // =========================
                    try (ResultSet colunasRs = metaData.getColumns(
                            connection.getCatalog(),
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
                    // 3. CHAVE PRIMÁRIA
                    // =========================
                    try (ResultSet pkRs = metaData.getPrimaryKeys(
                            connection.getCatalog(),
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
                    // 4. CHAVES ESTRANGEIRAS
                    // =========================
                    try (ResultSet fkRs = metaData.getImportedKeys(
                            connection.getCatalog(),
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
                    // 5. ADICIONAR AO BANCO
                    // =========================
                    banco.addTabela(tabela);
                }
            }
        }
    }
}


