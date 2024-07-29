package br.com.alura.screenmatch.exercises.principal;

import br.com.alura.screenmatch.exercises.entities.DadosModelo;
import br.com.alura.screenmatch.exercises.service.ConsumoApi;
import br.com.alura.screenmatch.exercises.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();

    public void exibirMenu() {
        System.out.println("**Opcoes:**\nCarros\nMotos\nCaminhoes");
        var veiculoSelecionado = sc.nextLine();

        while(!(veiculoSelecionado.equalsIgnoreCase("carros")
                || veiculoSelecionado.equalsIgnoreCase("motos")
                || veiculoSelecionado.equalsIgnoreCase("caminhoes"))) {
            System.out.println("Erro: opcao inválida, por favor, digite novamente");
            veiculoSelecionado = sc.nextLine();
        }

        String json = consumo.obterDados("https://parallelum.com.br/fipe/api/v1/"
                + veiculoSelecionado + "/marcas");
        System.out.println(json);

        System.out.println("Retorno omitido:");
        ConverteDados conversor = new ConverteDados();
        List<DadosModelo> marcas = conversor.obterListaDados(json, DadosModelo.class);
        System.out.println(marcas);
    }
}
