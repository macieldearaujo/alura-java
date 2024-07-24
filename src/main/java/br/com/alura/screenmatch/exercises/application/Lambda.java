package br.com.alura.screenmatch.exercises.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lambda {
    public static void main(String[] args) {
        List<String> lista = Arrays.asList("Paula", "Carlos", "Jorge", "Douglas", "Thomas");
        lista.stream()
                .sorted()
                .limit(3)
                .filter(n -> n.startsWith("D"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
