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

            // 1. Ler tabelas
            try (ResultSet tabelasRs = metaData.getTables(
                    null,
                    banco.getSchema(),
                    "%",
                    new String[]{"TABLE"})) {

                while (tabelasRs.next()) {
                    String nomeTabela = tabelasRs.getString("TABLE_NAME");
                    Tabela tabela = new Tabela(nomeTabela);

                    // 2. Ler colunas da tabela
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

                    banco.addTabela(tabela);
                }
            }
        }
    }
}
