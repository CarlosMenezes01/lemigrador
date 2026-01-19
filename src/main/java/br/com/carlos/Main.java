package br.com.carlos;

import br.com.carlos.dao.MetadataReader;
import br.com.carlos.ddl.CreateTableGenerator;
import br.com.carlos.model.Banco;
import br.com.carlos.model.Tabela;
import br.com.carlos.types.PostgreSqlTypeMapper;

public class Main {

    public static void main(String[] args) throws Exception {

        // =========================
        // CONFIGURAÇÃO DO BANCO ORIGEM (MySQL)
        // =========================
        Banco banco = new Banco();
        banco.setSgbd("mysql");
        banco.setHost("localhost");
        banco.setPorta("3306");
        banco.setNome("tcc_migracao");
        banco.setUsuario("tcc_user");
        banco.setSenha("tcc123");
        banco.setSchema(null);

        // =========================
        // LEITURA DE METADADOS
        // =========================
        MetadataReader reader = new MetadataReader();
        reader.lerEstruturaBanco(banco);

        // =========================
        // GERADOR DE DDL (PostgreSQL)
        // =========================
        CreateTableGenerator generator =
                new CreateTableGenerator(new PostgreSqlTypeMapper());

        // =========================
        // EXIBIR CREATE TABLE
        // =========================
        for (Tabela tabela : banco.getTabelas()) {
            System.out.println(generator.gerarCreateTable(tabela));
            System.out.println();
        }
    }
}
