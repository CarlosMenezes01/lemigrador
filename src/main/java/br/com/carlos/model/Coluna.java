package br.com.carlos.model;

public class Coluna {

    private String nome;
    private String tipo;
    private Integer tamanho;
    private boolean nullable;
    private boolean chavePrimaria;

    public Coluna() {
    }

    public Coluna(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    // Nome da coluna (ex: id, nome, email)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Tipo de dado (ex: INT, VARCHAR, DATE)
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Tamanho do campo (ex: VARCHAR(100))
    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    // Pode aceitar NULL?
    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    // É chave primária?
    public boolean isChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(boolean chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    @Override
    public String toString() {
        return nome + " " + tipo;
    }
}
