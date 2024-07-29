package br.com.alura.screenmatch.exercises.model;

import br.com.alura.screenmatch.exercises.entities.Modelo;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(
        @JsonAlias("modelos") List<DadosModelo> modelos,
        @JsonAlias("anos") List<DadosModelo> anos
        ) {
}
