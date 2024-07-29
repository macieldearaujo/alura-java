package br.com.alura.screenmatch.exercises.principal;

import br.com.alura.screenmatch.exercises.entities.Modelo;
import br.com.alura.screenmatch.exercises.entities.Veiculo;
import br.com.alura.screenmatch.exercises.model.DadosModelo;
import br.com.alura.screenmatch.exercises.model.DadosVeiculo;
import br.com.alura.screenmatch.exercises.service.ConsumoApi;
import br.com.alura.screenmatch.exercises.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private final String URL_PADRAO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu() {
        //Primeiro critério - Veículos
        System.out.println("**Opcoes:**\nCarros\nMotos\nCaminhoes");
        var veiculoSelecionado = sc.nextLine();

        while(!(veiculoSelecionado.equalsIgnoreCase("carros")
                || veiculoSelecionado.equalsIgnoreCase("motos")
                || veiculoSelecionado.equalsIgnoreCase("caminhoes"))) {
            System.out.println("Erro: opcao inválida, por favor, digite novamente");
            veiculoSelecionado = sc.nextLine();
        }

        String json = consumo.obterDados(URL_PADRAO
                + veiculoSelecionado + "/marcas/");
        ConverteDados conversor = new ConverteDados();
        List<DadosModelo> dadosMarca = conversor.obterListaDados(json, DadosModelo.class);
        List<Veiculo> opcoesMarcas  = dadosMarca.stream().sorted(Comparator.comparing(DadosModelo::codigo))
                .map(d -> new Veiculo(d.codigo(), d.descricao())).toList();
        opcoesMarcas.forEach(System.out::println);

        //Segundo critério - Marcas
        System.out.println("Informe o código da marca para consulta:");
        var marcaSelecionada = sc.nextLine();
        json = consumo.obterDados(URL_PADRAO + veiculoSelecionado + "/marcas/" + marcaSelecionada + "/modelos/");
        DadosVeiculo opcoesModelos = conversor.obterDados(json, DadosVeiculo.class);

        List<Veiculo> modelos = opcoesModelos.modelos().stream()
                .sorted(Comparator.comparing(DadosModelo::codigo))
                .map(m -> new Veiculo(m.codigo(), m.descricao()))
                .toList();
        modelos.forEach(System.out::println);

        System.out.println("Digite um trecho do nome do veículo para consulta:");
        var trechoSelecionado = sc.nextLine();

        List<Veiculo> opcoesVeiculos = modelos.stream()
                .filter(v -> v.getDescricao().toUpperCase()
                .contains(trechoSelecionado.toUpperCase()))
                .toList();
        opcoesVeiculos.stream().limit(10).forEach(System.out::println);

//        List<Veiculo> anos = opcoesModelos.anos().stream()
//                .sorted(Comparator.comparing(DadosModelo::codigo))
//                .map(m -> new Veiculo(m.codigo(), m.descricao()))
//                .toList();
////        anos.forEach(System.out::println);

//        System.out.println("Digite um trecho do nome do veículo para consulta:");

    }
}
