package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class OracleTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo().toUpperCase();

        return switch (tipo) {
            case "INT", "INTEGER", "BIGINT" -> "NUMBER";
            case "VARCHAR", "VARCHAR2" -> "VARCHAR2";
            case "DECIMAL", "NUMERIC" -> "NUMBER";
            case "DATE", "DATETIME", "TIMESTAMP" -> "TIMESTAMP";
            default -> tipo;
        };
    }
}
