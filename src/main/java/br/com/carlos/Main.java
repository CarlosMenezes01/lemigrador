package br.com.carlos;

import br.com.carlos.admin.DatabaseCreator;
import br.com.carlos.dao.MetadataReader;
import br.com.carlos.ddl.CreateTableGenerator;
import br.com.carlos.migration.DataMigrator;
import br.com.carlos.migration.SchemaMigrator;
import br.com.carlos.model.Banco;
import br.com.carlos.types.PostgreSqlTypeMapper;
import br.com.carlos.util.ConnectionFactory;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) throws Exception {

        // =========================
        // BANCO ORIGEM (MySQL)
        // =========================
        Banco bancoOrigem = new Banco();
        bancoOrigem.setSgbd("mysql");
        bancoOrigem.setHost("localhost");
        bancoOrigem.setPorta("3306");
        bancoOrigem.setNome("tcc_migracao");
        bancoOrigem.setUsuario("tcc_user");
        bancoOrigem.setSenha("tcc123");

        // Lê estrutura do banco origem
        MetadataReader reader = new MetadataReader();
        reader.lerEstruturaBanco(bancoOrigem);

        // Conexão com banco origem
        Connection connOrigem = ConnectionFactory.getConnection(bancoOrigem);

        // =========================
        // BANCO DESTINO (PostgreSQL)
        // =========================
        String sgbdDestino = "postgresql";
        String hostDestino = "localhost";
        String portaDestino = "5432";
        String usuarioDestino = "postgres";
        String senhaDestino = "postgres123";
        String nomeBancoDestino = "tcc_migracao_auto";

        // Criação automática do banco (Sprint 6)
        try {
            Connection adminConn = ConnectionFactory.getAdminConnection(
                    sgbdDestino,
                    hostDestino,
                    portaDestino,
                    usuarioDestino,
                    senhaDestino
            );
            DatabaseCreator.criarBanco(adminConn, nomeBancoDestino);
        } catch (Exception ignored) {
            // Banco já existe
        }

        // Conexão com banco destino
        Banco bancoDestino = new Banco();
        bancoDestino.setSgbd(sgbdDestino);
        bancoDestino.setHost(hostDestino);
        bancoDestino.setPorta(portaDestino);
        bancoDestino.setNome(nomeBancoDestino);
        bancoDestino.setUsuario(usuarioDestino);
        bancoDestino.setSenha(senhaDestino);

        Connection connDestino = ConnectionFactory.getConnection(bancoDestino);

        // =========================
        // MIGRAÇÃO DO ESQUEMA (Sprint 7)
        // =========================
        CreateTableGenerator generator =
                new CreateTableGenerator(new PostgreSqlTypeMapper());

        SchemaMigrator schemaMigrator = new SchemaMigrator(generator);
        schemaMigrator.migrarSchema(connDestino, bancoOrigem);

        // =========================
        // MIGRAÇÃO DOS DADOS (Sprint 8)
        // =========================
        DataMigrator dataMigrator = new DataMigrator();
        dataMigrator.migrarDados(connOrigem, connDestino, bancoOrigem);

        System.out.println("Migração completa (esquema + dados) finalizada com sucesso.");
    }
}

