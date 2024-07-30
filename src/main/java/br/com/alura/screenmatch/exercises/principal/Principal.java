package br.com.alura.screenmatch.exercises.principal;

import br.com.alura.screenmatch.exercises.model.Dados;
import br.com.alura.screenmatch.exercises.model.Modelo;
import br.com.alura.screenmatch.exercises.service.ConsumoApi;
import br.com.alura.screenmatch.exercises.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private String endereco;

    public void exibirMenu() {
        //Primeiro critério - Veículo
        System.out.println("**Opcoes:**\nCarros\nMotos\nCaminhoes");
        var veiculoSelecionado = sc.nextLine();

        while(!(veiculoSelecionado.equalsIgnoreCase("carros")
                || veiculoSelecionado.equalsIgnoreCase("motos")
                || veiculoSelecionado.equalsIgnoreCase("caminhoes"))) {
            System.out.println("Erro: opcao inválida, por favor, digite novamente");
            veiculoSelecionado = sc.nextLine();
        }

        endereco = URL_BASE + veiculoSelecionado + "/marcas/";
        String json = consumo.obterDados(endereco);

        List<Dados> dadosMarca = conversor.obterListaDados(json, Dados.class);
        dadosMarca.stream().sorted(Comparator.comparing(Dados::codigo))
                .map(d -> new Dados(d.codigo(), d.nome()))
                .forEach(System.out::println);

        //Segundo critério - Código da marca
        System.out.println("Informe o código da marca para consulta:");
        var marcaSelecionada = sc.nextLine();

        endereco += marcaSelecionada + "/modelos/";
        json = consumo.obterDados(endereco);
        Modelo dadosModelos = conversor.obterDados(json, Modelo.class);
        System.out.println(dadosModelos);
        dadosModelos.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .map(m -> new Dados(m.codigo(), m.nome()))
                .limit(15).forEach(System.out::println);

        //Terceiro critério - Trecho do modelo
        System.out.println("Digite um trecho do nome do veículo para consulta:");
        var trechoSelecionado = sc.nextLine();

        List<Dados> opcoesVeiculos = dadosModelos.modelos().stream()
                .filter(v -> v.nome().toUpperCase()
                .contains(trechoSelecionado.toUpperCase()))
                .toList();
        opcoesVeiculos.forEach(System.out::println);

        //Quarto critério - Código modelo - Consultar valores
        System.out.println("Digite o código do modelo para consultar valores:");
        var codigoSelecionado = sc.nextLine();

        endereco += codigoSelecionado + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> dadosVeiculosFiltradosAno = conversor.obterListaDados(json, Dados.class);

        List<Modelo.Veiculo> veiculosFiltradosAno = new ArrayList<>();

        for(Dados modelo : dadosVeiculosFiltradosAno) {
            String enderecoCopia = endereco + "/" + modelo.codigo();
            json = consumo.obterDados(enderecoCopia);
            Modelo.Veiculo dados = conversor.obterDados(json, Modelo.Veiculo.class);
            Modelo.Veiculo veiculo = new Modelo.Veiculo(dados.valor(), dados.marca(), dados.modelo(), dados.anoModelo(), dados.combustivel());
            veiculosFiltradosAno.add(veiculo);
        }
        veiculosFiltradosAno.forEach(System.out::println);
    }
}