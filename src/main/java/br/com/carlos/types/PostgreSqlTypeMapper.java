package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class PostgreSqlTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo().toUpperCase();

        switch (tipo) {
            case "INT":
            case "INTEGER":
                return "INTEGER";

            case "VARCHAR":
                return "VARCHAR(" + coluna.getTamanho() + ")";

            case "DECIMAL":
                return "DECIMAL";

            case "DATE":
                return "DATE";

            case "DATETIME":
                return "TIMESTAMP";

            default:
                return tipo;
        }
    }
}

