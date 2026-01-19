package br.com.carlos.migration;

import br.com.carlos.admin.DatabaseCreator;
import br.com.carlos.dao.MetadataReader;
import br.com.carlos.ddl.CreateTableGenerator;
import br.com.carlos.model.Banco;
import br.com.carlos.types.SqlTypeMapper;
import br.com.carlos.types.SqlTypeMapperFactory;
import br.com.carlos.util.ConnectionFactory;

import java.sql.Connection;

public class MigrationEngine {

    public void migrar(Banco origem, Banco destino) throws Exception {

        // 1) Lê metadados da origem
        MetadataReader reader = new MetadataReader();
        reader.lerEstruturaBanco(origem);

        // 2) Cria banco de destino (admin)
        try {
            Connection adminConn = ConnectionFactory.getAdminConnection(
                destino.getSgbd(),
                destino.getHost(),
                destino.getPorta(),
                destino.getUsuario(),
                destino.getSenha()
            );
            DatabaseCreator.criarBanco(adminConn, destino.getNome());
        } catch (Exception ignored) {
            // Banco já existe
        }

        // 3) Conexões
        Connection connOrigem = ConnectionFactory.getConnection(origem);
        Connection connDestino = ConnectionFactory.getConnection(destino);

        // 4) Mapper dinâmico conforme DESTINO
        SqlTypeMapper mapper =
            SqlTypeMapperFactory.getMapper(destino.getSgbd());

        // 5) Migração do esquema
        CreateTableGenerator ddlGenerator =
            new CreateTableGenerator(mapper);

        SchemaMigrator schemaMigrator =
            new SchemaMigrator(ddlGenerator);

        schemaMigrator.migrarSchema(connDestino, origem);

        // 6) Migração dos dados
        DataMigrator dataMigrator = new DataMigrator();
        dataMigrator.migrarDados(connOrigem, connDestino, origem);

        System.out.println("Migração bidirecional concluída com sucesso.");
    }
}
