package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class PostgreSqlTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {

        String tipo = coluna.getTipo().toUpperCase();
        Integer tamanho = coluna.getTamanho();

        // ===============================
        // TRATAMENTO DE TIPOS NUMÉRICOS
        // ===============================

        if (tipo.contains("TINYINT")) {
            return "SMALLINT";
        }

        if (tipo.contains("SMALLINT")) {
            return "INTEGER";
        }

        if (tipo.contains("MEDIUMINT")) {
            return "INTEGER";
        }

        if (tipo.contains("INT")) {
            return "INTEGER";
        }

        if (tipo.contains("BIGINT")) {
            return "BIGINT";
        }

        // ===============================
        // TRATAMENTO DE STRINGS
        // ===============================

        if (tipo.contains("VARCHAR")) {

            // PostgreSQL aceita VARCHAR sem tamanho,
            // mas vamos manter o tamanho se existir
            if (tamanho != null && tamanho > 0 && tamanho <= 10485760) {
                return "VARCHAR(" + tamanho + ")";
            }

            // Evita erro de tamanho excessivo
            return "TEXT";
        }

        if (tipo.contains("TEXT")) {
            return "TEXT";
        }

        if (tipo.contains("CHAR")) {
            return "CHAR(" + (tamanho != null ? tamanho : 1) + ")";
        }

        // ===============================
        // DATAS
        // ===============================

        if (tipo.contains("DATE")) {
            return "DATE";
        }

        if (tipo.contains("DATETIME") || tipo.contains("TIMESTAMP")) {
            return "TIMESTAMP";
        }

        // ===============================
        // BOOLEANOS
        // ===============================

        if (tipo.contains("BOOLEAN") || tipo.contains("BIT")) {
            return "BOOLEAN";
        }

        // ===============================
        // FALLBACK (SEGURANÇA)
        // ===============================

        // Caso não reconheça o tipo, usa TEXT
        return "TEXT";
    }
}


