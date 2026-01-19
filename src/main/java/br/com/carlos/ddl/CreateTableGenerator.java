package br.com.carlos.ddl;

import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;
import br.com.carlos.types.SqlTypeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTableGenerator {

    private final SqlTypeMapper typeMapper;

    // 🔴 Construtor obrigatório com TypeMapper
    public CreateTableGenerator(SqlTypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    public String gerarCreateTable(Tabela tabela) {

        StringBuilder sql = new StringBuilder();

        // CREATE TABLE nome_tabela (
        sql.append("CREATE TABLE ")
           .append(tabela.getNome())
           .append(" (\n");

        List<String> definicoes = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();
        List<String> foreignKeys = new ArrayList<>();

        // =========================
        // DEFINIÇÃO DAS COLUNAS
        // =========================
        for (Coluna coluna : tabela.getColunas()) {

            StringBuilder colunaSql = new StringBuilder();
            colunaSql.append("    ")
                     .append(coluna.getNome())
                     .append(" ")
                     .append(typeMapper.mapearTipo(coluna));

            if (!coluna.isNullable()) {
                colunaSql.append(" NOT NULL");
            }

            definicoes.add(colunaSql.toString());

            // PRIMARY KEY
            if (coluna.isChavePrimaria()) {
                primaryKeys.add(coluna.getNome());
            }

            // FOREIGN KEY
            if (coluna.isChaveEstrangeira()) {
                foreignKeys.add(
                    "    FOREIGN KEY (" + coluna.getNome() + ") REFERENCES "
                    + coluna.getTabelaReferenciada()
                    + "(" + coluna.getColunaReferenciada() + ")"
                );
            }
        }

        // =========================
        // PRIMARY KEY
        // =========================
        if (!primaryKeys.isEmpty()) {
            definicoes.add(
                "    PRIMARY KEY (" + String.join(", ", primaryKeys) + ")"
            );
        }

        // =========================
        // FOREIGN KEYS
        // =========================
        definicoes.addAll(foreignKeys);

        // Junta tudo com vírgula
        sql.append(definicoes.stream().collect(Collectors.joining(",\n")));

        // Fecha CREATE TABLE
        sql.append("\n);");

        return sql.toString();
    }
}

