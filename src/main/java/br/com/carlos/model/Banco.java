package br.com.carlos.model;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    private String host;
    private String nome;
    private String porta;
    private String schema;
    private String senha;
    private String sgbd;
    private String usuario;
    private List<Tabela> tabelas = new ArrayList<>();

    public Banco() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSgbd() {
        return sgbd;
    }

    public void setSgbd(String sgbd) {
        this.sgbd = sgbd;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Tabela> getTabelas() {
        return tabelas;
    }

    public void setTabelas(List<Tabela> tabelas) {
        this.tabelas = tabelas;
    }

    public void addTabela(Tabela tabela) {
        this.tabelas.add(tabela);
    }

    /**
     * Monta a URL JDBC base.
     * Exemplo: mysql://localhost:3306/meubanco
     */
    public String stringConexao() {
        return sgbd + "://" + host + ":" + porta + "/" + nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
