package br.com.carlos.model;

import java.util.ArrayList;
import java.util.List;

public class Tabela {

    private String nome;
    private List<Coluna> colunas = new ArrayList<>();

    public Tabela() {
    }

    public Tabela(String nome) {
        this.nome = nome;
    }

    // Nome da tabela
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Lista de colunas
    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }

    // Método utilitário
    public void addColuna(Coluna coluna) {
        this.colunas.add(coluna);
    }

    @Override
    public String toString() {
        return nome;
    }
}

