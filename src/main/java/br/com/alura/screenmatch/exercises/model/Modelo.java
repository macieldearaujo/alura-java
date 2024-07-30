package br.com.alura.screenmatch.exercises.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelo(List<Dados> modelos) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record Veiculo(@JsonAlias("Valor") String valor,
                                 @JsonAlias("Marca") String marca,
                                 @JsonAlias("Modelo") String modelo,
                                 @JsonAlias("AnoModelo") Integer anoModelo,
                                 @JsonAlias("Combustivel") String combustivel) {
        @Override
        public String toString() {
            return String.format("%s - %s - valor: %s, ano: %s, combustivel: %s",
                    modelo, marca, valor, anoModelo, combustivel);
        }
    }
}
