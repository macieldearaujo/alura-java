package br.com.alura.screenmatch.exercises.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe) throws JsonProcessingException;
    <T> List<T> obterListaDados(String json, Class<T> classe) throws JsonProcessingException;
}
