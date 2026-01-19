package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class SqlServerTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo().toUpperCase();

        return switch (tipo) {
            case "INT", "INTEGER" -> "INT";
            case "BIGINT" -> "BIGINT";
            case "VARCHAR", "VARCHAR2" -> "NVARCHAR";
            case "DECIMAL", "NUMERIC" -> "DECIMAL";
            case "DATE", "DATETIME", "TIMESTAMP" -> "DATETIME";
            default -> tipo;
        };
    }
}

