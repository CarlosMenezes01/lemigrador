package br.com.carlos;

import br.com.carlos.migration.MigrationEngine;
import br.com.carlos.model.Banco;

public class Main {

    public static void main(String[] args) throws Exception {

        
        // ===== ORIGEM =====
        Banco origem = new Banco();
        origem.setSgbd("mysql");
        origem.setHost("localhost");
        origem.setPorta("3306");
        origem.setNome("bd2024");
        origem.setUsuario("tcc_user");
        origem.setSenha("tcc123");

       
        // ===== DESTINO =====
        Banco destino = new Banco();
        destino.setSgbd("postgresql");
        destino.setHost("localhost");
        destino.setPorta("5432");
        destino.setNome("bd2024");
        destino.setUsuario("postgres");
        destino.setSenha("postgres123");

    
        // ===== ENGINE =====
        MigrationEngine engine = new MigrationEngine();
        engine.migrar(origem, destino);
    }
}


