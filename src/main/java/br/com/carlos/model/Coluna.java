package br.com.carlos.model;

public class Coluna {

    private String nome;
    private String tipo;
    private Integer tamanho;
    private boolean nullable;
    private boolean chavePrimaria;

    // Foreign Key
    private boolean chaveEstrangeira;
    private String tabelaReferenciada;
    private String colunaReferenciada;

    public Coluna() {
    }

    public Coluna(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(boolean chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
    }

    public boolean isChaveEstrangeira() {
        return chaveEstrangeira;
    }

    public void setChaveEstrangeira(boolean chaveEstrangeira) {
        this.chaveEstrangeira = chaveEstrangeira;
    }

    public String getTabelaReferenciada() {
        return tabelaReferenciada;
    }

    public void setTabelaReferenciada(String tabelaReferenciada) {
        this.tabelaReferenciada = tabelaReferenciada;
    }

    public String getColunaReferenciada() {
        return colunaReferenciada;
    }

    public void setColunaReferenciada(String colunaReferenciada) {
        this.colunaReferenciada = colunaReferenciada;
    }

    @Override
    public String toString() {
        if (chaveEstrangeira) {
            return nome + " " + tipo +
                " (FK -> " + tabelaReferenciada + "." + colunaReferenciada + ")";
        }
        if (chavePrimaria) {
            return nome + " " + tipo + " (PK)";
        }
        return nome + " " + tipo;
    }

}
