package br.com.carlos.types;

public class SqlTypeMapperFactory {

    public static SqlTypeMapper getMapper(String sgbdDestino) {

        if (sgbdDestino == null) {
            throw new IllegalArgumentException("SGBD destino não informado");
        }

        switch (sgbdDestino.toLowerCase()) {
            case "postgresql":
                return new PostgreSqlTypeMapper();
            case "mysql":
                return new MySqlTypeMapper();
            case "mariadb":
                return new MariaDbTypeMapper();
            default:
                throw new UnsupportedOperationException(
                    "SGBD não suportado: " + sgbdDestino
                );
        }
    }
}

