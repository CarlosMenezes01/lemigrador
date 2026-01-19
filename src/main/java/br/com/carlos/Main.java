package br.com.carlos;

import br.com.carlos.migration.MigrationEngine;
import br.com.carlos.model.Banco;

public class Main {

    public static void main(String[] args) throws Exception {

        // ===== ORIGEM =====
        Banco origem = new Banco();
        origem.setSgbd("postgresql");
        origem.setHost("localhost");
        origem.setPorta("5432");
        origem.setNome("tcc_migracao_auto");
        origem.setUsuario("postgres");
        origem.setSenha("postgres123");

        // ===== DESTINO =====
        Banco destino = new Banco();
        destino.setSgbd("mysql");
        destino.setHost("localhost");
        destino.setPorta("3306");
        destino.setNome("tcc_migracao");
        destino.setUsuario("tcc_user");
        destino.setSenha("tcc123");


        // ===== ENGINE =====
        MigrationEngine engine = new MigrationEngine();
        engine.migrar(origem, destino);
    }
}


