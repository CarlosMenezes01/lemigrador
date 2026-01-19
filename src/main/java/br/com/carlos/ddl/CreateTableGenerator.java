package br.com.carlos.ddl;

import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTableGenerator {

    public String gerarCreateTable(Tabela tabela) {

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ").append(tabela.getNome()).append(" (\n");

        List<String> definicoes = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();
        List<String> foreignKeys = new ArrayList<>();

        for (Coluna coluna : tabela.getColunas()) {

            StringBuilder colDef = new StringBuilder();
            colDef.append("    ")
                  .append(coluna.getNome())
                  .append(" ")
                  .append(coluna.getTipo());

            if (!coluna.isNullable()) {
                colDef.append(" NOT NULL");
            }

            definicoes.add(colDef.toString());

            if (coluna.isChavePrimaria()) {
                primaryKeys.add(coluna.getNome());
            }

            if (coluna.isChaveEstrangeira()) {
                foreignKeys.add(
                    "    FOREIGN KEY (" + coluna.getNome() + ") REFERENCES "
                    + coluna.getTabelaReferenciada()
                    + "(" + coluna.getColunaReferenciada() + ")"
                );
            }
        }

        if (!primaryKeys.isEmpty()) {
            definicoes.add(
                "    PRIMARY KEY (" + String.join(", ", primaryKeys) + ")"
            );
        }

        definicoes.addAll(foreignKeys);

        sql.append(
            definicoes.stream().collect(Collectors.joining(",\n"))
        );

        sql.append("\n);");

        return sql.toString();
    }
}

