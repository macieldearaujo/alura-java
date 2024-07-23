package br.com.alura.screenmatch.exercises.application;

import br.com.alura.screenmatch.exercises.entities.Caixa;

public class TesteCaixa {
    public static void main(String[] args) {
        Caixa<String> caixaTexto = new Caixa<>();
        caixaTexto.setConteudo("Guardando texto na minha caixa!");
        System.out.println(caixaTexto.somaConteudoCaixa("Mais uma linha"));

        Caixa<Integer> caixaIdade = new Caixa<>();
        caixaIdade.setConteudo(30);
        System.out.println(caixaIdade.somaConteudoCaixa(26));

        Caixa<Double> caixaValor = new Caixa<>();
        caixaValor.setConteudo(150.50);
        System.out.println(caixaValor.somaConteudoCaixa(150.50));
    }
}
