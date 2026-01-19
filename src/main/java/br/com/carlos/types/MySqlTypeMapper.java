package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class MySqlTypeMapper implements SqlTypeMapper {

    private static final int MAX_VARCHAR = 255;

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo() != null
                ? coluna.getTipo().toUpperCase()
                : "";

        Integer tamanho = coluna.getTamanho();

        // 🔴 REGRA 1 — CHAVE PRIMÁRIA SEMPRE NUMÉRICA
        if (coluna.isChavePrimaria()) {
            return "INT";
        }

        switch (tipo) {

            case "INT":
            case "INTEGER":
            case "BIGINT":
                return "INT";

            case "VARCHAR":
                if (tamanho == null || tamanho <= 0 || tamanho > MAX_VARCHAR) {
                    return "VARCHAR(" + MAX_VARCHAR + ")";
                }
                return "VARCHAR(" + tamanho + ")";

            case "TEXT":
            case "LONGTEXT":
                return "TEXT";

            case "DECIMAL":
            case "NUMERIC":
                return "DECIMAL";

            case "DATE":
                return "DATE";

            case "TIMESTAMP":
            case "DATETIME":
                return "DATETIME";

            default:
                // 🔴 REGRA 2 — fallback seguro (NUNCA PK)
                return "VARCHAR(" + MAX_VARCHAR + ")";
        }
    }
}


