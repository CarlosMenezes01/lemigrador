package br.com.carlos;

import br.com.carlos.dao.MetadataReader;
import br.com.carlos.model.Banco;

public class Main {

    public static void main(String[] args) throws Exception {

        Banco banco = new Banco();
        banco.setSgbd("mysql");
        banco.setHost("localhost");
        banco.setPorta("3306");
        banco.setNome("tcc_migracao");
        banco.setUsuario("tcc_user");
        banco.setSenha("tcc123");

        MetadataReader reader = new MetadataReader();
        reader.lerEstruturaBanco(banco);

        banco.getTabelas().forEach(tabela -> {
            System.out.println("Tabela: " + tabela.getNome());
            tabela.getColunas().forEach(coluna ->
                    System.out.println("  - " + coluna)
            );
        });
    }
}
