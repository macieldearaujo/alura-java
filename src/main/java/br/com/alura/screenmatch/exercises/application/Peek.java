package br.com.alura.screenmatch.exercises.application;

import java.util.Arrays;
import java.util.List;

public class Peek {
    public static void main(String[] args) {
        List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5);

        int soma = lista.stream()
                .peek(n -> System.out.println("Elemento: " + n))
                .map(n -> n * 2)
                .peek(n -> System.out.println("Conteúdo depois do map:  " + n))
                .reduce(0, (x, y) -> x + y);
        System.out.println("A soma dos números é: " + soma);
    }
}
