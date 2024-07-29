package br.com.alura.screenmatch.exercises.entities;

import java.util.List;

public class Modelo {
    private List<Veiculo> modelos;
    private List<Veiculo> anos;

    public Modelo(List<Veiculo> modelos, List<Veiculo> anos) {
        this.modelos = modelos;
        this.anos = anos;
    }

    public List<Veiculo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Veiculo> modelos) {
        this.modelos = modelos;
    }

    public List<Veiculo> getAnos() {
        return anos;
    }

    public void setAnos(List<Veiculo> anos) {
        this.anos = anos;
    }
}
