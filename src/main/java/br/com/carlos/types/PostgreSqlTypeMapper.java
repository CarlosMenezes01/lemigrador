package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class PostgreSqlTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo().toUpperCase();

        return switch (tipo) {
            case "INT", "INTEGER" -> "INTEGER";
            case "BIGINT" -> "BIGINT";
            case "VARCHAR", "VARCHAR2" -> "VARCHAR";
            case "DECIMAL", "NUMERIC" -> "NUMERIC";
            case "DATE" -> "DATE";
            case "DATETIME", "TIMESTAMP" -> "TIMESTAMP";
            default -> tipo;
        };
    }
}
