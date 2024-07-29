package br.com.alura.screenmatch.exercises.principal;

import br.com.alura.screenmatch.exercises.entities.Marca;
import br.com.alura.screenmatch.exercises.model.DadosModelo;
import br.com.alura.screenmatch.exercises.service.ConsumoApi;
import br.com.alura.screenmatch.exercises.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private String URL_PADRAO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu() {
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

        System.out.println("Retorno omitido:");
        ConverteDados conversor = new ConverteDados();
        List<DadosModelo> dadosMarca = conversor.obterListaDados(json, DadosModelo.class);
        List<Marca> marcas  = dadosMarca.stream().sorted(Comparator.comparingInt(DadosModelo::codigo))
                .map(d -> new Marca(d.codigo(), d.descricao())).toList();
        marcas.forEach(System.out::println);
//
//        System.out.println("Informe o código da marca para consulta:");
//        var modeloSelecionado = sc.nextLine();
//        json = consumo.obterDados(URL_PADRAO
//                + veiculoSelecionado + "/marcas/" + modeloSelecionado + "/modelos");
//        List<DadosModelo> modelos = conversor.obterListaDados(json, DadosModelo.class);
//        System.out.println(modelos);
//
//        System.out.println("Digite um trecho do nome do veículo para consulta:");

    }
}
